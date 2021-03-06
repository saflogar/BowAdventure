package GUI;

import java.awt.Rectangle;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

public class Flecha implements Renderizable{
	
	private static final float GRAVEDAD = 9.8f;
	//Imagen de la flecha
	private Image img;
	//largo de la pantalla
	private float scrWidth;
	//ancho de la pantalla
	private float scrHeight;
	//posicion de la flecha en x
	private float x;
	//posicion de la flecha en y
	private float y;
	//posicion inicial de la flecha en y
	private float yIni;
	//posicion inicial de la flecha en x
	private float xIni;
	//Angulo con que es lanzado la flecha
	private float teta;
	//Velocidad con que es lanzada la fleca
	private float velocidad;
	//Velocidad en y de la flecha
	private float velocidadY;
	//tiempo transcurrido desde que es lanzada la flecha
	private float tiempo;
	//true si la flecha esta lanzada
	private boolean throwed;
	//true si la flecha va hacia la derecha. false:si la flecha va hacia la izquierda
	private boolean direccion;
	//Lista de Arqueros
	
	
	public Flecha(Image img,boolean direccion) {
		this.img = img;
		this.direccion = direccion;
		
	}
	
	public void init(GameContainer gc,float x, float y , float teta , float velocidad){
		//se inicializan las variables
		this.x = x;
		this.y = y;
		this.yIni = y;
		this.xIni = x;
		this.teta = teta;
		this.velocidad = velocidad;
		this.scrHeight = gc.getHeight();
		this.scrWidth = gc.getWidth();
		tiempo = 0;
		throwed = true;
		if (direccion)
		img.setRotation(-teta);
		else
		img.setRotation(teta);

	}

	private void calcX(){
	if (direccion)
		x = (float)(xIni+((velocidad * Math.cos(Math.toRadians(teta)))*tiempo));
	else
		x = (float)(xIni-((velocidad * Math.cos(Math.toRadians(teta)))*tiempo));

	//x = velocidad * tiempo;
	}
	
	private void calcY(){
		calcVelocidadY(); 	
		//System.out.print("Veolocidad y=" + velocidadY);
		y = (float)(yIni-((((velocidadY * Math.sin(Math.toRadians(teta)))*tiempo)-((GRAVEDAD*Math.pow(tiempo, 2))))));
	}
	
	public boolean isThrowed(){
		return throwed;
	}
	
	public void calcVelocidadY(){
		velocidadY = (float) (velocidad * Math.sin(Math.toRadians(teta))-(GRAVEDAD * tiempo));		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame stateGame, Graphics g) {
		// TODO Auto-generated method stub
		img.draw(x,y);
		if (direccion)
		{
			img.rotate((float) (Math.PI + Math.atan2(velocidad,velocidadY)));
			
		}else 
		{
			img.rotate((float)(-Math.PI - Math.atan2(velocidad,velocidadY)));
		}
		
		//img.setRotation((float) (Math.PI + Math.atan2(velocidadY,velocidad)));
	//	System.out.println(Math.toDegrees(img.getRotation()));
		//System.out.println("render x="+x+" y="+y+"tiempo= "+tiempo+" Angulo= "+img.getRotation());
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame stateGame,
			int delta) {
		// TODO Auto-generated method stub
		if (x <= scrWidth && y <= scrHeight){
			   calcX();
			   calcY();	
			   tiempo+=.3;
		   }
		   else 
		   {
			   throwed = false;
		   }	
	}	
	
	public Rectangle getBounds(){
		return new Rectangle((int)x,(int)y,img.getWidth(),img.getHeight());
	}
	
	public void setX(float x){this.xIni += x;}
	
	public void setY(float y){this.yIni = y;}
	
	public float getVelX (){return velocidad;}
	
	public void setVelX(float velocidadX){this.velocidad = velocidadX;}
	
}
