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
 * An abstract class which provides backbone features of the various
 * Enumerations in the sdk.
 * @author jcolosi
 */
public abstract class Enum implements Serializable {

	static public int NONE = -1;
	static public String NONE_STRING = "";

	static protected boolean p(double d) {
		return RandomHelper.get() < d;
	}

	protected int key = NONE;

	public Enum() {}

	public Enum(String value) throws InvalidValueException {
		set(value);
	}

	/**
	 * Use this constructor for cloning.
	 */
	protected Enum(int key) throws InvalidValueException {
		set(key);
	}

	abstract public Object clone() throws CloneNotSupportedException;

	public boolean equals(Object o) {
		if ((o != null) && (o.getClass().equals(this.getClass()))) {
			Enum other = (Enum) o;
			return this.key == other.key;
		}
		return false;
	}

	public int get() {
		return key;
	}

	abstract public String[] getData();

	public boolean isSet() {
		return key != NONE;
	}

	public void set(int x) throws InvalidValueException {
		String[] data = getData();
		if (x < 0 || x >= data.length) throw new InvalidValueException(x);
		this.key = x;
	}

	public void set(String s) throws InvalidValueException {
		this.key = decode(s);
	}

	public String toLogString() {
		if (isSet()) return "" + getData()[key].charAt(0);
		return NONE_STRING;
	}

	public String toString() {
		if (isSet()) return getData()[key];
		return NONE_STRING;
	}

	public void unset() {
		this.key = NONE;
	}

	private int decode(String s) throws InvalidValueException {
		if (s == null || s.length() == 0) return NONE;
		String[] data = getData();
		for (int i = 0; i < data.length; i++) {
			if (s.equalsIgnoreCase(data[i])) return i;
		}
		throw new InvalidValueException(s);
	}
}