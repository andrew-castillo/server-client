import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class server {
	private static final int PORT = 1337;
	private static ServerSocket server;
	private static Socket connection;
	private static ObjectOutputStream output;
	private static ObjectInputStream input;
	
	private static boolean isPrime(int n) {
	    if (n % 2 == 0 && n > 2) return false;
	    for(int i = 3 ; i * i <= n ; i += 2) {
	        if(n % i == 0)
	            return false;
	    }
	    return true;
	}
	
	public static void main(String[] args) {		
		try {
			server = new ServerSocket(PORT, 100);
			System.out.println("SERVER RUNNING");
			
			connection = server.accept();
			
			output = new ObjectOutputStream(connection.getOutputStream());
			output.flush();
			
			input = new ObjectInputStream(connection.getInputStream());
			
			ArrayList<Integer> current_input = new ArrayList<Integer>();
			ArrayList<Integer> int_list = new ArrayList<Integer>();
			
			try{
				current_input = (ArrayList<Integer>) input.readObject();
				for(Integer number : current_input){
					if (isPrime(number) == true){
						int_list.add(number);
					}
				}
				output.writeObject(int_list.toString());
				output.flush();
			} catch (ClassNotFoundException cnfe){
				System.out.println("Error. Not found");
			}
			
			connection.close();
			server.close();
		} catch (IOException ioe){
		System.out.println("Error");
		}
	}
}