import java.io.IOException;
import java.net.*;

public class Rfc865UdpServer {

    static DatagramSocket socket;
    public static void main(String[] args) {
        
        try {
            socket = new DatagramSocket(17);
        } catch (SocketException e) {}

        byte[] buffer = new byte[512];
        // Stores data in terms of 8bits. 
        while(true) {
            try {
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                socket.receive(request);
                String quote = "GG";
                byte[] replyQuote = quote.getBytes();
                DatagramPacket reply = new DatagramPacket(replyQuote, replyQuote.length,
                                    request.getAddress(), request.getPort());
                socket.send(reply);
            } catch (IOException e) {}
        }
    }
}
