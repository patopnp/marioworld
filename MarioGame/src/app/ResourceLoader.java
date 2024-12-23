package app;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.net.*;
import javax.swing.*;
import javax.imageio.ImageIO;


class ResourceLoader 
{
	
	Map currMap;
	
	Image imagenes[] = new Image[5];
	Image imagenesm[] = new Image[8];
	Image imagenesm_bsliding[] = new Image[1];
	Image imagenesm_s[] = new Image[8];
	Image imagenesm_sg[] = new Image[8];
	Image imagenest[] = new Image[8];
	Image imagenest2[] = new Image[6];
	Image imagenesm_sjumping[] = new Image[1];
	Image imagenesm_ssliding[] = new Image[1];
	Image imagenesm_bjumping[] = new Image[1];
	Image imagenesm_fsliding;
	Image imagenesm_fjumping;
	Image goombasm = null;
	Image turtlesm[] = new Image[4];
	Image goombainv = null;
	Image turtleinv = null;
	Image imagenesb[] = new Image[13];
	Image h = null;
	Image esc = null;
	Image[] cinside = new Image[4];
	Image[] planta = new Image[2];
	Image[] bolita = new Image[8];
	Image[] imagenesmf = new Image[8];
	Image[] bowser = new Image[2];
	Image aliento = null;
	Image imagenesmmm = null;
	Image imagenesmm[] = new Image[3];
	Image ssprinkle[] = new Image[6];
	Image smoke[] = new Image[6];
	static Image grass = null;
	Image ff_img = null;
	static Image flower1 = null;
	static Image flower2 = null;
	Image flag = null;
	Image flagPole = null;
	Image plataforma = null;
	Image plataformas[] = new Image[3];
	static Image pipe = null;
	static Image tronco = null;
	Image cloud = null;
	static Image castle = null;
	static Image grass2 = null;
	static Image[] hud = new Image[2];
	Image imagenesm_n[] = new Image[8];
	Image star = null;
	Image bolsa[] = new Image[9];
	Image axeimgs[] = new Image[4];
	
	static SoundEffect flagPoleSliding;
	static SoundEffect powerUpAppearing;
	static SoundEffect breakBlock;
	static SoundEffect jump;
	static SoundEffect pickUpCoin;
	static SoundEffect grabbingPowerUp;
	static SoundEffect steppedOnTurtle;
	static SoundEffect kickTurtle;
	static SoundEffect bumpBlock;
	static SoundEffect fireball;
	static SoundEffect bowserFireball;
	static SoundEffect bowserDie;
	static SoundEffect bridge;
	static SoundEffect marioGettingDamaged;
	static SoundEffect marioFalling;
	static SoundEffect gameOverForMario;

	static SoundEffect stageClear;
	static SoundEffect worldClear;
	
	static BackgroundMusic starPowerUpMusic;
	static BackgroundMusic levelOneMusic;
	static BackgroundMusic levelOneUndergroundMusic;
	static BackgroundMusic levelTwoMusic;
	static BackgroundMusic levelThreeMusic;
	static BackgroundMusic bowserCastleMusic;
	
	
	public void loadSoundEffects() {
		breakBlock = new SoundEffect("smb_breakblock.wav");
		jump = new SoundEffect("jump.wav");
		powerUpAppearing = new SoundEffect("item.wav");
		pickUpCoin = new SoundEffect("pickUpCoin.wav");
		grabbingPowerUp = new SoundEffect("grabbingPowerUp.wav");
		steppedOnTurtle = new SoundEffect("steppedOnTurtle.wav");
		flagPoleSliding = new SoundEffect("Flagpole.wav");
		kickTurtle = new SoundEffect("Kick.wav");
		bumpBlock = new SoundEffect("Bump.wav");
		marioGettingDamaged = new SoundEffect("Warp.wav");
		fireball = new SoundEffect("Fire Ball.wav");
		bowserFireball = new SoundEffect("Enemy Fire.wav");
		bridge = new SoundEffect("smb_breakblock.wav");
		bowserDie = new SoundEffect("Bowser Die.wav");
		marioFalling = new SoundEffect("Falling.wav");
		gameOverForMario = new SoundEffect("Game Over.wav");
		starPowerUpMusic = new BackgroundMusic("starpowerupmusic.wav");
		levelOneMusic = new BackgroundMusic("level1.wav");
		levelOneUndergroundMusic = new BackgroundMusic("level2.wav");
		levelTwoMusic = new BackgroundMusic("level2.wav");
		levelThreeMusic = new BackgroundMusic("level1.wav");
		bowserCastleMusic = new BackgroundMusic("level3.wav");
		stageClear = new SoundEffect("smb_stage_clear.wav");
		worldClear = new SoundEffect("smb_world_clear.wav");
	}
	
