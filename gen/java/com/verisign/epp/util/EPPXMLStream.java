/***********************************************************
Copyright (C) 2004 VeriSign, Inc.

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.Ê See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MAÊ 02111-1307Ê USA

http://www.verisign.com/nds/naming/namestore/techdocs.html
***********************************************************/
package com.verisign.epp.util;


// PoolMan Imports
import com.codestudio.util.*;

// Log4j Imports
import org.apache.log4j.Logger;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;

import org.w3c.dom.Document;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

// JDK Imports
import java.io.*;

// JAXP, SAX, DOM, and Xerces Imports
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

// EPP SDK Imports
import com.verisign.epp.exception.EPPException;
import com.verisign.epp.framework.EPPAssemblerException;
import com.verisign.epp.util.EPPCatFactory;


/**
 * <code>EPPXMLStream</code> is a utility class for reading and writing EPP
 * messages to/from streams.  DOM Document are read and written to the
 * streams.  An XML parser is required when reading from the stream.  There is
 * one constructor that will create an XML parser per call to
 * <code>read(InputStream)</code> and one that will use a parser pool.  Use of
 * a parser pool is recommended.
 */
public class EPPXMLStream {
	
	/**
	 * Default Maximum packet size of bytes accepted to ensure that the client
	 * is not overrun with an invalid packet or a packet that exceeds the 
	 * maximum size.  This setting could be made configurable in the future.
	 */ 
	public static final int DEFAULT_MAX_PACKET_SIZE = 355000;
	
	/**
	 * Maximum packet size of bytes accepted to ensure that the client
	 * is not overrun with an invalid packet or a packet that exceeds the 
	 * maximum size.  This setting defaults to {@link DEFAULT_MAX_PACKET_SIZE} 
	 * and can be overridden with the &quot;EPP.MaxPacketSize&quot; 
	 * configuration property.
	 */ 
	private static int maxPacketSize;
	
	/** Document Builder Factory for creating a parser per operation. */
	private static DocumentBuilderFactory factory = null;

	/** Log4j category for logging */
	private static Logger cat =
		Logger.getLogger(
						 EPPXMLStream.class.getName(),
						 EPPCatFactory.getInstance().getFactory());
	
	private static Logger packetCat =
		Logger.getLogger(
						 EPPXMLStream.class.getName() + ".packet",
						 EPPCatFactory.getInstance().getFactory());
	
	
	static {
		// Initialize the Document Builder Factory
		factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		factory.setValidating(true);
		

		maxPacketSize = DEFAULT_MAX_PACKET_SIZE;
		
		String maxPacketSizeProp = Environment.getOption("EPP.MaxPacketSize");
		if (maxPacketSizeProp != null) {
			try {
				maxPacketSize = Integer.parseInt(maxPacketSizeProp);
			}
			catch (NumberFormatException ex) {
				System.err.println("EPPXMLStream: EPP.MaxPacketSize property format error: " + ex);
			}			
		}
		
		cat.info("maxPacketSize = " + maxPacketSize);
	}



	/**
	 * Pool Manager that could contain the EPP XML Parser Pool
	 * (<code>poolName</code>).  If this is <code>null</code> there will be
	 * one XML parser created per call to <code>read</code>.
	 */
	private GenericPoolManager manager = null;

	/** Name of XML Parser Pool contained in <code>manager</code>. */
	private String poolName = null;

	/**
	 * Default constructor for <code>EPPXMLStream</code>.  When using this
	 * constructor, a parser instance will be created on each call to
	 * <code>read(InputStream)</code>.
	 */
	public EPPXMLStream() {
		this.manager	  = null;
		this.poolName     = null;
	}

	/**
	 * Construct <code>EPPXMLStream</code> to use a parser pool. The
	 * <code>aPoolMgr</code> parameter has to be a pool of
	 * <code>EPPParserPool</code> subclasses.  When using this constructor, a
	 * parser instance will be checked out and checkin as needed on each call
	 * to <code>read(InputStream)</code>.
	 *
	 * @param aPoolName Pool name to use
	 */
	public EPPXMLStream(String aPoolName) {
		this.manager	  = GenericPoolManager.getInstance();
		this.poolName     = aPoolName;
	}

