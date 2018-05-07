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
package com.verisign.epp.util;

import com.verisign.epp.exception.EPPException;


/**
 * Thrown when an unexpected condition or undesirable behavior is encountered
 * pertaining to this package.
 *
 * @author P. Amiri
 * @version $Id: EPPEnvException.java,v 1.2 2004/01/26 21:21:07 jim Exp $
 *
 * @see java.lang.Exception
 * @since JDK1.0
 */
public class EPPEnvException extends EPPException {
	/**
	 * Constructs an Exception with the specified detail message. This message
	 * should provide information about the source, reasons and any other
	 * useful facts that could justifies the reason the exception is thrown.
	 *
	 * @param newDescription String containing a detailed message.
	 *
	 * @see java.lang.Exception#Exception(String newDesc)
	 */
	public EPPEnvException(String newDescription) {
		super(newDescription);
	}

	/**
	 * This Constructor is private in order to discourage its usage. The
	 * message is intended to provide information about the source and reasons
	 * the exception is thrown, which could be very useful for identifying and
	 * reason the unexpected behaviors is occurred.
	 *
	 * @see java.lang.Exception#Exception()
	 */
	private EPPEnvException() {
		super("Unknow Reason EPPEnvException is invoked");
	}
}
