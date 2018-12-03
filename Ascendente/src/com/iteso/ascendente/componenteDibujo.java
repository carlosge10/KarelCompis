package com.iteso.ascendente;

import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.Color;


public class componenteDibujo extends JComponent {
		
	public int pBeepers[][];
	public int pWalls[][];
	public int renglones;
	public int columnas;
	public int karelPosX;
	public int karelPosY;
	public int karelOrientation;  //north = 0, west = 1, south = 2, east = 3
	
	
	
	public void paintComponent(Graphics g)
	{
		Graphics2D grafica2D = (Graphics2D) g;
		Rectangle rectangulo = new Rectangle(0,0,1024,720);				
		grafica2D.draw(rectangulo);
		
		// Se dibuja al Karel
		grafica2D.setColor(Color.red);
		if (karelOrientation == 0) // north
		{			
			grafica2D.draw(new Line2D.Double(karelPosX*7+3,(karelPosY)*7,karelPosX*7+3,(karelPosY+1)*7));
			grafica2D.fillOval(karelPosX*7+1, karelPosY*7-2, 4, 4);
		}
		else
			if (karelOrientation == 1) // west
			{			
				grafica2D.draw(new Line2D.Double(karelPosX*7,(karelPosY)*7,(karelPosX+1)*7,(karelPosY)*7));
				grafica2D.fillOval(karelPosX*7-2, karelPosY*7-2, 4, 4);
			}						
			else
				if (karelOrientation == 2) // south
				{			
					grafica2D.draw(new Line2D.Double(karelPosX*7+3,(karelPosY)*7,karelPosX*7+3,(karelPosY+1)*7));
					grafica2D.fillOval(karelPosX*7+1, karelPosY*7+5, 4, 4);
				}
				else
					if (karelOrientation == 3) // east
					{			
						grafica2D.draw(new Line2D.Double(karelPosX*7,(karelPosY)*7,(karelPosX+1)*7,(karelPosY)*7));
						grafica2D.fillOval((karelPosX+1)*7-2, karelPosY*7-2, 4, 4);
					}				
		
		grafica2D.setColor(Color.BLACK);
		
		// Se dibujan los objetos del mundo
		for (int x=0; x<columnas; x++)
			for (int y=0; y<renglones;y++)
			{	
				// Se dibujan las paredes
				if (pWalls[y][x] != 0)
					{
						if (pWalls[y][x] == 2)  // Pared sur
							grafica2D.draw(new Line2D.Double((x)*7,(y+1)*7,(x+1)*7,(y+1)*7));
						else
							if (pWalls[y][x] == 3) // Pared este
								grafica2D.draw(new Line2D.Double((x)*7,(y)*7,(x)*7,(y+1)*7));
							else
								if (pWalls[y][x] == 5)  // Pared sur y este
								{
									grafica2D.draw(new Line2D.Double((x+1)*7,(y)*7,(x+1)*7,(y+1)*7));  
									grafica2D.draw(new Line2D.Double((x)*7,(y+1)*7,(x+1)*7,(y+1)*7));
								}
							
					}
				
				// Se dibujan los beepers
				if (pBeepers[y][x]>0)
				{
					grafica2D.drawString("*", x*7+1, y*7+10);
				}
			}					
		
	}
	
}
