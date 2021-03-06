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
package com.verisign.epp.serverstub;

import com.verisign.epp.codec.domain.*;
import com.verisign.epp.codec.gen.*;

// java imports
// EPP Imports
import com.verisign.epp.framework.*;


/**
 * The &lt;DomainPollHandler&gt; implements EPPPollHandler for Domain name
 * mapping only. <br><br>
 *
 * @author $Author: jim $
 * @version $Revision: 1.5 $
 */
public class DomainPollHandler implements EPPPollHandler {
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public String getKind() {
		return EPPDomainMapFactory.NS;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param aRecord DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 *
	 * @throws EPPPollQueueException DOCUMENT ME!
	 */
	public EPPResponse toResponse(EPPPollDataRecord aRecord)
						   throws EPPPollQueueException {
		if (!aRecord.getKind().equals(getKind())) {
			throw new EPPPollQueueException("Handler for kind "
											+ aRecord.getKind()
											+ " does not match");
		}

		// Get the concrete response from the record
		EPPResponse theResponse = (EPPResponse) aRecord.getData();

		if (theResponse instanceof EPPDomainTransferResp) {
			// Set the poll message generic information
			theResponse.setMsgQueue(new EPPMsgQueue(
													new Long(aRecord.getSize()),
													aRecord.getMsgId(),
													aRecord.getQDate(),
													"Transfer Requested."));
		}
		else if (theResponse instanceof EPPDomainPendActionMsg) {
			theResponse.setMsgQueue(new EPPMsgQueue(
													new Long(aRecord.getSize()),
													aRecord.getMsgId(),
													aRecord.getQDate(),
													"Pending action completed successfully."));
		}
		else {
			throw new EPPPollQueueException("Unable to handle message class <"
											+ theResponse.getClass().getName());
		}

		theResponse.setResult(EPPResult.SUCCESS_POLL_MSG);

		return theResponse;
	}
}
