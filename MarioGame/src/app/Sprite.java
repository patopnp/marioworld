package app;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class Sprite 
{
	int estado = Criatura.VIVO;
	public static Map mapa;
	static ArrayList<Sprite> pending = new ArrayList<Sprite>();
	public static Map currMap;
	public static Animacion hongo;

	private float x;
	private float y;
	protected Animacion animacion;

	public void update(long elapsedTime)
	{
		animacion.update(elapsedTime);
	}

	public float getx(){return this.x;}
	public float gety(){return this.y;}
	public void setx(float x){this.x = x;}
	public void sety(float y){this.y = y;}
	
	public Sprite(Animacion animacion, int x, int y)
	{
		this.animacion = animacion;
		this.x = x; this.y = y;
	}

	public Sprite(Image img, int x, int y)
	{
		this.animacion = new Animacion(new ArrayList(), 100);
		animacion.add(img,0);
		this.x = x; this.y = y;
	}
	
	public Sprite(int x, int y)
	{
		this.x = x; this.y = y;
	}
	
	public Image getImage()
	{
		return animacion.getFrame();
	}

}
abstract class Criatura extends Sprite
{
	protected Animacion animDer;
	protected Animacion animIzq;
	
	protected float maxx;
	protected float minx;
	
	final static int VIVO = 1;
	final static int MURIENDO = 2;
	final static int MUERTO = 3;
	final static int CAYENDOSE = 4;
	
	private int xcache;
	private int ycache;

	boolean onBlock;
	protected int floory;
	private float dx;
	private float dy;
	public float getdx(){return this.dx;}	public void setdx(float dx){this.dx = dx;}
	public float getdy(){return this.dy;}	public void setdy(float dy){this.dy = dy;}
	protected boolean onGround;
	
	public Criatura( int x, int y, int dx, int dy)
	{
		super( x, y);
		this.dx = dx;
		this.dy = dy;
	}
	
	public void update(long elapsedTime)
	{
		if(estado == VIVO || estado == Turtle.VOLANDO)
		{
			if(dx > 0) animacion = animDer; 
			else if (dx < 0) animacion = animIzq; 
			else{ 
				
				if (estado != Turtle.VOLANDO && !(this instanceof Mario && (((Mario)this).getCond() == Mario.GETTING_BIGGER))){
					animacion.start();
				}
			}
		}
		if(getx() < 5)
		{ 
			if(this instanceof Mario){ setx(5); setdx(0);}
			else{ estado = MUERTO; }
		}
		if(gety() > 255)
		{ 
			if (this instanceof Mario && estado == VIVO) {
				BackgroundMusic.stop();
				ResourceLoader.marioFalling.playSingle();
			}
			
			estado = MUERTO;
			
		}		


		super.update(elapsedTime);
		setx(getx() +dx * elapsedTime/100);
		sety(gety() + dy * elapsedTime/100);
		
		
	}
	

	
	public void collide(Map mapa, int elapsedTime)
	{
		Sprite.mapa = mapa;
		if(estado != CAYENDOSE && estado != MUERTO)
		{

			float limiteh = (getx() + getImage().getWidth(null));
			float limitev = (gety() + getImage().getHeight(null));

			//check tiles for collision
			for(int u = 0; u < mapa.tiles.length; u++)
			{
				for(int i = 0; i < mapa.tiles[0].length; i++)
				{
					if(mapa.tiles[u][i] != null)
					{
						boolean one = ((gety()+getdy()*elapsedTime*0.01) <= (i+1)*30);
						boolean two = ((limitev+getdy()*elapsedTime*0.01) >= (i)*30);
						boolean three = (limiteh >= (u)*30);
						boolean four = (getx() <=(u+1)*30);
						
						if( one  && two && three && four ) { int[] mat = {u,i}; tileCollisionV( mat); }
						if( ( (limiteh+getdx()*elapsedTime*0.01)> (u)*30) && ((getx()+getdx()*elapsedTime*0.01)<(u+1)*30) && (limitev > (i)*30) && (gety()<(i+1)*30)  ) { int[] mat = {u,i}; tileCollisionH(mat); /*setdx(-getdx());*/}

						
					}
				}
			}

			int[] f = { (int)(getx()/30), (int)((getx()+getImage().getWidth(null))/30) };

			if(getdy() != 0){ onGround = false; onBlock = false;}

			else if(onGround){ if( (mapa.tiles[f[1]][floory] == null) && (mapa.tiles[f[0]][floory] == null)){ onGround = false;}}

			for(Sprite s : mapa.sprites)
			{
				if(s != this)
				{
					boolean one = ( (gety() +getdy()*elapsedTime*0.01) < (s.gety()+s.getImage().getHeight(null)) );
					boolean two = ( (limitev +getdy()*elapsedTime*0.01) > s.gety() );
					boolean three = ( (limiteh+getdx()*elapsedTime*0.01) > s.getx() );
					boolean four = ( (getx()+getdx()*elapsedTime*0.01) < (s.getx()+s.getImage().getWidth(null)));

					if( one  && two && three && four ) { spriteCollision(s); }
				} 
			}
		}
	}

