package GUI;

import java.io.InputStream;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;



class GamePanel extends BasicGameState  {
    private InputStream in ;
	private int id;
    /*Mapa del juego*/
    private TiledMap map;
    /*jugador 2*/
    private HumanPlayer player1;
    /*Jugador 2*/
    private ComputerPlayer player2;
    //tiempo transcurrido
    int delta;
    //true si es el turno de player1. false si es turno de player2
    private int turno;
    //lista de arqueros
	private Player[] playerList;

    
    private boolean hitTarget;
    
    public GamePanel(int id)
    {
    	this.id = id;
    }

    
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		//map = new TiledMap("Animaciones/Map/map.tmx");
		
		turno = 1;
		hitTarget = false;
		player1 = new HumanPlayer(new Image("Animaciones/Bowman/arco.png",true),10,310);
		player2 = new ComputerPlayer(player1.getImg().getFlippedCopy(true,false),800,310);
		playerList = new Player[]{player1,player2};
		
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		g.drawString("Puntaje jugador1: "+player1.getPuntos(), 100, 25);
		g.drawString("Puntaje jugador2:"+player2.getPuntos(), 300, 25);
	//	map.render(0, 0);
    	player1.render(container, game, g);
    	player2.render(container, game, g);
	}

	@Override
	public void update(GameContainer container, StateBasedGame stateGame, int delta)
			throws SlickException {
		Input in = container.getInput();
	    //if (!player1.isMoveMaked())
		
		
		
		if ( Math.pow(-1, turno) > 0)//si es el turno del jugador 1
		{

			player1.update(container, stateGame, delta);
			if(player1.getFlecha().isThrowed())
			{
				Camera.translate(playerList);
			}
			if (collision())//si se detecto que el la flecha dio en el blanco hitTarget cambia a true
				hitTarget = true;
			if (player1.isMoveMaked())
			{
				if(hitTarget)
				{//si se dio en el blanco el jugador 1 obtiene un punto
					player1.addPunto();
					hitTarget = false;
				}
				turno ++;
				player2.init();
			}
		}
		else
		{

			player2.update(container, stateGame, delta);
			if (collision())// si hubo una collision significa que se dio en el blanco
				hitTarget = true;
			if (player2.isMoveMaked())
			{
				if (hitTarget)//se se dio en el blanco el jugador 2 obtiene un punto
				{
					player2.addPunto();
					hitTarget = false;
					
				}
				turno ++;
				player1.init();
				
			}

		}
			
		if (in.isKeyPressed(Input.KEY_ENTER)){
			stateGame.enterState(Estados.Menu.getNumberOfMenu());
		}
	}

	@Override
	public int getID() {
		return id;
	}
	
	public boolean collision()
	{//detecta si la flecha hace contacto con alguno de los jugadores
		if (player1.getArrowBounds().intersects(player2.getBounds()))
		{
			System.out.println("Jugador 2 golpeado");
			return true;
		}else
		
		if (player2.getArrowBounds().intersects(player1.getBounds()))
		{
			System.out.println("Jugador 1 golpeado");

			return true;
		}
		  else return false;
		
	}
	
	
	
	

}
