package es.ucm.fdi.tp.practica6.server.response;

import java.io.Serializable;

import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;

/**
 * Interfaz que extiende del GameObserver
 * @author Nhala
 *
 */
public interface Response extends Serializable{
	public void run(GameObserver o);
	

}
