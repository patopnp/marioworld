package app;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class Mario extends Criatura implements KeyListener{

	boolean freeze;

	static int score;
	int stateTransitionTime = 0;
	
	boolean isJumping;
	int star = 0;
	int starSprinkleTime = 0;
	protected Animacion animMur;
	int movimiento;	
	private int condicion = 1;
	private int fxt = 0;
	boolean goUnder;
	long levelTime = 200*1000;
	int coins;
	int lives = 3;
	long tf;

	public static final int CHICO = 1;
	public static final int GRANDE = 2;
	public static final int FUEGO = 3;
	public static final int GETTING_BIGGER = 4;
	public static final int GETTING_SMALLER = 5;
	public static final int TURNING_FIRE_POWERED = 6;

	public static final int FLAGPOLE_SLIDING = 5;
	
	int invinsible;

	public static Animacion starSprinkle;
	public static Animacion humo;
	
	public static Animacion animacionD_fire_sliding;
	public static Animacion animacionI_fire_sliding;
	
	public static Animacion animacionD_big_sliding;
	public static Animacion animacionI_big_sliding;

	public static Animacion animacionD_fire_saltando;
	public static Animacion animacionI_fire_saltando;
	
	public static Animacion animacionD_small_sliding;
	public static Animacion animacionI_small_sliding;
	
	public static Animacion animacionD_big_saltando;
	public static Animacion animacionI_big_saltando;
	
	public static Animacion animacionD_small_saltando;
	public static Animacion animacionI_small_saltando;
	
	public static Animacion animacionD_naked;
	public static Animacion animacionI_naked;
	public static Animacion animacionM_naked;

	public static Animacion animacionD_small;
	public static Animacion animacionI_small;
	public static Animacion animacionM_small;
	public static Animacion animacionD_smallg;
	public static Animacion animacionI_smallg;
	public static Animacion animacionD_fire;
	public static Animacion animacionI_fire;
	public static Animacion animacionM_fire;

	public static Animacion animacionD_big;
	public static Animacion animacionI_big;
	public static Animacion animacionM_big;

	private Animacion anim_gettingBigger;

	private Animacion animDer_firesaltando;
	private Animacion animIzq_firesaltando;
	
	private Animacion animDer_smallsaltando;
	private Animacion animIzq_smallsaltando;
	
	private Animacion animDer_bigsaltando;
	private Animacion animIzq_bigsaltando;
	
	private Animacion animDer_fire;
	private Animacion animIzq_fire;	
	private Animacion animMur_fire;

	private Animacion animDer_naked;
	private Animacion animIzq_naked;	
	private Animacion animMur_naked;

	private Animacion animDer_small; private Animacion animIzq_small;
	private Animacion animDer_big; private Animacion animIzq_big;
	private Animacion animMur_small; private Animacion animMur_big;
	private Animacion animDer_sSliding; private Animacion animIzq_sSliding;	
	private Animacion animDer_bSliding; private Animacion animIzq_bSliding;
	private Animacion animDer_fSliding; private Animacion animIzq_fSliding;
	
	private boolean driveTimeToZero;
	
	public Mario( int x, int y, int dx, int dy)
	{
		super(x, y, dx, dy);
		animDer_small = animacionD_small.clone();
		animIzq_small = animacionI_small.clone();
		animMur_small = animacionM_small.clone();

		animDer_fSliding = animacionD_fire_sliding.clone();
		animIzq_fSliding = animacionI_fire_sliding.clone();
		
		animDer_sSliding = animacionD_small_sliding.clone();
		animIzq_sSliding = animacionI_small_sliding.clone();
		
		animDer_bSliding = animacionD_big_sliding.clone();
		animIzq_bSliding = animacionI_big_sliding.clone();
		
		animDer_naked = animacionD_naked.clone();
		animIzq_naked = animacionI_naked.clone();
		animMur_naked = animacionM_small.clone();

		animDer_big = animacionD_big.clone();
		animIzq_big = animacionI_big.clone();
		animMur_big = animacionM_big.clone();

		animDer_fire = animacionD_fire.clone();
		animIzq_fire = animacionI_fire.clone();
		animMur_fire = animacionM_small.clone();

		anim_gettingBigger = null;
		
		animDer_smallsaltando = animacionD_small_saltando.clone();
		animIzq_smallsaltando = animacionI_small_saltando.clone();
		
		animDer_bigsaltando = animacionD_big_saltando.clone();
		animIzq_bigsaltando = animacionI_big_saltando.clone();
		
		animDer_firesaltando = animacionD_fire_saltando.clone();
		animIzq_firesaltando = animacionI_fire_saltando.clone();
		
		animDer = animDer_small;
		animIzq = animIzq_small;
		animMur = animMur_small;

		animacion = animDer;
	}
	
	public int getCond(){ return condicion;}
	
	public void getCoin() {
		coins++;
		
		score += 200;
		coins++; if(coins == 100){
			lives++; coins = 0;
		} 
	}
	
	public void keyPressed(KeyEvent evento)
	{
		if (condicion == GETTING_SMALLER || condicion == GETTING_BIGGER || condicion == TURNING_FIRE_POWERED ) return;
		if( estado == VIVO && !freeze)
		{
			int cod = evento.getKeyCode();
			if ( cod == KeyEvent.VK_LEFT) movimiento = 2;
			if (cod == KeyEvent.VK_RIGHT) movimiento = 1;
			if (cod == KeyEvent.VK_UP) {
				if(onGround || onBlock) {
					setdy(-47);
					isJumping = true;
					ResourceLoader.jump.play();;
					changeStatus();
					//this.sety(this.gety()-2);
				}
			}
			if (cod == KeyEvent.VK_DOWN) {
				if((getx()>mapa.mapx) && (getx()<(mapa.mapx+30)) && onGround) { 
					
					goUnder = true;
				}
			}
			if (cod == KeyEvent.VK_P) {
				setx(getx()+1000);
			}
			if (cod == KeyEvent.VK_X) 
			{
				System.out.println(getx());
				System.out.println(gety());
				if(condicion == FUEGO && tf>100)
				{ 
					System.out.println(Sprite.pending.size());
					int numFireballs = 0;
					
					for (Sprite s : GameManager.getResourceLoader().currMap.sprites) {
						
						if (s instanceof BolaDeFuego) {
							numFireballs++;
						}
					}
					
					if (numFireballs == 2) {
						return;
					}
					
					tf = 0;
					if(animacion == animDer) {
						ResourceLoader.fireball.play();
						new BolaDeFuego((int)getx()+getImage().getWidth(null)/2, (int)gety()+8, 23);
					} 
					else if (animacion == animIzq) {
						
						ResourceLoader.fireball.play();
						new BolaDeFuego((int)getx()-4, (int)gety()+8, -23);
					}
				}
			}
			if (cod == KeyEvent.VK_N) { star = 1; changeStatus();}
			//if (cod == KeyEvent.VK_O){setx(GameManager.rl.currMap.mapSize-200);}
			//if (cod == KeyEvent.VK_I){setx(getx()-200);}

		}
	}
	
	public void keyReleased(KeyEvent evento)
	{
		if(estado == VIVO && !freeze)
		{
			int cod = evento.getKeyCode();
			if ( cod == KeyEvent.VK_LEFT) { if (movimiento == 2) movimiento  = 3;}
			else if (cod == KeyEvent.VK_RIGHT) { if (movimiento == 1) movimiento = 3;}
		}
	}
	
	public void keyTyped(KeyEvent evento){}

	//collision with a tile detected
	public void tileCollisionV(int[] a)
	{
		if(getdy() >= 0) {
			floory =a[1]; 
			sety(a[1]*30-1-getImage().getHeight(null)); onGround = true; isJumping = false; changeStatus();
		}
		else{ 
			//sety((a[1]+1)*30+10); 
		} 
		setdy(0);
	}
	
	public void tileCollisionH(int[] a){setdx(0);}	


	//collision with a sprite detected
	public void spriteCollision( Sprite spr)
	{
		if (spr instanceof Axe) {
			GameManager.endLevel = true;
			spr.estado = MUERTO;
		}
		if( spr instanceof FlagPole){ if (((FlagPole)spr).bandera.getdy() == 0) {
			BackgroundMusic.stop();
			ResourceLoader.flagPoleSliding.playConsecutiveSounds(ResourceLoader.stageClear);
			score += 5000+5000*(spr.gety()-gety())/spr.getImage().getHeight(null);
			
			((FlagPole)spr).bandera.setdy(10); freeze = true; movimiento = 0; 
			estado = FLAGPOLE_SLIDING; 
			setx(spr.getx()-7);
			setdy(10); 
			setdx(0); 
			changeStatus();
			} 
		}
		if (spr instanceof Star)
		{
			Mario.score += 1000;
			if(condicion == CHICO){
				//sety(gety()-12);
			}
			ResourceLoader.starPowerUpMusic.play();
			star = 1;
			changeStatus();
			spr.estado = 3;

		}
		if (spr instanceof Bag)
		{
			spr.animacion = Bag.bolsa2;
			movimiento = 0;
			setdx(0);
		}
		if (spr instanceof Mushroom)
		{
				score += 1000;
				ResourceLoader.grabbingPowerUp.play();
				if (condicion != FUEGO) {
				
					if (((Criatura)spr).getdx() == 0) { 
						if(condicion == CHICO){ 
							sety(gety()-12);
							} 
						condicion = TURNING_FIRE_POWERED; 
						changeStatus();  
						}
					else {   
						sety(gety()-12); 
						condicion = GETTING_BIGGER; 
						changeStatus();  
						}
					
				}
				((Criatura)spr).estado = MUERTO;
		}
		if(spr instanceof Coin)
		{
			if( ((Criatura)spr).getdy() == 0){ ((Criatura)spr).estado = MUERTO; if(estado != MUERTO){ 
				ResourceLoader.pickUpCoin.play();
				score += 200;
				coins++; if(coins == 100){lives++; coins = 0;} } }
		}
		if(spr instanceof EnemyFireBall && star == 0)
		{
			if(condicion == CHICO && invinsible == 0){ 
				BackgroundMusic.stop();
				ResourceLoader.marioFalling.playSingle();
				estado = CAYENDOSE; } 
			if(condicion > CHICO) { 
				ResourceLoader.marioGettingDamaged.play();
				condicion = GETTING_SMALLER; /*sety(gety() + getImage().getHeight(null) - animDer_small.getFrame().getHeight(null));*/}
			if(invinsible == 0) invinsible = 1;
			changeStatus();
		}
		if(spr instanceof Plant || spr instanceof Bowser.AlientoDeFuego || spr instanceof Bowser)
		{
			if(star != 0){spr.estado = MUERTO;}
			else
			{
				if ((((Criatura)spr).estado != CAYENDOSE) && (((Criatura)spr).estado != MUERTO)){
				if(condicion == CHICO && invinsible == 0){ 
					BackgroundMusic.stop();
					ResourceLoader.marioFalling.play();
					estado = CAYENDOSE; 
				} 
				if(condicion > CHICO) { 
					ResourceLoader.marioGettingDamaged.play();
					condicion = GETTING_SMALLER; 
					//sety(gety() + getImage().getHeight(null) - animDer_small.getFrame().getHeight(null));
				}
				if(invinsible == 0) invinsible = 1;
				changeStatus();}
			}
		}
		if (spr instanceof Turtle) 
		{
			
			if(star == 0)
			{
				
				if ( ( ((Criatura)spr).estado == Turtle.VOLANDO) && ( (gety() + (getImage()).getHeight(null)) < (spr.gety())+3) ) 
				{
						score += 100;
						ResourceLoader.steppedOnTurtle.play();
						sety(spr.gety()-animacion.getFrame().getHeight(null));
						setdy(-30);
						((Turtle)spr).estado = VIVO;
						((Turtle)spr).setdx(-3);
						((Turtle)spr).sety(spr.gety()+12);
						((Turtle)spr).changeStatus();	
				}
			}
			else
			{
				
				if ((((Criatura)spr).estado != CAYENDOSE) && (((Criatura)spr).estado != MUERTO)) 
				{ 
					ResourceLoader.kickTurtle.play();
					if ( getdx() > 0 ) { ((Criatura)spr).setdx(8);} else { ((Criatura)spr).setdx(-8); }
					if (spr instanceof Turtle){ spr.sety(spr.gety()+spr.getImage().getHeight(null)-16); ((Turtle)spr).dyingTime = 0;}
					((Criatura)spr).setdy(-16);
					((Criatura)spr).estado = CAYENDOSE;
					((Criatura)spr).changeStatus();
				
				}				
			}
		}
		if(spr instanceof Goomba && star != 0)
		{
			if ((((Criatura)spr).estado != CAYENDOSE) && (((Criatura)spr).estado != MUERTO)) 
			{ 
				ResourceLoader.kickTurtle.play();
				if ( getdx() > 0 ) { ((Criatura)spr).setdx(8);} else { ((Criatura)spr).setdx(-8); }
				if (spr instanceof Turtle){ spr.sety(spr.gety()+spr.getImage().getHeight(null)-16); ((Turtle)spr).dyingTime = 0;}
				((Criatura)spr).setdy(-16);
				((Criatura)spr).estado = CAYENDOSE;
				((Criatura)spr).changeStatus();	
			}
		}		

		if( (gety() + (getImage()).getHeight(null)) > spr.gety() && star == 0)
		{
			if(spr instanceof Turtle)
			{
				if(((Turtle)spr).dangerous || ((Criatura)spr).estado == VIVO || ((Criatura)spr).estado == Turtle.VOLANDO) 
				{
					if(condicion == CHICO && invinsible == 0){ 
						BackgroundMusic.stop();
						ResourceLoader.marioFalling.playSingle();
						estado = CAYENDOSE; changeStatus();} 
					if(condicion > CHICO) {/*sety(gety()+(animDer_big.getFrame()).getHeight(null)-(animDer_small.getFrame()).getHeight(null));*/ 
						ResourceLoader.marioGettingDamaged.play();
						condicion = GETTING_SMALLER; invinsible = 1; changeStatus();}
				}

			}
			if(spr instanceof Goomba)
			{
				if( ((Criatura)spr).estado == VIVO) 
				{
					if(condicion == CHICO && invinsible == 0){ 
						BackgroundMusic.stop();
						ResourceLoader.marioFalling.playSingle();
						estado = CAYENDOSE; changeStatus(); 
						} 
					if(condicion > CHICO) {
						ResourceLoader.marioGettingDamaged.play();
						condicion = GETTING_SMALLER;
						
						invinsible = 1; 
						changeStatus();
					}
				}

			}
		}

		if( (getx() + (getImage()).getWidth(null)) <= spr.getx() )
		{

			if (spr instanceof Block || spr instanceof Plataforma)
			{
					setdx(0);
					setx(getx()-0.1f);
					//setx(gety()+(float)ri.getHeight()+1);
			}
		}
		else if (getx() >= (spr.getx() + (spr.getImage()).getWidth(null)) )
		{
			if (spr instanceof Block || spr instanceof Plataforma)
			{
					setdx(0);
					setx(getx()+0.1f);
					//setx(gety()+(float)ri.getHeight()+1);
			}
		}
		else if( (gety() + (getImage()).getHeight(null)) < spr.gety() || gety() > (spr.gety() + (spr.getImage()).getHeight(null))  )
		{
			if( getdy()>0 )
			{
				if( spr instanceof Goomba )
				{
					if (((Goomba)spr).estado == VIVO)
					{
						score += 100;
						ResourceLoader.steppedOnTurtle.play();
						setdy(-30);
						((Goomba)spr).estado = MURIENDO;
						((Goomba)spr).changeStatus();
					}
				}
				if( spr instanceof Turtle )
				{
					if (((Turtle)spr).estado == Turtle.VOLANDO)
					{
						score += 100;
						ResourceLoader.steppedOnTurtle.play();
						sety(spr.gety()-animacion.getFrame().getHeight(null));
						setdy(-30);
						((Turtle)spr).estado = VIVO;
						((Turtle)spr).setdx(-3);
						((Turtle)spr).sety(spr.gety()+12);
						((Turtle)spr).changeStatus();
					}

					else if (((Turtle)spr).estado == VIVO)
					{
						ResourceLoader.steppedOnTurtle.play();
						sety(spr.gety()-animacion.getFrame().getHeight(null));
						setdy(-30);
						((Turtle)spr).estado = MURIENDO;
						((Turtle)spr).sety(spr.gety()+18);
						((Turtle)spr).changeStatus();
					}
					else if (((Turtle)spr).estado == MURIENDO)
					{
						sety(spr.gety()-animacion.getFrame().getHeight(null));
						setdy(-30);
						if ( getx() < spr.getx() ){ ((Criatura)spr).setdx(16); }else{ ((Criatura)spr).setdx(-16);}
						ResourceLoader.kickTurtle.play();
						((Turtle)spr).estado = Turtle.APLASTADO_DESPLAZANDOSE;
						((Turtle)spr).changeStatus();		
					}
					else if (((Turtle)spr).estado == Turtle.APLASTADO_DESPLAZANDOSE)
					{
						ResourceLoader.kickTurtle.play();
						((Turtle)spr).dangerous = false;
						sety(spr.gety()-animacion.getFrame().getHeight(null));
						setdy(-30);
						((Criatura)spr).setdx(0);
						((Turtle)spr).estado = MURIENDO;
						((Turtle)spr).changeStatus();
					}										
				}
				if (spr instanceof Block)
				{
					setdy(0);
					sety(spr.gety()-getImage().getHeight(null)-1);
					onBlock = true;
					isJumping = false;
					maxx = spr.getx()+(spr.getImage()).getWidth(null);
					minx = spr.getx();
					int ty = 0;
					while(ty < mapa.sprites.size())
					{
						Sprite s = mapa.sprites.get(ty);

						boolean one = ((s.getx()+(s.getImage()).getWidth(null)+getImage().getWidth(null)+1) > minx);
						boolean two = ((s.getx()) < (maxx+1+getImage().getWidth(null)));

						if ( s.gety() == spr.gety() && one && two )
						{  
							if (s.getx() < minx){ minx = s.getx(); ty = 0; }
							if ( (s.getx() + s.getImage().getWidth(null)) >maxx) { maxx = (s.getx()+ s.getImage().getWidth(null));  ty = 0;} 
						} 
						ty++;

					}
					changeStatus();
				}
				if (spr instanceof Plataforma && estado == VIVO)
				{
					setdy(0);
					sety(spr.gety()-getImage().getHeight(null)-1);
					onBlock = true;
					isJumping = false;
					changeStatus();
					maxx = spr.getx()+(spr.getImage()).getWidth(null);
					minx = spr.getx();
				}
				
			}
			else
			{
				if (spr instanceof Block)
				{
					setdy(0);
					//sety(gety()+(float)ri.getHeight()+1);
					((Block) spr).changeStatus();
				}
				if (spr instanceof Plataforma) {setdy(0);}
			}
		}
	}
	public void changeStatus()
	{
		boolean facingRight = animacion==animDer?true:false;
		
		//if(star > 0){
			/*animDer = animDer_naked; 
			animIzq = animIzq_naked; 
			animMur = animMur_big;*/
		//}
		//else
		//{
			if(estado == FLAGPOLE_SLIDING){
				
				if (condicion == FUEGO) {
					animDer = animDer_fSliding; 
					animIzq = animIzq_fSliding;
					animMur = animMur_big;
					animacion = animDer_fSliding;
				}
				else if (condicion == GRANDE) {
					animDer = animDer_bSliding; 
					animIzq = animIzq_bSliding;
					animMur = animMur_big;
					animacion = animDer_bSliding;
				}
				else if (condicion == CHICO) {
					animDer = animDer_sSliding; 
					animIzq = animIzq_sSliding;
					animMur = animMur_small;
					animacion = animDer_sSliding;					
				}
				return;
			}
			if(condicion == FUEGO){
				
				if (isJumping) {
					animDer = animDer_firesaltando; 
					animIzq = animIzq_firesaltando;
				}
				else {
					animDer = animDer_fire; 
					animIzq = animIzq_fire; 
					animMur = animMur_fire;
					
				}
			}
			if(condicion == GRANDE){
				
				if (isJumping) {
					animDer = animDer_bigsaltando; 
					animIzq = animIzq_bigsaltando;
				}
				else {
					animDer = animDer_big; 
					animIzq = animIzq_big; 
					animMur = animMur_big;
					
				}
			}
			else if(condicion == CHICO){
				
				if (isJumping) {
					animDer = animDer_smallsaltando; 
					animIzq = animIzq_smallsaltando;
				}
				else {
					animDer = animDer_small; 
					animIzq = animIzq_small; 
					animMur = animMur_small;
				}
			}
			else if(condicion == TURNING_FIRE_POWERED){ 
				stateTransitionTime = 1000;
				int i = animacion.getCurrFrameIndex();
				
				animDer = new Animacion(new ArrayList(), 0);
				
				animDer.add(animacionD_fire.getFrame(i), 100);
				animDer.add(animDer_big.getFrame(i), 100);
				
				animIzq = new Animacion(new ArrayList(), 0);
				
				animIzq.add(animacionI_fire.getFrame(i), 100); 
				animIzq.add(animIzq_big.getFrame(i), 100);
				
				animacion = facingRight?animDer:animIzq;
				
				animMur = animMur_small;
				
				/*
				animacion = new Animacion(new ArrayList(), 0);
				animacion.add(animDer_small.getFrame(0), 50);
				animacion.add(animDer_big.getFrame(1), 50);
				*/
				return;
			}
			else if(condicion == GETTING_BIGGER || condicion == GETTING_SMALLER){ 
				stateTransitionTime = 1000;
				int i = animacion.getCurrFrameIndex();
				
				animDer = new Animacion(new ArrayList(), 0);
				
				animDer.add(animacionD_smallg.getFrame(i), 100);
				animDer.add(animDer_big.getFrame(i), 100);
				
				animIzq = new Animacion(new ArrayList(), 0);
				
				animIzq.add(animacionI_smallg.getFrame(i), 100); 
				animIzq.add(animIzq_big.getFrame(i), 100);
				
				animacion = facingRight?animDer:animIzq;
				
				animMur = animMur_small;
				
				/*
				animacion = new Animacion(new ArrayList(), 0);
				animacion.add(animDer_small.getFrame(0), 50);
				animacion.add(animDer_big.getFrame(1), 50);
				*/
				return;
			}
		//}
		animacion = facingRight?animDer:animIzq;
		if(animacion == animDer_small) animacion = animDer;
		else if (animacion == animIzq_small) animacion = animIzq;
		else if (animacion == animDer_big) animacion = animDer;
		else if (animacion == animIzq_big) animacion = animIzq;
		else if (animacion == animIzq_fire) animacion = animIzq;
		else if (animacion == animDer_fire) animacion = animDer;
		else if (animacion == animIzq_naked) animacion = animIzq;
		else if (animacion == animDer_naked) animacion = animDer;

 		if (estado == CAYENDOSE){onGround = false; onBlock = false; animacion = animMur; setdx(0); setdy(-40); movimiento = 0;}
	}

	public void update(long elapsedTime)
	{
		if (condicion == TURNING_FIRE_POWERED) {
			animacion.update(elapsedTime);
			stateTransitionTime -= elapsedTime;
			if (stateTransitionTime < 0) {
				condicion = FUEGO;
				System.out.println("Satus changed");
				animacion = animDer_fire;
				
				//sety(gety()+(animDer_big.getFrame()).getHeight(null)-(animDer_small.getFrame()).getHeight(null)); 
				changeStatus();
				
			}
			return;
		}
		
		if (condicion == GETTING_SMALLER) {
			animacion.update(elapsedTime);
			stateTransitionTime -= elapsedTime;
			if (stateTransitionTime < 0) {
				condicion = CHICO;
				System.out.println("Satus changed");
				//animacion = animacionD_small;
				sety(gety()+(animDer_big.getFrame()).getHeight(null)-(animDer_small.getFrame()).getHeight(null)); 
				changeStatus();
				
			}
			return;
		}
		
		if (condicion == GETTING_BIGGER) {
			animacion.update(elapsedTime);
			stateTransitionTime -= elapsedTime;
			if (stateTransitionTime < 0) {
				condicion = GRANDE;
				System.out.println("Satus changed");
				//animacion = animacionD_big;
				changeStatus();
				
			}
			return;
		}
		
		super.update(elapsedTime);

		
		
		if(star > 0){ 
			starSprinkleTime += elapsedTime;
			star += elapsedTime; 
			if(star > 8000){
				star = 0; 
				
				if (GameManager.indice == 1) {
					ResourceLoader.levelOneMusic.play();
				}
				else if (GameManager.indice == 2) {
				ResourceLoader.levelTwoMusic.play();
				}
				else {
					ResourceLoader.levelOneMusic.play();
				}
				
				changeStatus();
			}
			
			if (starSprinkleTime  > 100) {
				starSprinkleTime = 0;
				
				Random r = new Random();
				int sprinkleX = r.nextInt(21)-5+(int)getx();
				int sprinkleY = r.nextInt(animacion.getFrame().getHeight(null))-5+(int)gety();
				pending.add(new Humo(starSprinkle, sprinkleX, sprinkleY));
			}
			
		}
 
		if(!(freeze))
		{
			if(levelTime <= 0) {
				BackgroundMusic.stop();
				ResourceLoader.marioFalling.playSingle();
				estado = CAYENDOSE; changeStatus(); levelTime = (200*1000);}
			else { levelTime -= elapsedTime;}
		}
		else{if(movimiento == 1) {if(getdx() < 12) setdx(getdx() + elapsedTime*0.03f);}if (estado == FLAGPOLE_SLIDING && onGround) {
			
				//sety(gety());
				estado = VIVO;
				movimiento = 1;
				freeze = true;
				changeStatus();
			}
		}

		if(condicion == FUEGO){ tf += elapsedTime;}		

		if (onGround == false && onBlock == false && estado != FLAGPOLE_SLIDING) setdy(getdy()+(float)(elapsedTime*0.15));
		if ( (getx()+getImage().getWidth(null)+2) < minx || getx()>maxx ){ onBlock = false;} 
		if (invinsible > 0){ invinsible += elapsedTime; if (invinsible>1000){invinsible = 0;} }
		if(movimiento == 1) {if(getdx() < 12) setdx(getdx() + elapsedTime*0.03f);}
		else if (movimiento == 2) {if(getdx() > -12) setdx(getdx() - elapsedTime*0.03f);}
		else if (movimiento == 3)
		{
			if(getdx() > 0) {setdx(getdx() -elapsedTime*0.02f); if(getdx() <0) setdx(0);}
			else if(getdx() <0) {setdx(getdx() + elapsedTime*0.02f); if(getdx() >0) setdx(0);}
		}
		if(onGround || onBlock)
		{
			if ( Math.abs(getdx()) > 12 )
			{
				fxt +=elapsedTime;
				if(fxt > 100)
				{
					pending.add(new Humo(humo, (int)(this.getx()), (int)this.gety()+getImage().getHeight(null)-8));
					fxt = 0;
				}
			}
		}
		if(estado == MUERTO){ 
			lives--; 
			
			if (lives == -1) {
				GameManager.showGameOverScreen = true;
				BackgroundMusic.stop();
				ResourceLoader.marioFalling.stop();
				ResourceLoader.gameOverForMario.play();
				return;
			}
			
			setx(80); 
			sety(120); 
			GameManager.showToNextLevelScreen = true;
			levelTime = 1000*200;
			
			estado = VIVO; 
			condicion = CHICO; 
			animacion = animDer_small; 
			changeStatus(); 
			setdy(0);
		}
	}
}
