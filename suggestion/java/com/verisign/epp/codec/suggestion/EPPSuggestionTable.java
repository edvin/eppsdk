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
import com.verisign.epp.codec.suggestion.util.InvalidValueException;
import com.verisign.epp.util.EqualityUtil;

/**
 * A Table is a type of response that associates domain names with scores. The
 * suggestions returned using a Table are not organized by Top Level Domain.
 * They are simply listed in order of their score.
 * @author jcolosi
 */
public class EPPSuggestionTable implements EPPCodecComponent {

	/** XML Element Name of <code>EPPSuggestionTld</code> root element. */
	final static String ELM_NAME = "suggestion:table";

	private List rows = null;

	/**
	 * <code>EPPSuggestionTld</code> default constructor.
	 */
	public EPPSuggestionTable() {}

	public EPPSuggestionTable(Element element) throws EPPDecodeException {
		decode(element);
	}

	/**
	 * Clone <code>EPPSuggestionTld</code>.
	 * @return clone of <code>EPPSuggestionTld</code>
	 * @exception CloneNotSupportedException standard Object.clone exception
	 */
	public Object clone() throws CloneNotSupportedException {
		return (EPPSuggestionTable) super.clone();
	}

	/**
	 * Decode the EPPSuggestionTld attributes from the aElement DOM Element
	 * tree.
	 * @param aElement - Root DOM Element to decode EPPSuggestionTld from.
	 * @exception EPPDecodeException Unable to decode aElement
	 */
	public void decode(Element aElement) throws EPPDecodeException {
		NodeList nodes = aElement.getChildNodes();
		Node node = null;
		resetRows();
		int size = nodes.getLength();
		for (int i = 0; i < size; i++) {
			node = nodes.item(i);
			if (node instanceof Element) {
				rows.add(new EPPSuggestionRow((Element) node));
			}
		}
		if (rows.size() == 0) rows = null;
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
		if (rows != null) EPPUtil.encodeCompList(aDocument, root, rows);
		return root;
	}

	/**
	 * implements a deep <code>EPPSuggestionTld</code> compare.
	 * @param aObject <code>EPPSuggestionTld</code> instance to compare with
	 * @return DOCUMENT ME!
	 */
	public boolean equals(Object o) {
		if ((o != null) && (o.getClass().equals(this.getClass()))) {
			EPPSuggestionTable other = (EPPSuggestionTable) o;
			if (!EqualityUtil.equals(this.rows, other.rows)) return false;
			return true;
		}
		return false;
	}

	/**
	 * @return Returns the Row objects
	 */
	public List getRows() {
		return rows;
	}

	public void addRow(EPPSuggestionRow aRow) throws InvalidValueException {
		if (aRow == null) throw new InvalidValueException("Cannot add a null Row");
		if (rows == null) resetRows();
		rows.add(aRow);
	}

	public void resetRows() {
		rows = new ArrayList();
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