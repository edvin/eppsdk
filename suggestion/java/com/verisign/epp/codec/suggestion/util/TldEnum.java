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
package com.verisign.epp.codec.suggestion.util;

/**
 * An enumeration of supported tlds. This is used only to generate random
 * commands for the unit test. The server can add or remove tlds so this list is
 * not authoritative.
 * 
 * @author jcolosi
 */
public class TldEnum extends Enum {

	static private String[] data = { "com", "net", "tv", "cc" };

	public TldEnum() {}

	public TldEnum(String value) throws InvalidValueException {
		super(value);
	}

	private TldEnum(int key) throws InvalidValueException {
		super(key);
	}

	public Object clone() throws CloneNotSupportedException {
		try {
			return new TldEnum(key);
		} catch (InvalidValueException x) {
			return null;
		}
	}

	public String[] getData() {
		return data;
	}

	static public String getRandomString() {
		if (p(.5)) return data[RandomHelper.getInt(data.length)].toLowerCase();
		else return data[RandomHelper.getInt(data.length)].toUpperCase();
	}

}