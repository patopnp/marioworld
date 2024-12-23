package app;

import java.awt.*;
import java.util.ArrayList;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.font.*;
import javax.swing.Timer;
import java.awt.event.*;

import javax.imageio.ImageIO;
import javax.swing.*;

public class GameManager extends JFrame
{
	public int levelNumber;
	public static ResourceLoader rl;
	public static int NORMAL = 0;
	public static int MARIO_GETTING_BIGGER = 10;
	public static int MARIO_GETTING_SMALLER = 11;
	public static int MARIO_GETTING_INTO_A_PIPE = 12;
	public static int gameState;
	long time = System.currentTimeMillis();
	long timeSinceStartOfTransition;
	long elapsedTime = 0;
	static int timeSinceVictory = 0;
	int awaitingTime = 0;
	static int indice = 1;
	static Color currColor;
	Color colores[] = new Color[5];
	static boolean showToNextLevelScreen;
	static boolean showGameOverScreen;
	static boolean endLevel;
	public static boolean displayVictoryMessage;
	Mario mario;

	public void run()
	{
		while(true) {
			
			
			try {
				Thread.sleep(Duration.ofMillis(5));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			if (showGameOverScreen) return;
			
			time = time + elapsedTime;
			elapsedTime = System.currentTimeMillis() - time;
			
			if (displayVictoryMessage) {
				
				timeSinceVictory += elapsedTime;
				
			}
			
			if (showToNextLevelScreen) {
				
				timeSinceStartOfTransition += elapsedTime;
				if (timeSinceStartOfTransition > 3000) {
					
					timeSinceStartOfTransition = 0;
					showToNextLevelScreen = false;
					if (indice == 1) {
						ResourceLoader.levelOneMusic.play();
					}
					if (indice == 2) {
						ResourceLoader.levelTwoMusic.play();
					}
					if (indice == 4) {
						ResourceLoader.levelOneMusic.play();
					}
					if (indice == 5) {
						ResourceLoader.bowserCastleMusic.play();
					}
					
				}
				continue;
			}
			
			if (mario.getCond() == Mario.GETTING_BIGGER || mario.getCond() == Mario.GETTING_SMALLER || mario.getCond() == Mario.TURNING_FIRE_POWERED) {
				System.out.println(elapsedTime);
				mario.update(elapsedTime);
				SwingUtilities.invokeLater(this::repaint);
				continue;
			}
			
			
			if(mario.lives < 0){comenzarJuego();}
			if (awaitingTime < 6000 &&  !(((Mario)rl.currMap.player).goUnder) )
			{

				if (rl.currMap.player.getx()> rl.currMap.mapSize){ 
					awaitingTime += elapsedTime;  
					if(mario.levelTime>0) {
						
						if (awaitingTime > 1200){
							mario.levelTime -= elapsedTime*100;
							Mario.score += elapsedTime*5;
						}
						
						//aumentar score
					}
					else{
						mario.levelTime = 0;
					}
				}

				int p = rl.currMap.sprites.size();

				for (int k = 0; k < p; k++) 
				{ 
					if ( (rl.currMap.sprites.get(k)).estado == 3 && !(rl.currMap.sprites.get(k) instanceof Mario)) { rl.currMap.sprites.remove(k); p--; k--; }
				}
			
		

				

				int elapsedTime2 = Math.min(20,(int)elapsedTime);

				

				for(Sprite spr2 : Sprite.pending){rl.currMap.sprites.add(spr2);}
				Sprite.pending.clear();

				for(Sprite spr : rl.currMap.sprites){ if(spr instanceof Criatura){((Criatura)spr).collide(rl.currMap,(int)elapsedTime2);}}
	 			for(Sprite spr : rl.currMap.sprites){ if (spr instanceof EnemyFireBall || (spr.getx() < (rl.currMap.player.getx() + 300)) || ( (spr instanceof Bowser || spr instanceof Bowser.AlientoDeFuego) && spr.getx() < (rl.currMap.player.getx() + 800)) ) {spr.update(elapsedTime2);}}

			}
			else
			{



			mario.setx(80);
			mario.sety(190); 
			mario.setdx(0);
			mario.movimiento = 3;

			if(indice !=0 && indice != 2 && !(((Mario)rl.currMap.player).goUnder))
			{
				showToNextLevelScreen = true;
				timeSinceStartOfTransition = 0;
				mario.levelTime = (200*1000);
			}
			mario.freeze = false;

			rl.currMap = new Map();

			(rl.currMap).cargarImagenesdeTiles();
			
			try
			{
				if(mario.goUnder){ 
					ResourceLoader.levelOneUndergroundMusic.play();
					mario.goUnder = false; 
					System.out.println(indice+"u");
					rl.currMap.loadMap("map"+indice+"u"); 
					mario.onGround = false; 
					mario.sety(0);
					mario.setx(30);
					indice--; 
					awaitingTime = 5999;
				}
				else
				{ 
					indice++; 
					System.out.println(indice);
					rl.currMap.loadMap("map"+indice); 
					if(indice == 1)
					{ 
						ResourceLoader.levelOneMusic.stop();
						ResourceLoader.levelOneMusic.play();
						mario.sety(150); mario.setx(3009);
					} 
					if (indice == 2) {
						mario.sety(0); mario.setx(50);
					}
					if (indice == 3) {
						ResourceLoader.levelOneMusic.stop();
						ResourceLoader.levelOneMusic.play();
						mario.sety(120);
						mario.setx(30);
					}
					
					mario.onGround = false;
					awaitingTime = 0;
					if(indice == 2) { 
						awaitingTime = 5999;
					}
					
				}
			}catch (Exception e){}

				rl.currMap.sprites.add(mario);
				rl.currMap.player = mario;
				if(indice <= 5 && indice >= 1){currColor = colores[(indice-1)];}
			}
			SwingUtilities.invokeLater(this::repaint);
		}
	}

	public void comenzarJuego()
	{
		indice = 1;
		currColor = colores[0];

		try {
			
			rl = new ResourceLoader();
			rl.loadSoundEffects();
			rl.loadImages();
			rl.loadAnims();
			rl.currMap = new Map();
			mario = new Mario(80,190,0,0);
			(rl.currMap).cargarImagenesdeTiles();
		}
		catch(IOException ioe) {
			
		}
		
		try{
		rl.currMap.loadMap("map1");}catch (Exception e){}

		//indice = 4;
		rl.currMap.sprites.add(mario);
		rl.currMap.player = mario;
		
		
		
		new Thread(this::run).start();
		
		
		/*
		
		Timer a = new Timer(15,this);
		a.start();
		
		*/
		
		
		showToNextLevelScreen = true;
		
		timeSinceStartOfTransition = 0;
		
		elapsedTime = 0;
		awaitingTime = 0;	

		Lamina lam = new Lamina();
		add(lam);
		lam.setFocusable(true);
		lam.addKeyListener(mario);
		setVisible(true);

		lam.requestFocus();
		repaint();
		lam.repaint();
		validate();

		elapsedTime = 0;
		awaitingTime = 0;
		//mario.setx(2500);
		
	}
	

	public static void main(String[] args)
	{
	
        JFrame f = new GameManager();
        
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack();
        f.setVisible(true);
		
	}

	public static ResourceLoader getResourceLoader() {
		return rl;
	}
	
	public GameManager(){
		colores[0] = new Color(241,158,46);
		colores[1] = new Color(56,73,226);
		colores[2] = new Color(184,186,95);
		colores[3] = new Color(111,162,89);
		colores[4] = new Color(233,63,75);
		levelNumber = 1;
		
		setPreferredSize(new Dimension(412,313));
		
		final JButton inicio = new JButton("Play");
		final JButton acercaDe = new JButton("About");
		
		final JLabel rotulo = new JLabel("Programmer: Patricio Petraglia", JLabel.CENTER);
		final JLabel rotulo2 = new JLabel("Sprites: Mario bros", JLabel.CENTER);
		rotulo.setVisible(false);
		rotulo2.setVisible(false);
		final Box cajaH1 = Box.createHorizontalBox();
		final Box cajaV = Box.createVerticalBox();

		cajaH1.add(Box.createGlue());
		cajaH1.add(inicio);
		cajaH1.add(Box.createGlue());
		cajaH1.add(acercaDe);
		cajaH1.add(Box.createGlue());

		cajaV.add(rotulo);
		cajaV.add(Box.createGlue());
		cajaV.add(rotulo2);
		cajaV.add(Box.createGlue());
		cajaV.add(cajaH1);
		
		add(cajaV, BorderLayout.CENTER);
/*
		// Load the background image
        ImageIcon backgroundImage = new ImageIcon("mariomain.png");
        
        // Create a JLabel with the background image
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, getWidth(), getHeight());  // Set size to match frame
        
        // Add the background label to the frame's content pane
        getContentPane().add(backgroundLabel);

        // Add other components on top of the background
        JLabel helloLabel = new JLabel("Hello, World!");
        helloLabel.setBounds(20, 20, 100, 30);  // Set position and size manually
        getContentPane().add(helloLabel);
        // Bring the background label to the back
        getContentPane().setComponentZOrder(backgroundLabel, getContentPane().getComponentCount() - 1);
*/
        setVisible(true);
		//add(inicio);
		//add(acercaDe);

		inicio.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evento)
			{ 
				remove(cajaV);
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						comenzarJuego();
					}
					
				});
			} 
		});
		acercaDe.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evento)
			{ rotulo.setVisible(true); rotulo2.setVisible(true); inicio.setVisible(true); acercaDe.setVisible(false); repaint();} 
		});

	}
	

	static class Lamina extends JPanel
	{
		Font pixelFont;
		
		public Lamina() {
			try {
				pixelFont = Font.createFont(Font.TRUETYPE_FONT, new File("PressStart2P-Regular.ttf")).deriveFont(12f);
				GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
				ge.registerFont(pixelFont);
			} catch (FontFormatException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			g.setFont(pixelFont);
            g.setColor(Color.WHITE);
            
            
            
            
            if (showGameOverScreen) {
            	
            	int level = indice>=3?indice-1:indice;
            	String formattedScore = String.format("%0" + 6 + "d", Mario.score);
            	g.drawImage(rl.cinside[0],132,16, null);
				g.drawString("MARIO",40,20);
	            g.drawString(formattedScore,40,35);
	            g.drawString("WORLD",210,20);
	            g.drawString(1+"-"+level, 227, 35);
	            g.drawString("TIME",305, 20);
	            int coinScore = ((Mario)rl.currMap.player).coins;
	            g.drawString(coinScore<10?"x0"+coinScore:"x"+coinScore,150,35);
	            
            	g.drawString("GAME OVER",140,140);
            	return;
            }
            
            
            
			if (showToNextLevelScreen == true) {
				setBackground(Color.BLACK);
				
					int level = indice>=3?indice-1:indice;
					
				
					g.drawImage(rl.cinside[0],132,16, null);
					g.drawString("MARIO",40,20);
					String formattedScore = String.format("%0" + 6 + "d", Mario.score);
		            g.drawString(formattedScore,40,35);
		            int coinScore = ((Mario)rl.currMap.player).coins;
		            g.drawString(coinScore<10?"x0"+coinScore:"x"+coinScore,150,35);
					g.drawString("WORLD",210,20);
		            g.drawString(1+"-"+level, 227, 35);
		            g.drawString("TIME",305, 20);
					
					g.drawString("WORLD 1 - "+level,125,100);
					g.drawImage(rl.imagenesm_s[0],140+15,130, null);
					g.drawString("x "+((Mario)rl.currMap.player).lives, 170+15, 150);	
				
	           
				
				return;
			}
			
			
			g.drawImage(rl.currMap.background, (int)(rl.currMap.player.getx()*(-106)/rl.currMap.mapSize), -230, null);
			
			int cameraFollow;			

			
			ArrayList<Sprite> drawFirst = new ArrayList<Sprite>();
			ArrayList<Sprite> drawSecond = new ArrayList<Sprite>();
			ArrayList<Sprite> drawThird = new ArrayList<Sprite>();

			if( rl.currMap.player.getx()>200)
			{
				cameraFollow = (int)(-rl.currMap.player.getx()+200);
				g.drawImage(rl.currMap.background, (int)((rl.currMap.player.getx()-200)*(-106)/rl.currMap.mapSize), 0, null);
			} 
			else
			{ 
				g.drawImage(rl.currMap.background, 0, 0, null);
				cameraFollow = 0;
			}
			


			for(Sprite img : rl.currMap.sprites){ if(img instanceof Plant){ drawFirst.add(img); }else if(img.getImage() == ResourceLoader.pipe){ drawThird.add(img);} else {drawSecond.add(img);}}

			for(Sprite img : drawFirst){ g.drawImage(img.getImage(), (int)(img.getx()+cameraFollow), (int)img.gety(),null); }

			for(int x = 0; x < rl.currMap.tiles.length; x++) //draw second
			{
				for(int y = 0; y < rl.currMap.tiles[0].length; y++)
				{
					g.drawImage(rl.currMap.tiles[x][y], (int)(x*30+cameraFollow), y*30, null);
				}
			}
			int level = indice>=3?indice-1:indice;
			g.drawImage(rl.cinside[0],132,16, null);
			for(Sprite img : drawSecond){ g.drawImage(img.getImage(), (int)(img.getx()+cameraFollow), (int)img.gety(),null); }
			for(Sprite img : drawThird){ g.drawImage(img.getImage(), (int)(img.getx()+cameraFollow), (int)img.gety(),null); }
			
			


			
			g.drawString("MARIO",40,20);
			
			String formattedScore = String.format("%0" + 6 + "d", Mario.score);
			
            g.drawString(formattedScore,40,35);
            int coinScore = ((Mario)rl.currMap.player).coins;
            g.drawString(coinScore<10?"x0"+coinScore:"x"+coinScore,150,35);
			g.drawString("WORLD",210,20);
            g.drawString("1-"+level, 227, 35);
            g.drawString("TIME",305, 20);
            g.drawString((int)(((Mario)rl.currMap.player).levelTime*0.001)+"", 305, 35);
            
            
            if (displayVictoryMessage) {
            	
            	String message = "THANK YOU MARIO!";
            	String messageSecondPart =  "BUT OUR PRINCESS IS IN";
            	String messageThirdPart =  "ANOTHER CASTLE!";
            	
            	int numOfLetters = timeSinceVictory/100;
            	
            	
            	if (numOfLetters <= 16) {
            		String displayedMsg = message.substring(0, numOfLetters);
            		g.drawString(displayedMsg,100,80);
            	}
            	else if (numOfLetters <= 38){
            		g.drawString(message,100,80);
            		String displayedMsg = messageSecondPart.substring(0, numOfLetters-16);
            		g.drawString(displayedMsg,61,105);
            	}
            	else if (numOfLetters <= 53) {
            		g.drawString(message,100,80);
            		g.drawString(messageSecondPart,61,105);
            		String displayedMsg = messageThirdPart.substring(0, numOfLetters-38);
            		g.drawString(displayedMsg,61,130);
            	}
            	else {
            		g.drawString(message,100,80);
            		g.drawString(messageSecondPart,61,105);
            		g.drawString(messageThirdPart,61,130);
            	}
            }

		}
	}
}