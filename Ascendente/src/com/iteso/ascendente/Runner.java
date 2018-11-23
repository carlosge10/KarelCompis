package com.iteso.ascendente;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Stack;

public class Runner {
	public static void main(String []args) {
		try {
			File file;
			file = new File("src/input.txt");
			BufferedReader br;
			br = new BufferedReader(new FileReader(file));
			System.out.println("reading file...");
			SLRTable table = new SLRTable(file, br);
			System.out.println("Here's your table...");
			table.printTable();
			TokenList input = getInput(file, br);			
			solve(table, input);
		}
		catch(Exception e) 
		{
			System.out.println(e.getMessage());
		}
	}
	
	public static TokenList getInput(File f, BufferedReader br) throws Exception
	{
		TokenList tl = new TokenList();
		String len;
		len = br.readLine();
		int i = Integer.parseInt(len.trim());
		System.out.println("Dime la lista de tokens separados por comas");
		String aux;
		while(i-->0) 
		{
			aux = br.readLine();
			String[] arr = aux.split(",");
			tl.tl.add(new Token(arr[1], arr[0]));
		}
		return tl;
	}
	
	public static boolean solve(SLRTable table, TokenList tl) throws Exception
	{
		KarelRobot k = new KarelRobot();
		BufferedWriter bw = null;
		bw = new BufferedWriter(new FileWriter("src/output.txt"));
		
		Token acc;
		int ptr = 0;
		Stack<Token> symbols = new Stack<Token>();
		Stack<Integer> st = new Stack<Integer>(); 
		st.push(0);
		Token t$=new Token();
		t$.value="$";
		tl.tl.add(t$);
		while(true)
		{
			System.out.println("----------------------------------------");
			//imprimir pila
			System.out.println("PILA:");
			for (Integer integer : st) {
				System.out.print(integer+" ");
			}
			System.out.println();
			//imrimir entrada desde ptr hasta el tamano
			System.out.println("ENTRADA:");
			for (int i=ptr; i < tl.tl.size(); i++) {
				System.out.print(tl.tl.get(i).value);
			}
			System.out.println();
			//imprimir symbols
			System.out.println("SYMBOLOS:");
			for (Token t : symbols) {
				System.out.print(t.value);
			}
			System.out.println();
			//imprimir accion
			try {
			acc = table.getAction(st.peek(), tl.tl.get(ptr).value);
			}
			catch(Exception e) 
			{
				System.out.println("Error, no aceptada");
				return false;
			}
			System.out.println("ACCION:");
			System.out.print(acc.tclass + acc.value + " ");
			//algoritmo
			if(acc.value == null) 
			{
				System.out.println("Error, no aceptada");
				return false;
			}
			else if(acc.tclass.equals("s")) 
			{
				st.push(Integer.parseInt(acc.value));
				ptr++;
				System.out.println();
			}
			else if(acc.tclass.equals("r")) 
			{
				TokenList prod = table.getRule(Integer.parseInt(acc.value));
				k.executeLine(prod);
				if(prod.tl.get(2).value.equals("I"))
					bw.write("Karel: " + k.toString()+"\n");
				if(prod == null) 
				{
					return false;
				}
				//pop del tamano de la prod
				for(int i=2; i<prod.tl.size(); i++) 
				{
					st.pop();
					if(!symbols.isEmpty())
						symbols.pop();
				}
				symbols.push(prod.tl.get(0));
				int gt =table.getGoto(st.peek(), prod.tl.get(0).value); 
				if(gt == -1)
					return false;
				st.push(gt);
				for (Token p : prod.tl) {
					System.out.print(p.value);
				}
				System.out.println();
				
			}
			else if(acc.tclass.equals("acc")) {
				System.out.println("Aceptada");
				bw.close();
				return true;
			}
		}
	}
}
