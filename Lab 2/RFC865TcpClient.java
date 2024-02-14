import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class RFC865TcpClient {

    static Socket socket;
    static String serverAddress = "127.0.0.1";
    static int serverPort = 12345;
    public static void main(String[] args) throws UnknownHostException, IOException {
        try{
            socket = new Socket(serverAddress, serverPort);
        } catch (SocketException e) {}

        try {
            OutputStream os = socket.getOutputStream(); // Returns an outputsteam object
            // Printwrite is a class for writing chars, autoFlush means the data gets send automatically
            PrintWriter out = new PrintWriter(os, true); 
            out.println("Hello Server");

            InputStream is = socket.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(is));
            String response = in.readLine();
            System.out.println("Server response is: " + response);

        } catch(IOException e) {}
        
    }
}
