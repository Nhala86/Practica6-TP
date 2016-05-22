package es.ucm.fdi.tp.practica6.server.response;

import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.Game.State;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

public class GameOverResponse implements Response {

	/**
	 * numero del serial
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Atributo del tablero
	 */
	private Board board;
	/**
	 * Atributo del stado del juego
	 */
	private State state;
	/**
	 * Atributo de la pieza ganadora
	 */
	private Piece winner;

	/**
	 * Metodo constructor de la clase
	 * @param board atributo del tablero
	 * @param state atributo del estado del juego
	 * @param winner la pieza ganadora del juego
	 */
	public GameOverResponse(Board board, State state, Piece winner) {
		this.board = board;
		this.state = state;
		this.winner = winner;
	}

	@Override
	public void run(GameObserver o) {
		o.onGameOver(board, state, winner);

	}

}
