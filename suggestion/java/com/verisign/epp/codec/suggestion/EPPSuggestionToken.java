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

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.verisign.epp.codec.gen.EPPCodecComponent;
import com.verisign.epp.codec.gen.EPPDecodeException;
import com.verisign.epp.codec.gen.EPPEncodeException;
import com.verisign.epp.codec.gen.EPPUtil;
import com.verisign.epp.codec.suggestion.util.ExceptionUtil;
import com.verisign.epp.codec.suggestion.util.InvalidValueException;
import com.verisign.epp.util.EqualityUtil;

/**
 * A Token describes a part of the &lt;suggestion:key&gt; element which has been
 * seperated by the Suggestions server. This information is optionally returned
 * by the server.
 * @author jcolosi
 */
public class EPPSuggestionToken implements EPPCodecComponent {

	private static final String ATT_NAME = "name";

	/** XML Element Name of <code>EPPSuggestionTld</code> root element. */
	final static String ELM_NAME = "suggestion:token";

	private String name = null;
	private List related = null;

	/**
	 * <code>EPPSuggestionTld</code> default constructor.
	 */
	public EPPSuggestionToken() {}

	public EPPSuggestionToken(String name) {
		setName(name);
	}

	public EPPSuggestionToken(Element element) throws EPPDecodeException {
		decode(element);
	}

	/**
	 * Clone <code>EPPSuggestionTld</code>.
	 * @return clone of <code>EPPSuggestionTld</code>
	 * @exception CloneNotSupportedException standard Object.clone exception
	 */
	public Object clone() throws CloneNotSupportedException {
		return (EPPSuggestionToken) super.clone();
	}

	/**
	 * Decode the EPPSuggestionTld attributes from the aElement DOM Element
	 * tree.
	 * @param aElement - Root DOM Element to decode EPPSuggestionTld from.
	 * @exception EPPDecodeException Unable to decode aElement
	 */
	public void decode(Element aElement) throws EPPDecodeException {
		this.name = aElement.getAttribute(ATT_NAME);
		if (name == null) ExceptionUtil.missingDuringDecode("name");

		NodeList nodes = aElement.getChildNodes();
		Node node = null;
		resetRelatedList();
		int size = nodes.getLength();
		for (int i = 0; i < size; i++) {
			node = nodes.item(i);
			if (node instanceof Element) {
				related.add(new EPPSuggestionRelated((Element) node));
			}
		}
		if (related.size() == 0) related = null;
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
		if (name == null) ExceptionUtil.missingDuringEncode("name");
		root.setAttribute(ATT_NAME, name);
		if (related != null) EPPUtil.encodeCompList(aDocument, root, related);
		return root;
	}

	/**
	 * implements a deep <code>EPPSuggestionTld</code> compare.
	 * @param aObject <code>EPPSuggestionTld</code> instance to compare with
	 * @return DOCUMENT ME!
	 */
	public boolean equals(Object o) {
		if ((o != null) && (o.getClass().equals(this.getClass()))) {
			EPPSuggestionToken other = (EPPSuggestionToken) o;
			if (!EqualityUtil.equals(this.name, other.name)) return false;
			if (!EqualityUtil.equals(this.related, other.related)) return false;
			return true;
		}
		return false;
	}

	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return Returns the synonyms.
	 */
	public List getRelatedList() {
		return related;
	}

	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	public void addRelated(EPPSuggestionRelated aRelated) throws InvalidValueException {
		if (aRelated == null) throw new InvalidValueException(
				"Cannot add a null related");
		if (related == null) resetRelatedList();
		related.add(aRelated);
	}

	public void resetRelatedList() {
		related = new ArrayList();
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