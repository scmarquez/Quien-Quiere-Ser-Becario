//
// YodafyServidorIterativo
// (CC) jjramos, 2012
//
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.PrintWriter;

//
// Nota: si esta clase extendiera la clase Thread, y el procesamiento lo hiciera el método "run()",
// ¡Podríamos realizar un procesado concurrente! 
//
public class Pregunton {
	//Preguntas
	private ArrayList<String> preguntas = new ArrayList<String>();
	 
	private ArrayList<String> respuestas = new ArrayList<String>();
	private int nPreg;
	private int npregunta;



	// Referencia a un socket para enviar/recibir las peticiones/respuestas
	private Socket socketServicio;
	// stream de lectura (por aquí se recibe lo que envía el cliente)
	private InputStream inputStream;
	// stream de escritura (por aquí se envía los datos al cliente)
	private OutputStream outputStream;
	// Para que la respuesta sea siempre diferente, usamos un generador de números aleatorios.
	private Random random;
	//Escribe sobre el flujo outputStream
	private PrintWriter escritor;

	
	// Constructor que tiene como parámetro una referencia al socket abierto en por otra clase
	public Pregunton(Socket socketServicio) {
		this.socketServicio=socketServicio;
		preguntas.add("¿Quien mato a kennedy?");
	 	preguntas.add("¿En que año empezo la guerra civil?");
	 	preguntas.add("¿Quien ideo el mito de la caverna?");
	 	respuestas.add("oswald");
	 	respuestas.add("1936");
	 	respuestas.add("platon");
	}
	// Aquí es donde se realiza el procesamiento realmente:
	void procesa(){
		
		// Como máximo leeremos un bloque de 1024 bytes. Esto se puede modificar.
		byte [] datosRecibidos=new byte[1024];
		int bytesRecibidos=0;
		
		// Array de bytes para enviar la respuesta. Podemos reservar memoria cuando vayamos a enviarka:
		byte [] datosEnviar;
		String pregunta = preguntas.get(npregunta);
		//System.out.print("La cadena es" + pregunta);
		try {
			//Lee del cliente
			inputStream=socketServicio.getInputStream();
			//Escribe en el cliente
			outputStream=socketServicio.getOutputStream();
			escritor = new PrintWriter(outputStream,true);

			//Recibe cadena vacia
			bytesRecibidos = inputStream.read(datosRecibidos);
			String r = new String(datosRecibidos,0,bytesRecibidos);
			System.out.println(r);
			for(int i = 0; i < 3; i++){
				//Enviar pregunta
				escritor.println(preguntas.get(i));
				escritor.flush();
				//Esperar respuesta
				bytesRecibidos = inputStream.read(datosRecibidos);
				r = new String(datosRecibidos,0,bytesRecibidos);

				//Devolvemos la corrección
				r = r.replace("\n","");
				if(r.equals(respuestas.get(i))){
					r = "La respuesta "+r+ " es correcta";
				}
				else{
					r = "La respuesta "+r+ " es falsa";
				}
				escritor.println(r);
				escritor.flush();
			}
			
		} catch (IOException e) {
			System.err.println("Error al obtener los flujso de entrada/salida.");
		}
	}
}
