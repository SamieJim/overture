package org.overture.ide.ui.templates;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VdmCompletionContext
{
	private StringBuffer rawScan;
	private StringBuffer processedScan;

/**
	 * Index where replacement must start. For example, if the
	 * rawScan is '<q' then the offset is -2 since the complete text '<q' should be replaced
	 */
	private SearchType type = SearchType.Types;

	private String proposalPrefix = "";

	private List<String> root = new Vector<String>();

	public VdmCompletionContext(StringBuffer rawScan)
	{
		this.rawScan = rawScan;
		init();
	}

	private void init()
	{
		String[] sepList = {"\n", "\r", "\t", "."};
		proposalPrefix = regexStringCleaner(rawScan.toString(),sepList);
		List<CharacterOrder> specialCharMatches = specialCharacterOrderExtractor(rawScan.toString());
		
		if (checkLastSpecialCharacter(specialCharMatches, "<"))
		{
			// Completion of quote, e.g. <Green>
			consQuoteContext(specialCharMatches.get(specialCharMatches.size()-1).start);
			return;
		}
		
		if (proposalPrefix.contains("new"))
		{
			// Completion of constructors
			consConstructorCallContext();
			return;
		}
		
		if(proposalPrefix.contains("mk_") && checkLastSpecialCharacter(specialCharMatches, "_"))
		{
			consMkContext();
			return;
		}

		// Completion for foo.bar. This covers things such as instance variables,
		// values, a record, a tuple, operations and functions
	
		if (checkLastSpecialCharacter(specialCharMatches, "."))
		{ // only works for one . atm
			String[] split = rawScan.toString().split("\\.");
			consDotContext(split);
			return;
		}
		
		String[] typeSepList = { " ", "\n", "\r", "\t", "."};
		proposalPrefix = regexStringCleaner(proposalPrefix,typeSepList);		
	}
	
	private String regexStringCleaner(String proposalPrefix,String[] sepList){
		//Default
		
		if( proposalPrefix == null || proposalPrefix.isEmpty()){
			return "";
		}
		
		char charMatch = proposalPrefix.charAt(proposalPrefix.length() - 1); //checks the end of the string
		if(charMatch == '\r' || charMatch == '\n' || charMatch == '\t' )
		{
			proposalPrefix = "";
		}
		else{
			StringBuffer regexp = new StringBuffer("");
			regexp.append("[");
			for(String s : sepList) {
			    regexp.append("[");
			    regexp.append(Pattern.quote(s));
			    regexp.append("]");
			}
			regexp.append("]");
			String[] bits = proposalPrefix.trim().split(regexp.toString());
			proposalPrefix = bits[bits.length-1];
			proposalPrefix = proposalPrefix.trim();

		}
		return proposalPrefix;
	}
	
	private void consDotContext(String[] split)
	{
		this.type = SearchType.Dot;
		if(split.length >= 2 ){
			this.proposalPrefix = split[1];
		}
		else{
			this.proposalPrefix = split[0];
		}
		this.root = new Vector<>();
		root.add(split[0]);
		
	}

	/**
	 * Constructs the completion context for a constructor call
	 * 
	 * @param index
	 */
	private void consConstructorCallContext()
	{
		// This gives us everything after new, e.g. ' MyClass' if you type 'new MyClass'
		proposalPrefix = rawScan.subSequence(rawScan.indexOf("new"),rawScan.length()).toString();

		processedScan = rawScan; //new StringBuffer(subSeq);
		type = SearchType.New;
	}
	
	/**
	 * Constructs the completion context for a 'mk_' call
	 * 
	 */
	private void consMkContext() {
		
		final int MK_LENGTH = "mk_".length();
		
		CharSequence subSeq = rawScan.subSequence(MK_LENGTH, rawScan.length());
		processedScan = new StringBuffer(subSeq);
		proposalPrefix = processedScan.toString().trim();
		
		type = SearchType.Mk;
	}
	
	/**
	 * Contrusts the completion context for the 'mk_token' call
	 */
//	private void consMK_tokenContext() {
//		final int MK_LENGTH = "mk_t".length();
//		
//		CharSequence subSeq = rawScan.subSequence(MK_LENGTH, rawScan.length());
//		processedScan = new StringBuffer(subSeq);
//		proposalPrefix = processedScan.toString().trim();
//		
//		type = SearchType.Mk;
//		
//	}

/**
	 * Constructs the completion context for quotes
	 * 
	 * @param index The index of the '<' character
	 */
	private void consQuoteContext(int index)
	{	
		processedScan = new StringBuffer(proposalPrefix.subSequence(index, proposalPrefix.length()));
		proposalPrefix = processedScan.substring(processedScan.lastIndexOf("<"));
		type = SearchType.Quote;
	}

	private String getQualifiedSource()
	{
		String res = "";
		if (root != null && !root.isEmpty())
		{
			for (Iterator<String> itr = root.iterator(); itr.hasNext();)
			{
				res += itr.next();
				if (itr.hasNext())
					res += ".";
			}
		}
		return res;
	}

	public int getReplacementOffset()
	{
		return -proposalPrefix.length();
	}
	
	public SearchType getType()
	{
		return type;
	}

	public String getProposalPrefix()
	{
		return proposalPrefix;
	}

	public List<String> getRoot()
	{
		return root;
	}

	@Override
	public String toString()
	{
		return type + " - Root: '" + getQualifiedSource() + "' Proposal: '"
				+ proposalPrefix + "'" + " offset: ";
	}
	
	public List<CharacterOrder> specialCharacterOrderExtractor(String inputString){
		List<CharacterOrder> matches = new ArrayList<CharacterOrder>();
	    //pattern to compare
		Pattern pattern = Pattern.compile("[^a-zA-Z0-9\\( ]");
	    String cleanInputString = inputString.replaceAll("[[a-zA-Z0-9]*$]", "");
	    Matcher matcher = pattern.matcher(cleanInputString);

	    while (matcher.find()) {
	    	matches.add(new CharacterOrder(matcherCleanUp(matcher.group()),matcher.start(),matcher.end()));
	    }
	    return matches;
	}
	
	public String matcherCleanUp(String match){
		String[] typeSepList = {"\n", "\r", "\t"};
		return regexStringCleaner(match,typeSepList);	
	}
	
	public Boolean checkLastSpecialCharacter(List<CharacterOrder> specialCharMatches, String inputString){

		if (specialCharMatches != null && !specialCharMatches.isEmpty() && inputString.equals(specialCharMatches.get(specialCharMatches.size()-1).content) )
		{
			return true;
		}
		return false;
	}
	
	class CharacterOrder
	{
		public CharacterOrder(String c, int s, int e){
			content = c;
			end = e;
			start = s;
		}
	    public String content; 
	    public int start;
	    public int  end;
	};
	
}