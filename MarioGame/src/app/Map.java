package app;

import java.awt.Image;
import java.io.*;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.net.*;
import java.awt.*;

public class Map 
{
	int mapx;
	int maplimit;
	Image background;
	Sprite player;
	ArrayList<Image> imagenes = new ArrayList();
	Block[] bridge = new Block[18];
	private ArrayList<String> lineas = new ArrayList<String>();
	ArrayList<Sprite> sprites = new ArrayList<Sprite>(); 
	Image[][] tiles;
	int mapSize;
	
	
	public void cargarImagenesdeTiles()
	{
		int i = 0;
		Image img = null;

		while(i < 15)
		{
			
			//img = new ImageIcon( (ResourceLoader.class).getResource("Tile_"+ ( (char)(i + 97) ) +".png") ).getImage();
			try {
				img = ImageIO.read(new File("Tile_"+ ( (char)(i + 97) ) +".png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			imagenes.add(img);
			i++;
		}
	}
	
	public void addSprite(Sprite spr)
	{
		sprites.add(spr);
	}
	
	public void loadMap(String nombreDelMapa) throws Exception
	{
		URL url;
		int maxLength = 0;
		File file = new File(nombreDelMapa+".txt");
		FileInputStream  secuencia = new FileInputStream(file);
		Scanner in = new Scanner(secuencia);

		while(in.hasNext())
		{
			String line = in.nextLine();

          		lineas.add(line);
			
                  	int actLength = line.length();
                 	if(actLength > maxLength) maxLength = actLength;
		}

 	  	tiles = new Image[maxLength][lineas.size()];

		// A seria tile_1

		for(int x = 0; x < maxLength; x++)
		{
			for(int y = 0; y < (lineas.size()); y++)
			{
				String s = lineas.get(y);
				if(s.charAt(x) != " ".charAt(0))   tiles[x][y] = imagenes.get( s.charAt(x) - 97 );
			}
		}

		if( nombreDelMapa.equals("map1"))
		{

		 maplimit = 3528;
		 mapx = 1140;
		 mapSize = 3528;

		 sprites.add(new Sprite(ResourceLoader.grass, 270, 165) );
		 sprites.add(new Sprite(ResourceLoader.flower1, 554, 182) );
		 sprites.add(new Sprite(ResourceLoader.castle, 3589, 34) );
		 sprites.add(new Sprite(ResourceLoader.flower2, 1255, 179) );
		 sprites.add(new Sprite(ResourceLoader.grass2, 1050, 183) );
		 sprites.add(new Sprite(ResourceLoader.flower1, 1744, 182) );
		 sprites.add(new Sprite(ResourceLoader.grass2, 2561, 183) );

		 

		 sprites.add(new Goomba(450,180,4,0) );
		 sprites.add(new Goomba(870,180,4,0) );

		 sprites.add(new Goomba(1020,180,4,0) );
		 sprites.add(new Goomba(1050,180,4,0) );
		 sprites.add(new Goomba(1848,180,4,0) );
		 sprites.add(new Turtle(1885,160,4,0, false) );
		 sprites.add(new Goomba(2100,180,4,0) );
		 sprites.add(new Goomba(2132,180,4,0) );
		 sprites.add(new Goomba(2264,180,4,0) );
		 sprites.add(new Goomba(2296,180,4,0) );
		 sprites.add(new Goomba(2396,180,4,0) );
		 sprites.add(new Goomba(2428,180,4,0) );
		 sprites.add(new Goomba(3182,180,4,0) );
		 sprites.add(new Goomba(3212,180,4,0) );

		// sprites.add(new Coin(280,180,false) );
		// sprites.add(new FlagPole(280,120) );

	/*	Block(x, y, type, cont)
		type = 0 blint			cont = 2 moneda
		type = 1 blmarron		cont = 1 hongo
		type = 3 blpress		cont = 0 nada	*/

		 sprites.add(new Block(410,140,0,2) );
		 sprites.add(new Block(490,140,1,0) );
		 sprites.add(new Block(506,140,0,1) );
		 sprites.add(new Block(522,140,1,0) );
		 sprites.add(new Block(538,140,0,2) );
		 sprites.add(new Block(554,140,1,0) );
		 sprites.add(new Block(522,75,0,2) );

		 sprites.add(new Block(1500,140,1,0) );
		 sprites.add(new Block(1516,140,0,1) );
		 sprites.add(new Block(1532,140,1,0) );
		 sprites.add(new Block(1548,80,1,0) );
		 sprites.add(new Block(1564,80,1,0) );
		 sprites.add(new Block(1580,80,1,0) );
		 sprites.add(new Block(1596,80,1,0) );
		 sprites.add(new Block(1612,80,1,0) );
		 sprites.add(new Block(1628,80,1,0) );
		 sprites.add(new Block(1644,80,1,0) );
		 sprites.add(new Block(1660,80,1,0) );
		 sprites.add(new Block(1676,80,1,0) );
		 sprites.add(new Block(1756,80,1,0) );
		 sprites.add(new Block(1772,80,1,0) );
		 sprites.add(new Block(1788,80,0,1) );
		 sprites.add(new Block(1788,140,1,0) );
		 sprites.add(new Block(1908,140,1,3) );
		 sprites.add(new Block(1924,140,1,0) );
		 sprites.add(new Block(2004,140,0,2) );
		 sprites.add(new Block(2052,140,0,2) );
		 sprites.add(new Block(2052,80,0,1) );
		 sprites.add(new Block(2100,140,0,2) );
		 sprites.add(new Block(2200,140,1,2) );
		 sprites.add(new Block(2232,80,1,0) );
		 sprites.add(new Block(2248,80,1,0) );
		 sprites.add(new Block(2264,80,1,0) );
		 sprites.add(new Block(2364,80,1,0) );
		 sprites.add(new Block(2380,80,0,2) );
		 sprites.add(new Block(2396,80,0,2) );
		 sprites.add(new Block(2412,80,1,0) );
		 sprites.add(new Block(2380,140,1,0) );
		 sprites.add(new Block(2396,140,1,0) );

		 sprites.add(new Block(2476,192,5,0) );
		 sprites.add(new Block(2492,192,5,0) );
		 sprites.add(new Block(2508,192,5,0) );
		 sprites.add(new Block(2524,192,5,0) );
		 sprites.add(new Block(2492,175,5,0) );
		 sprites.add(new Block(2508,175,5,0) );
		 sprites.add(new Block(2524,175,5,0) );
		 sprites.add(new Block(2508,158,5,0) );
		 sprites.add(new Block(2524,158,5,0) );
		 sprites.add(new Block(2524,141,5,0) );
		 sprites.add(new Block(2476+120,192,5,0) );
		 sprites.add(new Block(2492+120,192,5,0) );
		 sprites.add(new Block(2508+120,192,5,0) );
		 sprites.add(new Block(2524+120,192,5,0) );
		 sprites.add(new Block(2476+120,175,5,0) );
		 sprites.add(new Block(2492+120,175,5,0) );
		 sprites.add(new Block(2508+120,175,5,0) );
		 sprites.add(new Block(2476+120,158,5,0) );
		 sprites.add(new Block(2492+120,158,5,0) );
		 sprites.add(new Block(2476+120,141,5,0) );

		 sprites.add(new Block(2476+260,192,5,0) );
		 sprites.add(new Block(2492+260,192,5,0) );
		 sprites.add(new Block(2508+260,192,5,0) );
		 sprites.add(new Block(2524+260,192,5,0) );
		 sprites.add(new Block(2492+260,175,5,0) );
		 sprites.add(new Block(2508+260,175,5,0) );
		 sprites.add(new Block(2524+260,175,5,0) );	 sprites.add(new Block(2524+276,192,5,0) );
		 sprites.add(new Block(2508+260,158,5,0) );	 sprites.add(new Block(2524+276,175,5,0) );
		 sprites.add(new Block(2524+260,158,5,0) );	 sprites.add(new Block(2524+276,158,5,0) );
		 sprites.add(new Block(2524+260,141,5,0) );	 sprites.add(new Block(2524+276,141,5,0) );

		 sprites.add(new Block(2850,141,5,0) );
		 sprites.add(new Block(2850,158,5,0) );
		 sprites.add(new Block(2850,175,5,0) );
		 sprites.add(new Block(2850,192,5,0) );
		 sprites.add(new Block(2866,158,5,0) );
		 sprites.add(new Block(2866,175,5,0) );
		 sprites.add(new Block(2866,192,5,0) );
		 sprites.add(new Block(2882,175,5,0) );
		 sprites.add(new Block(2882,192,5,0) );
		 sprites.add(new Block(2898,192,5,0) );

		 sprites.add(new Block(3100,140,1,0) );
		 sprites.add(new Block(3116,140,1,0) );
		 sprites.add(new Block(3132,140,0,2) );
		 sprites.add(new Block(3148,140,1,0) );

		for(int kx = 0; kx < 8; kx++)
		{
			for(int ky = 0; ky <= kx; ky++)
			{
				 sprites.add(new Block(3310+kx*16,192-ky*17,5,0) );	
			}
				 sprites.add(new Block(3310+8*16,192-kx*17,5,0) );	
		}

		 sprites.add(new FlagPole(3528,42) );

		background = ImageIO.read(new File(("background1.png")));

		}
		if(nombreDelMapa.equals("map1u"))
		{

		
		background = ImageIO.read(new File(("background2.png")));

		for(int ind = 0; ind < 12; ind++){sprites.add(new Block(0,210-17-ind*17,2,0) );}
		mapSize = 326; 
		for(int u =0; u< 3; u++)
		{
			for(int k = 0; k<10; k++)
			{
				sprites.add(new Block(100+k*16,193-u*17,2,0));

			}
		}
		for(int k = 0; k<10; k++)
		{
			sprites.add(new Block(100+k*16,17,2,0));
			sprites.add(new Block(100+k*16,17+17,2,0));
			sprites.add(new Block(100+k*16,17+34,2,0));
		}
		for(int k = 0; k<18; k++)
		{
			sprites.add(new Block(64+k*16,0,2,0));
		}
		for(int u =0; u< 3; u++)
		{
			for(int k = 0; k<7; k++)
			{
				sprites.add(new Coin(130-8+k*18, 159-20-20*u, false ) );
			}
		}

		sprites.add(new Sprite(ResourceLoader.pipe, 310+26-10, 178) );
		for( int iy = 1; iy < 11; iy++)
		{
			sprites.add(new Block(310+26-10,160-iy*17,2,0) );
			sprites.add(new Block(326+26-10,160-iy*17,2,0) );
		}

		sprites.add(new Block(310+26-10,160,2,2) );
		sprites.add(new Block(326+26-10,160,2,2) );

			//	sprites.add(new Coin(100+k*, 159 ) );

		}


		if(nombreDelMapa.equals("map2"))
		{
			mapSize = 2906;
			sprites.add(new Sprite(ResourceLoader.pipe, 2900+6, 178) );
			for(int ind = 0; ind < 12; ind++){sprites.add(new Block(0,210-17-ind*17,2,0) );}
			for(int ind = 0; ind < 140; ind++){sprites.add(new Block(ind*16+100,0,2,0) );}
			sprites.add(new Block(200,140,0,1) );
			for(int ind = 0; ind < 4; ind++){sprites.add(new Block(ind*16+216,140,0,2) );}
			sprites.add(new Goomba(325, 180, -4, 0));
			sprites.add(new Goomba(300, 180, -4, 0));
			for(int ind = 0; ind < 6; ind++){sprites.add(new Block(ind*40+370,193,4,0) );}
			for(int ind = 0; ind < 5; ind++){sprites.add(new Block(ind*40+410,193-17,4,0) );}
			for(int ind = 0; ind < 4; ind++){sprites.add(new Block(ind*40+450,193-34,4,0) );}
			for(int ind = 0; ind < 2; ind++){sprites.add(new Block(ind*40+490,193-51,4,0) );}
			for(int ind = 0; ind < 2; ind++){sprites.add(new Block(ind*40+650,193,4,0) );}
			for(int ind = 0; ind < 2; ind++){sprites.add(new Block(ind*40+650,193-17,4,0) );}
			sprites.add(new Block(650,193-34,4,0) );
			sprites.add(new Goomba(620, 180, -4, 0));
			sprites.add(new Block(610,193-68,2,2) );
			sprites.add(new Plataforma(2717+33, 170, 0, -6));
			sprites.add(new Plataforma(2539, 170, 0, +6));

		//	sprites.add(new Plataforma(200, 170, 0, -4));
			//sprites.add(new Plataforma(500, 170, 0, -4));
		//	sprites.add(new Plataforma(300, 170, 0, -4));

			sprites.add(new Block(790,193-51,2,0) );
			sprites.add(new Block(790,193-68,2,0) );
			sprites.add(new Block(790,193-85,2,0) );
			sprites.add(new Block(790+16,193-51,2,0) ); sprites.add(new Coin(804,193-69,false) );
			sprites.add(new Block(790+32,193-51,2,0) );
			sprites.add(new Block(790+32,193-68,2,0) );
			sprites.add(new Block(790+32,193-85,2,0) );
			sprites.add(new Block(790+48,193-85,2,0) );
			sprites.add(new Block(790+64,193-85,2,0) );
			sprites.add(new Block(790+80,193-85,2,0) );
			sprites.add(new Block(790+80,193-68,2,0) );
			sprites.add(new Block(790+80,193-51,2,0) );
			sprites.add(new Coin(804+16,193-69-38,false) );
			sprites.add(new Coin(804+32,193-69-38,false) );
			sprites.add(new Coin(804+48,193-69-38,false) );
			sprites.add(new Coin(804+64,193-69-38,false) );
			sprites.add(new Coin(804+64+16,193-69,false) );
			sprites.add(new Block(790+96,193-51,2,0) );
			sprites.add(new Block(790+112,193-51,2,0) );
			sprites.add(new Block(790+112,193-68,2,0) );
			sprites.add(new Block(790+112,193-85,2,3) );

			sprites.add(new Turtle(804+48,180, -4, 0, false) );
			sprites.add(new Turtle(804+85,180, -4, 0, false) );

			for(int ind = 0; ind < 5; ind++){sprites.add(new Block(1000,193-51-17*ind,2,0) );}
			for(int ind = 0; ind < 5; ind++){sprites.add(new Block(1016,193-51-17*ind,2,0) );}
			sprites.add(new Block(1032,193-34,2,0) ); sprites.add(new Block(1048,193-34,2,0) );
			sprites.add(new Block(1032,193-51,2,0) ); sprites.add(new Block(1048,193-51,2,0) );
			sprites.add(new Block(1032,193-102-17,2,0) ); sprites.add(new Block(1032,193-119-17,2,0) );
			sprites.add(new Block(1048,193-102-17,2,0) ); sprites.add(new Block(1048,193-119-17,2,0) );
			sprites.add(new Block(1032,193-119-34,2,0) ); sprites.add(new Block(1048,193-119-34,2,0) );
			sprites.add(new Block(1032,193-119-51,2,0) ); sprites.add(new Block(1048,193-119-51,2,0) );

			for(int ind = 0; ind < 6; ind++){sprites.add(new Block(1130+16*ind,193-119-34,2,0) ); sprites.add(new Block(1130+16*ind,193-119-34-17,2,0) ); sprites.add(new Block(1130+16*ind,193-51,2,0) );}
			for(int ind = 0; ind < 5; ind++){ sprites.add(new Block(1210-16,193-68-17*ind,2,0)); sprites.add(new Block(1210,193-68-17*ind,2,0));}

			for(int ind = 0; ind < 4; ind++){sprites.add(new Coin(1129+16*ind,193-51-20,false) );}
			sprites.add(new Turtle(1130,180,-4, 0, false) );
			sprites.add(new Goomba(1180,180,-4, 0) );
			sprites.add(new Goomba(1220,180,-4, 0) );
			for(int ind = 0; ind < 8; ind++){ sprites.add(new Block(1310,193-51-17*ind,2,0));}
			sprites.add(new Block(1310-16,23,2,0));			sprites.add(new Block(1310+16,23,2,0)); sprites.add(new Block(1310+32,23,2,0));
			sprites.add(new Block(1310-16,23+17,2,0));		sprites.add(new Block(1310+16,23+17,2,0)); sprites.add(new Block(1310+32,23+17,2,0));
			sprites.add(new Block(1310+16,193-51,2,0));
			sprites.add(new Block(1310+32,193-51,2,0));
			sprites.add(new Block(1310+32,193-68,2,0));
			sprites.add(new Coin(1310+14,193-68-5,false));
			sprites.add(new Goomba(1420,180,-4, 0) );

			for(int ind = 0; ind < 7; ind++){ sprites.add(new Block(1342+50,193-51-17*ind,2,0));sprites.add(new Block(1342+50+16,193-51-17*ind,2,0));}
			for(int ind = 0; ind < 4; ind++){ sprites.add(new Block(1342+140+ind*16,193-51,2,0));}

			sprites.add(new Goomba(1500,100,-4, 0) );			sprites.add(new Goomba(1530,100,-4, 0) );
			for(int ind = 0; ind < 4; ind++){ sprites.add(new Block(1342+140+ind*16,23,2,0)); sprites.add(new Block(1342+140+ind*16,23+17,2,0)); }
			for(int ind = 0; ind < 5; ind++){ sprites.add(new Block(1596+ind*16,142-17,2,0)); sprites.add(new Block(1596+ind*16,142-34,2,0)); sprites.add(new Coin(1596+ind*16, 142-34-35, false)); }
			for(int ind = 0; ind < 3; ind++){ sprites.add(new Goomba(1760+ind*30,180,-4, 0) );}
			sprites.add(new Plant(1864,127,60) );	sprites.add(new Plant(1984,127,60) );	sprites.add(new Plant(2104,157,40) );
			sprites.add(new Goomba(2030,180,-4,0) );
			sprites.add(new Block(2219,193,2,0) );	sprites.add(new Block(2235,193,2,0) );
			sprites.add(new Block(2219,193-17,2,0) );	sprites.add(new Block(2235,193-17,2,0) );
			sprites.add(new Block(2219,193-34,2,0) );	sprites.add(new Block(2235,193-34,2,0) );

			for( int ix = 0; ix < 4; ix++)
			{
				for( int iy = 0; iy <= ix; iy++)
				{
					sprites.add(new Block(2440+ix*16,193-iy*17,4,0) );
				}
				sprites.add(new Block(2504,193-ix*17,4,0) );
			}
			sprites.add(new Goomba(2500,90,-4,0) ); sprites.add(new Goomba(2470,90,-4,0) );
			for(int ind = 0; ind < 6; ind++)
			{
				sprites.add(new Block(2609+16*ind,142, 2, 0));
			}
			//sprites.add(new Turtle(2659+30, 120, -4, 0, false));

			for( int iy = 1; iy < 11; iy++)
			{
				sprites.add(new Block(2900+6,160-iy*17,2,0) );
				sprites.add(new Block(2916+6,160-iy*17,2,0) );
			}
				sprites.add(new Block(2900+6,160,2,2) );
				sprites.add(new Block(2916+6,160,2,2) );

			
			background = ImageIO.read(new File("background2.png"));
		}
		if(nombreDelMapa.equals("map3"))
		{
			mapSize = 308;
			background = ImageIO.read(new File(("background1.png")));
			for(int kx = 0; kx < 8; kx++)
			{
				for(int ky = 0; ky <= kx; ky++)
				{
					 sprites.add(new Block(90+kx*16,192-ky*17,5,0) );	
				}
				 sprites.add(new Block(90+8*16,192-kx*17,5,0) );	
			}

		 sprites.add(new FlagPole(90+218,42) );
		 sprites.add(new Sprite(ResourceLoader.castle, 439, 34) );

		}
		if(nombreDelMapa.equals("map4"))
		{
			mapSize = 3542;
			int a = -30;

			sprites.add(new Cloud(200,40+a,4));
			sprites.add(new Cloud(140,60+a,2));

			background = ImageIO.read(new File(("background3.png")));

			sprites.add(new Sprite(ResourceLoader.grass, 100, 165) );
			sprites.add(new Sprite(ResourceLoader.flower1, 340, 182) );
			

			for(int u = 0; ((230+17)+26*(u-1))<240; u++) sprites.add(new Sprite(ResourceLoader.tronco, 440+38-14, 230+17+26*u+a) );
			sprites.add(new Plataforma(440,230+a, 1));


			sprites.add(new Plataforma(590,115+a, 3));
			for(int u = 0; ((115+24)+26*(u-1))<240; u++) sprites.add(new Sprite(ResourceLoader.tronco, 590+65-14, 115+24+26*u+a) );

			 sprites.add(new Plataforma(550,180+a, 2));
			for(int u = 0; ((180+16)+26*(u-1))<240; u++) sprites.add(new Sprite(ResourceLoader.tronco, 550+94-14, 180+16+26*u+a) );

			sprites.add(new Turtle(630, 50+a, -3, 0, false));

			sprites.add(new Plataforma(763,230+a, 1));
			for(int u = 0; (247+26*(u-1))<240; u++) sprites.add(new Sprite(ResourceLoader.tronco, 763+38-14, 230+17+26*u+a) );

			sprites.add(new Plataforma(838,165+a, 3));
			for(int u = 0; ((165+24)+26*(u-1))<240; u++) sprites.add(new Sprite(ResourceLoader.tronco, 838+65-14, 165+24+26*u+a) );

			sprites.add(new Plataforma(980,100+a, 2));
			for(int u = 0; ((116)+26*(u-1))<240; u++) sprites.add(new Sprite(ResourceLoader.tronco, 980+94-14, 100+16+26*u+a) );

			sprites.add(new Goomba(1100, 50+a, -3, 0));
			sprites.add(new Goomba(1140, 50+a, -3, 0));
			sprites.add(new Coin(795, 205+a, false));
			sprites.add(new Coin(920, 55+a, false));
			sprites.add(new Coin(940, 55+a, false));
			sprites.add(new Coin(1200, 140+a,false));
			sprites.add(new Coin(1220, 140+a,false));

			sprites.add(new Plataforma(1205, 240+a,1));
			for(int u = 0; (257+26*(u-1))<240; u++) sprites.add(new Sprite(ResourceLoader.tronco,1205+38-14, 240+17+26*u+a) );


			sprites.add(new Plataforma(1310, 180+a, -5, true));

			sprites.add(new Plataforma(1398, 90+a,1));
			for(int u = 0; (107+26*(u-1))<240; u++) sprites.add(new Sprite(ResourceLoader.tronco,1398+38-14, 90+17+26*u+a) );

			sprites.add(new Plataforma(1380, 240+a,3));
			for(int u = 0; (264+26*(u-1))<240; u++) sprites.add(new Sprite(ResourceLoader.tronco, 1380+65-14, 240+24+26*u+a) );

			sprites.add(new Block(1380, 180+a,0,1));



			sprites.add(new Coin(1402, 65+a,false));
			sprites.add(new Coin(1422, 65+a,false));
			sprites.add(new Coin(1442, 65+a,false));
			sprites.add(new Plataforma(1536, 180+a,1));
			for(int u = 0; (197+26*(u-1))<240; u++) sprites.add(new Sprite(ResourceLoader.tronco,1536+38-14, 180+17+26*u+a) );

			sprites.add(new Turtle(1622, 100+a,0,4,true));

			sprites.add(new Plataforma(1662,125+a, 2));
			for(int u = 0; ((141)+26*(u-1))<240; u++) sprites.add(new Sprite(ResourceLoader.tronco, 1662+94-14, 125+16+26*u+a) );

			sprites.add(new Goomba(1722, 80+a,-4,0));
			sprites.add(new Plataforma(1860, 120+a, 6, 0));
			sprites.add(new Plataforma(2050, 160+a, 3, 0));
			sprites.add(new Coin(1870, 90+a, false));
			sprites.add(new Coin(1890, 90+a, false));
			sprites.add(new Coin(2060, 90+a, false));
			sprites.add(new Coin(2080, 90+a, false));
			sprites.add(new Plataforma(2230, 200+a, 3));
			for(int u = 0; (224+26*(u-1))<240; u++) sprites.add(new Sprite(ResourceLoader.tronco, 2230+65-14, 200+24+26*u+a) );

			sprites.add(new Sprite(ResourceLoader.tronco, 2230+65-14, 200+24+a) );
			sprites.add(new Plataforma(2390, 140+a, 2));
			for(int u = 0; ((156)+26*(u-1))<240; u++) sprites.add(new Sprite(ResourceLoader.tronco, 2390+94-14, 140+16+26*u+a) );
			sprites.add(new Turtle(2500, 100+a, -3, 0, false));
			sprites.add(new Plataforma(2610, 240+a, 1));
			for(int u = 0; (257+26*(u-1))<240; u++) sprites.add(new Sprite(ResourceLoader.tronco,2610+38-14, 240+17+26*u+a) );

			sprites.add(new Coin(2640, 220+a, false));
			sprites.add(new Coin(2620, 220+a, false));
			sprites.add(new Coin(2660, 220+a, false));
			sprites.add(new Turtle(2637, 80+a,0,4,true));

			sprites.add(new Plataforma(2700, 180+a, 3));
			for(int u = 0; (204+26*(u-1))<240; u++) sprites.add(new Sprite(ResourceLoader.tronco, 2700+65-14, 180+24+26*u+a) );
			sprites.add(new Plataforma(2865, 180+a, 3));
			for(int u = 0; (204+26*(u-1))<240; u++) sprites.add(new Sprite(ResourceLoader.tronco, 2865+65-14, 180+24+26*u+a) );
			sprites.add(new Plataforma(3000, 180+a, -6, 0));
			sprites.add(new Turtle(3350, 160+a, -3, 0, false));

			//sprites.add(new Turtle(650, 50, -3, 0, false)); 
			for(int u = 0; u < 3; u++) sprites.add(new Coin(625+u*25, 90+a, false));
			sprites.add(new FlagPole(3542,42) );
			sprites.add(new Sprite(ResourceLoader.castle, 3649, 34) );

		}
		if(nombreDelMapa.equals("map5"))
		{
			mapSize = 3000+500;
			int a = -30;
			
			background = ImageIO.read(new File(("background4.png")));

			sprites.add(new Block(464,150+a, 4, 0));
			sprites.add(new Block(578, 210+a, 4,0));
			for(int th = 0; th < 6; th++){sprites.add(new EnemyFireBall(580,212+a, th*11));}
			sprites.add(new Block(578, 142+a, 0,1));
			sprites.add(new Block(690, 120+a, 4,0));
			sprites.add(new Block(922, 120+a, 4,0));
			for(int th = 0; th < 5; th++){sprites.add(new EnemyFireBall(924,122+a, th*11));}
			sprites.add(new Block(922+150, 120+a, 4,0));
			for(int th = 0; th < 5; th++){sprites.add(new EnemyFireBall(924+150,122+a, th*11));}
			sprites.add(new Block(1186, 120+a, 4,0));
			for(int th = 0; th < 5; th++){sprites.add(new EnemyFireBall(1188,122+a, th*11));}
			sprites.add(new Block(1340, 180-17+30+a, 4,0));
			for(int th = 0; th < 5; th++){sprites.add(new EnemyFireBall(1342,182-17+30+a, th*11));}
			sprites.add(new Block(1410, 120+a, 4,0));
			sprites.add(new Block(1340+140, 180-17+30+a, 4,0));
			for(int th = 0; th < 5; th++){sprites.add(new EnemyFireBall(1342+140,182-17+30+a, th*11));}
			sprites.add(new Block(1560, 120+a, 4,0));
			for(int th = 0; th < 5; th++){sprites.add(new EnemyFireBall(1562,122+a, th*11));}
			sprites.add(new Block(1340+280+30, 180-17+30+a, 4,0));
			
			bridge[0] = new Block(2583+50-5, 180-30, 6,0);
			bridge[1] = new Block(2583+50-5-17, 180-30, 6,0);
			bridge[2] = new Block(2583+50-5-17*2, 180-30, 6,0);
			bridge[3] = new Block(2583+50-5-17*3, 180-30, 6,0);
			bridge[4] = new Block(2583+50-5-17*4, 180-30, 6,0);
			bridge[5] = new Block(2583+50-5-17*5, 180-30, 6,0);
			bridge[6] = new Block(2583+50-5-17*6, 180-30, 6,0);
			bridge[7] = new Block(2583+50-5-17*7, 180-30, 6,0);
			bridge[8] = new Block(2583+50-5-17*8, 180-30, 6,0);
			bridge[9] = new Block(2583+50-5-17*9, 180-30, 6,0);
			bridge[10] = new Block(2583+50-5-17*10, 180-30, 6,0);
			bridge[11] = new Block(2583+50-5-17*11, 180-30, 6,0);
			bridge[12] = new Block(2583+50-5-17*12, 180-30, 6,0);
			bridge[13] = new Block(2583+50-5-17*13, 180-30, 6,0);
			bridge[14] = new Block(2583+50-5-17*14, 180-30, 6,0);
			bridge[15] = new Block(2583+50-5-17*15, 180-30, 6,0);
			bridge[16] = new Block(2583+50-5-17*16, 180-30, 4,0);
			bridge[17] = new Block(2583+50-5-17*17, 180-30, 6,0);
			
			sprites.add(bridge[0]);
			sprites.add(bridge[1]);
			sprites.add(bridge[2]);
			sprites.add(bridge[3]);
			sprites.add(bridge[4]);
			sprites.add(bridge[5]);
			sprites.add(bridge[6]);
			sprites.add(bridge[7]);
			sprites.add(bridge[8]);
			sprites.add(bridge[9]);
			sprites.add(bridge[10]);
			sprites.add(bridge[11]);
			sprites.add(bridge[12]);
			sprites.add(bridge[13]);
			sprites.add(bridge[14]);
			sprites.add(bridge[15]);
			
			sprites.add(new Axe(2665, 130));
			sprites.add(new Bowser(2570, 120+a, bridge));
			sprites.add(new Plataforma(2418, 110+a, +4, 0));

			sprites.add(new Bag(3000,150));
		}
	}
}