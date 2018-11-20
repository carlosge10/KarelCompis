package com.iteso.ascendente;

import java.util.ArrayList;
import java.util.List;

public class Grammar {
	int rules;
	List<TokenList> grammar;
	public Grammar(){
		grammar = new ArrayList<TokenList>();
	}
	public Grammar(Grammar g, int rules) 
	{
		grammar = g.grammar;
	}
}
