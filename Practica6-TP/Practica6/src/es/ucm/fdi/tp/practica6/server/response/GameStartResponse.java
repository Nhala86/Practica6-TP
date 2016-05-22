package es.ucm.fdi.tp.practica6.server.response;

import java.util.List;

import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

public class GameStartResponse implements Response {

	/**
	 * numero del serial
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Atributo del tablero
	 */
	private Board board;
	/**
	 * Atributo String del juego
	 */
	private String gameDesc;
	/**
	 * Atributo del array de la lista de piezas
	 */
	private List<Piece> pieces;
	/**
	 * Atributo de las piezas
	 */
	private Piece turn;

	/**
	 * Metodo constructor de la clase
	 * @param board atributo del tablero
	 * @param gameDesc atributo string del juego
	 * @param pieces array de las listas de piezas
	 * @param turn pieza del turno
	 */
	public GameStartResponse(Board board, String gameDesc, List<Piece> pieces, Piece turn) {
		this.board = board;
		this.gameDesc = gameDesc;
		this.pieces = pieces;
		this.turn = turn;
	}

	@Override
	public void run(GameObserver o) {
		o.onGameStart(board, gameDesc, pieces, turn);

	}

}