	public abstract void spriteCollision(Sprite spr);
	public abstract void tileCollisionV(int[] b);
	public abstract void tileCollisionH(int[] b);
	public void changeStatus(){}
	
}
class Goomba extends Criatura
{
	protected Animacion animMur;
	protected Animacion animMur2;
	
	private Sprite currBlock;

	public static Animacion animacionD;
	public static Animacion animacionI;
	public static Animacion animacionM;
	public static Animacion animacionM2;
	
	int dyingTime =0;
	public Goomba(int x, int y, int dx, int dy)
	{
		super(x, y, dx, dy);
		animDer = animacionD.clone();
		animIzq = animacionI.clone();
		animMur = animacionM.clone();
		animMur2 = animacionM2.clone();
		animacion = animDer;
	}
	
	//collision with a tile detected
	public void tileCollisionV(int[] a)
	{
		if(getdy() >= 0) {floory =a[1]; sety(a[1]*30-getImage().getHeight(null)); onGround = true; }else{ sety((a[1]+1)*30); } 
		setdy(0);

	}
	public void tileCollisionH(int[] a)
	{ 
		if(getx()<(a[0]*30))
		{
			setdx(-Math.abs(getdx()));
		}
		else
		{
			setdx(Math.abs(getdx()));
		}
	}
	public void changeStatus()
	{
		if (estado == MURIENDO){ setdx(0); animacion = animMur;}
 		if (estado == CAYENDOSE){onGround = false; onBlock = false; animacion = animMur2;}
	}

	public void update(long elapsedTime)
	{
		if (onGround == false && onBlock == false) setdy(getdy()+(float)(elapsedTime*0.15));
		if ( (getx()+getImage().getWidth(null)+2) < minx || getx()>maxx ){ onBlock = false;} 
		if (estado == MURIENDO) 
		{

			dyingTime += elapsedTime;
			if (dyingTime >= 600)
			{
				estado = MUERTO;
			}
		}
		if(currBlock != null)
		{
			int ty = 0;
			maxx = currBlock.getx()+(currBlock.getImage()).getWidth(null);
			minx = currBlock.getx();

			while(ty < mapa.sprites.size())
			{
				Sprite s = mapa.sprites.get(ty);

				boolean one = ((s.getx()+(s.getImage()).getWidth(null)+getImage().getWidth(null)+1) > minx);
				boolean two = ((s.getx()) < (maxx+1+getImage().getWidth(null)));

				if ( s.gety() == currBlock.gety() && one && two && s instanceof Block)
				{  
					if (s.getx() < minx){ minx = s.getx(); ty = 0;}
					if ( (s.getx() + s.getImage().getWidth(null)) >maxx) { maxx = (s.getx()+ s.getImage().getWidth(null)); ty = 0;} 
				}
				ty++;
			}
		}
		super.update(elapsedTime);
		
	}
	public void spriteCollision( Sprite spr)
	{
 		if(estado != CAYENDOSE && spr.estado != CAYENDOSE)
		{
			if(!(spr instanceof BolaDeFuego) && !(spr instanceof Mario) && !(spr instanceof Escombro) && !(spr instanceof Plataforma) && !(spr instanceof Coin) )
			{		
				boolean b = true;
				if(spr instanceof Turtle)
				{
					if(((Turtle)spr).estado == Turtle.APLASTADO_DESPLAZANDOSE) b=false;
				}
				if(spr instanceof Criatura && b ){
				if( spr.getx() > getx() ){ setdx(-Math.abs(getdx())); ((Criatura)spr).setdx(+Math.abs(((Criatura)spr).getdx()));} else { setdx(Math.abs(getdx())); ((Criatura)spr).setdx(-Math.abs(((Criatura)spr).getdx()));}
				}
			}
			if( spr instanceof Block)
			{
				if ((gety()+ getImage().getHeight(null)) < spr.gety())
				{	
					setdy(0);
					sety(spr.gety()-getImage().getHeight(null));
					onBlock = true;
					maxx = spr.getx()+(spr.getImage()).getWidth(null);
					minx = spr.getx();
					currBlock = spr;
	
				}
				else if ( gety() > (spr.gety()+spr.getImage().getHeight(null)) )
				{
					setdy(0);
				}
				else if ( getx() > (spr.getx()/*+spr.getImage().getWidth(null)*/)) { setdx(Math.abs(getdx())); }
				else if	( spr.getx() > (getx()/*+getImage().getWidth(null)*/)) { setdx(-Math.abs(getdx())); }
			}
			if (spr instanceof Plataforma)
			{
				if(getdy() > 0)
				{
					setdy(0);
					sety(spr.gety()-getImage().getHeight(null)-1);
					onBlock = true;
					maxx = spr.getx()+(spr.getImage()).getWidth(null);
					minx = spr.getx();
				}
			}
		}
	}
}

class Turtle extends Criatura
{

