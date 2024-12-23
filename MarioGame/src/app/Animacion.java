package app;

import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Animacion 
{
	private ArrayList<Anim> anim;
	private int duracionTotal;
	private int currFrame = 0;
	private long animTime;
	
	public Animacion(ArrayList<Anim> matriz, int duracion)
	{
		this.anim = matriz;
		this.duracionTotal = duracion;
	}

	public Animacion clone(){return new Animacion(this.anim, duracionTotal);}
	public void start(){ currFrame = 0; animTime = 0;}
	public long getduracionTotal(){ return duracionTotal; }
	
	public void add(Image imagen, int duracion)
	{
		duracionTotal += duracion;
		anim.add(new Anim(imagen, duracionTotal));
	}

	public int getCurrFrameIndex() {
		return currFrame;
	}
	
	public void addFlipped(Image imagen, int duracion)
	{

		BufferedImage bufferedImage =  new BufferedImage(imagen.getWidth(null), imagen.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics gb = bufferedImage.getGraphics();
	        gb.drawImage(imagen, 0, 0, null);
	        gb.dispose();
	        AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
	        tx.translate(-imagen.getWidth(null), 0);
	        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
	        bufferedImage = op.filter(bufferedImage, null);

	        add(bufferedImage, duracion);
	}

	public Image getFrame(int index)
	{
		return anim.get(index).frame;
	}
	
	private class Anim
	{
		Image frame;
		int endTime;
		public Anim(Image img, int dur)
		{
			this.frame = img;
			this.endTime = dur;
		}
		
	}

	public void update(long elapsedTime)
	{
		animTime += elapsedTime;
		if (animTime > duracionTotal) {animTime = animTime % duracionTotal; currFrame = 0;}
		while(animTime > ((Anim)anim.get(currFrame)).endTime)
		{
			currFrame++;
			//System.out.println("elapsedTime = " + animTime + " currFrame = " + currFrame);
		}
		
	}

	public Image getFrame()
	{
		return anim.get(currFrame).frame;
	}
}
class Test
{

	public static void main(String[] args)
	{
		Image imagenes[] = new Image[5];
		Image imagenesm[] = new Image[8];
		Image imagenesm_s[] = new Image[8];
		Image imagenest[] = new Image[8];
		Image imagenest2[] = new Image[6];
		Image goombasm = null;
		Image turtlesm[] = new Image[4];
		Image goombainv = null;
		Image turtleinv = null;
		Image imagenesb[] = new Image[10];
		Image h = null;
		Image esc = null;
		Image[] cinside = new Image[2];
		Image[] planta = new Image[2];
		Image[] bolita = new Image[8];
		Image[] imagenesmf = new Image[8];
		Image bowser = null;
		Image aliento = null;
		Image imagenesmm[] = new Image[3];
		Image smoke[] = new Image[6];
		Image grass = null;
		Image background = null;
		Image ff_img = null;

		try 
		{
			goombasm = ImageIO.read(new File("C:/Nueva carpeta/goombasm.png"));
			goombainv = ImageIO.read(new File("C:/Nueva carpeta/goombainv.png"));
			turtleinv = ImageIO.read(new File("C:/Nueva carpeta/turtleinv.png"));
			h = ImageIO.read(new File("C:/Nueva carpeta/hongo.png"));
			esc = ImageIO.read(new File("C:/Nueva carpeta/escombro.png"));
			cinside[0] = ImageIO.read(new File("C:/Nueva carpeta/coininside1.png"));
			cinside[1] = ImageIO.read(new File("C:/Nueva carpeta/coininside2.png"));
			planta[0] = ImageIO.read(new File("C:/Nueva carpeta/plant1.png"));
			planta[1] = ImageIO.read(new File("C:/Nueva carpeta/plant2.png"));
			ff_img = ImageIO.read(new File("C:/Nueva carpeta/fireflower.png"));

			bowser = ImageIO.read(new File("C:/Nueva carpeta/bowser1.png"));
			aliento = ImageIO.read(new File("C:/Nueva carpeta/aliento.png"));
			grass = ImageIO.read(new File("C:/Nueva carpeta/grass1.png"));
			background = ImageIO.read(new File("C:/Nueva carpeta/background.png"));
			
			for(int u = 0; u < 8; u++) { bolita[u] = ImageIO.read(new File("C:/Nueva carpeta/bola"+u+".png")); }
			
			for(int u = 0; u < 6; u++)	{imagenest2[u] = ImageIO.read(new File("C:/Nueva carpeta/tortugavoladora"+(u+1)+".png")); smoke[u]= ImageIO.read(new File("C:/Nueva carpeta/humo"+(u+1)+".png"));}
			for(int u = 0; u < 8; u++){
							imagenesm[u] = ImageIO.read(new File("C:/Nueva carpeta/mario"+ (u+1) + ".png"));
							imagenesmf[u] = ImageIO.read(new File("C:/Nueva carpeta/mariof"+ (u+1) + ".png"));
						  }
			for(int u = 0; u < 8; u++)	imagenest[u] = ImageIO.read(new File("C:/Nueva carpeta/tortuga"+ (u+1) + ".png"));
			for(int u =0; u < 4; u++)	turtlesm[u] = ImageIO.read(new File("C:/Nueva carpeta/caparazon"+(u+1)+".png"));			
			for(int u = 0; u < 8; u++) imagenesm_s[u] = ImageIO.read(new File("C:/Nueva carpeta/minimario"+(u+1)+".png"));

			for(int u = 0; u < 10; u++) imagenesb[u] = ImageIO.read(new File("C:/Nueva carpeta/bloque"+(u+1)+".png"));
		
			for(int u =0; u < 3; u++) {imagenesmm[u] = ImageIO.read(new File("C:/Nueva carpeta/mariomur"+(u+1)+".png"));}
			
			for(int u = 0; u <5; u++) { imagenes[u] = ImageIO.read(new File("C:/Nueva carpeta/goomba"+ (u+1) + ".png")); }
			/*
			imagenes[1] = ImageIO.read(new File("C:/nuevacarpeta/2.png"));
			imagenes[2] = ImageIO.read(new File("C:/nuevacarpeta/3.png"));
			imagenes[3] = ImageIO.read(new File("C:/nuevacarpeta/4.png"));
			imagenes[4] = ImageIO.read(new File("C:/nuevacarpeta/5.png"));
			imagenes[5] = ImageIO.read(new File("C:/nuevacarpeta/6.png"));
			imagenes[6] = ImageIO.read(new File("C:/nuevacarpeta/7.png"));*/

		}
		catch(IOException e)
		{
			e.printStackTrace();
		}

		final Animacion fondo = new Animacion(new ArrayList(),0);
		fondo.add(background, 100);

		final Animacion fireflower = new Animacion(new ArrayList(),0);
		final Animacion plant = new Animacion(new ArrayList(),0);
		final Animacion pasto = new Animacion(new ArrayList(),0);
		pasto.add(grass, 100);
		
		final Animacion humo2 = new Animacion(new ArrayList(),0);

		final Animacion alientoDeFuego = new Animacion(new ArrayList(),0);
		final Animacion bows = new Animacion(new ArrayList(), 0);
		final Animacion bola1 = new Animacion(new ArrayList(), 0);


		final Animacion coini = new Animacion(new ArrayList(), 0);
		final Animacion hong = new Animacion(new ArrayList(), 0);		
		final Animacion esco = new Animacion(new ArrayList(), 0);

		final Animacion blmarron = new Animacion(new ArrayList(), 0);
		final Animacion blblue = new Animacion(new ArrayList(), 0);
		final Animacion blint = new Animacion(new ArrayList(), 0);

		final Animacion antd = new Animacion(new ArrayList(), 0);
		final Animacion anti = new Animacion(new ArrayList(), 0);
		final Animacion antd2 = new Animacion(new ArrayList(), 0);
		final Animacion anti2 = new Animacion(new ArrayList(), 0);

		final Animacion antmur = new Animacion(new ArrayList(), 0);
		final Animacion antmur2 = new Animacion(new ArrayList(), 0);
		final Animacion anmur = new Animacion(new ArrayList(), 0);
		final Animacion anmur2 = new Animacion(new ArrayList(), 0);
	
		
		final Animacion anm_big = new Animacion(new ArrayList(), 0);
		final Animacion anmi_big = new Animacion(new ArrayList(), 0);
		final Animacion anmm_big = new Animacion(new ArrayList(), 0);
		final Animacion anm_small = new Animacion(new ArrayList(),0);
		final Animacion anmi_small = new Animacion(new ArrayList(), 0);
		final Animacion anmm_small = new Animacion(new ArrayList(), 0);
		final Animacion anm_fire = new Animacion(new ArrayList(),0);
		final Animacion anmi_fire = new Animacion(new ArrayList(),0);
		final Animacion anmm_fire = new Animacion(new ArrayList(), 0);

		final Animacion an = new Animacion(new ArrayList(),0);
		final Animacion an2 = new Animacion(new ArrayList(),0);

		Block.bloque[1] = blmarron;
		Block.bloque[0] = blint;
		Block.bloque[2] = blblue;
		Block.bloque[3] = new Animacion(new ArrayList(),0);	

		Plant.plant = plant;
		Coin.coininside = coini;
		Escombro.escombro = esco;

		fireflower.add(ff_img, 100);

		Mushroom.fireFlower = fireflower;

		Goomba.animacionD = an2;
		Goomba.animacionI = an;
		Goomba.animacionM = anmur;
		Goomba.animacionM2 = anmur2;

		Mario.animacionD_big = anm_big;
		Mario.animacionI_big = anmi_big;
		Mario.animacionM_big = anmm_big;
		Mario.animacionD_small = anm_small;
		Mario.animacionI_small = anmi_small;
		Mario.animacionM_small = anmm_small;
		Mario.animacionM_fire = anmm_fire;
		Mario.animacionD_fire = anm_fire;
		Mario.animacionI_fire = anmi_fire;
		Mario.humo = humo2;

		Turtle.animacionD = anti;
		Turtle.animacionI = antd;
		Turtle.animacionM = antmur;
		Turtle.animacionM2 = antmur2;
		Turtle.animacionD2 = antd2;
		Turtle.animacionI2 = anti2;		


		Bowser.bowser = bows;		
		Bowser.alientoDeFuego = alientoDeFuego;

		Sprite.hongo = hong;		

		alientoDeFuego.add(aliento, 100);
		bows.addFlipped(bowser, 100);
		esco.add(esc, 1000);
		hong.add(h,10000);
		coini.add(cinside[0],400);
		coini.add(cinside[1],1000);
		plant.add(planta[0], 500);
		plant.add(planta[1], 500);

		anmur.add(goombasm, 100);
		anmur2.add(goombainv, 100); 

		antmur2.add(turtleinv,100);
		
		Block.bloque[3].add(imagenesb[9], 100);

		for(int i = 0; i < 4; i++){ blmarron.add(imagenesb[i], 100); blint.add(imagenesb[i+4], 100);}
		blblue.add(imagenesb[8], 100);

		for(int i = 0; i < 8; i++){ bola1.add(bolita[i], 100);}

		for(Image im : smoke){ humo2.add(im,100);}
		for(Image im : imagenesmm){ anmm_small.add(im,150);}
		for(Image im : imagenest2){antd2.add(im, 150); anti2.addFlipped(im,150);}
		for(Image im : turtlesm) { antmur.add(im,100); }
		for(Image im : imagenest) { anti.add(im, 150); antd.addFlipped(im, 150);}				
		for(Image im : imagenesm) { anm_big.add(im, 100); anmi_big.addFlipped(im, 100);}	
		for(Image im : imagenesm_s){ anm_small.add(im,100); anmi_small.addFlipped(im, 100);}
		for(Image im : imagenes){ an.add(im, 200); an2.addFlipped(im,200); }
		for(Image im : imagenesmf) { anm_fire.add(im, 100); anmi_fire.addFlipped(im, 100);}

		an.add(imagenes[3], 200);
		an.add(imagenes[2], 200);
		an.add(imagenes[1], 200);
		an2.addFlipped(imagenes[3], 200);
		an2.addFlipped(imagenes[2], 200);
		an2.addFlipped(imagenes[1], 200);

//		final Block bloque = new Block(110, 70, 1, 0);
//		final Block bloque2 = new Block(128, 70, 2, 0);



		final Goomba goomba2 = new Goomba( 255, -1, -4, 0);
		final Turtle goomba = new Turtle( 400, 2, 4, 0, true);
		final Mario mario = new Mario( 40, 30, 0, 0);
		final Turtle turtle = new Turtle(300, 55, 4, 0, false);
		//final Plant plantaa = new Plant(120, 55, 50);		
		//final Bowser bowser1 = new Bowser(180, 55);


		JFrame marco = new JFrame();
		marco.setVisible(true);
		marco.setSize(400,400);
		marco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final Map mapa = new Map();

		mapa.sprites.add(new Sprite(pasto, 80, 170));

		//mapa.sprites.add(bowser1);
		mapa.sprites.add(goomba2);
		mapa.sprites.add(goomba);
		mapa.sprites.add(turtle);
		//mapa.sprites.add(bloque);
		mapa.sprites.add(mario);
		//mapa.sprites.add(bloque2);

//		mapa.sprites.add(new Block(110, 70, 0, 0));
		mapa.sprites.add(new Block(128, 140, 1, 1));
//		mapa.sprites.add(new Block(146, 70, 1, 0));
//		mapa.sprites.add(new Block(164, 70, 2, 2));
//		mapa.sprites.add(new Block(182, 70, 2, 2));

			mapa.sprites.add(new Goomba( 400, -1, -4, 0));

		//mapa.sprites.add(plantaa);
		
/*		for(int pj =0; pj < 30; pj++)
		{	
			mapa.sprites.add(new Block(100+16*pj, 160, 1, 0));
		}*/

		mapa.cargarImagenesdeTiles();
		try
		{
			mapa.loadMap("map");
		}catch(Exception e){e.printStackTrace();}

		Sprite.currMap = mapa;

		JPanel lamina = new JPanel(){
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);

				g.drawImage(fondo.getFrame(), 0, -230, null);
			
				if(mario.getx()>200)
				{
				
					for(Sprite img : mapa.sprites)
					{
						g.drawImage(img.getImage(), (int)(img.getx()-mario.getx()+200), (int)img.gety(),null);
					}
					for(int x = 0; x < mapa.tiles.length; x++)
					{
						for(int y = 0; y < mapa.tiles[0].length; y++)
						{
							g.drawImage(mapa.tiles[x][y], (int)(x*30-mario.getx()+200), y*30, null);
						}
					}
				}
				else
				{
					for(Sprite img : mapa.sprites)
					{
						g.drawImage(img.getImage(), (int)img.getx(), (int)img.gety(),null);
					}
					for(int x = 0; x < mapa.tiles.length; x++)
					{
						for(int y = 0; y < mapa.tiles[0].length; y++)
						{
							g.drawImage(mapa.tiles[x][y],x*30,y*30,null);
	
						}
					}
				}

				//g.drawImage(mario.getImage(), (int)mario.getx(), (int)mario.gety(), null);
//				
				//mapa = new Map();
/*				mapa.cargarImagenesdeTiles();
				try
				{
					mapa.loadMap("map");
				}catch(Exception e){e.printStackTrace();}
*/
				//System.out.println("hola");

			}
		};

		marco.add(lamina);
		lamina.setFocusable(true);
		lamina.addKeyListener(mario);
		long time = System.currentTimeMillis();
		long elapsedTime = 0;
		//try{ Thread.sleep(1000);}catch(InterruptedException e){}
		while(true)
		{
			int p = mapa.sprites.size();

			for (int k = 0; k < p; k++) 
			{ 
//				if(mapa.sprites.get(k) instanceof Criatura)
//				{
					if ((/*(Criatura)*/(mapa.sprites.get(k))).estado == 3) {mapa.sprites.remove(k); p--; k--; }
//				}
			}
		
			
			try{ Thread.sleep(15);}catch(InterruptedException e){}
			time = time + elapsedTime;
			elapsedTime = System.currentTimeMillis() - time;

			//System.out.println(elapsedTime);
			
			/* goomba.collide(mapa, (int)elapsedTime);
			 goomba2.collide(mapa, (int)elapsedTime);
			 turtle.collide(mapa, (int)elapsedTime);*/
			/*goomba.setdy(goomba.getdy()+(float)(elapsedTime*0.15));
			goomba2.setdy(goomba2.getdy()+(float)(elapsedTime*0.15));
			turtle.setdy(turtle.getdy()+(float)(elapsedTime*0.15));*/

			//mario.collide(mapa, (int)elapsedTime);

			for(Sprite spr2 : Sprite.pending){mapa.sprites.add(spr2);}
			Sprite.pending.clear();

			for(Sprite spr : mapa.sprites){ if(spr instanceof Criatura){((Criatura)spr).collide(mapa,(int)elapsedTime);}}
 			for(Sprite spr : mapa.sprites){spr.update(elapsedTime);}


			lamina.repaint();
		}
	}
}