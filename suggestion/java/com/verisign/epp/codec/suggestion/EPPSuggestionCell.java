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
import com.verisign.epp.codec.suggestion.util.StatusEnum;
import com.verisign.epp.codec.suggestion.util.UnsignedShort;
import com.verisign.epp.util.EqualityUtil;

/**
 * A Cell is an element within a Record, which in turn is an element within the
 * Grid response. A Cell associates a score and status with a TLD. The
 * encapsulating record further associates a domain with these scores.
 * @author jcolosi
 */
public class EPPSuggestionCell implements EPPCodecComponent {
	static private final String ATT_TLD = "tld";
	static private final String ATT_SCORE = "score";
	static private final String ATT_STATUS = "status";
	final static String ELM_NAME = "suggestion:cell";

	private String tld = null;
	private UnsignedShort score = new UnsignedShort(); // non-null
	private StatusEnum status = new StatusEnum(); // non-null

	/**
	 * <code>EPPSuggestionTld</code> default constructor.
	 */
	public EPPSuggestionCell() {}

	public EPPSuggestionCell(Element element) throws EPPDecodeException {
		decode(element);
	}

	public EPPSuggestionCell(String aTld, short aScore, String aStatus)
			throws InvalidValueException {
		this();
		setTld(aTld);
		setScore(aScore);
		setStatus(aStatus);
	}

	/**
	 * Clone <code>EPPSuggestionTld</code>.
	 * @return clone of <code>EPPSuggestionTld</code>
	 * @exception CloneNotSupportedException standard Object.clone exception
	 */
	public Object clone() throws CloneNotSupportedException {
		return (EPPSuggestionCell) super.clone();
	}

	/**
	 * Decode the EPPSuggestionTld attributes from the aElement DOM Element
	 * tree.
	 * @param aElement - Root DOM Element to decode EPPSuggestionTld from.
	 * @exception EPPDecodeException Unable to decode aElement
	 */
	public void decode(Element aElement) throws EPPDecodeException {
		setTld(aElement.getAttribute(ATT_TLD));
		if (tld == null) ExceptionUtil.missingDuringDecode("tld");
		setScore(aElement.getAttribute(ATT_SCORE));
		if (!score.isSet()) ExceptionUtil.missingDuringDecode("score");
		setStatus(aElement.getAttribute(ATT_STATUS));
		if (!status.isSet()) ExceptionUtil.missingDuringDecode("status");
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
		if (tld == null) ExceptionUtil.missingDuringEncode("tld");
		if (!score.isSet()) ExceptionUtil.missingDuringEncode("score");
		if (!status.isSet()) ExceptionUtil.missingDuringEncode("status");
		root.setAttribute(ATT_TLD, tld);
		root.setAttribute(ATT_SCORE, score.get() + "");
		root.setAttribute(ATT_STATUS, status + "");
		return root;
	}

	/**
	 * implements a deep <code>EPPSuggestionTld</code> compare.
	 * @param aObject <code>EPPSuggestionTld</code> instance to compare with
	 * @return DOCUMENT ME!
	 */
	public boolean equals(Object o) {
		if ((o != null) && (o.getClass().equals(this.getClass()))) {
			EPPSuggestionCell other = (EPPSuggestionCell) o;
			if (!EqualityUtil.equals(this.tld, other.tld)) return false;
			if (!this.score.equals(other.score)) return false;
			if (!this.status.equals(other.status)) return false;
			return true;
		}
		return false;
	}

	public String getTld() {
		return tld;
	}

	public UnsignedShort getScore() {
		return score;
	}

	public StatusEnum getStatus() {
		return status;
	}

	public void setTld(String tld) {
		this.tld = tld;
	}

	public void setScore(short score) throws InvalidValueException {
		this.score.set(score);
	}

	public void setScore(String score) throws InvalidValueException {
		this.score.set(score);
	}

	public void setScore(UnsignedShort score) {
		this.score = score;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

	public void setStatus(String status) throws InvalidValueException {
		this.status.set(status);
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