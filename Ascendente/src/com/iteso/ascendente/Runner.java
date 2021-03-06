package com.iteso.ascendente;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

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
		String lastCS="";
		KarelRobot k = new KarelRobot();
		BufferedWriter bw = null;
		bw = new BufferedWriter(new FileWriter("src/output.txt"));
		for (int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				System.out.print(k.world.walls[i][j]);
			}
			System.out.println();
		}
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
			//imprimir pila de symbolos
			System.out.println("PILA:");
			for (Token t : symbols) {
				System.out.print(t.value+" ");
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

			if(!symbols.isEmpty()) {
				if(symbols.peek().value.equals("while") | symbols.peek().value.equals("if") | symbols.peek().value.equals("iterate"))
				{
					lastCS = symbols.peek().value;
					//bw.write("CS: " + symbols.peek().value +"\n");
				}
				else if(symbols.peek().value.equals("{"))
				{
					if(lastCS.equals("if"))
					{
						bw.write("if,!t" + k.varNumber + ",LABELFALSE"+k.tagNumber +"\nLABELTRUE"+k.tagNumber+":\n");
						k.tagNumber++;
						k.varNumber++;
					}
					else if (lastCS.equals("while"))
					{
						bw.write("LABELTRUE"+ k.tagNumber +":\nif,!t"+ k.varNumber + ",LABELFALSE"+k.tagNumber+"\n");
						k.tagNumber++;
						k.varNumber++;
					}
					else if (lastCS.equals("iterate"))
					{
						
					}
				}
				else if(symbols.peek().value.equals("}"))
				{
					if(lastCS.equals("if"))
					{
						k.tagNumber--;
						bw.write("LABELFALSE"+k.tagNumber+":\n");						
					}
					else if (lastCS.equals("while"))
					{
						k.tagNumber--;
						k.varNumber--;
						bw.write("if,!t"+ k.varNumber + ",LABELTRUE"+k.tagNumber+"\nLABELFALSE"+k.tagNumber+":\n");	
						
					}
					else if (lastCS.equals("iterate"))
					{
						
					}
					//bw.write("BR: LABELFALSE"+k.closeTag()+":\n");
				}
				else if(symbols.peek().value.equals("move") | symbols.peek().value.equals("turnright") | symbols.peek().value.equals("turnleft") | symbols.peek().value.equals("pickbeeper") | symbols.peek().value.equals("putbeeper") | symbols.peek().value.equals("turnoff")) 
				{
					bw.write("I: " + symbols.peek().value +"\n");
				}
			}

			//algoritmo
			if(acc.value == null) 
			{
				System.out.println("Error, no aceptada");
				return false;
			}
			else if(acc.tclass.equals("s")) 
			{
				st.push(Integer.parseInt(acc.value));
				symbols.push(tl.tl.get(ptr));
				ptr++;
				System.out.println();
			}
			else if(acc.tclass.equals("r")) 
			{
				TokenList prod = table.getRule(Integer.parseInt(acc.value));
				k.executeLine(prod);
				if(prod.tl.get(2).value.equals("I")) {
					if(k.crashError) {
						JOptionPane.showMessageDialog(null, "Karel crashed into a wall.", "UnexpectedError", JOptionPane.ERROR_MESSAGE);
						return false;
					}
					else if(k.pickbeeperError)
					{
						JOptionPane.showMessageDialog(null, "Karel wanted to pick a beeper in an empty position.", "UnexpectedError", JOptionPane.ERROR_MESSAGE);
						return false;
					}
					else if(k.putbeeperError) {
						JOptionPane.showMessageDialog(null, "Karel wanted to put a beeper with an empty bag.", "UnexpectedError", JOptionPane.ERROR_MESSAGE);
						return false;
					}
					Thread.sleep(1000);
					//bw.write("Karel: " + k.toString()+"\n");
					k.world.mundoKarel.karelPosX = k.posx;
					k.world.mundoKarel.karelPosY = k.posy;
					k.world.mundoKarel.karelOrientation = k.orientation;
					k.world.mundoKarel.repaint();
				}
				else if(prod.tl.get(0).value.equals("BS"))
				{
					k.evaluateBooleanStatement(prod, symbols);
					String prodS="";
					for(Token t : prod.tl)
						if(t.value.equals("BS") | t.value.equals("BE"))
							prodS += (t.value+""+k.varNumber++);
						else
							prodS += t.value;
					bw.write("Eval: " + prodS + ":" + prod.tl.get(0).bExprValue +"\n");
				}
				else if(prod.tl.get(0).value.equals("BE"))
				{
					k.evaluateBooleanExpression(prod, symbols);
					String prodS="";
					for(Token t : prod.tl)
						if(t.value.equals("BS") | t.value.equals("BE"))
							prodS += (t.value+""+k.varNumber++);
						else
							prodS += t.value;
					bw.write("Eval: " + prodS + ":" + prod.tl.get(0).bExprValue +"\n");
				}
				if(prod == null)
				{
					return false;
				}
				//pop del tamano de la prod
				for(int i=2; i<prod.tl.size(); i++) 
				{
					st.pop();
					//if(!symbols.isEmpty())
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