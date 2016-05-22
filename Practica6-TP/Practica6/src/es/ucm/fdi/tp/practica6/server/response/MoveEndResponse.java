package es.ucm.fdi.tp.practica6.server.response;

import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

public class MoveEndResponse implements Response {

	/**
	 * numero de serial
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Atributo del tablero
	 */
	private Board board;
	/**
	 * Atributo de la pieza
	 */
	private Piece turn;
	/**
	 * Atributo boleano
	 */
	private boolean success;
	
	/**
	 * Metodo constructor de la clase
	 * @param board atributo del tablero
	 * @param turn atributo de la pieza
	 * @param success atributo booleano
	 */
	public MoveEndResponse(Board board, Piece turn, boolean success) {
		this.board = board;
		this.turn = turn;
		this.success = success;
	}

	@Override
	public void run(GameObserver o) {
		o.onMoveEnd(board, turn, success);

	}

}
