package es.ucm.fdi.tp.practica6.server.response;

import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

public class MoveStartResponse implements Response {

	/**
	 * numero del serial
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
	 * Metodo constructor de la clase
	 * @param board atributo del tablero
	 * @param turn atributo de la pieza
	 */
	public MoveStartResponse(Board board, Piece turn) {
		this.board = board;
		this.turn = turn;
	}

	@Override
	public void run(GameObserver o) {
		o.onMoveStart(board, turn);

	}

}
