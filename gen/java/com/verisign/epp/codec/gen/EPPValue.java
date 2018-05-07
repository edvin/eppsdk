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
package com.verisign.epp.codec.gen;


// Log4j Imports
import org.apache.log4j.Logger;

import org.apache.xml.serialize.*;

import org.w3c.dom.*;

import org.xml.sax.*;

//----------------------------------------------
//
// imports...
//
//----------------------------------------------
// JDK Imports
import java.io.*;

// Java XML imports
import javax.xml.parsers.*;

import com.verisign.epp.util.EPPCatFactory;


/**
 * Identifies a client-provided element (including XML tag and value) that
 * caused a server error condition.  The value includes a reference to the
 * namespace URI and namespace prefix for encoding the value.  The
 * <code>String</code> value needs to be set using XML with the specified
 * namespace prefix. For example, the URI could be
 * "urn:ietf:params:xml:ns:domain-1.0", the prefix could be "domain", and the
 * value could be &lt;domain:name&gt;example.com&lt;/domain&gt;.  The default
 * namespace prefix is "epp" and the default URI is
 * "urn:ietf:params:xml:ns:epp-1.0".
 *
 * @see com.verisign.epp.codec.gen.EPPResult
 */
public class EPPValue implements EPPCodecComponent {
	/** Default namespace prefix */
	public static final String DEFAULT_PREFIX = EPPCodec.NS_PREFIX;

	/** Default namespace */
	public static final String DEFAULT_NS = EPPCodec.NS;

	/** Document Builder Factory for creating a parser per value. */
	private static DocumentBuilderFactory factory = null;

	static {
		// Initialize the Document Builder Factory
		factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(false);
	}

	/** XML root tag name for <code>EPPValue</code>. */
	final static String ELM_NAME = "value";

	/** Log4j category for logging */
	private static Logger cat =
		Logger.getLogger(
						 EPPValue.class.getName(),
						 EPPCatFactory.getInstance().getFactory());

	/**
	 * identifies a client-provided element (including XML tag and value) that
	 * caused a server error condition.
	 */
	private String value;

	/**
	 * XML prefix of value.  For example, "domain" or the default value "epp".
	 */
	private String prefix = DEFAULT_PREFIX;

	/**
	 * XML namespace of value.  For example, "urn:ietf:params:xml:ns:epp-1.0".
	 */
	private String namespace = DEFAULT_NS;

	/**
	 * Default constructor for serialization.  The <code>value</code> attribute
	 * must be set before calling <code>encode</code>.
	 */
	public EPPValue() {
		// Do nothing
	}

	/**
	 * Allocates a new <code>EPPValue</code> with only the <code>String</code>
	 * value.  The prefix and namespace to <code>DEFAULT_PREFIX</code> and
	 * <code>DEFAULT_NS</code> respectively.
	 *
	 * @param aValue XML <code>String</code> that identifies a client-provided
	 * 		  element (including XML tag and value) that caused a server
	 * 		  error.
	 */
	public EPPValue(String aValue) {
		this.value = aValue;
	}

	// End EPPValue.EPPValue(String)

	/**
	 * Allocates a new <code>EPPValue</code> with all attribute values.
	 *
	 * @param aValue XML <code>String</code> that identifies a client-provided
	 * 		  element (including XML tag and value) that caused a server
	 * 		  error.  For example,
	 * 		  &lt;domain:name&gt;example.com&lt;/domain&gt;.
	 * @param aPrefix XML Namespace prefix.  For example, "domain" or "epp".
	 * @param aNamespace XML Namespace URI.  For example,
	 * 		  "urn:ietf:params:xml:ns:domain-1.0".
	 */
	public EPPValue(String aValue, String aPrefix, String aNamespace) {
		this.value		   = aValue;
		this.prefix		   = aPrefix;
		this.namespace     = aNamespace;
	}

	// End EPPValue.EPPValue(String, String, String)

	/**
	 * Gets XML <code>String</code> that identifies a client-provided element
	 * (including XML tag and value) that caused a server error.
	 *
	 * @return XML <code>String</code> value using namespace prefix.
	 */
	public String getValue() {
		return this.value;
	}

	// End EPPValue.getValue()

	/**
	 * Sets XML <code>String</code> that identifies a client-provided element
	 * (including XML tag and value) that caused a server error.
	 *
	 * @param aValue XML <code>String</code> that identifies a client-provided
	 * 		  element (including XML tag and value) that caused a server
	 * 		  error.  For example,
	 * 		  &lt;domain:name&gt;example.com&lt;/domain&gt;.
	 */
	public void setValue(String aValue) {
		this.value = aValue;
	}

	// End EPPValue.setValue(String)

	/**
	 * Gets the XML prefix of the client element.  For example, "domain" or the
	 * default value "epp".
	 *
	 * @return XML prefix <code>String</code>
	 */
	public String getPrefix() {
		return this.prefix;
	}

	// End EPPValue.getPrefix()

	/**
	 * Sets the XML prefix of the client element.  For example, "domain" or the
	 * default value "epp".
	 *
	 * @param aPrefix XML prefix <code>String</code>
	 */
	public void setPrefix(String aPrefix) {
		this.prefix = aPrefix;
	}

	// End EPPValue.setPrefix(String)

	/**
	 * Gets the XML namespace URI of the client element.  For example,
	 * "urn:ietf:params:xml:ns:domain-1.0" or the default value
	 * "urn:ietf:params:xml:ns:epp-1.0".
	 *
	 * @return XML namespace URI <code>String</code>
	 */
	public String getNamespace() {
		return this.namespace;
	}

