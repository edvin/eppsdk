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
package com.verisign.epp.codec.suggestion;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.verisign.epp.codec.gen.EPPCodec;
import com.verisign.epp.codec.gen.EPPDecodeException;
import com.verisign.epp.codec.gen.EPPEncodeException;
import com.verisign.epp.codec.gen.EPPResponse;
import com.verisign.epp.codec.gen.EPPTransId;
import com.verisign.epp.codec.gen.EPPUtil;
import com.verisign.epp.codec.suggestion.util.ExceptionUtil;
import com.verisign.epp.codec.suggestion.util.InvalidValueException;
import com.verisign.epp.util.EqualityUtil;

/**
 * An <code>EPPSuggestionInfoResp</code> provides an answer to an
 * <code>EPPSuggestionInfoCmd</code> and includes the following attributes:<br>
 * <br>
 * <ul>
 * <li>key - Required suggestion key that matches the
 * <code>EPPSuggestionInfoCmd</code> key</li>
 * <li>answer - Optional answer in either grid or table view</li>
 * <li>tokens - Optional suggestion tokens</li>
 * </ul>
 * @see EPPSuggestionInfoCmd
 * @author jcolosi
 */
public class EPPSuggestionInfoResp extends EPPResponse {
	static private final String ELM_ANSWER = "suggestion:answer";	
	static private final String ELM_TOKEN = "suggestion:token";
	final static String ELM_NAME = "suggestion:infData";

	/**
	 * Suggestion key
	 */
	private String key = null;
	
	/**
	 * Suggestion  language (defaults to ENG (english))
	 */
	private String language = EPPSuggestionConstants.DEFAULT_LANGUAGE;

	/**
	 * Suggestion answer
	 */
	private EPPSuggestionAnswer answer = null;

	/**
	 * Optional suggestion tokens
	 */
	private List tokens = null;

	/**
	 * Default constructor that needs the <code>key</code> attribute and the
	 * transid attribute set prior to calling <code>encode</code>.
	 */
	public EPPSuggestionInfoResp() {}

	/**
	 * Creates an <code>EPPSuggestionInfoResp</code> only the transaction id
	 * set. The <code>key</code> attribute must be set prior to calling
	 * <code>encode</code>.
	 * @param aTransId The transaction id containing the server transaction and
	 *            optionally the client transaction id
	 */
	public EPPSuggestionInfoResp(EPPTransId aTransId) {
		super(aTransId);
	}

	/**
	 * Creates an <code>EPPSuggestionInfoResp</code> with the required
	 * attributes set.
	 * @param aTransId The transaction id containing the server transaction and
	 *            optionally the client transaction id
	 * @param aKey Suggestion key
	 */
	public EPPSuggestionInfoResp(EPPTransId aTransId, String aKey) {
		super(aTransId);
		this.key = aKey;
	}

	/**
	 * Creates an <code>EPPSuggestionInfoResp</code> with the all the
	 * attributes.
	 * @param aTransId The transaction id containing the server transaction and
	 *            optionally the client transaction id
	 * @param aKey Suggestion key
	 * @param aLanguage Suggestion language
	 * @param aTokens The optional suggestion tokens
	 * @param aAnswer the optional answer in table or grid view
	 */
	public EPPSuggestionInfoResp(EPPTransId aTransId, String aKey, String aLanguage, List aTokens,
			EPPSuggestionAnswer aAnswer) {
		super(aTransId);
		this.key = aKey;
		setLanguage(aLanguage);
		this.tokens = aTokens;
		this.answer = aAnswer;
	}

	/**
	 * Adds a suggestion token to the response.
	 * @param aToken Suggestion token to add
	 * @throws InvalidValueException
	 */
	public void addToken(EPPSuggestionToken aToken) throws InvalidValueException {
		if (aToken == null) throw new InvalidValueException("Cannot add a null Token");
		if (this.tokens == null) resetTokens();
		this.tokens.add(aToken);
	}

	/**
	 * Does a deep clone of the <code>EPPSuggestionInfoResp</code> instance.
	 * @return Cloned instance
	 */
	public Object clone() throws CloneNotSupportedException {
		return (EPPSuggestionInfoResp) super.clone();
	}

	/**
	 * Compares two <code>EPPSuggestionInfoResp</code> instances.
	 * @return <code>true</code> if equal;<code>false</code> otherwise.
	 */
	public boolean equals(Object o) {
		if ((o != null) && (o.getClass().equals(this.getClass()))) {
			if (!super.equals(o)) return false;
			EPPSuggestionInfoResp other = (EPPSuggestionInfoResp) o;
			if (!EqualityUtil.equals(this.key, other.key)) return false;
			if (!EqualityUtil.equals(this.tokens, other.tokens)) return false;
			if (!EqualityUtil.equals(this.answer, other.answer)) return false;
			if (!EqualityUtil.equals(this.language, other.language)) return false;
			return true;
		}
		return false;
	}

	/**
	 * Is the answer defined?
	 * @return <code>true</code> if is defined;<code>false</code>
	 *         otherwise.
	 */
	public boolean hasAnswer() {
		return (this.answer != null ? true : false);
	}

	/**
	 * Gets the suggestion answer that is either in table or grid view.
	 * @return Suggestion answer if defined;<code>null</code> otherwise.
	 */
	public EPPSuggestionAnswer getAnswer() {
		return this.answer;
	}

	/**
	 * Gets the suggestion key.
	 * @return Returns the key.
	 */
	public String getKey() {
		return key;
	}

