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


//----------------------------------------------
//
// imports...
//
//----------------------------------------------
// Log4j Imports
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.verisign.epp.util.EPPCatFactory;


/**
 * Represents authorization information which is a shared structure been used
 * by other mapping such as  domain and contact mappings. This object
 * structure is a direct mapping from the data type <code>authInfo</code> in
 * the EPP Shared Structure Schema (with the name space <code>eppcom</code>).
 */
public class EPPAuthInfo implements com.verisign.epp.codec.gen.EPPCodecComponent {
	/** password auth info type */
	public final static short TYPE_PW = 0;

	/** Extensible auth info type. */
	public final static short TYPE_EXT = 1;

	/** XML Element name of <code>EPPAuthInfo</code> root element. */
	final static String ELM_NAME = "authInfo";

	/** XML Element name password authorization type */
	protected final static String ELM_PW = "pw";

	/** XML Element name extensible authorization type */
	protected final static String ELM_EXT = "ext";

	/**
	 * XML Element roid attribute name of <code>EPPAuthInfo</code> root
	 * element.
	 */
	protected final static String ATTR_ROID = "roid";

	/** Log4j category for logging */
	private static Logger cat =
		Logger.getLogger(
						 EPPAuthInfo.class.getName(),
						 EPPCatFactory.getInstance().getFactory());

	/** Password authorization information. */
	protected String password = null;

	/** Extension authorization information. */
	protected EPPCodecComponent ext = null;

	/** type, and default value is <code>TYPE_PW</code> */
	protected short type = TYPE_PW;

	/** roid. */
	protected String roid = null;

	/**
	 * Root mapping name such as domain or contact (e.g.
	 * <code>domain:authInfo</code> for domain mapping).  This attribute needs
	 * to be specified before calling encode(Document) method.
	 */
	private String rootName = ELM_NAME;

	/**
	 * XML namespace prefix for the auth info elements.  This is parsed from
	 * the  <code>rootName</code> attribute.  For example, if
	 * <code>rootName</code> is  <code>domain:authInfo</code> than the prefix
	 * is set to <code>domain</code>.
	 */
	private String prefix = null;

	/**
	 * Default constructor that must have the password or extension  attributes
	 * set before calling <code>encode</code>.
	 */
	public EPPAuthInfo() {
	}

	// End EPPAuthInfo.EPPAuthInfo()

	/**
	 * Constructor that takes just the authorization password.
	 *
	 * @param aPassword Authorization password
	 */
	public EPPAuthInfo(String aPassword) {
		this.setPassword(aPassword);
	}

	// End EPPAuthInfo.EPPAuthInfo(String)

	/**
	 * Constructor that takes the root element and the  authorization password.
	 *
	 * @param aRootName Root element of auth info.
	 * @param aPassword Authorization password
	 */
	public EPPAuthInfo(String aRootName, String aPassword) {
		this.setRootName(aRootName);
		this.setPassword(aPassword);
	}

	// End EPPAuthInfo.EPPAuthInfo(String, String)

	/**
	 * Constructor that takes the root element, the  authorization password,
	 * and the roid.
	 *
	 * @param aRootName Root element of auth info.
	 * @param aRoid Roid of the Registrant
	 * @param aPassword Authorization password
	 */
	public EPPAuthInfo(String aRootName, String aRoid, String aPassword) {
		this.setRootName(aRootName);
		this.roid = aRoid;
		this.setPassword(aPassword);
	}

	// End EPPAuthInfo.EPPAuthInfo(String, String, String)

	/**
	 * Constructor that takes just the authorization extension.
	 *
	 * @param aExt Extension authorization element
	 */
	public EPPAuthInfo(EPPCodecComponent aExt) {
		this.setExt(aExt);
	}

	// End EPPAuthInfo.EPPAuthInfo(EPPCodecComponent)

	/**
	 * Constructor that takes a root elemeent and  the authorization extension.
	 *
	 * @param aRootName Root element of auth info.
	 * @param aExt Extension authorization element
	 */
	public EPPAuthInfo(String aRootName, EPPCodecComponent aExt) {
		this.setRootName(aRootName);
		this.setExt(aExt);
	}

	// End EPPAuthInfo.EPPAuthInfo(String, EPPCodecComponent)


	// End EPPAuthInfo.EPPAuthInfo(String, String, EPCodecComponent)

	/**
	 * Get roid.
	 *
	 * @return String
	 */
	public String getRoid() {
		return this.roid;
	}

	// End EPPAuthInfo.getRoid()

