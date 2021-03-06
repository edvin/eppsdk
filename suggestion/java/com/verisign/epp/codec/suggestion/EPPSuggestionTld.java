/***********************************************************
 Copyright (C) 2004 VeriSign, Inc.

 This library is free software; you can redistribute it and/or
 modify it under the terms of the GNU Lesser General Public
 License as published by the Free Software Foundation; either
 version 2.1 of the License, or (at your option) any later version.

 This library is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 Lesser General Public License for more details.

 You should have received a copy of the GNU Lesser General Public
 License along with this library; if not, write to the Free Software
 Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-0107  USA

 http://www.verisign.com/nds/naming/namestore/techdocs.html
 ***********************************************************/
package com.verisign.epp.codec.suggestion;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.verisign.epp.codec.gen.EPPCodecComponent;
import com.verisign.epp.codec.gen.EPPDecodeException;
import com.verisign.epp.codec.gen.EPPEncodeException;
import com.verisign.epp.codec.gen.EPPUtil;
import com.verisign.epp.codec.suggestion.util.InvalidValueException;
import com.verisign.epp.util.EqualityUtil;

/**
 * A Tld object encapsulates a Top Level Domain. This element allows the client
 * to specify the Top Level Domains to use during suggestions creation.
 * @author jcolosi
 */
public class EPPSuggestionTld implements EPPCodecComponent {

	/** XML Element Name of <code>EPPSuggestionTld</code> root element. */
	static final String ELM_NAME = "suggestion:tld";

	private String tld = null;

	/**
	 * <code>EPPSuggestionTld</code> default constructor.
	 */
	public EPPSuggestionTld() {}

	public EPPSuggestionTld(Element element) throws InvalidValueException {
		decode(element);
	}

	public EPPSuggestionTld(String tld) {
		setTld(tld);
	}

	/**
	 * Clone <code>EPPSuggestionTld</code>.
	 * @return clone of <code>EPPSuggestionTld</code>
	 * @exception CloneNotSupportedException standard Object.clone exception
	 */
	public Object clone() throws CloneNotSupportedException {
		return (EPPSuggestionTld) super.clone();
	}

	/**
	 * Decode the EPPSuggestionTld attributes from the aElement DOM Element
	 * tree.
	 * @param aElement - Root DOM Element to decode EPPSuggestionTld from.
	 * @throws InvalidValueException
	 * @exception EPPDecodeException Unable to decode aElement
	 */
	public void decode(Element aElement) throws InvalidValueException {
		setTld(aElement.getFirstChild().getNodeValue());
	}

	/**
	 * Encode a DOM Element tree from the attributes of the EPPSuggestionTld
	 * instance.
	 * @param aDocument - DOM Document that is being built. Used as an Element
	 *            factory.
	 * @return Element - Root DOM Element representing the EPPSuggestionTld
	 *         instance.
	 * @exception EPPEncodeException - Unable to encode EPPSuggestionTld
	 *                instance.
	 */
	public Element encode(Document aDocument) throws EPPEncodeException {
		Element root = aDocument.createElementNS(EPPSuggestionMapFactory.NS, ELM_NAME);
		if (tld == null) { throw new EPPEncodeException(
				"Missing required element: \"tld\""); }
		root.appendChild(aDocument.createTextNode(tld));
		return root;
	}

	/**
	 * implements a deep <code>EPPSuggestionTld</code> compare.
	 * @param aObject <code>EPPSuggestionTld</code> instance to compare with
	 * @return DOCUMENT ME!
	 */
	public boolean equals(Object o) {
		if ((o != null) && (o.getClass().equals(this.getClass()))) {
			EPPSuggestionTld other = (EPPSuggestionTld) o;
			if (!EqualityUtil.equals(this.tld, other.tld)) return false;
			return true;
		}
		return false;
	}

	public String getTld() {
		return tld;
	}

	public void setTld(String tld) {
		if (tld != null) tld = tld.toLowerCase();
		this.tld = tld;
	}

	public String toLogString() {
		return tld;
	}

	/**
	 * Implementation of <code>Object.toString</code>, which will result in
	 * an indented XML <code>String</code> representation of the concrete
	 * <code>EPPCodecComponent</code>.
	 * @return Indented XML <code>String</code> if successful;
	 *         <code>ERROR</code> otherwise.
	 */
	public String toString() {
		return EPPUtil.toString(this);
	}
}