	// End EPPValue.getNamespace()

	/**
	 * Sets the XML namespace URI of the client element.  For example,
	 * "urn:ietf:params:xml:ns:domain-1.0" or the default value
	 * "urn:ietf:params:xml:ns:epp-1.0".
	 *
	 * @param aNamespace XML namespace URI <code>String</code>
	 */
	public void setNamespace(String aNamespace) {
		this.namespace = aNamespace;
	}

	// End EPPValue.setNamespace(String)

	/**
	 * encode <code>EPPValue</code> into a DOM element tree.
	 *
	 * @param aDocument DOCUMENT ME!
	 *
	 * @return &lt;extValue&gt; root element tree.
	 *
	 * @exception EPPEncodeException Error encoding the DOM element tree.
	 */
	public Element encode(Document aDocument) throws EPPEncodeException {
		Element root = aDocument.createElementNS(EPPCodec.NS, ELM_NAME);

		// Prefix and Namespace
		root.setAttribute("xmlns:" + this.prefix, this.namespace);

		// Value
		DocumentBuilder theParser = null;

		try {
			theParser = factory.newDocumentBuilder();
		}
		 catch (Exception ex) {
			cat.error("EPPValue.encode(): error creating DocumentBuilder: "
					  + ex);
			throw new EPPEncodeException("Error creating DocumentBuilder: "
										 + ex);
		}

		ByteArrayInputStream theValueStream =
			new ByteArrayInputStream(this.value.getBytes());

		Document			 theValueDoc = null;

		try {
			theValueDoc = theParser.parse(theValueStream);
		}
		 catch (Exception ex) {
			cat.error("EPPValue.encode(): error parsing value [" + this.value
					  + "]: " + ex);
			throw new EPPEncodeException("Error parsing value [" + this.value
										 + "]: " + ex);
		}

		Element theValueElm = theValueDoc.getDocumentElement();
		root.appendChild(aDocument.importNode(theValueElm, true));

		return root;
	}

	// End EPPValue.encode(Document)

	/**
	 * decode <code>EPPValue</code> from a DOM element tree.  The
	 * <code>aElement</code> argument needs to be the "extValue" element.
	 *
	 * @param aElement The "extValue" XML element.
	 *
	 * @exception EPPDecodeException Error decoding the DOM element tree.
	 */
	public void decode(Element aElement) throws EPPDecodeException {
		// Value
		Element valueNode = (Element) EPPUtil.getFirstElementChild(aElement);

		if (valueNode == null) {
			cat.error("EPPValue.decode(): value node could not be found");
			throw new EPPDecodeException("EPPValue.decode(): value node could not be found");
		}
		
		this.prefix = valueNode.getPrefix();
		this.namespace = valueNode.getNamespaceURI();

		StringWriter xmlValueWriter = new StringWriter();

		OutputFormat outFormat = new OutputFormat();
		outFormat.setOmitXMLDeclaration(true);

		XMLSerializer serializer = new XMLSerializer(xmlValueWriter, outFormat);

		try {
			DOMSerializer domSerializer = serializer.asDOMSerializer();
			domSerializer.serialize(valueNode);
		}
		 catch (IOException e) {
			cat.error("EPPValue.decode(): IOException serializing value: " + e);
			throw new EPPDecodeException("decode() : serializing value: " + e);
		}

		this.value = xmlValueWriter.toString();
	}

	// End EPPValue.decode(Element)

	/**
	 * implements a deep <code>EPPValue</code> compare.
	 *
	 * @param aObject <code>EPPValue</code> instance to compare with
	 *
	 * @return <code>true</code> if equal; <code>false</code> otherwise
	 */
	public boolean equals(Object aObject) {
		if (!(aObject instanceof EPPValue)) {
			cat.error("EPPValue.equals(): " + aObject.getClass().getName()
					  + " not EPPValue instance");

			return false;
		}

		EPPValue theValue = (EPPValue) aObject;

		// Value
		if (
			!(
					(this.value == null) ? (theValue.value == null)
											 : this.value.equals(theValue.value)
				)) {
			cat.error("EPPValue.equals(): value not equal");

			return false;
		}

		// Prefix
		if (!this.prefix.equals(theValue.prefix)) {
			cat.error("EPPValue.equals(): prefix not equal");

			return false;
		}

		// Namespace
		if (!this.namespace.equals(theValue.namespace)) {
			cat.error("EPPValue.equals(): namespace not equal");

			return false;
		}

		return true;
	}

	// End EPPValue.equals(Object)

	/**
	 * Clone <code>EPPValue</code>.
	 *
	 * @return Deep copy clone of <code>EPPValue</code>
	 *
	 * @exception CloneNotSupportedException standard Object.clone exception
	 */
	public Object clone() throws CloneNotSupportedException {
		EPPValue clone = null;

		clone = (EPPValue) super.clone();

		return clone;
	}

	// End EPPValue.clone()

	/**
	 * Implementation of <code>Object.toString</code>, which will result in an
	 * indented XML <code>String</code> representation of the concrete
	 * <code>EPPCodecComponent</code>.
	 *
	 * @return Indented XML <code>String</code> if successful;
	 * 		   <code>ERROR</code> otherwise.
	 */
	public String toString() {
		return EPPUtil.toString(this);
	}

	// End EPPValue.toString()
}


// End class EPPValue