	/**
	 * Set roid.
	 *
	 * @param newRoid String
	 */
	public void setRoid(String newRoid) {
		this.roid = newRoid;
	}

	// End EPPAuthInfo.SetRoid()

	/**
	 * Get root name such as domain or contact.
	 *
	 * @return String
	 */
	public String getRootName() {
		return rootName;
	}

	// End EPPAuthInfo.getRootName()

	/**
	 * Set root name such as domain or contact.
	 *
	 * @param newRootName String
	 */
	public void setRootName(String newRootName) {
		this.rootName = newRootName;

		int colonPos = this.rootName.indexOf(':');

		if (colonPos != -1) {
			this.prefix = this.rootName.substring(0, colonPos);
		}
		else {
			this.prefix = null;
		}
	}

	// End EPPAuthInfo.setRootName()

	/**
	 * Gets the prefix to add to the elements during an encode.
	 *
	 * @return Prefix with ':' if defined; empty string otherwise.
	 */
	private String getPrefix() {
		if (this.prefix == null) {
			return "";
		}
		else {
			return this.prefix + ":";
		}
	}

	/**
	 * Gets the password authorization information.
	 *
	 * @return Password
	 *
	 * @deprecated Replaced by {@link #getPassword()}.
	 */
	public String getAuthInfo() {
		return this.password;
	}

	// End EPPAuthInfo.getAuthInfo()

	/**
	 * Gets the password authorization information.
	 *
	 * @return Authorization password
	 */
	public String getPassword() {
		return this.password;
	}

	// End EPPAuthInfo.getPassword()

	/**
	 * Sets the password authorization information.
	 *
	 * @param aPassword Authorization password
	 */
	public void setPassword(String aPassword) {
		this.password     = aPassword;
		this.type		  = TYPE_PW;
	}

	// End EPPAuthInfo.SetAuthInfo(String)

	/**
	 * Gets the extension authorization.
	 *
	 * @return Authorization extension
	 */
	public EPPCodecComponent getExt() {
		return this.ext;
	}

	// End EPPAuthInfo.getExt()

	/**
	 * Sets the extension authorization information.
	 *
	 * @param aExt Authorization extension
	 */
	public void setExt(EPPCodecComponent aExt) {
		this.ext	  = aExt;
		this.type     = TYPE_EXT;
	}

	// End EPPAuthInfo.SetAuthInfo(EPPCodecComponent)

	/**
	 * Sets the password authorization information.
	 *
	 * @param aPassword Authorization password
	 *
	 * @deprecated Replaced by {@link #setPassword(String)}.
	 */
	public void setAuthInfo(String aPassword) {
		this.password = aPassword;
	}

	// End EPPAuthInfo.SetAuthInfo(String)

	/**
	 * Get the type of the auth info.
	 *
	 * @return One of the <code>TYPE_</code> constants.
	 */
	public short getType() {
		return this.type;
	}

	// End EPPAuthInfo.getType()

	/**
	 * Set auth info type.  The default value is <code>TYPE_PW</code>.
	 *
	 * @param aType One of the <code>TYPE_</code> constants
	 */
	public void setType(short aType) {
		this.type = aType;
	}

	// End EPPAuthInfo.setType(short)

	/**
	 * Clone <code>EPPAuthInfo</code>.
	 *
	 * @return clone of <code>EPPAuthInfo</code>
	 *
	 * @exception CloneNotSupportedException standard Object.clone exception
	 */
	public Object clone() throws CloneNotSupportedException {
		EPPAuthInfo clone = null;

		clone = (EPPAuthInfo) super.clone();

		if (this.ext != null) {
			clone.ext = (EPPCodecComponent) this.ext.clone();
		}

		return clone;
	}

	// End EPPAuthInfo.clone()

