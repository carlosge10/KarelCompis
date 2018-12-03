package com.iteso.ascendente;

import javax.swing.JFrame;
import java.awt.Rectangle;

public class Test {

	public static int beepers[][];
	public static int walls[][];		
	
	public static void main(String[] args) throws InterruptedException {
		
		/* INICIA DATOS DE PRUEBA */			
		
		beepers = new int[100][100];
		walls = new int[100][100];		
		
		walls [0][0] = 5;
		walls [0][1] = 5;
		walls [1][0] = 5;
		walls [1][1] = 5;
		
		
		walls [10][10] = 2;
		walls [20][20] = 3;		
		walls [30][30] = 5;
		walls [31][31] = 5;
		
		beepers [0][0] = 1;
		beepers [1][1] = 1;		
		beepers [15][15] = 1;
		beepers [25][25] = 1;
		beepers [30][30] = 1;		
		beepers [31][31] = 1;		
		
		/* TERMINA DATOS DE PRUEBA */
		
		
		
		JFrame ventana = new JFrame();
		ventana.setSize(1070,780);
		ventana.setTitle("Karel CG");
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.setVisible(true);
		
		componenteDibujo mundoKarel = new componenteDibujo();
		mundoKarel.pBeepers = beepers;
		mundoKarel.pWalls = walls;
		mundoKarel.columnas = 100;
		mundoKarel.renglones = 100;
		
		// Posiciona a Karel		
		mundoKarel.karelPosX = 10;
		mundoKarel.karelPosY = 10;
		mundoKarel.karelOrientation = 0;		
		
		// Muestra al Karel en su posicion inicial
		ventana.add(mundoKarel);
		
		// Rutina para mover al Karel por renglon y por columna
		for (int y=0;y<100;y++)
			for (int x=0;x<100;x++)
			{
				mundoKarel.karelPosX = x;
				mundoKarel.karelPosY = y;					
				mundoKarel.repaint();
				Thread.sleep(30);
			}
	}

}