	/**
	 * Reads an EPP packet from the stream based on a search for the End Of
	 * Message (EOM) string (&lt;/epp&gt;).
	 *
	 * @param aStream Stream to read packet from
	 *
	 * @return EPP packet <code>String</code>
	 *
	 * @exception EPPException Error reading packet from stream.  The stream
	 * 			  should be closed.
	 * @exception InterruptedIOException Time out reading for packet
	 * @exception IOException Exception from the input stream
	 */
	private String readPacket(InputStream aStream)
					   throws EPPException, InterruptedIOException, IOException {
		cat.debug("readPacket(): enter");

		// Validate argument
		if (aStream == null) {
			cat.error("readPacket() : null stream passed");
			throw new EPPException("EPPXMLStream.readPacket() : null stream passed");
		}

		// Read network header (32 bits) that defines the total length
		// of the EPP data unit measured in octets in network (big endian)
		// byte order.
		DataInputStream theStream     = new DataInputStream(aStream);
		int			    thePacketSize = -1;
		byte[]		    thePacket     = null;

		try {
			// Read the packet size which includes network header itself.
			thePacketSize = theStream.readInt();
			
			if (thePacketSize > maxPacketSize) {
				cat.error("readPacket(InputStream): Packet header specifies a packet larger that the maximum of " + maxPacketSize + " bytes");
				throw new EPPException("EPPXMLStream.readPacket() : Packet header exceeds the maximum of " + maxPacketSize + " bytes");
			}
			
			cat.debug("readPacket(): Received network header with value = "
					  + thePacketSize);

			thePacket = new byte[thePacketSize - 4];

			// Read the packet
			theStream.readFully(thePacket);
		}
		 catch (EOFException ex) {
			cat.error("readPacket(InputStream): EOFException while attempting to read packet, size = "
					  + thePacketSize + ", packet = [" + thePacket + "]: " + ex);
			throw ex;
		}
		 catch (InterruptedIOException ex) {
			cat.debug("readPacket(InputStream): InterruptedIOException while attempting to read packet: " + ex);
			throw ex;
		}
		 catch (IOException ex) {
			cat.error("readPacket(InputStream): IOException while attempting to read packet, size = "
					  + thePacketSize + ", packet = [" + thePacket + "]: " + ex);
			throw ex;
		}

		String thePacketStr = new String(thePacket);

		cat.debug("readPacket(): Received packet [" + thePacketStr + "]");
		cat.debug("readPacket(): exit");

		return thePacketStr;
	}

	// End EPPXMLStream.readPacket(InputStream)

