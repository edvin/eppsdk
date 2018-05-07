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
import com.verisign.epp.codec.suggestion.util.ExceptionUtil;
import com.verisign.epp.codec.suggestion.util.InvalidValueException;
import com.verisign.epp.codec.suggestion.util.WeightEnum;
import com.verisign.epp.util.EqualityUtil;

/**
 * Encapsulates a server action to be taken by the Name Suggestions application.
 * Actions indicate how Name Suggestions should be constructed. For intance, the
 * Basic action constructs suggestions by adding a prefix or suffix to the
 * original input. The server can support an unlimited number of actions.
 * @author jcolosi
 */
public class EPPSuggestionAction implements EPPCodecComponent {
	static private final String ATT_NAME = "name";
	static private final String ATT_WEIGHT = "weight";
	final static String ELM_NAME = "suggestion:action";

	private String name = null;
	private WeightEnum weight = new WeightEnum(); // non-null

	public EPPSuggestionAction() {}

	public EPPSuggestionAction(Element element) throws EPPDecodeException {
		decode(element);
	}

	public EPPSuggestionAction(String aName, String aWeight)
			throws InvalidValueException {
		this();
		setName(aName);
		setWeight(aWeight);
	}

	public Object clone() throws CloneNotSupportedException {
		return (EPPSuggestionAction) super.clone();
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
		setWeight(aElement.getAttribute(ATT_WEIGHT));
		if (weight == null) ExceptionUtil.missingDuringDecode("weight");
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
		if (!weight.isSet()) ExceptionUtil.missingDuringEncode("weight");

		root.setAttribute(ATT_NAME, name);
		root.setAttribute(ATT_WEIGHT, weight.toString());

		return root;
	}

	/**
	 * implements a deep <code>EPPSuggestionTld</code> compare.
	 * @param aObject <code>EPPSuggestionTld</code> instance to compare with
	 * @return DOCUMENT ME!
	 */
	public boolean equals(Object o) {
		if ((o != null) && (o.getClass().equals(this.getClass()))) {
			EPPSuggestionAction other = (EPPSuggestionAction) o;
			if (!EqualityUtil.equals(this.name, other.name)) return false;
			if (!this.weight.equals(other.weight)) return false;
			return true;
		}
		return false;
	}

	public String getName() {
		return name;
	}

	public WeightEnum getWeight() {
		return weight;
	}

	public void setName(String name) {
		if (name != null) name = name.toLowerCase();
		this.name = name;
	}

	public void setWeight(String weight) throws InvalidValueException {
		this.weight.set(weight);
	}

	public String toLogString() {
		return name + ":" + weight;
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