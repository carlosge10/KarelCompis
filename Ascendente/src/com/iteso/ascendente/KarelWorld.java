package com.iteso.ascendente;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.swing.JFrame;

public class KarelWorld {
	public int beepers[][];
//	only keep track of walls at north and east of a position
	public int walls[][];
	public componenteDibujo mundoKarel;
	public KarelWorld() 
	{
		beepers = new int[100][100];
		walls = new int[100][100];
		try {
			File file;
			file = new File("src/inputWorld.txt");
			BufferedReader br;
			br = new BufferedReader(new FileReader(file));
			int x;
			int y;
			System.out.println("reading Worldfile...");
			String s = br.readLine();
			x = Integer.parseInt(s);
			s = br.readLine();
			y = Integer.parseInt(s);
//			for(int i=x-1; i>=0;i--) 
			for(int i=0; i<x;i++)
			{
				s = br.readLine();
				String [] arr = s.split(" ");
				for(int j=0; j<y;j++) 
				{
					beepers[i][j] = Integer.parseInt(arr[j]);
				}
			}
			s = br.readLine();
			x = Integer.parseInt(s);
			s = br.readLine();
			y = Integer.parseInt(s);
//			for(int i=x-1; i>=0;i--) 
			for(int i=0; i<x;i++)
			{
				s = br.readLine();
				String [] arr = s.split(" ");
				for(int j=0; j<y;j++) 
				{
					walls[i][j] = Integer.parseInt(arr[j]);
				}
			}
			
			/*Graphics*/
			
			JFrame ventana = new JFrame();
			ventana.setSize(1070,780);
			ventana.setTitle("Karel");
			ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			ventana.setVisible(true);
			
			mundoKarel = new componenteDibujo();
			mundoKarel.pBeepers = beepers;
			mundoKarel.pWalls = walls;
			mundoKarel.columnas = 100;
			mundoKarel.renglones = 100;
			
			// Posiciona a Karel		
			mundoKarel.karelPosX = 0;
			mundoKarel.karelPosY = 0;
			mundoKarel.karelOrientation = 0;
			
			// Muestra al Karel en su posicion inicial
			ventana.add(mundoKarel);
			
			/*Graphics*/
		}
		catch(Exception e) 
		{
			System.out.println(e.getMessage());
		}
	}
	public void addBeeper(int x, int y) 
	{
		beepers[x][y] ++;
	}
	public boolean removeBeeper(int x, int y) 
	{
		if(beeperInPos(x,y))
		{
			beepers[x][y]--;
			return true;
		}
		return false;
	}
	public boolean beeperInPos(int x, int y) 
	{
		return beepers[x][y] > 0;
	}
	//0-north, 1-west, 2-south, 3-east
	public boolean directionBlockedAt(int x, int y, int dir) 
	{
		if(x<0 | y<0 | x>100 | y>100)
			return true;
		switch(dir) 
		{
			//java:walls[renglon][columna]
			//north
			case 0:
				if (x == 100)
					return true;
				return walls[x+1][y] == 2 || walls[x+1][y] == 5;
			//east
			case 1:
				if(y == 0)
					return true;
				return walls[x][y-1] == 3 || walls[x][y-1] == 5;
			//south
			case 2:
				if(x==0)
					return true;
				return walls[x][y] == 2 || walls[x][y] == 5;
			//west
			case 3:
				if(x==100)
					return true;
				return walls[x][y] == 3 || walls[x][y] == 5;
		}
		return false;
	}
	//0-north, 1-west, 2-south, 3-east
	public void addWall(int x,int y, int dir) 
	{
		walls[x][y] = dir;
		//will only place: 2 southern wall, 3 eastern wall, 5 south and eastern wall
		/*switch(dir) 
		{
			case 0:
				if(walls[x][y+1] == 0)
				{
					walls[x][y+1] = 2;  
				}
				else if(walls[x][y+1] == 2) {
					//do nothing
				}
				else if(walls[x][y+1] == 3) {
					walls[x][y+1] = 5;
				}
				break;
			case 1:
				if(walls[x-1][y] == 0)
				{
					walls[x-1][y] = 2;  
				}
				else if(walls[x-1][y] == 2) {
					walls[x-1][y]=0;
				}
				else if(walls[x-1][y] == 3) {
					walls[x-1][y] = 5;
				}
				break;
			case 2:
				
				break;
			case 3:
				
				break;
			default:
				
				break;
		}*/
		
	}
}
