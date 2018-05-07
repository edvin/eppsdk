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
package com.verisign.epp.transport;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.CloneNotSupportedException;


/**
 * EPPEventTstHandler : This class is not part of Implementatin. It is provided
 * for unit testing, Debuging and it may also serve as a Sample Code also.
 *
 * @author P. Amiri
 * @version $Id: EPPEventTstHandler.java,v 1.2 2004/01/26 21:21:06 jim Exp $
 *
 * @since JDK1.0
 */
public class EPPEventTstHandler implements ServerEventHandler {
	/**
	 * @see java.io
	 */
	private PrintWriter myPrintWriter = null;

	/**
	 * @see java.io
	 */
	private BufferedReader myBufferReader = null;

	/**
	 * @see java.io
	 */
	private InputStream myInputStream = null;

	/**
	 * @see java.io
	 */
	private OutputStream myOutputStream = null;

	/**
	 * DOCUMENT ME!
	 *
	 * @param newInputStream DOCUMENT ME!
	 * @param newOutputStream DOCUMENT ME!
	 */
	public void handleConnection(
								 InputStream newInputStream,
								 OutputStream newOutputStream) {
		myInputStream	   = newInputStream;
		myOutputStream     = newOutputStream;
		myPrintWriter	   = new PrintWriter(myOutputStream, true);
		myBufferReader =
			new BufferedReader(new InputStreamReader(myInputStream));

		/** this is a test */
		BufferedReader inBuffer = null;

		try {
			inBuffer =
				new BufferedReader(new InputStreamReader(new FileInputStream(new File("TestSession.xml"))));
		}
		 catch (IOException myException) {
			System.out.print("This is a exeption" + myException.getMessage());
		}

		String fromFile = null;
		String fromUser = null;
		int    cnt	    = 0;

		try {
			while ((fromFile = inBuffer.readLine()) != null) {
				cnt++;
				myPrintWriter.println(fromFile);
				System.out.println("Server (" + cnt + "): " + fromFile);
			}
		}
		 catch (IOException myException) {
			System.out.print("This is a exeption" + myException.getMessage());
		}

		try {
			inBuffer.close();
		}
		 catch (IOException myException) {
			System.out.print("This is a exeption" + myException.getMessage());
		}

		try {
			while ((fromUser = myBufferReader.readLine()) != null) {
				System.out.println("User: " + fromUser);

				if (fromUser.equals("Bye.")) {
					break;
				}
			}
		}
		 catch (IOException myException) {
			System.out.print("This is a exeption" + myException.getMessage());
		}

		//		try {
		myPrintWriter.println("Bye.");

		//		}
		//		catch (IOException myException) {
		//				   System.out.print("This is a exeption" +
		//				   myException.getMessage());
		//		}
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 *
	 * @throws CloneNotSupportedException DOCUMENT ME!
	 */
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
