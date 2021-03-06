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

package com.verisign.epp.codec.namestoreext;

import org.w3c.dom.Document;

// W3C Imports
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

//----------------------------------------------
// Imports
//----------------------------------------------
// Java Core Imports
import java.util.Hashtable;

// SDK Imports
import com.verisign.epp.codec.gen.*;


/**
 * Namestore &ltnsExtErrData&gt extension element to an error EPP Response. The
 * error code and message is currently associated with an EPP response code of
 * 2306 "Parameter value policy error", and can have one of the
 * <code>EPPNamestoreExtNSExtErrData</code><code>ERROR</code> constant values.
 * Optionally, a "lang" attribute can be provide to indicate the language. The
 * default value for "lang" is "en" (English). <br><br>
 *
 * @author $Author: jim $
 * @version $Revision: 1.3 $
 */
public class EPPNamestoreExtNSExtErrData implements EPPCodecComponent {
	/** The default language of the error message "en". */
	public static final String DEFAULT_LANG = "en";

	/** Specified sub-product does not exist */
	public static final int ERROR_SUB_PRODUCT_NOT_EXISTS = 1;

	/** XML root tag for <code>EPPNamestoreExtNSExtErrData</code>. */
	public static final String ELM_NAME = "namestoreExt:nsExtErrData";

	/**
	 * Hash that maps the pre-defined result codes to their associated message
	 * in     the default language of "en".
	 */
	private static Hashtable _defaultMsg;

	// Static Initializer for EPPNamestoreExtNSExtErrData
	static {
		_defaultMsg = new Hashtable();
		_defaultMsg.put(
						new Integer(ERROR_SUB_PRODUCT_NOT_EXISTS),
						"Specified sub-product does not exist");
	}

	/** XML tag name for the <code>_message</code> element. */
	private final static String ELM_MSG = "namestoreExt:msg";

	/** XML attribute name for the result <code>_code</code> attribute. */
	private final static String ATTR_CODE = "code";

	/**
	 * XML attribute name for the optional result <code>_lang</code> attribute.
	 */
	private final static String ATTR_LANG = "lang";

	/** Error code */
	private int _code = 0;

	/**
	 * Error message associated with _code.  A default English message is set
	 * by default.
	 */
	private String _message = "";

	/** Language of the <code>_message</code> attribute. */
	private String _lang = DEFAULT_LANG;

	/**
	 * Default constructor.  The error code is set to -1.
	 */
	public EPPNamestoreExtNSExtErrData() {
		// Do nothing
	}

	// End EPPNamestoreExtNSExtErrData.EPPNamestoreExtNSExtErrData()

	/**
	 * Constructor that sets the error code.  Use of the the <code>ERROR</code>
	 * constants for the error code.
	 *
	 * @param aCode Error code
	 */
	public EPPNamestoreExtNSExtErrData(int aCode) {
		_code		 = aCode;
		_message     = (String) _defaultMsg.get(new Integer(aCode));
	}

	// End EPPNamestoreExtNSExtErrData.EPPNamestoreExtNSExtErrData(int)

	/**
	 * Gets the error code.
	 *
	 * @return Error code that should be one of the <code>ERROR</code> constant
	 * 		   values.
	 */
	public int getCode() {
		return _code;
	}

	// End EPPNamestoreExtNSExtErrData.getCode()

	/**
	 * Sets the error code.
	 *
	 * @param aCode Error code that should be one of the <code>ERROR</code>
	 * 		  constant values.
	 */
	public void setCode(int aCode) {
		_code = aCode;
	}

	// End EPPNamestoreExtNSExtErrData.setCode(int)

	/**
	 * Sets the error code and the default "en" message associated with the
	 * error code if <code>aUserDefaultMessage</code> is set to
	 * <code>true</code>.
	 *
	 * @param aCode Error code that should be one of the <code>ERROR</code>
	 * 		  constant values.
	 * @param aUseDefaultMessage Use the default en message associated with
	 * 		  aCode?
	 */
	public void setCode(int aCode, boolean aUseDefaultMessage) {
		_code = aCode;

		if (aUseDefaultMessage) {
			_message = (String) _defaultMsg.get(new Integer(aCode));
		}
	}

	// End EPPNamestoreExtNSExtErrData.setCode(int, boolean)

	/**
	 * Gets the error message.
	 *
	 * @return Error message associated with the error code in the specified
	 * 		   language.
	 */
	public String getMessage() {
		return _message;
	}

	// End EPPNamestoreExtNSExtErrData.getMessage()

	/**
	 * Sets the error message.  This should only be called if the default "en"
	 * language message is not valid.
	 *
	 * @param aMessage Error message associated with the error code in the
	 * 		  specified language.
	 */
	public void setMessage(String aMessage) {
		_message = aMessage;
	}

	// End EPPNamestoreExtNSExtErrData.setMessage(String)

	/**
	 * Gets the error message language.  The Language must be structured as
	 * documented in <a
	 * href="http://www.ietf.org/rfc/rfc1766.txt?number=1766">[RFC1766]</a>.
	 *
	 * @return Error message language.
	 */
	public String getLang() {
		return _lang;
	}

	// End EPPNamestoreExtNSExtErrData.getLang()

