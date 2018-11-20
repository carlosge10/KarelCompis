package com.iteso.ascendente;

import java.util.ArrayList;
import java.util.List;

public class TokenList {
	List<Token> tl;
	
	public TokenList() {
		tl = new ArrayList<Token>();
	}
	
	public TokenList(List<Token> tl) 
	{
		this.tl = tl;
	}
	public int indexOfValue(String val) 
	{
		int res = 0;
		for (Token token : tl) {
			if(token.value.equals(val))
				return res;
			res++;
		}
		return -1;
	}
}