	protected Animacion animMur;
	protected Animacion animMur2;
	protected Animacion animDer_flying;
	protected Animacion animIzq_flying;
	protected Animacion animDer_walking;
	protected Animacion animIzq_walking;

	final static int APLASTADO_DESPLAZANDOSE = 5;
	final static int VOLANDO = 6;
	
	public static Animacion animacionD;
	public static Animacion animacionI;
	public static Animacion animacionD2;
	public static Animacion animacionI2;
	public static Animacion animacionM;
	public static Animacion animacionM2;
	
	int dyingTime =0;
	boolean dangerous;
	private int desplazamientoV = 100;
	private int yi;

	public Turtle(int x, int y, int dx, int dy, boolean voladora)
	{
		super(x, y, dx, dy);
		animDer_walking = animacionD.clone();
		animIzq_walking = animacionI.clone();
		if(voladora)
		{ 

			animIzq_flying = animacionI2.clone(); 
			animDer_flying = animacionD2.clone();
			estado = VOLANDO; animDer = animDer_flying; animIzq = animIzq_flying;
			yi = y;
		}
		else
		{
			animDer = animDer_walking; animIzq = animIzq_walking;
		}
		animMur = animacionM.clone();
		animMur2 = animacionM2.clone();
		animacion = animIzq;
	}
	
	//collision with a tile detected

	public void tileCollisionV(int[] a)
	{
		if(getdy() >= 0) {floory =a[1]; sety(a[1]*30-getImage().getHeight(null)); onGround = true; }else{ sety((a[1]+1)*30); } 
		setdy(0);

	}
	public void tileCollisionH(int[] a){ if(getx()<(a[0]*30)){setdx(-Math.abs(getdx()));}else{setdx(Math.abs(getdx()));} dangerous = true;}
	
	public void changeStatus()
	{
		if (estado == MURIENDO){ setdx(0); animacion = animMur; dangerous = false;}
 		if (estado == CAYENDOSE){onGround = false; onBlock = false; animacion = animMur2;}
		if (estado == VIVO){if(animacion == animDer){ animacion= animDer_walking;} else if (animacion == animIzq){animacion = animIzq_walking;} animDer = animDer_walking; animIzq = animIzq_walking; }
		if (estado == APLASTADO_DESPLAZANDOSE){ animacion = animMur;  animacion.start();}
	}

