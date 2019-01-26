import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class client {
	private static final int PORT = 1337;
	private static Scanner scan;

	public static void main(String[] args) {
		System.out.println("CILENT RUNNING");
		
		Socket socket = null;
		ObjectOutputStream outputStream = null;
		ObjectInputStream inputStream = null;	
		
		try {
			InetAddress address = InetAddress.getLocalHost();
			socket = new Socket(address, PORT);
			
			outputStream = new ObjectOutputStream(socket.getOutputStream());
			outputStream.flush();
			
			inputStream = new ObjectInputStream(socket.getInputStream());
			
			ArrayList<Integer> intlist = new ArrayList();
			String current_input = "";
			Scanner scan = new Scanner(System.in);
			
			while (current_input.compareTo("!") != 0){
		    	System.out.print("Enter an integer (“!” to send):  ");
		    	if (!scan.hasNextInt()){
		    		break;
		    	}
		    	current_input = scan.nextLine();
		    	intlist.add(Integer.parseInt(current_input));
			}
			
		    	
		    outputStream.writeObject(intlist);
			outputStream.flush();
			System.out.println("Sent: " + intlist.toString());
			    
			    
			try{
			    current_input = (String) inputStream.readObject();
			    System.out.println("Recieved:  " + current_input);
			}catch(ClassNotFoundException cnf){
			    System.out.println("\nClass not found");
			    }
		}catch (IOException ex) {
			ex.printStackTrace();
			
		}finally{
			try {
				if (socket != null) {
					socket.close();
				}
				if (outputStream != null) {
					outputStream.close();
				}
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
		System.out.println("CILENT FINISHED");
	}
}