	public void loadImages() throws IOException
	{

			URL url;

			//url = (ResourceLoader.class).getResource("star.png");
			//String imagePath = "star.png";
			star = ImageIO.read(new File("starpowerup.png"));
			//star = (new ImageIcon(url)).getImage();

			url = (ResourceLoader.class).getResource("liveshud.png");
			hud[0] = ImageIO.read(new File("liveshud.png"));

			url = (ResourceLoader.class).getResource("coinshud.png");
			hud[1] = ImageIO.read(new File("coinshud.png"));

			url = (ResourceLoader.class).getResource("flower2.png");
			flower2 = ImageIO.read(new File("flower2.png"));

			url = (ResourceLoader.class).getResource("grass2.png");
			grass2 = ImageIO.read(new File("grass2.png"));

			url = (ResourceLoader.class).getResource("castle.png");
			castle = ImageIO.read(new File("castle.png"));

			url = (ResourceLoader.class).getResource("tronco.png");
			tronco = ImageIO.read(new File("tronco.png"));

			url = (ResourceLoader.class).getResource("cloud.png");
			cloud = ImageIO.read(new File("cloud.png"));

			url = (ResourceLoader.class).getResource("pipe1.png");
			pipe = ImageIO.read(new File("pipe1.png"));

			url = (ResourceLoader.class).getResource("plataforma.png");
			plataforma = ImageIO.read(new File("plataforma.png"));

			url = (ResourceLoader.class).getResource("flagPole.png");
			flagPole = ImageIO.read(new File("flagPole.png"));

			url = (ResourceLoader.class).getResource("flag.png");
			flag = ImageIO.read(new File("flag.png"));

			url = (ResourceLoader.class).getResource("goombasm.png");
			goombasm = ImageIO.read(new File("goombasm.png"));
			
			url = (ResourceLoader.class).getResource("goombainv.png");
			goombainv = ImageIO.read(new File("goombainv.png"));

			url = (ResourceLoader.class).getResource("turtleinv.png");
			turtleinv = ImageIO.read(new File("turtleinv.png"));

			url = (ResourceLoader.class).getResource("hongo.png");
			h = ImageIO.read(new File("hongo.png"));

			url = (ResourceLoader.class).getResource("escombro.png");
			esc = ImageIO.read(new File("escombro.png"));


			for(int u = 0; u < 4; u++)
			{
				cinside[u] = ImageIO.read(new File("coin"+(u+1)+".png"));
			}

			url = (ResourceLoader.class).getResource("plant1.png");
			planta[0] = ImageIO.read(new File("plant1.png"));

			url = (ResourceLoader.class).getResource("plant2.png");
			planta[1] = ImageIO.read(new File("plant2.png"));

			url = (ResourceLoader.class).getResource("fireflower.png");
			ff_img = ImageIO.read(new File("fireflower.png"));

			url = (ResourceLoader.class).getResource("flower1.png");
			flower1 = ImageIO.read(new File("flower1.png"));

			url = (ResourceLoader.class).getResource("bowser1.png");
			bowser[0] = ImageIO.read(new File("bowser1.png"));

			url = (ResourceLoader.class).getResource("bowser2.png");
			bowser[1] = ImageIO.read(new File("bowser2.png"));

			url = (ResourceLoader.class).getResource("aliento.png");
			aliento = ImageIO.read(new File("aliento.png"));

			url = (ResourceLoader.class).getResource("grass1.png");
			grass = ImageIO.read(new File("grass1.png"));

			
			for(int u =0; u < 3; u++) 
			{
				url = (ResourceLoader.class).getResource("plat"+(u+1)+".png");
				plataformas[u] = ImageIO.read(new File("plat"+(u+1)+".png"));
			}

			for(int u = 0; u < 8; u++) 
			{
				url = (ResourceLoader.class).getResource("bola"+u+".png");
				bolita[u] = ImageIO.read(new File("bola"+u+".png"));
			}

			


			for(int u = 0; u < 6; u++)
			{
				url = (ResourceLoader.class).getResource("tortugavoladora"+(u+1)+".png");
				imagenest2[u] = ImageIO.read(new File("tortugavoladora"+(u+1)+".png"));
				url = (ResourceLoader.class).getResource("humo"+(u+1)+".png");
				smoke[u] = ImageIO.read(new File("humo"+(u+1)+".png"));
			}

			ssprinkle[0] = ImageIO.read(new File("ssprinkle1.png"));
			ssprinkle[1] = ImageIO.read(new File("ssprinkle2.png"));
			ssprinkle[2] = ImageIO.read(new File("ssprinkle3.png"));
			ssprinkle[3] = ImageIO.read(new File("ssprinkle4.png"));
			ssprinkle[4] = ImageIO.read(new File("ssprinkle3.png"));
			ssprinkle[5] = ImageIO.read(new File("ssprinkle2.png"));
			
			for(int u = 0; u < 8; u++)
			{
				url = (ResourceLoader.class).getResource("mario"+(u+1)+".png");
				imagenesm[u] = ImageIO.read(new File("mario"+(u+1)+".png"));
				url = (ResourceLoader.class).getResource("mariof"+(u+1)+".png");
				imagenesmf[u] = ImageIO.read(new File("mariof"+(u+1)+".png"));
			}
			
			imagenesm_bjumping[0] = ImageIO.read(new File("mariojumping.png"));
			imagenesm_bsliding[0] = ImageIO.read(new File("mariosliding.png"));
			imagenesm_sjumping[0] = ImageIO.read(new File("minimariojumping.png"));
			imagenesm_ssliding[0] = ImageIO.read(new File("minimariosliding.png"));
			
			imagenesm_fsliding = ImageIO.read(new File("mariofsliding.png"));
			imagenesm_fjumping = ImageIO.read(new File("mariofjumping.png"));
			
			for(int u = 0; u < 8; u++)
			{
				url = (ResourceLoader.class).getResource("tortuga"+(u+1)+".png");
				imagenest[u] = ImageIO.read(new File("tortuga"+(u+1)+".png"));
			}
			for(int u =0; u < 4; u++)
			{
				url = (ResourceLoader.class).getResource("caparazon"+(u+1)+".png");
				turtlesm[u] = ImageIO.read(new File("caparazon"+(u+1)+".png"));
			}	
			for(int u = 0; u < 8; u++)
			{
				url = (ResourceLoader.class).getResource("minimario"+(u+1)+".png");
				imagenesm_sg[u] = ImageIO.read(new File("minimario"+(u+1)+"g.png"));
			}
			for(int u =0; u < 4; u++)
			{
				url = (ResourceLoader.class).getResource("axe"+(u+1)+".png");
				axeimgs[u] = ImageIO.read(new File("axe"+(u+1)+".png"));
			}	
			
			for(int u = 0; u < 8; u++)
			{
				url = (ResourceLoader.class).getResource("minimario"+(u+1)+".png");
				imagenesm_s[u] = ImageIO.read(new File("minimario"+(u+1)+".png"));
			}
			
			for(int u = 0; u < 12; u++)
			{
				url = (ResourceLoader.class).getResource("bloque"+(u+1)+".png");
				imagenesb[u] = ImageIO.read(new File("bloque"+(u+1)+".png"));
			}
			
			imagenesb[12] = ImageIO.read(new File("bridge.png"));
			
			imagenesmmm = ImageIO.read(new File("minimariomur.png"));
			
			for(int u =0; u < 3; u++)
			{
				url = (ResourceLoader.class).getResource("mariomur"+(u+1)+".png");
				imagenesmm[u] = ImageIO.read(new File("mariomur"+(u+1)+".png"));
			}
			
			for(int u = 0; u <5; u++) 
			{
				url = (ResourceLoader.class).getResource("goomba"+(u+1)+".png");
				imagenes[u] = ImageIO.read(new File("goomba"+(u+1)+".png"));
			}
			for(int u = 0; u < 8; u++) 
			{
				url = (ResourceLoader.class).getResource("marionaked"+(u+1)+".png");
				imagenesm_n[u] = ImageIO.read(new File("marionaked"+(u+1)+".png"));
			}

			for (int i = 0; i < 9; i++) {
				bolsa[i] = ImageIO.read(new File("bolsa"+(i+1)+".png"));
			}

	}
	public void loadAnims()
	{
	
		Animacion estrella = new Animacion(new ArrayList(), 0);
		estrella.add(star, 100);

		Star.star = estrella;

		Animacion plat1 = new Animacion(new ArrayList(), 0);
		Animacion plat2 = new Animacion(new ArrayList(), 0); 
		Animacion plat3 = new Animacion(new ArrayList(), 0);

		plat1.add(plataformas[0], 100); plat2.add(plataformas[1], 100); plat3.add(plataformas[2], 100);

		Animacion nube = new Animacion(new ArrayList(), 0);
		nube.add(cloud, 100);
		Cloud.cloud = nube;

		Animacion plat = new Animacion(new ArrayList(), 0);
		plat.add(plataforma, 100);

		Animacion flaganim = new Animacion(new ArrayList(), 0);
		flaganim.add(flag,100);
		Animacion flagpanim = new Animacion(new ArrayList(), 0);
		flagpanim.add(flagPole,100);

		Animacion fireflower = new Animacion(new ArrayList(),0);
		Animacion plant = new Animacion(new ArrayList(),0);
		Animacion pasto = new Animacion(new ArrayList(),0);
		pasto.add(grass, 100);
		
		Animacion starSprinkle = new Animacion(new ArrayList(),0);
		Animacion humo2 = new Animacion(new ArrayList(),0);

		Animacion alientoDeFuego = new Animacion(new ArrayList(),0);
		Animacion bows = new Animacion(new ArrayList(), 0);
		Animacion bolaDer = new Animacion(new ArrayList(), 0);
		Animacion bolaIzq = new Animacion(new ArrayList(), 0);
		
		Animacion bag1 = new Animacion(new ArrayList(),0);
		Animacion bag2 = new Animacion(new ArrayList(),0);
		Animacion bag3 = new Animacion(new ArrayList(),0);

		Animacion axe = new Animacion(new ArrayList(), 0);
		
		
		
		bag1.add(bolsa[0], 100);	
		bag3.add(bolsa[8], 100);
		for(int i = 1; i < 8; i++){ bag2.add(bolsa[i], 100);}

		Bag.bolsa1 = bag1; Bag.bolsa2 = bag2; Bag.bolsa3 = bag3;

		Animacion coini = new Animacion(new ArrayList(), 0);
		Animacion hong = new Animacion(new ArrayList(), 0);		
		Animacion esco = new Animacion(new ArrayList(), 0);

		Animacion blmarron = new Animacion(new ArrayList(), 0);
		Animacion blblue = new Animacion(new ArrayList(), 0);
		Animacion blint = new Animacion(new ArrayList(), 0);

		Animacion antd = new Animacion(new ArrayList(), 0);
		Animacion anti = new Animacion(new ArrayList(), 0);
		Animacion antd2 = new Animacion(new ArrayList(), 0);
		Animacion anti2 = new Animacion(new ArrayList(), 0);

		Animacion antmur = new Animacion(new ArrayList(), 0);
		Animacion antmur2 = new Animacion(new ArrayList(), 0);
		Animacion anmur = new Animacion(new ArrayList(), 0);
		Animacion anmur2 = new Animacion(new ArrayList(), 0);
	
		Animacion anm_naked = new Animacion(new ArrayList(), 0);
		Animacion anmi_naked = new Animacion(new ArrayList(), 0);
		
		Animacion anm_fire_saltando = new Animacion(new ArrayList(), 0);
		Animacion anmi_fire_saltando = new Animacion(new ArrayList(), 0);
		Animacion anm_small_saltando = new Animacion(new ArrayList(), 0);
		Animacion anmi_small_saltando = new Animacion(new ArrayList(), 0);
		Animacion anm_big_saltando = new Animacion(new ArrayList(), 0);
		Animacion anmi_big_saltando = new Animacion(new ArrayList(), 0);
		Animacion anm_big = new Animacion(new ArrayList(), 0);
		Animacion anmi_big = new Animacion(new ArrayList(), 0);
		Animacion anmm_big = new Animacion(new ArrayList(), 0);
		Animacion anm_fsliding = new Animacion(new ArrayList(), 0);
		Animacion anmi_fsliding = new Animacion(new ArrayList(), 0);		
		Animacion anm_ssliding = new Animacion(new ArrayList(), 0);
		Animacion anmi_ssliding = new Animacion(new ArrayList(), 0);		
		Animacion anm_bsliding = new Animacion(new ArrayList(), 0);
		Animacion anmi_bsliding = new Animacion(new ArrayList(), 0);
		Animacion anm_small = new Animacion(new ArrayList(),0);
		Animacion anmi_small = new Animacion(new ArrayList(), 0);
		Animacion anm_smallg = new Animacion(new ArrayList(),0);
		Animacion anmi_smallg = new Animacion(new ArrayList(), 0);
		Animacion anmm_small = new Animacion(new ArrayList(), 0);
		Animacion anm_fire = new Animacion(new ArrayList(),0);
		Animacion anmi_fire = new Animacion(new ArrayList(),0);
		Animacion anmm_fire = new Animacion(new ArrayList(), 0);

		Animacion an = new Animacion(new ArrayList(),0);
		Animacion an2 = new Animacion(new ArrayList(),0);

		Block.bloque[1] = blmarron;
		Block.bloque[0] = blint;
		Block.bloque[2] = blblue;
		Block.bloque[3] = new Animacion(new ArrayList(),0);
		Block.bloque[4] = new Animacion(new ArrayList(),0);
		Block.bloque[5] = new Animacion(new ArrayList(),0);
		Block.bloque[6] = new Animacion(new ArrayList(),0);

		
		Plataforma.plat = plat;
		Plant.plant = plant;
		Coin.coininside = coini;
		Coin.coin = coini;
		Escombro.escombro = esco;

		fireflower.add(ff_img, 100);

		Mushroom.fireFlower = fireflower;
		FlagPole.flag = flaganim;
		FlagPole.flagPole = flagpanim;

		Goomba.animacionD = an2;
		Goomba.animacionI = an;
		Goomba.animacionM = anmur;
		Goomba.animacionM2 = anmur2;

		Mario.animacionD_small_saltando = anm_small_saltando;
		Mario.animacionI_small_saltando = anmi_small_saltando;
		
		Mario.animacionD_fire_saltando = anm_fire_saltando;
		Mario.animacionI_fire_saltando = anmi_fire_saltando;
		
		Mario.animacionD_big_saltando = anm_big_saltando;
		Mario.animacionI_big_saltando = anmi_big_saltando;
		
		Mario.animacionD_naked = anm_naked;
		Mario.animacionI_naked = anmi_naked;
		Mario.animacionM_naked = anmm_big;

		Mario.animacionD_big = anm_big;
		Mario.animacionI_big = anmi_big;
		Mario.animacionM_big = anmm_big;
		Mario.animacionD_fire_sliding = anm_fsliding;
		Mario.animacionI_fire_sliding = anmi_fsliding;		
		Mario.animacionD_big_sliding = anm_bsliding;
		Mario.animacionI_big_sliding = anmi_bsliding;
		Mario.animacionD_small_sliding = anm_ssliding;
		Mario.animacionI_small_sliding = anmi_ssliding;		
		Mario.animacionI_small = anmi_small;
		Mario.animacionD_small = anm_small;
		Mario.animacionI_small = anmi_small;
		Mario.animacionD_smallg = anm_smallg;
		Mario.animacionI_smallg = anmi_smallg;
		Mario.animacionM_small = anmm_small;
		Mario.animacionM_fire = anmm_fire;
		Mario.animacionD_fire = anm_fire;
		Mario.animacionI_fire = anmi_fire;
		Mario.humo = humo2;
		Mario.starSprinkle = starSprinkle;
		
		Turtle.animacionD = anti;
		Turtle.animacionI = antd;
		Turtle.animacionM = antmur;
		Turtle.animacionM2 = antmur2;
		Turtle.animacionD2 = antd2;
		Turtle.animacionI2 = anti2;	

		Plataforma.plat1 = plat1;
		Plataforma.plat2 = plat2;
		Plataforma.plat3 = plat3;

		BolaDeFuego.bolaDeFuegoHaciaDer = bolaDer;
		BolaDeFuego.bolaDeFuegoHaciaIzq = bolaIzq;
		
		Bowser.bowser = bows;		
		Bowser.alientoDeFuego = alientoDeFuego;

		Axe.axe = axe;
		
		Sprite.hongo = hong;		

		alientoDeFuego.add(aliento, 100);
		bows.addFlipped(bowser[0], 3500);
		bows.addFlipped(bowser[1], 500);
		esco.add(esc, 1000);
		hong.add(h,10000);
		for(Image img : cinside){ coini.add(img,200); }
		plant.add(planta[0], 500);
		plant.add(planta[1], 500);

		anmur.add(goombasm, 100);
		anmur2.add(goombainv, 100); 

		antmur2.add(turtleinv,100);
		
		Block.bloque[6].add(imagenesb[12], 100);
		Block.bloque[3].add(imagenesb[9], 100);
		Block.bloque[4].add(imagenesb[10], 100);
		Block.bloque[5].add(imagenesb[11], 100);
		
		for(int i = 0; i < 4; i++){ blmarron.add(imagenesb[i], 100); blint.add(imagenesb[i+4], 200);}
		blmarron.add(imagenesb[0], 2000);

		blblue.add(imagenesb[8], 100);
		for(int i = 0; i < 8; i++){ bolaDer.addFlipped(bolita[i], 20); bolaIzq.add(bolita[i], 20);}
		
		for(Image a : axeimgs) { axe.add(a, 200); }
		for(Image im : imagenesm_n){ anm_naked.add(im,100); anmi_naked.addFlipped(im,100); }
		for(Image im : ssprinkle){ starSprinkle.add(im,100);}
		for(Image im : smoke){ humo2.add(im,100);}
		for(Image im : imagenesmm){ anmm_big.add(im,150);}
		for(Image im : imagenest2){antd2.add(im, 150); anti2.addFlipped(im,150);}
		for(Image im : turtlesm) { antmur.add(im,100); }
		for(Image im : imagenest) { anti.add(im, 150); antd.addFlipped(im, 150);}				
		for(Image im : imagenesm) { anm_big.add(im, 100); anmi_big.addFlipped(im, 100);}	
		for(Image im : imagenesm_s){ anm_small.add(im,100); anmi_small.addFlipped(im, 100);}
		for(Image im : imagenesm_sg){ anm_smallg.add(im,100); anmi_smallg.addFlipped(im, 100);}
		for(Image im : imagenes){ an.add(im, 200); an2.addFlipped(im,200); }
		for(Image im : imagenesmf) { anm_fire.add(im, 100); anmi_fire.addFlipped(im, 100);}

		anm_fsliding.add(imagenesm_fsliding, 100);
		anmi_fsliding.addFlipped(imagenesm_fsliding, 100);
		
		anm_bsliding.add(imagenesm_bsliding[0], 100);
		anmi_bsliding.addFlipped(imagenesm_bsliding[0], 100);
		
		anm_ssliding.add(imagenesm_ssliding[0], 100);
		anmi_ssliding.addFlipped(imagenesm_ssliding[0], 100);
		
		anm_big_saltando.add(imagenesm_bjumping[0], 10);
		anmi_big_saltando.addFlipped(imagenesm_bjumping[0], 10);
		
		anm_fire_saltando.add(imagenesm_fjumping, 10);
		anmi_fire_saltando.addFlipped(imagenesm_fjumping, 10);
		
		anm_small_saltando.add(imagenesm_sjumping[0], 10);
		anmi_small_saltando.addFlipped(imagenesm_sjumping[0], 10);
		
		anmm_small.add(imagenesmmm,150);
		
		an.add(imagenes[3], 200);
		an.add(imagenes[2], 200);
		an.add(imagenes[1], 200);
		an2.addFlipped(imagenes[3], 200);
		an2.addFlipped(imagenes[2], 200);
		an2.addFlipped(imagenes[1], 200);
		
	}
}
