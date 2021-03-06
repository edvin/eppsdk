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
package com.verisign.epp.framework;

import java.io.InputStream;

//----------------------------------------------
//
// imports...
//
//----------------------------------------------
//Java Core imports
import java.io.OutputStream;

//EPP imports
import com.verisign.epp.codec.gen.EPPMessage;


/**
 * The EPPAssembler interface defines an interface for serializing EPPEvent
 * objects and EPPEventResponse objects.  Implementing classes should define
 * the format of the input and output streams.     <br><br>
 *
 * @author $Author: jim $
 * @version $Revision: 1.3 $
 *
 * @see com.verisign.epp.framework.EPPXMLAssembler
 * @see com.verisign.epp.framework.EPPEventResponse
 * @see com.verisign.epp.framework.EPPEvent
 * @see com.verisign.epp.framework.EPPAssemblerException
 */
public interface EPPAssembler {
	/**
	 * Takes an <code> EPPEventResponse </code> and serializes it to an <code>
	 * OutputStream </code>
	 *
	 * @param aResponse The response that will be serialized
	 * @param aOutputStream The OutputStream that the response will be
	 * 		  serialized to.
	 *
	 * @exception EPPAssemblerException Error serializing the
	 * 			  <code>EPPEventResponse</code>
	 */
	public void toStream(
						 EPPEventResponse aResponse, OutputStream aOutputStream,
						 Object aData) throws EPPAssemblerException;

	/**
	 * Takes an <code> InputStream </code> and creates a <code> EPPEvent
	 * </code>
	 *
	 * @param aInputStream The InputStream to read data from.
	 * @param aData Optional Client Data <code>Object</code>
	 *
	 * @return EPPEvent The <code> EPPEvent </code> that is created from the
	 * 		   InputStream
	 *
	 * @exception EPPAssemblerException Error creating the <code> EPPEvent
	 * 			  </code>
	 */
	public EPPEvent toEvent(InputStream aInputStream, Object aData)
					 throws EPPAssemblerException;
}
