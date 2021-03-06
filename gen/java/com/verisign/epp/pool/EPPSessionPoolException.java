/***********************************************************
Copyright (C) 2004 VeriSign, Inc.

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.Ê See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MAÊ 02111-1307Ê USA

http://www.verisign.com/nds/naming/namestore/techdocs.html
***********************************************************/
package com.verisign.epp.pool;

public class EPPSessionPoolException extends Exception {
	/**
	 * Constructor for <code>EPPSessionPoolException</code> that requires a info
	 * <code>String</code> description.
	 *
	 * @param info text description
	 */
	public EPPSessionPoolException(String info) {
		super(info);
	}

	// End EPPSessionPoolException.EPPSessionPoolException(String)
}
