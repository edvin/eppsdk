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

import java.io.Serializable;

/**
 * This class doesn't really implement a complete Unsigned Long because it uses
 * a long primitive. The primitive is simply prohibited from being negative, so
 * the maximum value of this class is half of the maximum value of a true
 * UnsignedLong value.
 * 
 * @author jcolosi
 */
public class UnsignedLong implements Serializable {

	static public long NONE = -1;
	static public String NONE_STRING = "";
	protected long key = NONE;

	public UnsignedLong() {}

	public UnsignedLong(long key) throws InvalidValueException {
		set(key);
	}

	public UnsignedLong(String value) throws InvalidValueException {
		set(value);
	}

	public Object clone() throws CloneNotSupportedException {
		try {
			return new UnsignedLong(key);
		} catch (InvalidValueException x) {
			return null;
		}
	}

	public boolean equals(Object o) {
		if ((o != null) && (o.getClass().equals(this.getClass()))) {
			UnsignedLong other = (UnsignedLong) o;
			return this.key == other.key;
		}
		return false;
	}

	public long get() {
		return key;
	}

	public boolean isSet() {
		return key != NONE;
	}

	public void set(long key) throws InvalidValueException {
		if (key >= 0) this.key = key;
		else throw new InvalidValueException(key);
	}

	public void set(String s) throws InvalidValueException {
		if (s == null || s.length() == 0) key = NONE;
		else set(Long.parseLong(s));
	}

	public String toString() {
		if (isSet()) return "" + key;
		return NONE_STRING;
	}

	public void unset() {
		this.key = NONE;
	}

	public UnsignedLong getRandom() {
		try {
			return new UnsignedLong((long) ((Math.random() * 75) + 25));
		} catch (InvalidValueException x) {
			return null;
		}
	}
}
