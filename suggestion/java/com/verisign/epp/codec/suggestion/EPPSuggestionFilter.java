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
import java.util.HashMap;
import java.util.HashSet;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.verisign.epp.codec.gen.EPPCodecComponent;
import com.verisign.epp.codec.gen.EPPDecodeException;
import com.verisign.epp.codec.gen.EPPEncodeException;
import com.verisign.epp.codec.gen.EPPUtil;
import com.verisign.epp.codec.suggestion.util.InvalidValueException;
import com.verisign.epp.codec.suggestion.util.UnsignedShort;
import com.verisign.epp.codec.suggestion.util.WeightEnum;
import com.verisign.epp.util.EqualityUtil;

/**
 * A Filter encapsulates all of the configurable aspects of a Suggestion
 * request.
 * @author jcolosi
 */
public class EPPSuggestionFilter implements EPPCodecComponent {

	static private final String ATT_CONTENTFILTER = "contentfilter";
	static private final String ATT_CUSTOMFILTER = "customfilter";
	static private final String ATT_FORSALE = "forsale";
	static private final String ATT_MAXLENGTH = "maxlength";
	static private final String ATT_MAXRESULTS = "maxresults";
	static private final String ATT_USEHYPHENS = "usehyphens";
	static private final String ATT_USENUMBERS = "usenumbers";
	static private final String ATT_VIEW = "view";
	static private final String GRID_STRING = "grid";
	static private final Boolean GRID_VIEW = Boolean.FALSE;
	static private final String TABLE_STRING = "table";
	static private final Boolean TABLE_VIEW = Boolean.TRUE;
	static private final String ELM_NAME = "suggestion:filter";

	static private String abbreviate(Boolean bool) {
		if (bool == null) return "";
		else if (bool.booleanValue()) return "t";
		else return "f";
	}

	private HashMap actionMap = null;
	private ArrayList actions = null;
	private Boolean contentFilter = null;
	private Boolean customFilter = null;
	private WeightEnum forsale = new WeightEnum();
	private UnsignedShort maxLength = new UnsignedShort();
	private UnsignedShort maxResults = new UnsignedShort();
	private ArrayList tlds = null;
	private HashSet tldSet = null;
	private Boolean useHyphens = null;
	private Boolean useNumbers = null;
	private Boolean view = null;

	/**
	 * <code>EPPSuggestionTld</code> default constructor.
	 */
	public EPPSuggestionFilter() {}

	public EPPSuggestionFilter(Element element) throws EPPDecodeException {
		this();
		decode(element);
	}

	public void addAction(EPPSuggestionAction action) throws InvalidValueException {
		if (action == null) throw new InvalidValueException("Cannot add a null action");
		if (actions == null) resetActions();
		actions.add(action);
		actionMap.put(action.getName().toLowerCase(), action.getWeight());
	}

	public void addTld(EPPSuggestionTld tld) throws InvalidValueException {
		if (tld == null) throw new InvalidValueException("Cannot add a null tld");
		if (tlds == null) resetTlds();
		tlds.add(tld);
		tldSet.add(tld.getTld());
	}

