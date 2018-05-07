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
import com.verisign.epp.util.EqualityUtil;

/**
 * This component represents a Suggestion answer. The answer can be either a
 * Table or a Grid. The XML schema requires one or the other but not both. This
 * implementation does not prohibit having both a grid and a table. That
 * exercise is left up to the schema validator.
 * @author jcolosi
 */
public class EPPSuggestionAnswer implements EPPCodecComponent {
	/** XML Element Name of <code>EPPSuggestionTld</code> root element. */
	final static String ELM_NAME = "suggestion:answer";

	static private final String ELM_GRID = "suggestion:grid";
	static private final String ELM_TABLE = "suggestion:table";

	private EPPSuggestionGrid grid = null;
	private EPPSuggestionTable table = null;

	public EPPSuggestionAnswer() {}

	public EPPSuggestionAnswer(EPPSuggestionGrid aGrid) {
		this.grid = aGrid;
	}

	public EPPSuggestionAnswer(EPPSuggestionTable aTable) {
		this.table = aTable;
	}

	public EPPSuggestionAnswer(Element element) throws EPPDecodeException {
		decode(element);
	}

	public Object clone() throws CloneNotSupportedException {
		return (EPPSuggestionAnswer) super.clone();
	}

	public void decode(Element aElement) throws EPPDecodeException {
		table = (EPPSuggestionTable) EPPUtil.decodeComp(aElement,
				EPPSuggestionMapFactory.NS, ELM_TABLE, EPPSuggestionTable.class);
		grid = (EPPSuggestionGrid) EPPUtil.decodeComp(aElement,
				EPPSuggestionMapFactory.NS, ELM_GRID, EPPSuggestionGrid.class);
	}

	public Element encode(Document aDocument) throws EPPEncodeException {
		Element root = aDocument.createElementNS(EPPSuggestionMapFactory.NS, ELM_NAME);

		if (table != null) EPPUtil.encodeComp(aDocument, root, table);
		if (grid != null) EPPUtil.encodeComp(aDocument, root, grid);
		return root;
	}

	/**
	 * implements a deep <code>EPPSuggestionTld</code> compare.
	 * @param aObject <code>EPPSuggestionTld</code> instance to compare with
	 * @return DOCUMENT ME!
	 */
	public boolean equals(Object o) {
		if ((o != null) && (o.getClass().equals(this.getClass()))) {
			EPPSuggestionAnswer other = (EPPSuggestionAnswer) o;
			if (!EqualityUtil.equals(this.table, other.table)) return false;
			if (!EqualityUtil.equals(this.grid, other.grid)) return false;
			return true;
		}
		return false;
	}

	public EPPSuggestionGrid getGrid() {
		return grid;
	}

	public EPPSuggestionTable getTable() {
		return table;
	}

	public void setGrid(EPPSuggestionGrid grid) {
		this.grid = grid;
	}

	public void setTable(EPPSuggestionTable table) {
		this.table = table;
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
