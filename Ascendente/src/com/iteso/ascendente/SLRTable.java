package com.iteso.ascendente;

import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SLRTable {
	TokenList terminales;
	TokenList noTerminales;
	Grammar g;
	int reglas;
	List<TokenList> acciones;
	List<TokenList> gotos;
	public SLRTable(File f, BufferedReader br) throws Exception
	{
		terminales = new TokenList();
		noTerminales = new TokenList();
		g = new Grammar();
		acciones = new ArrayList<TokenList>();
		gotos = new ArrayList<TokenList>();
		//reading nonterminals
		String s = br.readLine();
		String arr[] = s.split(",");
		Token t;
		for (int i = 0; i < arr.length; i++) {
			t = new Token();
			t.value = arr[i];
			noTerminales.tl.add(t);
		}
		//reading terminals
		s = br.readLine();
		String arr2[] = s.split(",");
		for (int i = 0; i < arr2.length; i++) {
			t = new Token();
			t.value = arr2[i];
			terminales.tl.add(t);
		}
		//adding $
		t = new Token();
		t.value = "$";
		terminales.tl.add(t);
		//reading Grammar
		g.rules = Integer.parseInt(br.readLine());
		TokenList auxtl;
		for (int i = 0; i < g.rules; i++) {
			s = br.readLine();
			String arrAux[] = s.split(" ");
			auxtl = new TokenList();
			for (int j = 0; j < arrAux.length; j++) {
				t = new Token();
				t.value = arrAux[j];
				auxtl.tl.add(t);
			}
			g.grammar.add(auxtl);
		}
		//reading table
		reglas = Integer.parseInt(br.readLine());
		//reading acciones
		for (int i = 0; i < reglas; i++) {
			s = br.readLine();
			String arrAux[] = s.split(",");
			auxtl = new TokenList();
			for (int j = 0; j < arrAux.length; j++) {
				String arrAuxB[] = arrAux[j].split(" ");
				t = new Token();
				if(arrAuxB.length > 1) {
					t.tclass = arrAuxB[0];
					t.value = arrAuxB[1];
				}
				auxtl.tl.add(t);
			}
			acciones.add(auxtl);
		}
		//reading gotos
		for (int i = 0; i < reglas; i++) {
			s = br.readLine();
			String arrAux[] = s.split(",");
			auxtl = new TokenList();
			for (int j = 0; j < arrAux.length; j++) {
				t = new Token();
				t.value = arrAux[j];
				auxtl.tl.add(t);
			}
			gotos.add(auxtl);
		}
	}
	public SLRTable() 
	{}
	public void printTable() 
	{
		for(int i=0; i<reglas; i++) 
		{
			//print ith row of actions
			for (Token t : acciones.get(i).tl) {
				if(t.value == null)
					System.out.print("|   |");
				else
					System.out.print("|"+t.tclass+t.value + " |");
			}
			//prtnt ith row of gotos
			for (Token t : gotos.get(i).tl) {
				if(t.value == null)
					System.out.print("|   |");
				else
					System.out.print("|"+t.value+"  |");
			}
			System.out.println();
		}
	}
	public Token getAction(int i, String s) 
	{
		int columna = terminales.indexOfValue(s);
		return acciones.get(i).tl.get(columna);
	}
	public TokenList getRule(int i)
	{
		try {
			return g.grammar.get(i);
		}
		catch(Exception e) 
		{
			System.out.println("Error no aceptada");
			return null;
		}
	}
	public int getGoto(int i, String s) 
	{
		try {
			int columna = noTerminales.indexOfValue(s);
			return Integer.parseInt(gotos.get(i).tl.get(columna).value);
		}
		catch(Exception e) 
		{
			System.out.println("Error, no aceptada");
			return -1;
		}
	}
}