	/**
	 * Gets the EPP command namespace associated with
	 * <code>EPPSuggestionInfoResp</code>.
	 * @return <code>EPPSuggestionMapFactory.NS</code>
	 */
	public String getNamespace() {
		return EPPSuggestionMapFactory.NS;
	}

	/**
	 * Does the response have tokens defined?
	 * @return <code>true</code> if is defined;<code>false</code>
	 *         otherwise.
	 */
	public boolean hasTokens() {
		return (this.tokens != null && this.tokens.size() != 0 ? true : false);
	}

	/**
	 * Gets the suggestion tokens.
	 * @return Returns the tokens if defined;<code>null</code> otherwise.
	 */
	public List getTokens() {
		return this.tokens;
	}

	/**
	 * Gets the EPP response type associated with
	 * <code>EPPSuggestionInfoResp</code>.
	 * @return <code>EPPSuggestionInfoResp.ELM_NAME</code>
	 */
	public String getType() {
		return ELM_NAME;
	}

	/**
	 * Resets the tokens to an empty list.
	 */
	public void resetTokens() {
		this.tokens = new ArrayList();
	}

	/**
	 * Sets the suggestion answer in either table or grid view.
	 * @param aAnswer The suggestion answer
	 */
	public void setAnswer(EPPSuggestionAnswer aAnswer) {
		this.answer = aAnswer;
	}

	/**
	 * Sets the suggestion key
	 * @param aKey Suggestion key
	 */
	public void setKey(String aKey) {
		this.key = aKey;
	}

	/**
     * @return Returns the language.
     */
    public String getLanguage()
    {
        return language;
    }

    /**
     * sets the suggestion language
     * 
     * @param language
     *            The language to set - a null value will force the deault to be
     *            set
     */
    public void setLanguage(String language)
    {
        if (language == null)
        {
            language = EPPSuggestionConstants.DEFAULT_LANGUAGE;
        }
        
        this.language = language.toUpperCase();
    }

    /**
	 * Implementation of <code>Object.toString</code>, which will result in
	 * an indented XML <code>String</code> representation of the concrete
	 * <code>EPPCodecComponent</code>.
	 * @return Indented XML <code>String</code> if successful;
	 *         <code>ERROR</code> otherwise.
	 */
	public String toString() {
		return EPPUtil.toString(this);
	}

	/**
	 * Decode the <code>EPPSuggestionInfoResp</code> attributes from the
	 * aElement DOM Element tree.
	 * @param aElement Root DOM Element to decode
	 *            <code>EPPSuggestionInfoResp</code> from.
	 * @exception EPPDecodeException Unable to decode aElement
	 */
	protected void doDecode(Element aElement) throws EPPDecodeException {
		key = EPPUtil.decodeString(aElement, EPPSuggestionMapFactory.NS, EPPSuggestionConstants.ELM_KEY);
		if (key == null) ExceptionUtil.missingDuringDecode("key");
		
        String decodedLanguage = EPPUtil.decodeString(aElement, EPPSuggestionMapFactory.NS, EPPSuggestionConstants.ELM_LANGUAGE);
        
        // default to ENG (for english) if optional language element is not specified
        if (decodedLanguage == null)
        {
            decodedLanguage = EPPSuggestionConstants.DEFAULT_LANGUAGE;
        }
        
        language = decodedLanguage.toUpperCase();		

		answer = (EPPSuggestionAnswer) EPPUtil.decodeComp(aElement,
				EPPSuggestionMapFactory.NS, ELM_ANSWER, EPPSuggestionAnswer.class);

		NodeList nodes = aElement.getChildNodes();
		Node node = null;
		int size = nodes.getLength();
		if (size > 0) {
			for (int i = 0; i < size; i++) {
				node = nodes.item(i);
				if (node.getNodeName().equalsIgnoreCase(ELM_TOKEN)) {
					addToken(new EPPSuggestionToken((Element) node));
				}
			}
		}
	}

	/**
	 * Encode a DOM Element tree from the attributes of the
	 * <code>EPPSuggestionInfoResp</code> instance.
	 * @param aDocument DOM Document that is being built. Used as an Element
	 *            factory.
	 * @return Element Root DOM Element representing the EPPSuggestionInfoResp
	 *         instance.
	 * @exception EPPEncodeException Unable to encode EPPSuggestionInfoResp
	 *                instance.
	 */
	protected Element doEncode(Document aDocument) throws EPPEncodeException {
		Element root = aDocument.createElementNS(EPPSuggestionMapFactory.NS, ELM_NAME);
		root.setAttribute("xmlns:suggestion", EPPSuggestionMapFactory.NS);
		root.setAttributeNS(EPPCodec.NS_XSI, "xsi:schemaLocation",
				EPPSuggestionMapFactory.NS_SCHEMA);

		if (key == null) ExceptionUtil.missingDuringEncode("key");
		

		EPPUtil.encodeString(aDocument, root, key, EPPSuggestionMapFactory.NS, EPPSuggestionConstants.ELM_KEY);

		if ((language != null)
				&& (!this.language
						.equals(EPPSuggestionConstants.DEFAULT_LANGUAGE))) {
            language = language.toUpperCase();
            EPPUtil.encodeString(aDocument, root, language,
                    EPPSuggestionMapFactory.NS,
                    EPPSuggestionConstants.ELM_LANGUAGE);
        }
		
		EPPUtil.encodeCompList(aDocument, root, this.tokens);

		if (answer != null) EPPUtil.encodeComp(aDocument, root, answer);

		return root;
	}

}
