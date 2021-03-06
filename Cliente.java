//
// YodafyServidorIterativo
// (CC) jjramos, 2012
//
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Cliente {

	public static void main(String[] args) {
		
		String buferEnvio;
		String buferRecepcion;
		int bytesLeidos=0;
		
		// Nombre del host donde se ejecuta el servidor:
		String host="localhost";
		// Puerto en el que espera el servidor:
		int port=8989;
		
		// Socket para la conexión TCP
		Socket socketServicio=null;
		
		try {
			//Creación del socket para la comunicación
			socketServicio = new Socket(host,port);	
			//Contiene lo que recibe el teclado
      		InputStreamReader entrada = new InputStreamReader(System.in);
      		//Lee del teclado, es decir lo del flujo estraada
      		BufferedReader teclado = new BufferedReader(entrada);
      		//Flujo de entrada del socket. Recibe del servidor
			InputStream inputStream = socketServicio.getInputStream();
			//Flujo de salida del socket. Envia al servidor
			OutputStream outputStream = socketServicio.getOutputStream();
			//Escribe sobre el flujo outputStream
			PrintWriter escritor = new PrintWriter(outputStream,true);
			//Buffer de lectura del flujo de entrada. Contiene lo que devuelve el servidor
			BufferedReader lector = new BufferedReader(new InputStreamReader(inputStream));
			//Manda una cadena al servidor
			String cadena = "Enviado";
			escritor.println(cadena);
			escritor.flush();
			for(int i = 0; i < 3; i++){
				//Esperamos pregunta 1:
				String respuesta = lector.readLine();
				System.out.println(respuesta);
				//Enviamos la respuesta
				cadena = teclado.readLine();
				escritor.println(cadena);
				escritor.flush();
				//Esperamos la correción
				String r1 = lector.readLine();
				System.out.println(r1);
			}

			socketServicio.close();
			// Excepciones:
		} catch (UnknownHostException e) {
			System.err.println("Error: Nombre de host no encontrado.");
		} catch (IOException e) {
			System.err.println("Error de entrada/salida al abrir el socket.");
		}
	}
}
