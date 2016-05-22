package es.ucm.fdi.tp.practica6.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.tp.practica6.buffer.Connection;
import es.ucm.fdi.tp.practica6.server.response.Response;
import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.GameFactory;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.control.commands.Command;
import es.ucm.fdi.tp.basecode.bgame.control.commands.PlayCommand;
import es.ucm.fdi.tp.basecode.bgame.control.commands.QuitCommand;
import es.ucm.fdi.tp.basecode.bgame.control.commands.RestartCommand;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.Game.State;
import es.ucm.fdi.tp.basecode.bgame.model.GameError;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;


/**
 * <b>GameClient</b>
 * <p>Controlador de lado cliente, esencialmente es un observable para que la vista se registre
 * para recibir notificaciones del modelo enviadas desde el servidor. La vista usa GameClient como
 * modelo, ignorando el hecho de que dichas notificaciones viene de un servidor o que sus llamadas
 * a metodos del controlador van a un servidor</p>
 * @author Kadaiser
 *
 */
public class GameClient extends Controller implements Observable<GameObserver> {

	/**
	 * Host usado por el cliente para conectarse al puerto
	 */
	private String host;
	
	/**	 
	 * Puerto usado por el cliente para conectar al servidor
	 */
	private int port;
	
	/**	
	 * Las notificaciones que manda el servidor se reenvian a todos los observadores
	 */
	private List<GameObserver> observers;
	
	/**	 
	 * Reference for the owner of this view
	 */
	private Piece localPiece;
	
	/**	 
	 * Factoria del juego que se va a instanciar
	 */
	private GameFactory gameFactory;
	
	/**
	 * Encapsulado de Socket y canal de comunicacion del buffer de conexion con el servidor
	 */
	private Connection connectionToServer;
	
	/**	
	 * Valor indicador de fin de partida
	 */
	private boolean gameOver;

	/**
	 * Constructor de la clase 
	 * @param serverHost string del host
	 * @param serverPort entero del puerto
	 * @throws UnknownHostException exception a tratar en caso de fallo
	 * @throws IOException exception a tratar en caso de fallo
	 */
	public GameClient(String serverHost, Integer serverPort) throws UnknownHostException, IOException {
		super(null, null);
		this.host = serverHost;
		this.port = serverPort;
		this.observers = new ArrayList<>();
		try {
			connect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo que conecta al cliente con el servidor y que recibe el objeto
	 * @throws Exception exception a tratar en caso de fallo
	 */
	private void connect() throws Exception {
			this.connectionToServer = new Connection(new Socket(this.host,this.port));
			this.connectionToServer.sendObject(new String("Connect"));
			
			Object response = this.connectionToServer.getObject(); //1er mensaje del servidor DEBE ser string "ok"
			if(response instanceof Exception){
				throw (Exception) response;
			}
			else if((response instanceof String) && ((String)response).equalsIgnoreCase("ok")){
				try{
					this.gameFactory = (GameFactory) this.connectionToServer.getObject();
					this.localPiece = (Piece) this.connectionToServer.getObject();
					this.connectionToServer.sendObject(new String ("Client recieve game parametres for " + this.gameFactory.toString() +" the piece set for this client is : " + this.localPiece.toString()));
				}catch(Exception e){
					throw new GameError("Unknown server response: " + e.getMessage());
				}
			}
	}

	/**	 
	 * >metodo de consulta de la factoria entregada por el servidor
	 * @return gameFactory indicated by server connection
	 */
	public GameFactory getGameFactoty() {
		return this.gameFactory;
	}

	/**	 
	 * Metodo de consulta la pieza propietaria de la vista
	 * @return localpiece de esta vista
	 */
	public Piece getPlayerPiece() {
		return this.localPiece;
	}


	@Override
	public void addObserver(GameObserver o) {
		this.observers.add(o);		
	}

	@Override
	public void removeObserver(GameObserver o) {
		this.observers.remove(o);
	}
	
	/**
	 * Metodo que hace comenzar el juego
	 */
	public void start(){
		this.observers.add(new GameObserver(){

			@Override
			public void onGameStart(Board board, String gameDesc, List<Piece> pieces, Piece turn) {
			}

			@Override
			public void onGameOver(Board board, State state, Piece winner) {
				gameOver = true;
				
			}

			@Override
			public void onMoveStart(Board board, Piece turn) {
	
			}

			@Override
			public void onMoveEnd(Board board, Piece turn, boolean success) {
			}

			@Override
			public void onChangeTurn(Board board, Piece turn) {	
			}

			@Override
			public void onError(String msg) {
			}
		});
		
		gameOver = false;
		
		while(!gameOver){
			try{
				Response response = (Response) this.connectionToServer.getObject();
				for(GameObserver o : observers){
					response.run(o);
				}
			}catch(IOException | ClassNotFoundException e){}
		}
	}

	@Override
	public void makeMove(Player p) {
		forwardCommand(new PlayCommand(p));
	}

	@Override
	public void stop() {
		forwardCommand(new QuitCommand());
	}

	@Override
	public void restart() {
		forwardCommand(new RestartCommand());
	}

	/**
	 * Metodo encargado de enviar los objetos, en este caso los comandos
	 * @param cmd comandos del jugador
	 */
	private void forwardCommand(Command cmd) {		
		if(!this.gameOver){
			try {
				this.connectionToServer.sendObject(cmd);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