	/**
	 * Decode the EPPAuthInfo attributes from the aElement DOM Element tree.
	 *
	 * @param aElement - Root DOM Element to decode EPPDomainContact from.
	 *
	 * @exception EPPDecodeException - Unable to decode aElement.
	 */
	public void decode(Element aElement) throws EPPDecodeException {
		// root name
		this.setRootName(aElement.getTagName());

		Element theTypeElm = EPPUtil.getFirstElementChild(aElement);

		// password provided?
		if (theTypeElm == null) {
			throw new EPPDecodeException("EPPAuthInfo.decode could not find type child element");						
		}
		else if (theTypeElm.getLocalName().equals(ELM_PW)) {
			this.type = TYPE_PW;
			
			// Get password value
			Node textNode = theTypeElm.getFirstChild();
			if (textNode != null) {
				this.password = textNode.getNodeValue();
			}
			else {
				this.password = "";
			}
			
			// Get roid
			if (theTypeElm.getAttribute(ATTR_ROID).equals("")) {
				this.roid = null;
			}
			else {
				this.roid = theTypeElm.getAttribute(ATTR_ROID);
			}
		} // extension type
		else if (theTypeElm.getLocalName().equals(ELM_EXT)){
			this.type = TYPE_EXT;
			
			Element theExtElm = EPPUtil.getFirstElementChild(theTypeElm);
			
			// Create extension 
			try {
				this.ext =
					EPPFactory.getInstance().createExtension(theExtElm);
			}
			 catch (EPPCodecException e) {
				throw new EPPDecodeException("EPPAuthInfo.decode unable to create authInfo extension object: "
											 + e);
			}
			this.ext.decode(theExtElm);
		}
		else {
			throw new EPPDecodeException("EPPAuthInfo.decode invalid type child element tag name of " + theTypeElm.getTagName());						
		}
		
		// Set the prefix
		this.prefix = theTypeElm.getPrefix();
	}

	// End EPPAuthInfo.decode(Element)

	/**
	 * Encode a DOM Element tree from the attributes of the EPPAuthInfo
	 * instance.
	 *
	 * @param aDocument - DOM Document that is being built.  Used as an Element
	 * 		  factory.
	 *
	 * @return Element    - Root DOM Element representing the EPPAuthInfo
	 * 		   instance.
	 *
	 * @exception EPPEncodeException - Unable to encode EPPAuthInfo instance.
	 */
	public Element encode(Document aDocument) throws EPPEncodeException {
		Element root = aDocument.createElement(rootName);


		switch (this.type) {
			case TYPE_PW:

				if (this.password == null) {
					throw new EPPEncodeException("EPPAuthInfo: password is null on call to encode");
				}

				Element thePasswordElm =
					aDocument.createElement(this.getPrefix() + ELM_PW);
				thePasswordElm.appendChild(aDocument.createTextNode(this.password));
				
				// roid
				if (this.roid != null) {
					thePasswordElm.setAttribute(ATTR_ROID, this.roid);
				}
				
				root.appendChild(thePasswordElm);

				break;

			case TYPE_EXT:

				if (this.ext == null) {
					throw new EPPEncodeException("EPPAuthInfo: ext is null on call to encode");
				}

				Element theExtElm =
					aDocument.createElement(this.getPrefix() + ELM_EXT);
				EPPUtil.encodeComp(aDocument, theExtElm, this.ext);
				root.appendChild(theExtElm);

				break;

			default:
				throw new EPPEncodeException("EPPAuthInfo: invalid type"
											 + this.type);
		}

		return root;
	}

	// End EPPAuthInfo.encode(Document)

	/**
	 * implements a deep <code>EPPAuthInfo</code> compare.
	 *
	 * @param aObject <code>EPPAuthInfo</code> instance to compare with
	 *
	 * @return <code>true</code> if equal; <code>false</code> otherwise
	 */
	public boolean equals(Object aObject) {
		if (!(aObject instanceof EPPAuthInfo)) {
			cat.error("EPPAuthInfo.equals(): " + aObject.getClass().getName()
					  + " not EPPAuthInfo instance");

			return false;
		}

		EPPAuthInfo theComp = (EPPAuthInfo) aObject;

		// Root name
		if (!this.rootName.equals(theComp.rootName)) {
			cat.error("EPPAuthInfo.equals(): rootName not equal");

			return false;
		}

		// password
		if (
			!(
					(this.password == null) ? (theComp.password == null)
												: this.password.equals(theComp.password)
				)) {
			cat.error("EPPAuthInfo.equals(): password not equal");

			return false;
		}

		// ext
		if (
			!(
					(this.ext == null) ? (theComp.ext == null)
										   : this.ext.equals(theComp.ext)
				)) {
			cat.error("EPPAuthInfo.equals(): ext not equal");

			return false;
		}

		// type
		if (this.type != theComp.type) {
			cat.error("EPPAuthInfo.equals(): type not equal");

			return false;
		}

		//roid
		if (
			!(
					(this.roid == null) ? (theComp.roid == null)
											: this.roid.equals(theComp.roid)
				)) {
			cat.error("EPPAuthInfo.equals(): roid not equal");

			return false;
		}

		return true;
	}

	// End EPPAuthInfo.equals(Object)

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

	// End EPPAuthInfo.toString()
}


// End class EPPAuthInfo