	/**
	 * Clone <code>EPPSuggestionTld</code>.
	 * @return clone of <code>EPPSuggestionTld</code>
	 * @exception CloneNotSupportedException standard Object.clone exception
	 */
	public Object clone() throws CloneNotSupportedException {
		return (EPPSuggestionFilter) super.clone();
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
		int size = nodes.getLength();
		if (size > 0) {
			String name = null;
			for (int i = 0; i < size; i++) {
				node = nodes.item(i);
				if (node instanceof Element) {
					name = node.getNodeName();
					if (name.equals(EPPSuggestionTld.ELM_NAME)) {
						addTld(new EPPSuggestionTld((Element) node));
					} else if (name.equals(EPPSuggestionAction.ELM_NAME)) {
						addAction(new EPPSuggestionAction((Element) node));
					}
				}
			}
		}

		setContentFilter(aElement.getAttribute(ATT_CONTENTFILTER));
		setCustomFilter(aElement.getAttribute(ATT_CUSTOMFILTER));
		setForSale(aElement.getAttribute(ATT_FORSALE));
		setMaxLength(aElement.getAttribute(ATT_MAXLENGTH));
		setMaxResults(aElement.getAttribute(ATT_MAXRESULTS));
		setUseHyphens(aElement.getAttribute(ATT_USEHYPHENS));
		setUseNumbers(aElement.getAttribute(ATT_USENUMBERS));
		setView(aElement.getAttribute(ATT_VIEW));
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

		if (actions != null) EPPUtil.encodeCompList(aDocument, root, actions);
		if (tlds != null) EPPUtil.encodeCompList(aDocument, root, tlds);

		if (contentFilter != null) root.setAttribute(ATT_CONTENTFILTER, contentFilter
				.toString());
		if (customFilter != null) root.setAttribute(ATT_CUSTOMFILTER, customFilter
				.toString());
		if (forsale.isSet()) root.setAttribute(ATT_FORSALE, forsale.toString());
		if (maxLength.isSet()) root.setAttribute(ATT_MAXLENGTH, maxLength.toString());
		if (maxResults.isSet()) root
				.setAttribute(ATT_MAXRESULTS, maxResults.toString());
		if (useHyphens != null) root
				.setAttribute(ATT_USEHYPHENS, useHyphens.toString());
		if (useNumbers != null) root
				.setAttribute(ATT_USENUMBERS, useNumbers.toString());
		if (view != null) root.setAttribute(ATT_VIEW, getViewString());

		return root;
	}

	/**
	 * implements a deep <code>EPPSuggestionTld</code> compare.
	 * @param aObject <code>EPPSuggestionTld</code> instance to compare with
	 * @return DOCUMENT ME!
	 */
	public boolean equals(Object o) {
		if ((o != null) && (o.getClass().equals(this.getClass()))) {
			EPPSuggestionFilter other = (EPPSuggestionFilter) o;
			if (!EqualityUtil.equals(this.actions, other.actions)) return false;
			if (!EqualityUtil.equals(this.tlds, other.tlds)) return false;
			if (!EqualityUtil.equals(this.contentFilter, other.contentFilter)) return false;
			if (!EqualityUtil.equals(this.customFilter, other.customFilter)) return false;
			if (!this.forsale.equals(other.forsale)) return false;
			if (!this.maxLength.equals(other.maxLength)) return false;
			if (!this.maxResults.equals(other.maxResults)) return false;
			if (!EqualityUtil.equals(this.useHyphens, other.useHyphens)) return false;
			if (!EqualityUtil.equals(this.useNumbers, other.useNumbers)) return false;
			if (!EqualityUtil.equals(this.view, other.view)) return false;
			return true;
		}
		return false;
	}

	public HashMap getActionMap() {
		return actionMap;
	}

	public ArrayList getActions() {
		return actions;
	}

	public WeightEnum getActionWeight(String name) {
		return (WeightEnum) actionMap.get(name.toLowerCase());
	}

	public Boolean getContentFilter() {
		return contentFilter;
	}

	public Boolean getCustomFilter() {
		return customFilter;
	}

	public WeightEnum getForsale() {
		return forsale;
	}

	public UnsignedShort getMaxLength() {
		return maxLength;
	}

	public UnsignedShort getMaxResults() {
		return maxResults;
	}

	public ArrayList getTlds() {
		return tlds;
	}

	public HashSet getTldSet() {
		return tldSet;
	}

	public Boolean getUseHyphens() {
		return useHyphens;
	}

	public Boolean getUseNumbers() {
		return useNumbers;
	}

	public Boolean getView() {
		return view;
	}

	public String getViewString() {
		if (view == null) return null;
		if (view.equals(TABLE_VIEW)) return TABLE_STRING;
		else return GRID_STRING;
	}

