package com.iteso.ascendente;

import java.util.Stack;

public class KarelRobot {
	
	public KarelWorld world;
	
	public int posx;
	public int posy;
	
	//north = 0, west = 1, south = 2, east = 3
	public int orientation;
	
	public int beepersInBeeperBag;
	
	public boolean on;
	
//	public boolean lastBooleanStatement;
	
	public KarelRobot() 
	{
		posx = 0;
		posy = 0;
		orientation = 0;
		beepersInBeeperBag = 0;
		on = true;
		world = new KarelWorld();
//		lastBooleanStatement = true;
	}
	
	public void executeLine(TokenList prod) 
	{
		if(prod.tl.get(0).value.equals("I")) 
		{
			executeInstruction(prod.tl.get(2).value);
		}
		else if(prod.tl.get(0).value.equals("CS")) 
		{
			return;
		}
		else if(prod.tl.get(0).value.equals("BE")) 
		{
			return ;
		}
		else if(prod.tl.get(0).value.equals("BS")) 
		{
			return;
		}
	}

	public void evaluateBooleanExpression(TokenList prod, Stack<Token> symbols)//recieve symbol stack, operate with the stack 
	{
		if(symbols.size() == 0)
		{
			return;
		}
		if(symbols.size() == 1) 
		{
			prod.tl.get(0).bExprValue = symbols.peek().bExprValue;
			return;
		}
		if(prod.tl.size() == 3) 
		{
			prod.tl.get(0).bExprValue = symbols.peek().bExprValue;
		}
		else  
		{
			String statement = prod.tl.get(3).value;
			if(statement.equals("|")) 
			{
				prod.tl.get(0).bExprValue = symbols.get(symbols.size()-3).bExprValue | symbols.get(symbols.size()-1).bExprValue;
			}
			else if(statement.equals("&")) 
			{
				prod.tl.get(0).bExprValue = symbols.get(symbols.size()-3).bExprValue & symbols.get(symbols.size()-1).bExprValue;
			}
			else 
			{
				return;
			}
		}
	}
	
	public void evaluateBooleanStatement(TokenList prod, Stack<Token> symbols) //recieve symbol stack, operate with the stack
	{
		
		String statement = prod.tl.get(2).value;
		if(statement.equals("facingNorth")) 
		{
			prod.tl.get(0).bExprValue = this.orientation == 0;
		}
		else if(statement.equals("facingSouth")) 
		{
			prod.tl.get(0).bExprValue = this.orientation == 2;			
		}
		else if(statement.equals("facingEast")) 
		{
			prod.tl.get(0).bExprValue = this.orientation == 1;
		}
		else if(statement.equals("facingWest")) 
		{
			prod.tl.get(0).bExprValue = this.orientation == 3;
		}
		else if(statement.equals("anyBeepersInBeeperBag")) 
		{
			prod.tl.get(0).bExprValue = this.beepersInBeeperBag > 0 ;
		}
		else if(statement.equals("frontIsClear")) 
		{
			prod.tl.get(0).bExprValue = !world.directionBlockedAt(posx, posy, orientation);
		}
		else if(statement.equals("rightIsClear")) 
		{
			prod.tl.get(0).bExprValue = !world.directionBlockedAt(posx, posy, (orientation + 3) %4);
		}
		else if(statement.equals("leftIsClear")) 
		{
			prod.tl.get(0).bExprValue = !world.directionBlockedAt(posx, posy, (orientation + 1) %4);
		}
		else if(statement.equals("backIsClear")) 
		{
			prod.tl.get(0).bExprValue = !world.directionBlockedAt(posx, posy, (orientation + 2) %4);
		}
		else if(statement.equals("nextToABeeper")) 
		{
			prod.tl.get(0).bExprValue = world.beeperInPos(posx, posy);
		}
		else if(statement.equals("!")) 
		{
			prod.tl.get(0).bExprValue = ! symbols.peek().bExprValue;
		}
		else if(statement.equals("(")) 
		{
			prod.tl.get(0).bExprValue = symbols.peek().bExprValue;
		}
		else 
		{
			//do nothing
			return;
		}
	}
	
	public void executeInstruction(String inst) 
	{
		if(!on)
			return;
		if(inst.equals("move")) 
		{
			switch(orientation) 
			{
				//facing north
				case 0:
					posy++;
					break;
				//facing west
				case 1:
					posx--;
					break;
				//facing south
				case 2:
					posy--;
					break;
				case 3:
					posx++;
					break;
				//facing east
				default:
					break;
			}
		}
		else if(inst.equals("turnright")) 
		{
			orientation+=-1;
			if(orientation < 0)
				orientation += 4;
			orientation%=4;
		}
		else if(inst.equals("turnleft")) 
		{
			orientation+=1;
			orientation%=4;
		}
		else if(inst.equals("pickbeeper")) 
		{
			beepersInBeeperBag++;
		}
		else if(inst.equals("putbeeper")) 
		{
			beepersInBeeperBag--;
		}
		else if(inst.equals("turnoff")) 
		{
			on = false;
		}
		else 
		{
			return;
		}
	}
	
	@Override
	public String toString() {
		return "{" + "X:" + posx + ", Y:" + posy + ", orientation:" + orientation + ", BeepersInBeeperBag:" + beepersInBeeperBag + ", on:" + on + "}";
	}
	
	
	
}
