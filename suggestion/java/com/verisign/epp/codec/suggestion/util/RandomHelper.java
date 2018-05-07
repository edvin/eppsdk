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

import java.util.Random;

import com.verisign.epp.codec.gen.EPPResult;
import com.verisign.epp.codec.gen.EPPTransId;
import com.verisign.epp.codec.suggestion.EPPSuggestionAction;
import com.verisign.epp.codec.suggestion.EPPSuggestionAnswer;
import com.verisign.epp.codec.suggestion.EPPSuggestionCell;
import com.verisign.epp.codec.suggestion.EPPSuggestionConstants;
import com.verisign.epp.codec.suggestion.EPPSuggestionFilter;
import com.verisign.epp.codec.suggestion.EPPSuggestionGrid;
import com.verisign.epp.codec.suggestion.EPPSuggestionInfoCmd;
import com.verisign.epp.codec.suggestion.EPPSuggestionInfoResp;
import com.verisign.epp.codec.suggestion.EPPSuggestionRecord;
import com.verisign.epp.codec.suggestion.EPPSuggestionRelated;
import com.verisign.epp.codec.suggestion.EPPSuggestionRow;
import com.verisign.epp.codec.suggestion.EPPSuggestionTable;
import com.verisign.epp.codec.suggestion.EPPSuggestionTld;
import com.verisign.epp.codec.suggestion.EPPSuggestionToken;

/**
 * This class knows how to construct a random command or response facilitating
 * the random unit test framework.
 * @author jcolosi
 */
public class RandomHelper {

	static private Random random = new Random();

	static public double get() {
		return random.nextDouble();
	}

	static public EPPSuggestionInfoCmd getCommand() throws InvalidValueException {

		EPPSuggestionInfoCmd cmd = new EPPSuggestionInfoCmd("51364-CLI");
		cmd.setKey("alphabravocharlie.com");
		cmd.setLanguage(EPPSuggestionConstants.DEFAULT_LANGUAGE);

		if (p(.9)) {
			if (p(.2)) cmd.setFilterId(RandomHelper.getLong(1000000));
			else {
				EPPSuggestionFilter filter = new EPPSuggestionFilter();
				if (p(.9)) filter.setContentFilter(p(.5));
				if (p(.9)) filter.setCustomFilter(p(.5));
				if (p(.9)) filter.setForSale(WeightEnum.getRandomString());
				if (p(.9)) filter.setMaxLength((short) RandomHelper.getInt(20, 30));
				if (p(.9)) filter.setMaxResults((short) RandomHelper.getInt(10, 20));
				if (p(.9)) filter.setUseHyphens(p(.5));
				if (p(.9)) filter.setUseNumbers(p(.5));
				if (p(.9)) {
					if (p(.5)) filter.setTableView();
					else filter.setGridView();
				}

				double d;
				String name = null;
				String weight = null;

				d = .8;
				while (p(d)) {
					if (p(.1)) name = "DUMMY";
					else name = ActionEnum.getRandomString();
					weight = WeightEnum.getRandomString();
					filter.addAction(new EPPSuggestionAction(name, weight));
					d -= .2;
				}

				d = .8;
				while (p(d)) {
					if (p(.1)) name = "DUMMY";
					else name = TldEnum.getRandomString();
					filter.addTld(new EPPSuggestionTld(name));
					d -= .2;
				}

				cmd.setFilter(filter);
			}
		}

		return cmd;
	}

	static public int getInt() {
		return random.nextInt();
	}

	static public int getInt(int r) {
		return (int) (random.nextDouble() * r);
	}

	static public int getInt(int n, int r) {
		return (int) ((random.nextDouble() * r) + n);
	}

	static public long getLong() {
		return random.nextLong();
	}

	static public long getLong(long r) {
		return (long) (random.nextDouble() * r);
	}

	static public long getLong(long n, long r) {
		return (long) ((random.nextDouble() * r) + n);
	}

