package com.iteso.ascendente;

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
			
		}
		else if(prod.tl.get(0).value.equals("BE")) 
		{
			
			return ;
		}
		else if(prod.tl.get(0).value.equals("BS")) 
		{
			evaluateBooleanStatement(prod.tl.get(2).value);
		}
	}

	public void evaluateBooleanStatement(String statement) 
	{
		if(statement.equals("facingNorth")) 
		{
			lastBooleanStatement &= this.orientation == 0;
		}
		else if(statement.equals("facingSouth")) 
		{
			lastBooleanStatement &= this.orientation == 2;			
		}
		else if(statement.equals("facingEast")) 
		{
			lastBooleanStatement &= this.orientation == 1;
		}
		else if(statement.equals("facingWest")) 
		{
			lastBooleanStatement &= this.orientation == 3;
		}
		else if(statement.equals("anyBeepersInBeeperBag")) 
		{
			lastBooleanStatement &= this.beepersInBeeperBag > 0 ;
		}
		else if(statement.equals("frontIsClear")) 
		{
			lastBooleanStatement &= world.directionBlockedAt(posx, posy, orientation);
		}
		else if(statement.equals("rightIsClear")) 
		{
			lastBooleanStatement &= world.directionBlockedAt(posx, posy, (orientation + 3) %4);
		}
		else if(statement.equals("leftIsClear")) 
		{
			lastBooleanStatement &= world.directionBlockedAt(posx, posy, (orientation + 1) %4);
		}
		else if(statement.equals("backIsClear")) 
		{
			lastBooleanStatement &= world.directionBlockedAt(posx, posy, (orientation + 2) %4);
		}
		else if(statement.equals("nextToABeeper")) 
		{
			lastBooleanStatement &= world.beeperInPos(posx, posy);
		}
		else 
		{
			//do nothing
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
