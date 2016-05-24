package es.ucm.fdi.tp.practica6.buffer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


/**
 * Buffer de comunicacion cliente servidor.
 * Esta clase actua a modo de structura para un tipo de dato que permite realizar llamadas
 * por metodos a un socket, permitiendo enviarlo y recibirlo, asi como sacar/introducir contenido del mismo 
 */
public class Connection {

	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	
	/**	 
	 * Constructor de clase connection almacenar los streams de entrada y salida en atributos
	 * @param socket hilos del servidor
	 * @throws IOException excepcion que en caso de fallo debe ser tratada
	 */
	public Connection(Socket socket) throws IOException{
		this.socket = socket;
		this.out = new ObjectOutputStream(this.socket.getOutputStream());
		this.in = new ObjectInputStream(this.socket.getInputStream());
	}
	
	/**
	 * Procedimeinto de envio de objetos
	 * @param r objeto enviado por el cliente
	 * @throws IOException excepcion que en caso de fallo debe ser tratada
	 */
	public void sendObject(Object r) throws IOException{
		out.reset();
		out.writeObject(r);
		out.flush();		
	}
	
	/**
	 * Excepcion que en caso de fallo debe ser tratada
	 * @param e parametro de la excepcion
	 * @throws IOException excepcion que en caso de fallo debe ser tratada
	 */
	public void sendException(Exception e) throws IOException{
		out.writeObject(e);
		out.flush();
		out.reset();
	}
	
	/**	
	 * Procedimiento de recepcion de objetos
	 * @return la lectura del objeto 
	 * @throws IOException excepcion que en caso de fallo debe ser tratada
	 * @throws ClassNotFoundException excepcion que en caso de fallo debe ser tratada
	 */
	public Object getObject() throws IOException, ClassNotFoundException{
		return in.readObject();		
	}
	
	/**	 
	 * Procedimiento de cierre de un canal de comunicacion
	 * @throws IOException excepcion que en caso de fallo debe ser tratada
	 */
	public void stop() throws IOException{
		this.socket.close();
	}
	
	/**	
	 * Consulta de valor del puerto para una conexion
	 * @return entero con el numero del puerto
	 */
	public int getPort(){
		return this.socket.getPort();
	}

}
