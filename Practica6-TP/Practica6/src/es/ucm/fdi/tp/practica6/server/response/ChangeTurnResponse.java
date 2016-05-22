package es.ucm.fdi.tp.practica6.server.response;

import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

public class ChangeTurnResponse implements Response {

	/**
	 * numero serializable
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Atributo del tablero
	 */
	private Board board;
	/**
	 * Atributo de las piezas
	 */
	private Piece turn;
	
	/**
	 * Metodo constructor de la clase
	 * @param board se le pasa el tablero
	 * @param turn la pieza del jugador de ese turno
	 */
	public ChangeTurnResponse(Board board, Piece turn) {
		this.board = board;
		this.turn = turn;
	}

	@Override
	public void run(GameObserver o) {
		o.onChangeTurn(board, turn);
	}

}