	public void update(long elapsedTime)
	{
		if(estado == APLASTADO_DESPLAZANDOSE){if(getx() > (GameManager.rl.currMap.player.getx() + 200)){estado = MUERTO;}}
		if (onBlock && estado == VIVO)
		{
			if ( (getx()+getImage().getWidth(null) ) < (minx+2) ) {setdx(Math.abs(getdx()));}
			else if ( getx()>(maxx-2) ){ setdx(-Math.abs(getdx())); }
		}
		if(estado == VOLANDO)
		{
			if (gety()< yi) setdy(3);
			else if (gety() > (yi + desplazamientoV)) setdy(-3); 
		}
		else
		{
			//en realidad, en este caso muriendo es aplastado
			if (onGround == false && onBlock == false) setdy(getdy()+(float)(elapsedTime*0.15));
			if ((getx()+getImage().getWidth(null)+2) < minx || getx()>maxx){ onBlock = false;}
			if (estado == MURIENDO) 
			{
				animacion.start(); 

				dyingTime += elapsedTime;
				if (dyingTime >= 2000)
				{
					estado = VIVO;
					setdx(4);
					sety(gety()-19);
					dyingTime = 0;
					changeStatus();
				}
			}
			if (estado == APLASTADO_DESPLAZANDOSE)
			{
				dyingTime = 0;			
			}
		}
		super.update(elapsedTime);
		
	}
	public void spriteCollision( Sprite spr)
	{
		if(!(spr instanceof BolaDeFuego) && !(spr instanceof Escombro) && !(spr instanceof Coin) && !(spr instanceof Plataforma) )
		{
			if(estado == VIVO || estado == VOLANDO)
			{
				boolean b = true;
				if(spr instanceof Turtle)
				{
					if(((Turtle)spr).estado == Turtle.APLASTADO_DESPLAZANDOSE) b=false;
				}
				if(spr instanceof Criatura && b && !(spr instanceof Mario))
				{
					if( spr.getx() > getx() ){ setdx(-Math.abs(getdx())); ((Criatura)spr).setdx(+Math.abs(((Criatura)spr).getdx()));} else { setdx(Math.abs(getdx())); ((Criatura)spr).setdx(-Math.abs(((Criatura)spr).getdx()));}
				}
			}
			else if (estado == APLASTADO_DESPLAZANDOSE)
			{
				if(spr instanceof Goomba)
				{
					if(((Criatura) spr).estado != MURIENDO && ((Criatura) spr).estado != MUERTO && ((Criatura) spr).estado != CAYENDOSE)
					{
						int o;
						if ((getx() + getImage().getWidth(null)*0.5) <= (spr.getx() + spr.getImage().getWidth(null)*0.5)){ o = 1;}
						else {o = -1; }

						ResourceLoader.kickTurtle.play();
						((Criatura) spr).estado = CAYENDOSE; 
						((Goomba) spr).changeStatus();
						((Criatura) spr).setdx(8*o);
						((Criatura) spr).setdy(-36);
					}
				}
				if(spr instanceof Turtle)
				{
					if(((Criatura) spr).estado != MUERTO && ((Criatura) spr).estado != CAYENDOSE )
					{
						int o;
						if ((getx() + getImage().getWidth(null)*0.5) <= (spr.getx() + spr.getImage().getWidth(null)*0.5)){ o = 1;}
						else {o = -1; }

						spr.sety(spr.gety()+spr.getImage().getHeight(null)-16);
						ResourceLoader.kickTurtle.play();
						((Criatura) spr).estado = CAYENDOSE; 
						((Turtle) spr).changeStatus();
						((Criatura) spr).setdx(8*o);
						((Criatura) spr).setdy(-36);
					}
				}				
			}
			if( spr instanceof Block)
			{
				if ((gety()+ getImage().getHeight(null)) < spr.gety())
				{	
					setdy(0);
					sety(spr.gety()-getImage().getHeight(null)-1);
					onBlock = true;
					maxx = spr.getx()+(spr.getImage()).getWidth(null);
					minx = spr.getx();
					int ty = 0;
					while(ty < mapa.sprites.size())
					{
						Sprite s = mapa.sprites.get(ty);

						boolean one = ((s.getx()+(s.getImage()).getWidth(null)+getImage().getWidth(null)+1) > minx);
						boolean two = ((s.getx()) < (maxx+1+getImage().getWidth(null)));
					

						if ( s.gety() == spr.gety() && one && two && s instanceof Block)
						{  
							if (s.getx() < minx){ minx = s.getx(); ty = 0;}
							if ( (s.getx() + s.getImage().getWidth(null)) >maxx) { maxx = (s.getx()+ s.getImage().getWidth(null)); ty = 0;} 
						} 
						ty++;

					}
				}
				else if ( gety() > (spr.gety()+spr.getImage().getHeight(null)) )
				{
					setdy(0);
				}
				else if ( getx() > (spr.getx()/*+spr.getImage().getWidth(null)*/)) { setdx(Math.abs(getdx())); if (estado == APLASTADO_DESPLAZANDOSE) dangerous = true;}
				else if	( spr.getx() > (getx()/*+getImage().getWidth(null)*/)) { setdx(-Math.abs(getdx())); if (estado == APLASTADO_DESPLAZANDOSE) dangerous = true; }
			}

		}
			if (spr instanceof Plataforma)
			{
				
				if(getdy() > 0)
				{
					setdy(0);
					sety(spr.gety()-getImage().getHeight(null)-1);
					onBlock = true;
					maxx = spr.getx()+(spr.getImage()).getWidth(null);
					minx = spr.getx();
				}
			}
	}
}
class Block extends Sprite
{
	//type 0 blint
	//type 1 blmarron
	//type 2 blblue
	//type 3 blpresionado
	//type 4 blbpresionado
	//type 5 peldanos escalera marron
	//type 6 puente
	
	private int contenido;
	private int ncoins = 4;

	public static final int MONEDA = 2;
	public static final int HONGO = 1;	

	protected static Animacion[] bloque = new Animacion[7];
	private int type;
	private int t = 0;
	
	boolean move;
	
