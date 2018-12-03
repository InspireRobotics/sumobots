import org.inspirerobotics.sumobots.socket.SocketPipe;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

public class Driverstation {

    public static void main(String[] args) throws IOException {
        SocketChannel socket = SocketChannel.open(new InetSocketAddress(8080));
        SocketPipe pipe = new SocketPipe(socket);

        while(!pipe.isClosed()){
            pipe.update();
        }
    }

}