	public boolean hasAction(String name) {
		return name != null && actionMap != null
				&& actionMap.containsKey(name.toLowerCase());
	}

	public boolean hasTld(String tld) {
		return tld != null && tldSet != null && tldSet.contains(tld);
	}

	public boolean isGrid() {
		if (this.view != null) return !this.view.booleanValue();
		else return false;
	}

	public boolean isTable() {
		if (this.view != null) return this.view.booleanValue();
		else return false;
	}

	public void resetActions() {
		actions = new ArrayList();
		actionMap = new HashMap();
	}

	public void resetTlds() {
		tlds = new ArrayList();
		tldSet = new HashSet();
	}

	public void setContentFilter(boolean contentFilter) {
		this.contentFilter = new Boolean(contentFilter);
	}

	public void setContentFilter(String s) {
		if (s != null && s.length() > 0) this.contentFilter = new Boolean(s);
	}

	public void setCustomFilter(boolean customfilter) {
		this.customFilter = new Boolean(customfilter);
	}

	public void setCustomFilter(String s) {
		if (s != null && s.length() > 0) this.customFilter = new Boolean(s);
	}

	public void setForSale(String forsale) throws InvalidValueException {
		this.forsale.set(forsale);
	}

	public void setGridView() {
		view = GRID_VIEW;
	}

	public void setMaxLength(short maxlength) throws InvalidValueException {
		this.maxLength.set(maxlength);
	}

	public void setMaxLength(String maxlength) throws InvalidValueException {
		this.maxLength.set(maxlength);
	}

	public void setMaxResults(short maxresults) throws InvalidValueException {
		this.maxResults.set(maxresults);
	}

	public void setMaxResults(String maxresults) throws InvalidValueException {
		this.maxResults.set(maxresults);
	}

	public void setTableView() {
		view = TABLE_VIEW;
	}

	public void setUseHyphens(boolean usehyphens) {
		this.useHyphens = new Boolean(usehyphens);
	}

	public void setUseHyphens(String s) {
		if (s != null && s.length() > 0) this.useHyphens = new Boolean(s);
	}

	public void setUseNumbers(boolean usenumbers) {
		this.useNumbers = new Boolean(usenumbers);
	}

	public void setUseNumbers(String s) {
		if (s != null && s.length() > 0) this.useNumbers = new Boolean(s);
	}

	public void setView(String aView) throws InvalidValueException {
		if (aView == null || aView.length() == 0) this.view = null;
		else if (aView.equalsIgnoreCase(TABLE_STRING)) setTableView();
		else if (aView.equalsIgnoreCase(GRID_STRING)) setGridView();
		else throw new InvalidValueException(aView);
	}

	public String toLogString() {
		StringBuffer out = new StringBuffer();
		out.append("cf:" + abbreviate(contentFilter));
		out.append(" uf:" + abbreviate(customFilter));
		out.append(" fs:" + forsale.toLogString());
		out.append(" ml:" + maxLength);
		out.append(" mr:" + maxResults);
		out.append(" uh:" + abbreviate(useHyphens));
		out.append(" un:" + abbreviate(useNumbers));
		out.append(" vw:" + abbreviate(view));

		out.append(" action:");
		if (actions != null && actions.size() > 0) {
			int size = actions.size();
			if (size > 0) out.append(((EPPSuggestionAction) actions.get(0))
					.toLogString());
			for (int i = 1; i < size; i++) {
				out.append("," + ((EPPSuggestionAction) actions.get(i)).toLogString());
			}
		}

		out.append(" tld:");
		if (tlds != null && tlds.size() > 0) {
			int size = tlds.size();
			if (size > 0) out.append(((EPPSuggestionTld) tlds.get(0)).toLogString());
			for (int i = 1; i < size; i++) {
				out.append("," + ((EPPSuggestionTld) tlds.get(i)).toLogString());
			}
		}
		return out.toString();
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
