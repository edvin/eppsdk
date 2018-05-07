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
 * An enumeration of the types of weights. This is used only to generate random
 * commands for the unit test. The server can add or remove weights so this list
 * is not authoritative.
 * 
 * @author jcolosi
 */
public class WeightEnum extends Enum {

	static private String[] data = { "off", "low", "medium", "high" };

	public WeightEnum() {}

	private WeightEnum(int key) throws InvalidValueException {
		super(key);
	}

	public WeightEnum(String value) throws InvalidValueException {
		super(value);
	}

	public Object clone() throws CloneNotSupportedException {
		try {
			return new WeightEnum(key);
		} catch (InvalidValueException x) {
			return null;
		}
	}

	public String[] getData() {
		return data;
	}

	static public String getRandomString() {
		return data[RandomHelper.getInt(data.length)];
	}

}
