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
 * A Record associates a domain label with Cell objects to be returned in a
 * Grid. Each Cell represents a Top Level Domain and associated scores. Taken
 * together, a Record associates a Domain label with various TLDs and scores.
 * @author jcolosi
 */
public class EPPSuggestionRecord implements EPPCodecComponent {

	static private final String ATT_NAME = "name";
	static private final String ATT_SOURCE = "source";
	static private final String ATT_MORELIKETHIS = "morelikethis";
	static private final String ATT_PPCVALUE = "ppcvalue";
	final static String ELM_NAME = "suggestion:record";

	private String name = null;
	private String source = null;
	private String moreLikeThis = null;
	private Integer ppcValue = null;
	private List cells = null;

	/**
	 * <code>EPPSuggestionTld</code> default constructor.
	 */
	public EPPSuggestionRecord() {}

	public EPPSuggestionRecord(Element element) throws EPPDecodeException {
		decode(element);
	}

	public EPPSuggestionRecord(String aName) {
		this();
		setName(aName);
	}

	public EPPSuggestionRecord(String aName, String aSource, String aMoreLikeThis,
			Integer aPpcValue) {
		this();
		setName(aName);
		setSource(aSource);
		setMoreLikeThis(aMoreLikeThis);
		setPpcValue(aPpcValue);
	}

	public void addCell(EPPSuggestionCell aCell) throws InvalidValueException {
		if (aCell == null) throw new InvalidValueException("Cannot add a null Cell");
		if (cells == null) resetCells();
		cells.add(aCell);
	}

	/**
	 * Clone <code>EPPSuggestionTld</code>.
	 * @return clone of <code>EPPSuggestionTld</code>
	 * @exception CloneNotSupportedException standard Object.clone exception
	 */
	public Object clone() throws CloneNotSupportedException {
		return (EPPSuggestionRecord) super.clone();
	}

	/**
	 * Decode the EPPSuggestionTld attributes from the aElement DOM Element
	 * tree.
	 * @param aElement - Root DOM Element to decode EPPSuggestionTld from.
	 * @exception EPPDecodeException Unable to decode aElement
	 */
	public void decode(Element aElement) throws EPPDecodeException {
		setName(aElement.getAttribute(ATT_NAME));
		if (name == null) ExceptionUtil.missingDuringDecode("name");

		String tmp;
		tmp = aElement.getAttribute(ATT_SOURCE);
		if (tmp != null && tmp.length() > 0) setSource(tmp);
		tmp = aElement.getAttribute(ATT_MORELIKETHIS);
		if (tmp != null && tmp.length() > 0) setMoreLikeThis(tmp);

		setPpcValue(aElement.getAttribute(ATT_PPCVALUE));

		NodeList nodes = aElement.getChildNodes();
		Node node = null;
		resetCells();
		int size = nodes.getLength();
		for (int i = 0; i < size; i++) {
			node = nodes.item(i);
			if (node instanceof Element) {
				cells.add(new EPPSuggestionCell((Element) node));
			}
		}
		if (cells.size() == 0) cells = null;
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
		if (isSetSource()) root.setAttribute(ATT_SOURCE, source);
		if (isSetMoreLikeThis()) root.setAttribute(ATT_MORELIKETHIS, moreLikeThis);
		if (ppcValue != null) root.setAttribute(ATT_PPCVALUE, ppcValue + "");

		if (cells != null) EPPUtil.encodeCompList(aDocument, root, cells);

		return root;
	}

	/**
	 * implements a deep <code>EPPSuggestionTld</code> compare.
	 * @param aObject <code>EPPSuggestionTld</code> instance to compare with
	 * @return DOCUMENT ME!
	 */
	public boolean equals(Object o) {
		if ((o != null) && (o.getClass().equals(this.getClass()))) {
			EPPSuggestionRecord other = (EPPSuggestionRecord) o;
			if (!EqualityUtil.equals(this.name, other.name)) return false;
			if (!EqualityUtil.equals(this.source, other.source)) return false;
			if (!EqualityUtil.equals(this.moreLikeThis, other.moreLikeThis)) return false;
			if (!EqualityUtil.equals(this.ppcValue, other.ppcValue)) return false;
			if (!EqualityUtil.equals(this.cells, other.cells)) return false;
			return true;
		}
		return false;
	}

	/**
	 * @return Returns the synonyms.
	 */
	public List getCells() {
		return cells;
	}

	public String getMoreLikeThis() {
		return moreLikeThis;
	}

	public String getName() {
		return name;
	}

	public Integer getPpcValue() {
		return ppcValue;
	}

	public String getSource() {
		return source;
	}

	public boolean isSetMoreLikeThis() {
		return moreLikeThis != null && moreLikeThis.length() > 0;
	}

	public boolean isSetSource() {
		return source != null && source.length() > 0;
	}

	public void resetCells() {
		cells = new ArrayList();
	}

	public void setMoreLikeThis(String moreLikeThis) {
		this.moreLikeThis = moreLikeThis;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPpcValue(int ppcValue) {
		this.ppcValue = new Integer(ppcValue);
	}

	public void setPpcValue(Integer ppcValue) {
		this.ppcValue = ppcValue;
	}

	public void setPpcValue(String ppcValue) {
		if (ppcValue != null && ppcValue.length() > 0) {
			this.ppcValue = new Integer(Integer.parseInt(ppcValue));
		}
	}

	public void setSource(String source) {
		this.source = source;
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