	/**
	 * Sets the error message language.  The Language must be structured as
	 * documented in <a
	 * href="http://www.ietf.org/rfc/rfc1766.txt?number=1766">[RFC1766]</a>.
	 *
	 * @param aLang DOCUMENT ME!
	 */
	public void setLang(String aLang) {
		_lang = aLang;
	}

	// End EPPNamestoreExtNSExtErrData.setLang(String)

	/**
	 * encode instance into a DOM element tree. A DOM Document is passed as an
	 * argument and functions as a factory for DOM objects.  The root element
	 * associated with the instance is created and each instance attributeis
	 * appended as a child node.
	 *
	 * @param aDocument DOM Document, which acts is an Element factory
	 *
	 * @return Element Root element associated with the object
	 *
	 * @exception EPPEncodeException Error encoding
	 * 			  <code>EPPNamestoreExtNSExtErrData</code>
	 */
	public Element encode(Document aDocument) throws EPPEncodeException {
		Element currElm;
		Text    currVal;

		// Validate state
		if (_code == -1) {
			throw new EPPEncodeException("code required attribute is not set");
		}

		if (_message == null) {
			throw new EPPEncodeException("message required attribute is not set");
		}

		Element root =
			aDocument.createElementNS(EPPNamestoreExtExtFactory.NS, ELM_NAME);
		root.setAttribute("xmlns:namestoreExt", EPPNamestoreExtExtFactory.NS);
		root.setAttributeNS(
							EPPCodec.NS_XSI, "xsi:schemaLocation",
							EPPNamestoreExtExtFactory.NS_SCHEMA);

		// Message
		currElm =
			aDocument.createElementNS(EPPNamestoreExtExtFactory.NS, ELM_MSG);
		currVal = aDocument.createTextNode(_message);

		// Code
		currElm.setAttribute(ATTR_CODE, _code + "");

		// Lang
		if (!_lang.equals(DEFAULT_LANG)) {
			currElm.setAttribute(ATTR_LANG, _lang);
		}

		currElm.appendChild(currVal);
		root.appendChild(currElm);

		return root;
	}

	// End EPPNamestoreExtNSExtErrData.encode(Document)

	/**
	 * decode a DOM element tree to initialize the instance attributes.  The
	 * <code>aElement</code> argument represents the root DOM element and is
	 * used to traverse the DOM nodes for instance attribute values.
	 *
	 * @param aElement <code>Element</code> to decode
	 *
	 * @exception EPPDecodeException Error decoding <code>Element</code>
	 */
	public void decode(Element aElement) throws EPPDecodeException {
		NodeList currElmList;
		Element  currElm;

		// Message
		currElmList = aElement.getElementsByTagName(ELM_MSG);

		if (currElmList.getLength() != 0) {
			currElm = (Element) currElmList.item(0);
		}
		else {
			throw new EPPDecodeException("Required EPPNamestoreExtNSExtErrData element "
										 + ELM_MSG + " not found");
		}

		_message = currElm.getFirstChild().getNodeValue();

		if (_message == null) {
			throw new EPPDecodeException("Required message value of EPPNamestoreExtNSExtErrData element "
										 + ELM_MSG + " not found");
		}

		// Lang
		setLang(currElm.getAttribute(ATTR_LANG));

		// Code
		_code = Integer.parseInt(currElm.getAttribute(ATTR_CODE));
	}

	// End EPPNamestoreExtNSExtErrData.decode(Element)

	/**
	 * Compare an instance of <code>EPPNamestoreExtNSExtErrData</code> with
	 * this instance.
	 *
	 * @param aObject Object to compare with.
	 *
	 * @return <code>true</code> if equal; <code>false</code> otherwise.
	 */
	public boolean equals(Object aObject) {
		if (!(aObject instanceof EPPNamestoreExtNSExtErrData)) {
			return false;
		}

		EPPNamestoreExtNSExtErrData theComp =
			(EPPNamestoreExtNSExtErrData) aObject;

		// _code
		if (_code != theComp._code) {
			return false;
		}

		// _message
		if (
			!(
					(_message == null) ? (theComp._message == null)
										   : _message.equals(theComp._message)
				)) {
			return false;
		}

		// _lang
		if (!_lang.equals(theComp._lang)) {
			return false;
		}

		return true;
	}

	// End EPPNamestoreExtNSExtErrData.equals(Object)

	/**
	 * clone an <code>EPPCodecComponent</code>.
	 *
	 * @return clone of concrete <code>EPPNamestoreExtNSExtErrData</code>
	 *
	 * @exception CloneNotSupportedException standard Object.clone exception
	 */
	public Object clone() throws CloneNotSupportedException {
		EPPNamestoreExtNSExtErrData clone =
			(EPPNamestoreExtNSExtErrData) super.clone();

		return clone;
	}

	// End EPPNamestoreExtNSExtErrData.clone()

	/**
	 * Gets the root element name.
	 *
	 * @return "persReg:creErrData"
	 */
	protected String getRootElm() {
		return ELM_NAME;
	}

	// End EPPNamestoreExtNSExtErrData.getRootElm()
}


// End class EPPNamestoreExtNSExtErrData