	public Block(int x, int y, int type, int cont)
	{
		super(x,y);
		contenido = cont;
		this.type = type;
		animacion = bloque[type].clone();
		estado = 0;
	}
	public void changeStatus()
	{
		
		if (contenido == 1 && type !=3 && type != 4 && type != 5 && type != 6)
		{
			boolean blean = false;
			for(Sprite ss : mapa.sprites)
			{ 
					if(ss instanceof Mario)
					{
						if ( ((Mario)ss).getCond() != Mario.CHICO ) {blean = false;}else{blean = true;}
					}
			}

			ResourceLoader.powerUpAppearing.play();
			new Mushroom((int)getx(), (int)gety()-20, blean); if (type == 2) type = 4; else type = 3; 
		}
		
		if (contenido == 2 && type !=3 && type != 4 && type != 5 && type != 6)
		{
			Mario.score += 200;
			ResourceLoader.pickUpCoin.play();
			((Mario)GameManager.rl.currMap.player).coins++;
			new Coin((int)getx(), (int)gety()-20, true);
			if(t == 0){sety(gety()-5); t = 1;}
			if( type == 0  || ncoins == 0)
			{ 
				if (type == 2) type = 4;
				else type = 3; 
			} 
			ncoins--;
		}
		if (contenido == 0 && (type == 1 || type == 2)) 
		{ 
			if( ((Mario)(GameManager.rl.currMap.player)).getCond() > Mario.CHICO)
			{
				ResourceLoader.breakBlock.play();
				System.out.println("Sound played");
				new Escombro((int)getx()+8, (int)gety()+8, -10, -18); 
				new Escombro((int)getx()+8, (int)gety()+8, -2, -20); 
				new Escombro((int)getx()+8, (int)gety()+8, 8, -18); 
				new Escombro((int)getx()+8, (int)gety()+8, 2, -20); 
				estado = 3;  
			}
			else
			{
				ResourceLoader.bumpBlock.play();
				if(t == 0){sety(gety()-5); t = 1;}
			}
		}

		if (contenido == 3 && type !=3 && type != 4 && type != 5 && type != 6)
		{
			boolean blean = false;
			for(Sprite ss : mapa.sprites)
			{ 
					if(ss instanceof Mario)
					{
						if ( ((Mario)ss).getCond() != Mario.CHICO ) {blean = false;}else{blean = true;}
					}
			}
			ResourceLoader.powerUpAppearing.play();
			new Star((int)getx(), (int)gety()-20, 6); if (type == 2) type = 4; else type = 3; 
		}

		animacion = bloque[type].clone();		
		
		for(Sprite s : mapa.sprites)
		{
			boolean one = ((s.getx()+s.getImage().getWidth(null)) > getx());
			boolean two = (s.getx() < (getx()+getImage().getWidth(null)));
			boolean three = ( (s.gety()+s.getImage().getHeight(null)) > (gety()-8) );
			if ( one && two && (s.gety()< gety()) && three)
			{ 
				if(s instanceof Goomba){s.estado = Criatura.CAYENDOSE; ((Goomba)s).changeStatus(); ((Goomba)s).setdy(-20);}
				if(s instanceof Turtle){s.estado = Criatura.CAYENDOSE; ((Turtle)s).changeStatus(); ((Turtle)s).setdy(-20);}
			}
		}
	}
	public void update(long elapsedTime)
	{
		if(t>=1){ t += elapsedTime;  if(t>200){ t = 0; sety(gety()+5); } }
		super.update(elapsedTime);
		if (move) {
			sety(gety()+elapsedTime*0.01f*30);
		}
	}

}
class Mushroom extends Criatura
{
	private Sprite currBlock;
	public static Animacion fireFlower;

	public Mushroom(int x, int y, boolean b)
	{
		super( x, y, 0, 0);
		if(b){ setdx(8); setdy(-15); animacion = hongo;} else { animacion = fireFlower; sety(gety()-2); setx(getx()-1); }
		estado = 0;
		pending.add(this);

	}
	public void update(long t)
	{
		if (onGround == false && onBlock ==false && animacion != fireFlower) setdy(getdy()+(float)(t*0.15));
		if ( (getx()+getImage().getWidth(null)+2) < minx || getx()>maxx ){ onBlock = false;} 
		if(currBlock != null)
		{
			int ty = 0;
			maxx = currBlock.getx()+(currBlock.getImage()).getWidth(null);
			minx = currBlock.getx();

			while(ty < mapa.sprites.size())
			{
				Sprite s = mapa.sprites.get(ty);

				boolean one = ((s.getx()+(s.getImage()).getWidth(null)+getImage().getWidth(null)+1) > minx);
				boolean two = ((s.getx()) < (maxx+1+getImage().getWidth(null)));

				if ( s.gety() == currBlock.gety() && one && two && s instanceof Block)
				{  
					if (s.getx() < minx){ minx = s.getx(); ty = 0;}
					if ( (s.getx() + s.getImage().getWidth(null)) >maxx) { maxx = (s.getx()+ s.getImage().getWidth(null)); ty = 0;} 
				}
				ty++;
			}
		}
		super.update(t);
	}
	public void tileCollisionV(int[] a)
	{
		if(getdy() >= 0) {floory =a[1]; sety(a[1]*30-getImage().getHeight(null)); onGround = true; }else{ sety((a[1]+1)*30);  } 
		setdy(0);

	}
	public void tileCollisionH(int[] a){ setdx(-getdx());}
	public void spriteCollision( Sprite spr)
	{
			if( spr instanceof Block || spr instanceof Plataforma)
			{
				if ((gety()+ getImage().getHeight(null)) < (spr.gety()+3))
				{	
					setdy(0);
					sety(spr.gety()-getImage().getHeight(null));
					onBlock = true;
					maxx = spr.getx()+(spr.getImage()).getWidth(null);
					minx = spr.getx();
					currBlock = spr;
	
				}
				else if ( gety() > (spr.gety()+spr.getImage().getHeight(null)) )
				{
					setdy(0);
				}
				else if ( getx() > (spr.getx()+spr.getImage().getWidth(null))) { setdx(Math.abs(getdx())); }
				else if	( spr.getx() > (getx()+getImage().getWidth(null))) { setdx(-Math.abs(getdx())); }
			}
	}
}
class Escombro extends Criatura
{
	public static Animacion escombro;
	boolean once;
	private int dyingTime;
	public Escombro(int x, int y, int dx, int dy)
	{
		super(x,y,dx,dy);
		animacion = escombro; 
		estado = 0;
		pending.add(this);
	}
	public void update(long t)
	{
		//if(once) dyingTime += t;
		setdy(getdy()+(float)(t*0.15));
		//if(dyingTime > 400)
		//{
		//	estado = MUERTO;
		//}
		super.update(t);
		
	}
	public void tileCollisionV(int[] a)
	{
		if(!once){setdy(-16); once =true;}

	}
	public void tileCollisionH(int[] a){}
	public void spriteCollision( Sprite k){}
}
class Axe extends Sprite{
	
