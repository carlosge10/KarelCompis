package com.iteso.ascendente;

public class Token {
	public String tclass;
	public String value;
	public boolean bExprValue;
	public Token() 
	{}
	public Token(String c, String v) 
	{
		this.tclass = c;
		this.value = v;
	}
}
