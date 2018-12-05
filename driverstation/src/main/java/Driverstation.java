import org.inspirerobotics.sumobots.packet.Packet;
import org.inspirerobotics.sumobots.packet.PingData;
import org.inspirerobotics.sumobots.socket.SocketPipe;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

public class Driverstation {

    public static void main(String[] args) throws IOException {
        SocketChannel socket = SocketChannel.open(new InetSocketAddress(8080));
        SocketPipe pipe = new SocketPipe(socket);

        while (!pipe.isClosed()) {
            pipe.update().ifPresent(Driverstation::handlePacket);
        }
    }

    private static void handlePacket(Packet packet) {
        if (packet.getAction().equals("ping")) {
            PingData data = (PingData) packet.getDataAs(PingData.class).get();
            System.out.println(data);
            System.out.println("Ping: " + (System.currentTimeMillis() - data.getStartTime()));
        }
    }

}
