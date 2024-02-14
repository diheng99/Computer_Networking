import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class RFC865TcpServer {

    static ServerSocket parentSocket; // ServerSocket class is usually for creating a server to accept TCP connections
    static int serverPort = 12345;

    public static void main(String[] args) throws IOException {
        try {
            parentSocket = new ServerSocket(serverPort);
        } catch (Exception e) {
        }

        while (true) {
            try {
                // accept method is invoked when a client attempts to connect to the server.
                // Block method -> all execution will be paused until a connection is made at
                // this line
                // Once accepted, a new instance of socket class will be created
                Socket childSocket = parentSocket.accept();

                // A common practice to handle interaction with connected client
                // The runnable interface requires the implementation of a 'run' method, which
                // defines
                // the code that will execute on the new thread. Running in parallel
                ClientHandler client = new ClientHandler(childSocket);
                Thread thread = new Thread(client);
                thread.start(); // The code in the ClientHandler's run method will execute concurrently

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (parentSocket != null) {
                    try {
                        parentSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}

class ClientHandler implements Runnable {
    private Socket socket;

    ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            InputStream is = socket.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(is));
            String request = in.readLine();
            System.out.println("Client says: " + request);

            OutputStream os = socket.getOutputStream();
            PrintWriter out = new PrintWriter(os, true);
            out.println("Hello, Client");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