	/**
	 * Reads an EPP packet from the <code>aStream</code> parameter,
	 * parses/validates it, and returns the associated DOM Document.  The XML
	 * parser is either created per call, or is retrieved from a parser pool
	 * when <code>EPPXMLStream(GenericPoolManager)</code> is used.  Use of a
	 * parser pool is recommended.
	 *
	 * @param aStream Input stream to read for an EPP packet.
	 *
	 * @return Parsed DOM Document of packet 
	 *
	 * @exception EPPException Error with received packet or end of stream.  It
	 * 			  is recommended that the stream be closed.
	 * @exception EPPAssemblerException Error parsing packet
	 * @exception IOException Error reading packet from stream
	 */
	public Document read(InputStream aStream) throws EPPAssemblerException, EPPException, IOException {
		cat.debug("read(InputStream): enter");

		// Validate argument
		if (aStream == null) {
			throw new EPPException("EPPXMLStream.read() : BAD ARGUMENT (aStream)");
		}

		DocumentBuilder theBuilder = null;
		Document	    theDoc = null;

		// Parser pool specified?
		if (manager != null) {
			theBuilder = (DocumentBuilder) manager.requestObject(poolName);
	        theBuilder.setErrorHandler(new EPPXMLErrorHandler());
		}
		else {
			// Create new parser instance.
			theBuilder = new EPPSchemaCachingParser();
	        theBuilder.setErrorHandler(new EPPXMLErrorHandler());
		}

		try {
			String thePacket = null;

			try {
				// Read EPP packet from stream
				thePacket = readPacket(aStream);

				packetCat.debug("read() : Received [" + thePacket + "]");

				// Parse/validate EPP Packet and create DOM document
				theDoc =
					theBuilder.parse(new ByteArrayInputStream(thePacket
															  .getBytes()));
			}
			catch (SAXParseException ex) {

				// Error generated by parser
				cat.error(thePacket
							+  "\nline      " + ex.getLineNumber ()
	                        + "\ncolumn    " + ex.getColumnNumber()
							+ "\nuri       " + ex.getSystemId ()
							+ "\nMessage : " + ex.getMessage(), ex);

	            throw new EPPAssemblerException("[SAXParseException]"
	                    +  "\nline      " + ex.getLineNumber()
	                    + "\ncolumn    " + ex.getColumnNumber()
	                    + "\nuri       " + ex.getSystemId ()
	                    + "\nMessage : " + ex.getMessage(),
	                    EPPAssemblerException.XML);

			}
			catch (SAXException ex) {

				// Error generated by this application
				cat.error("NamestoreBaseAssembler.parse() : [SAXException]" +
						thePacket, ex);

	            throw new EPPAssemblerException("[SAXException] " + ex,
	                                            EPPAssemblerException.XML);
			}
		}
		 finally {
			// Check in pool object
			if (manager != null) {
				manager.returnObject(theBuilder);
			}
		}

		cat.debug("read(InputStream): exit");

		return theDoc;
	}

	// End EPPXMLStream.read(InputStream)

	/**
	 * Writes a DOM Document to the output stream.  The DOM Document will be
	 * serialized to XML and written to the output stream.
	 *
	 * @param aDoc DOM Document to write to stream
	 * @param aOutput Output stream to write to
	 *
	 * @exception EPPException Error writing to stream.  It is recommended that
	 * 			  the stream be closed.
	 */
	public void write(Document aDoc, OutputStream aOutput)
			   throws EPPException {
		cat.debug("write(Document, InputStream): enter");

		// Validate arguments
		if (aOutput == null) {
			cat.error("write(Document, InputStream): aOutput == null");
			throw new EPPException("EPPXMLStream.write() : BAD ARGUMENT (aOutput)");
		}

		if (aDoc == null) {
			cat.error("write(Document, InputStream): aDoc == null");
			throw new EPPException("EPPXMLStream.write() : BAD ARGUMENT (aDoc)");
		}

		ByteArrayOutputStream theBuffer = new ByteArrayOutputStream();

		// Serialize DOM Document to stream
		try {
			XMLSerializer theSerializer =
				new XMLSerializer(theBuffer, (OutputFormat) null);

			theSerializer.serialize(aDoc);

			theBuffer.close();
		}
		 catch (IOException ex) {
			cat.error(
					  "write(Document, InputStream) : serialize() :"
					  + ex.getMessage(), ex);
			throw new EPPException("EPPXMLStream.write : serialize() "
								   + ex.getMessage());
		}

		packetCat.debug("write() : Sending [" + theBuffer + "]");

		// Write to stream
		try {
			byte[]			 thePacket = theBuffer.toByteArray();
			DataOutputStream theStream = new DataOutputStream(aOutput);
			theStream.writeInt(thePacket.length + 4);
			aOutput.write(thePacket);
			aOutput.flush();
		}
		 catch (IOException ex) {
			cat.error("write(Document, InputStream) : Writing to stream :" + ex);
			throw new EPPException("EPPXMLStream.write() : Writing to stream "
								   + ex);
		}

		cat.debug("write(Document, InputStream): exit");
	}

	// End EPPXMLStream.write(Document, OutputStream)
}


// End class EPPXMLStream
