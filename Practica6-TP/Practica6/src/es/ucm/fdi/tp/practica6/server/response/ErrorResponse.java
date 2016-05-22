package es.ucm.fdi.tp.practica6.server.response;

import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;

public class ErrorResponse implements Response {

	/**
	 * numero del serial
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Atributo String del mensaje
	 */
	private String msg;

	/**
	 * Metodo constructor de la clase
	 * @param msg string del mensaje
	 */
	public ErrorResponse(String msg) {
		this.msg = msg;
	}

	@Override
	public void run(GameObserver o) {
		o.onError(msg);

	}

}