	static Animacion axe;
	
	public Axe(int x, int y) {
		super(axe, x, y);
	}
}
class Coin extends Criatura
{
	private int dyingTime;
	public static Animacion coininside;
	public static Animacion coin;

	public Coin(int x, int y, boolean inside)
	{
		super(x,y, 0, -4);
		estado = 0;
		if(!inside) {setdy(0); animacion = coin.clone();}else{animacion = coininside.clone(); pending.add(this);}
	}
	public void update(long t)
	{
		super.update(t);
		if(getdy() != 0)
		{
			dyingTime += t;
			if (dyingTime > coininside.getduracionTotal()) estado = MUERTO;
		}
	}
	public void collide(){}
	public void tileCollisionV(int[] a){}
	public void tileCollisionH(int[] a){}
	public void spriteCollision( Sprite k){}
}
class Plant extends Criatura
{
	int desplazamientoV;
	int timeReverseDirection;
	int yi;
	public static Animacion plant;
	public Plant(int x, int y, int desplV)
	{
		super(x,y,0,3);
		animacion = plant.clone();
		yi = y;
		desplazamientoV = desplV;
		estado = 0;
	}
	public void update(long t)
	{
		
		if (gety()<= yi) {
			sety(yi);
			timeReverseDirection += t;
			setdy(0);
			if (timeReverseDirection > 2000) {
				timeReverseDirection = 0;
				setdy(3);
				
			}
		}
		else if (gety() >= (yi + desplazamientoV)) { 
			setdy(-3);
		}
		super.update(t);
	}
	public void collide(){}
	public void tileCollisionV(int[] a){}
	public void tileCollisionH(int[] a){}
	public void spriteCollision( Sprite k){}
}
class BolaDeFuego extends Criatura
{

	public static Animacion bolaDeFuegoHaciaDer;
	public static Animacion bolaDeFuegoHaciaIzq;
	

	public BolaDeFuego(int x, int y, int dx)
	{
		super(x,y, dx, 0);
		estado = 0;
		
		if (dx > 0) {
			animacion = bolaDeFuegoHaciaDer.clone();
		}
		else {
			animacion = bolaDeFuegoHaciaIzq.clone();
		}
		
		pending.add(this);
	}

	public void tileCollisionV(int[] a)
	{
		if(getdy() > 0) setdy(-20);
	}

	public void tileCollisionH(int[] a){ if ((gety()+getImage().getHeight(null)-4)>a[1]*30){ estado = MUERTO; }}

	public void update(long t)
	{
		setdy(getdy()+(float)(t*0.10));
		super.update(t);
		if(getx() > (GameManager.rl.currMap.player.getx() + 200)){estado = MUERTO;}
	}

	public void spriteCollision( Sprite spr)
	{

		if(spr instanceof Turtle || spr instanceof Goomba )
		{ 
			if ((((Criatura)spr).estado != CAYENDOSE) && (((Criatura)spr).estado != MUERTO)) 
			{ 

				if ( getdx() > 0 ) { ((Criatura)spr).setdx(8);} else { ((Criatura)spr).setdx(-8); }
				if (spr instanceof Turtle){ spr.sety(spr.gety()+spr.getImage().getHeight(null)-16); ((Turtle)spr).dyingTime = 0;}
				((Criatura)spr).setdy(-16);
				((Criatura)spr).estado = CAYENDOSE;
				((Criatura)spr).changeStatus(); estado = MUERTO;
				ResourceLoader.steppedOnTurtle.play();
				Mario.score+=100;
			}
		}
		if( spr instanceof Block || spr instanceof Plataforma)
		{
			if ((gety()+ getImage().getHeight(null)) < (spr.gety()+2))
			{
				setdy(-20);
			}
			else if((gety()+getImage().getHeight(null)-4) > spr.gety()){	estado = MUERTO; }
		}
		if( spr instanceof Plant){spr.estado = MUERTO; estado = MUERTO;}
	}

}
class Bowser extends Criatura
{
	Block[] bridge;
	int time = 300;
	int time2 = 0;
	long timeForBridgeDropping = 0;
	boolean startDroppingBridge;
	int bridgeFragmentDropped = 0;
	public static Animacion bowser;
	public static Animacion alientoDeFuego;
	int py2;	
	boolean fall;
	long lettingBowserFall = 0;
	