	/**
	 * There was an issue with empty elements. The table, grid, and record
	 * elements can occasionally be returned without any sub elements. I have
	 * added code to generate these records in the random responses.
	 */
	static public EPPSuggestionInfoResp getResponse(String aClientTransId)
			throws InvalidValueException {
		EPPSuggestionAnswer answer = null;

		EPPTransId aTransId = new EPPTransId(aClientTransId, "SRV-43659");
		EPPSuggestionInfoResp theResponse = new EPPSuggestionInfoResp(aTransId);

		theResponse.setKey("AlphaBravoCharlie.com");
		theResponse.setLanguage(EPPSuggestionConstants.DEFAULT_LANGUAGE);

		// Create tokens and related words
		if (p(.75)) { // 75% of the time, there are no tokens
			String[] TOKEN = { "Alpha", "Bravo", "Charlie", "Delta", "Echo" };
			int i = 0;
			double d = 1;
			while (p(d)) {
				String tokenName = TOKEN[i++];
				EPPSuggestionToken token = new EPPSuggestionToken(tokenName);
				int j = 0;
				double d2 = .9; // 10% of the time the token is empty
				while (p(d2)) {
					token.addRelated(new EPPSuggestionRelated(tokenName + (j++)));
					d2 -= .2;
				}
				theResponse.addToken(token);
				d -= .2;
			}
		}

		String[] NAME = { "AlphaBravoCharlie1", "AlphaBravoCharlie2",
				"AlphaBravoCharlie3", "AlphaBravoCharlie4", "AlphaBravoCharlie5",
				"AlphaBravoCharlie6" };

		String[] TLD = { "com", "net", "cc", "tv", "DUMMY" };

		if (p(.9)) {
			answer = new EPPSuggestionAnswer();

			// Grid
			if (p(.5)) {
				EPPSuggestionGrid grid = new EPPSuggestionGrid();
				int i = RandomHelper.getInt(NAME.length);
				double d = .9; // 10% of the time the grid is empty
				while (p(d)) {
					EPPSuggestionRecord record = new EPPSuggestionRecord(NAME[i++]);
					if (p(.5)) record.setSource("dummy");
					if (p(.5)) record.setMoreLikeThis("http://dummy.com");
					if (p(.5)) record.setPpcValue(RandomHelper.getInt());

					int j = RandomHelper.getInt(TLD.length);
					double d2 = .9; // 10% of the time the record is empty
					short score = 1000;
					while (p(d2)) {
						record.addCell(new EPPSuggestionCell(TLD[j++], score,
								StatusEnum.getRandomString()));
						j %= TLD.length;
						d2 -= .2;
						score -= RandomHelper.getInt(10, 20);
					}

					grid.addRecord(record);
					i %= NAME.length;
					d -= .2;
				}
				answer.setGrid(grid);
			}

			// Table
			else {
				EPPSuggestionTable table = new EPPSuggestionTable();
				EPPSuggestionRow row;
				int i = RandomHelper.getInt(NAME.length);
				int j = RandomHelper.getInt(TLD.length);
				double d = .9; // 10% of the time the table is empty
				short score = 1000;
				while (p(d)) {
					if (p(.5)) {
						row = new EPPSuggestionRow(NAME[i++] + "." + TLD[j++], score,
								StatusEnum.getRandomString());
					} else {
						row = new EPPSuggestionRow(NAME[i++] + "." + TLD[j++], score,
								StatusEnum.getRandomString(), "dummy",
								"http://dummy.com", new Integer(RandomHelper.getInt()));
					}
					table.addRow(row);
					i %= NAME.length;
					j %= TLD.length;
					score -= RandomHelper.getInt(10, 20);
					d -= .2;
				}
				answer.setTable(table);
			}

			theResponse.setAnswer(answer);
		}
		theResponse.setResult(EPPResult.SUCCESS);
		return theResponse;
	}

	static public EPPSuggestionInfoResp getEmptyTableResponse(String aClientTransId) {
		EPPTransId aTransId = new EPPTransId(aClientTransId, "SRV-43659");
		EPPSuggestionInfoResp theResponse = new EPPSuggestionInfoResp(aTransId);

		theResponse.setKey("Empty Table");
		theResponse.setLanguage(EPPSuggestionConstants.DEFAULT_LANGUAGE);
		EPPSuggestionAnswer answer = new EPPSuggestionAnswer();
		answer.setTable(new EPPSuggestionTable());
		theResponse.setAnswer(answer);

		theResponse.setResult(EPPResult.SUCCESS);
		return theResponse;
	}

	static public EPPSuggestionInfoResp getEmptyGridResponse(String aClientTransId) {
		EPPTransId aTransId = new EPPTransId(aClientTransId, "SRV-43659");
		EPPSuggestionInfoResp theResponse = new EPPSuggestionInfoResp(aTransId);

		theResponse.setKey("Empty Grid");
		EPPSuggestionAnswer answer = new EPPSuggestionAnswer();
		answer.setGrid(new EPPSuggestionGrid());
		theResponse.setAnswer(answer);

		theResponse.setResult(EPPResult.SUCCESS);
		return theResponse;
	}

	static public EPPSuggestionInfoResp getEmptyRecordResponse(String aClientTransId)
			throws InvalidValueException {
		EPPTransId aTransId = new EPPTransId(aClientTransId, "SRV-43659");
		EPPSuggestionInfoResp theResponse = new EPPSuggestionInfoResp(aTransId);

		theResponse.setKey("Empty Record");
		theResponse.setLanguage(EPPSuggestionConstants.DEFAULT_LANGUAGE);
		EPPSuggestionAnswer answer = new EPPSuggestionAnswer();
		EPPSuggestionGrid grid = new EPPSuggestionGrid();
		grid.addRecord(new EPPSuggestionRecord("Empty-Record"));
		answer.setGrid(grid);
		theResponse.setAnswer(answer);

		theResponse.setResult(EPPResult.SUCCESS);
		return theResponse;
	}

	static public void reset() {
		random = new Random();
	}

	static public void reset(long seed) {
		random = new Random(seed);
		System.out.println("Random Helper seed: " + seed);
	}

	static private boolean p(double d) {
		return RandomHelper.get() < d;
	}

}