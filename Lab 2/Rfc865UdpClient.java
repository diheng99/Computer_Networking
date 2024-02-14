import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Rfc865UdpClient {
    static DatagramSocket socket;

    public static void main(String[] args) {

        InetAddress serverAddress, serverAddress2; 
        int serverPort = 17;
        byte[] buffer = new byte[512];

        try {
            serverAddress = InetAddress.getByName("localhost"); // Resolve hostname to address
            serverAddress2 = InetAddress.getByName("hwlab1.scse.ntu.edu.sg");

            socket = new DatagramSocket(); // The OS will assign available port. This socket is used for sending data
            // Do not need to specify Port OS will choose an ephemerical port as its not important to server which port client uses.
            String message = "Hello, QOTD Server";
            String message2 = "Di Heng Chew, SCS3, " + InetAddress.getLocalHost().getHostAddress();
            byte[] requestData = message.getBytes(); // String message parsed into bytes
            byte[] requestData2 = message2.getBytes(); // String message parsed into bytes

            // This creates a datagram packet
            DatagramPacket request = new DatagramPacket(requestData, requestData.length, serverAddress, serverPort);
            DatagramPacket request2 = new DatagramPacket(requestData2, requestData2.length, serverAddress2, serverPort);
            
            socket.send(request); // This line sends the datagram packet to the server specified by the IP address and port number

            DatagramPacket reply = new DatagramPacket(buffer, buffer.length); // Use the same buffer to store incoming data
            socket.receive(reply); // Blocking method -> Wait indefinitely until a packet is receieved. 
            
            String replyString = new String(reply.getData(), 0, reply.getLength());
            System.out.println("Quote of the Day: " + replyString);

        } catch (IOException e) {}
    }
}
    