	public Bowser(int x, int y, Block[] puente)
	{
		super(x,y,0,0);
		animacion = bowser.clone();
		estado = 0;
		bridge = puente;
	}
	public void update(long t)
	{
		if (((Mario)GameManager.getResourceLoader().currMap.player).getx() > 2800) {
			((Mario)GameManager.getResourceLoader().currMap.player).setx(2800);
		}
		
		if (gety() > 200) {
			
			((Mario)GameManager.getResourceLoader().currMap.player).movimiento = 1;
			((Mario)GameManager.getResourceLoader().currMap.player).setdx(10);
			
		}
		if(!onGround) setdy(getdy()+(float)(t*0.10));
		
		if (gety()>150-animacion.getFrame().getHeight(null) && !fall) {
			sety(150-animacion.getFrame().getHeight(null));
			setdy(0);
			onGround = true;
			System.out.println("ADJUSTING Y");
		}
		
		if (bridgeFragmentDropped == 5) {
			lettingBowserFall += t;
			
			if (lettingBowserFall > 0) {
				if (fall == false) {
					Mario.score += 5000;
					ResourceLoader.bowserDie.play();
				}
				fall = true;
				onGround = false;
				
			}
		}
		
		time += t; time2 += t;
		if (!fall) {
		if (time > 4000 ) { 
			ResourceLoader.bowserFireball.play();
			new AlientoDeFuego(py2); 
			time = 0; 
			if(py2 == 0){py2 = 15;}else{py2 = 0;} }
		if (time2 > 8000 ) { System.out.println("MUST JUMP");
			setdy(-25); onGround = false; time2 = 0;}
		}
		if (startDroppingBridge) {
			timeForBridgeDropping += t;
			System.out.println("c");
			
			if (timeForBridgeDropping > 100 && bridgeFragmentDropped<11) {
				bridge[bridgeFragmentDropped].move = true;
				ResourceLoader.bridge.play();
				bridgeFragmentDropped++;
				timeForBridgeDropping = 0;
				if (bridgeFragmentDropped == 11) {
					setdy(-15);
					onGround = false;
					
					
				}
			}
		}
		
		if (GameManager.endLevel && ((Mario)GameManager.getResourceLoader().currMap.player).freeze == false) {
			((Mario)GameManager.getResourceLoader().currMap.player).freeze = true;
			((Mario)GameManager.getResourceLoader().currMap.player).movimiento = 0;
			((Mario)GameManager.getResourceLoader().currMap.player).setdx(0);
			
			startDroppingBridge = true;
			
			//setdy(-15);
		}
		
		super.update(t);
	}
	class AlientoDeFuego extends Criatura
	{
		public AlientoDeFuego(int py)
		{
			super((int)Bowser.this.getx()-15, (int)Bowser.this.gety()+30+py, -8,0);
			estado = 0;
			pending.add(this);
			animacion = alientoDeFuego.clone();
		}
		public void collide(){}
		public void tileCollisionV(int[] a){ }
		public void tileCollisionH(int[] a){}
		public void spriteCollision( Sprite k){}
	}

	public void collide(){}
	public void tileCollisionV(int[] a){/*onGround = true; setdy(0); sety(a[1]*30-getImage().getHeight(null));*/}
	public void tileCollisionH(int[] a){}
	public void spriteCollision( Sprite k){
		
		/*
		if (k instanceof Block && ((Mario)GameManager.getResourceLoader().currMap.player).freeze == false) {
			sety(k.gety()-getImage().getHeight(null));
			setdy(0);
			onGround = true;
		}
		*/
	}	
}
class Humo extends Sprite
{
	int p = 0;
	public Humo(Animacion anim, int x, int y)
	{
		super(anim.clone(), x,y);
	}
	public void update(long elapsedTime)
	{
		super.update(elapsedTime);
		p += elapsedTime;
		if (p>600) { estado = Criatura.MUERTO;}
	}
}
class FlagPole extends Sprite
{
	public static Animacion flag;
	public static Animacion flagPole;

	public Flag bandera;

	public FlagPole(int x, int y)
	{
		super(flagPole.clone(), x, y);
		//animacion = flagPole.clone();
		pending.add(this);
		bandera = new Flag();
	}	

	class Flag extends Criatura
	{
		public Flag()
		{
			super( (int)FlagPole.this.getx()+8, (int)FlagPole.this.gety()+9, 0, 0);
			
			animacion = flag;
			pending.add(this);
		}

		public void spriteCollision( Sprite k){}
		public void collide(){}
		public void tileCollisionV(int[] a){ estado = MUERTO;}
		public void tileCollisionH(int[] a){}

	}
}
class Plataforma extends Criatura
{
	public static Animacion plat;
	public static Animacion plat1;
	public static Animacion plat2;
	public static Animacion plat3;
	private int xi;
	private boolean g;

	public Plataforma(int x, int y, int dx, int dy)
	{
		super(x,y,dx,dy);
		animacion = plat.clone();
		estado = 0;
		xi = x;
	}

	public Plataforma(int x, int y, int tp)
	{
		super(x,y,0,0);
		if(tp == 1) animacion = plat1; else if (tp ==2) animacion = plat2; else if (tp == 3) animacion = plat3;
	}
	public Plataforma(int x, int y, int dy, boolean g)
	{
		super(x,y,0,dy);
		animacion = plat.clone();
		estado = 0;
		xi = x;
		this.g = g;
	}


