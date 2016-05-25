package es.ucm.fdi.tp.practica5.attt;

import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.practica5.view.RectBoardSwingView;

public class AdvancedTTTSwingView extends RectBoardSwingView {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The row where the piece is return return by {@link GameMove#getPiece()}.
	 * <p>
	 * Fila en la que se encuentra la ficha devuelta por
	 * {@link GameMove#getPiece()}.
	 */
	private int originRow;

	/**
	 * The column where the piece is return by {@link GameMove#getPiece()}
	 * .
	 * <p>
	 * Columna en la que se encuentra la ficha devuelta por
	 * {@link GameMove#getPiece()}.
	 */
	private int originCol;
	/**
	 * The row where to place the piece return by {@link GameMove#getPiece()}.
	 * <p>
	 * Fila en la que se coloca la ficha devuelta por
	 * {@link GameMove#getPiece()}.
	 */
	private int destinyRow;

	/**
	 * The column where to place the piece return by {@link GameMove#getPiece()}
	 * .
	 * <p>
	 * Columna en la que se coloca la ficha devuelta por
	 * {@link GameMove#getPiece()}.
	 */
	private int destinyCol;
	
	boolean allPieces;
	
	private AdvancedTTTSwingPlayer player;
	
	private boolean secondClick;

	public AdvancedTTTSwingView(Observable<GameObserver> game, Controller c, Piece localPiece, Player randPlayer, Player aiPlayer) {
		super(game, c, localPiece, randPlayer, aiPlayer);		
		this.player = new AdvancedTTTSwingPlayer();
		this.secondClick = false;			
	}	
	

	@Override
	protected void handelMouseClick(int row, int col, int clickcounter, int mouseButton) {
		if(!secondClick){
			this.originRow = row;
			this.originCol = col;
			this.secondClick = true;
			if(!allPieces){
				this.secondClick = false;
				this.player.setMoveValue(this.originRow, this.originCol, this.originRow, this.originCol);
				if(mouseButton == 1)
					this.caseMakeManualMove(this.player);
			}
		}
		
		else{
			this.destinyRow = row;
			this.destinyCol = col;
			this.secondClick = false;
			
			this.player.setMoveValue(this.originRow, this.originCol, this.destinyRow, this.destinyCol);
			if(mouseButton == 1)
			this.caseMakeManualMove(this.player);
		}
	}
	
	@Override
	public void onMoveStart(Board board, Piece turn) {
		if(board.getPieceCount(turn) > 0){
			this.allPieces = false;
		}
		else{
			this.allPieces = true;
		}
	}

}