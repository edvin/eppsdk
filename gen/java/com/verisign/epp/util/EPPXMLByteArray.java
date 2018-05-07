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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.codestudio.util.GenericPoolManager;
import com.verisign.epp.exception.EPPException;
import com.verisign.epp.framework.EPPAssemblerException;
import com.verisign.epp.util.EPPCatFactory;
import com.verisign.epp.util.EPPSchemaCachingParser;
import com.verisign.epp.util.EPPXMLErrorHandler;

/**
 * <code>EPPXMLByteArray</code> is a utility class for reading and writing EPP
 * messages to/from byte arrays. DOM Document is converted to and from byte
 * arrays. An XML parser is required when reading from the stream. There is one
 * constructor that will create an XML parser per call to
 * <code>read(InputStream)</code> and one that will use a parser pool. Use of
 * a parser pool is recommended.
 * 
 * @author Srikanth Veeramachaneni
 * @version 1.0 Dec 04, 2006
 */
public class EPPXMLByteArray {

	/** Document Builder Factory for creating a parser per operation. */
	private static DocumentBuilderFactory factory = null;

	static {
		// Initialize the Document Builder Factory
		factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware( true );
		factory.setValidating( true );
	}

	/** Log4j category for logging */
	private static Logger LOG =
			Logger.getLogger( EPPXMLByteArray.class.getName(), EPPCatFactory
					.getInstance().getFactory() );

	private static Logger PACKET_LOG =
			Logger.getLogger( EPPXMLByteArray.class.getName() + ".packet",
					EPPCatFactory.getInstance().getFactory() );

	/**
	 * Pool Manager that could contain the EPP XML Parser Pool (<code>poolName</code>).
	 * If this is <code>null</code> there will be one XML parser created per
	 * call to <code>read</code>.
	 */
	private GenericPoolManager manager = null;

	/** Name of XML Parser Pool contained in <code>manager</code>. */
	private String poolName = null;


	/**
	 * Default constructor for <code>EPPXMLByteArray</code>. When using this
	 * constructor, a parser instance will be created on each call to
	 * <code>read(InputStream)</code>.
	 */
	public EPPXMLByteArray () {
		this.manager = null;
		this.poolName = null;
	}


	/**
	 * Construct <code>EPPXMLByteArray</code> to use a parser pool. The
	 * <code>aPoolMgr</code> parameter has to be a pool of
	 * <code>EPPParserPool</code> subclasses. When using this constructor, a
	 * parser instance will be checked out and checkin as needed on each call to
	 * <code>read(InputStream)</code>.
	 * 
	 * @param aPoolName
	 *        Pool name to use
	 */
	public EPPXMLByteArray ( String aPoolName ) {
		this.manager = GenericPoolManager.getInstance();
		this.poolName = aPoolName;
	}


	/**
	 * Decodes(parses) and validates the <code>aPacket</code> parameter and
	 * returns the associated DOM Document. The XML parser is either created per
	 * call, or is retrieved from a parser pool when
	 * <code>EPPXMLByteArray(GenericPoolManager)</code> is used. Use of a parser
	 * pool is recommended.
	 * 
	 * @param aPacket
	 *        The byte array containing the EPP packet.
	 * @return Parsed DOM Document of packet
	 * @exception EPPException
	 *            Error with received packet or end of stream. It is recommended
	 *            that the stream be closed.
	 * @exception EPPAssemblerException
	 *            Error parsing packet
	 * @exception IOException
	 *            Error reading packet from stream
	 */
	public Document decode ( byte[] aPacket ) throws EPPAssemblerException,
			EPPException, IOException {
		LOG.debug( "decode(): enter" );

		// Validate argument
		if ( aPacket == null ) {
			throw new EPPException( "decode(): BAD ARGUMENT (aPacket)" );
		}

		DocumentBuilder theBuilder = null;
		Document theDoc = null;

		// Parser pool specified?
		if ( this.manager != null ) {
			theBuilder = (DocumentBuilder) this.manager.requestObject( this.poolName );
			theBuilder.setErrorHandler( new EPPXMLErrorHandler() );
		}
		else {
			// Create new parser instance.
			theBuilder = new EPPSchemaCachingParser();
			theBuilder.setErrorHandler( new EPPXMLErrorHandler() );
		}

		if ( PACKET_LOG.isDebugEnabled() ) {
			PACKET_LOG.debug( "decode() : epp packet [" + new String(aPacket) + "]" );
		}

		try {
			try {
				// Parse/validate EPP Packet and create DOM document
				theDoc = theBuilder.parse( new ByteArrayInputStream( aPacket ) );
			}
			catch ( SAXParseException ex ) {
				// Error generated by parser
				LOG.debug( aPacket + "\nline      " + ex.getLineNumber()
						+ "\ncolumn    " + ex.getColumnNumber() + "\nuri       "
						+ ex.getSystemId() + "\nMessage : " + ex.getMessage(), ex );
				throw new EPPAssemblerException( "[SAXParseException]" + "\nline      "
						+ ex.getLineNumber() + "\ncolumn    " + ex.getColumnNumber()
						+ "\nuri       " + ex.getSystemId() + "\nMessage : "
						+ ex.getMessage(), EPPAssemblerException.XML );

			}
			catch ( SAXException ex ) {
				// Error generated by this application
				LOG.debug( "decode(): [SAXException]" + aPacket, ex );
				throw new EPPAssemblerException( "[SAXException] " + ex,
						EPPAssemblerException.XML );
			}
		}
		finally {
			// Check in pool object
			if ( this.manager != null ) {
				this.manager.returnObject( theBuilder );
			}
		}

		LOG.debug( "decode(): exit" );
		return theDoc;
	}


	/**
	 * Encodes(converts) a DOM Document to a <code>byte</code> array. The DOM
	 * Document will be serialized to XML and converted into a <code>byte</code>
	 * array.
	 * 
	 * @param aDoc
	 *        DOM Document to convert to <code>byte</code> array.
	 * @exception EPPException
	 *            Error writing to stream. It is recommended that the stream be
	 *            closed.
	 */
	public byte[] encode ( Document aDoc ) throws EPPException {
		LOG.debug( "encode(): enter" );

		if ( aDoc == null ) {
			LOG.debug( "encode(): aDoc == null" );
			throw new EPPException( "encode(): BAD ARGUMENT (aDoc)" );
		}

		// Serialize DOM Document to stream
		ByteArrayOutputStream theBuffer = new ByteArrayOutputStream();
		try {
			XMLSerializer theSerializer =
					new XMLSerializer( theBuffer, (OutputFormat) null );
			theSerializer.serialize( aDoc );
			theBuffer.close();
		}
		catch ( IOException ex ) {
			LOG.debug( "encode() : serialize() :" + ex.getMessage(), ex );
			throw new EPPException( "encode: serialize() " + ex.getMessage() );
		}

		if ( PACKET_LOG.isDebugEnabled() ) {
			PACKET_LOG.debug( "encode() : epp packet [" + theBuffer + "]" );
		}
		byte[] thePacket = theBuffer.toByteArray();
		LOG.debug( "encode(): exit" );
		return thePacket;
	}

} // End class EPPXMLByteArray