	public void update(long elapsedTime)
	{
		if(getdy() != 0)
		{
			for(Sprite s : Sprite.mapa.sprites)
			{
				if (s instanceof Mario && s.estado == VIVO)
				{
					if ( ((s.getx()+s.getImage().getWidth(null)) > getx()) && (s.getx() < (getx()+getImage().getWidth(null))) )
					{
						if ( (s.gety()+s.getImage().getHeight(null)+6)> gety() && (s.gety()+s.getImage().getHeight(null)-3) <gety() ) {	s.sety( s.gety() + (getdy()*elapsedTime*0.01f) ); ((Mario)s).onBlock = true; ((Mario)s).minx = (getx()+2); ((Mario)s).maxx = (getx()+getImage().getWidth(null)-2); }
					}
				}
			}

			if(g)
			{
				if(gety() < 100) setdy(Math.abs(getdy()));
				else if (gety() > 235) setdy(-Math.abs(getdy()));
			}

			if(gety() < 10) sety(240); 
			if(gety() > 240) sety(10);
		}
		if(getdx() != 0)
		{
			for(Sprite s : Sprite.mapa.sprites)
			{
				if (s instanceof Mario && s.estado == VIVO)
				{
					if ( ((s.getx()+s.getImage().getWidth(null)) > getx()) && (s.getx() < (getx()+getImage().getWidth(null))) )
					{
						if ( (s.gety()+s.getImage().getHeight(null)+6)> gety() && (s.gety()+s.getImage().getHeight(null)-3) <gety() ) {	s.setx( s.getx() + (getdx()*elapsedTime*0.01f) ); ((Mario)s).onBlock = true; ((Mario)s).minx = (getx()+1); ((Mario)s).maxx = (getx()+getImage().getWidth(null)-1); }
					}
				}
			}
			if (getx() < xi) setdx(+Math.abs(getdx())); else if ( getx() > (xi+200) ) setdx(-Math.abs(getdx()));
		}
		super.update(elapsedTime);
	}

	public void spriteCollision( Sprite k){}
	public void collide(){}
	public void tileCollisionV(int[] a){}
	public void tileCollisionH(int[] a){}

}
class EnemyFireBall extends Sprite
{
	int r;
	float d;
	int cx;
	int cy;

	public EnemyFireBall(int cx, int cy, int r)
	{
		super((BolaDeFuego.bolaDeFuegoHaciaIzq).clone(), cx, cy); 
		this.r = r;
		this.cx = cx;
		this.cy = cy;
	}

	public void update(long elapsedTime)
	{
		super.update(elapsedTime);
		setx((float)Math.cos(d)*r+cx);
		sety((float)Math.sin(d)*r+cy);
		d += elapsedTime*0.003f;
	}
}
class Cloud extends Sprite
{
	public static Animacion cloud;
	int dx;

	public Cloud(int x, int y, int dx)
	{
		super(x,y);
		this.dx = dx;
		animacion = cloud;
	}
	public void update(long elapsedTime)
	{
		if(getx()>(GameManager.rl.currMap.player.getx()-240)){ setx(getx()-elapsedTime*(dx*0.01f));}
		else{setx(GameManager.rl.currMap.player.getx()+210); sety(((int)(Math.random()*170)));}
	}
}
class Star extends Criatura
{
	public static Animacion star;
	public Star(int x, int y, int dx)
	{
		super(x,y, dx, 0);
		estado = 0;
		animacion = star.clone();
		pending.add(this);
	}

	public void tileCollisionV(int[] a)
	{
		if(getdy() > 0) setdy(-10);
	}

	public void tileCollisionH(int[] a){ if ((gety()+getImage().getHeight(null)-4)>a[1]*30){ estado = MUERTO; }}

	public void update(long t)
	{
		setdy(getdy()+(float)(t*0.03));
		super.update(t);
		if(getx() > (GameManager.rl.currMap.player.getx() + 200)){estado = MUERTO;}
	}

	public void spriteCollision( Sprite spr)
	{
		if( spr instanceof Block || spr instanceof Plataforma)
		{
			if ((gety()+ getImage().getHeight(null)) < (spr.gety()+2))
			{
				setdy(-10);
			}
			else if((getx()+getImage().getWidth(null)+4) > spr.getx()){	setdx(-6); }
		}
	}

}
class Bag extends Sprite
{
	public static Animacion bolsa1;
	public static Animacion bolsa2;
	public static Animacion bolsa3;
	int t;

	public Bag(int x, int y)
	{
		super(bolsa1, x,y);
	}
	
	public void update(long elapsedTime)
	{
		super.update(elapsedTime);
		if(animacion == bolsa2){ t += elapsedTime; 
			if(t>bolsa2.getduracionTotal()){ 
				animacion = bolsa3;
				GameManager.displayVictoryMessage = true;
				ResourceLoader.bowserCastleMusic.stop();
				ResourceLoader.worldClear.play();
			} 
		}
		
		
	}
}