import org.inspirerobotics.sumobots.FmsComponent;
import org.inspirerobotics.sumobots.packet.Packet;
import org.inspirerobotics.sumobots.packet.PacketPath;
import org.inspirerobotics.sumobots.socket.SocketPipe;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

public class Driverstation {

    public static void main(String[] args) throws IOException {
        SocketChannel socket = SocketChannel.open(new InetSocketAddress(8080));
        PacketPath path = new PacketPath(FmsComponent.DRIVER_STATION, FmsComponent.FIELD_SERVER);
        SocketPipe pipe = new SocketPipe(socket, Driverstation::handlePacket, path);

        while (!pipe.isClosed()) {
            pipe.update();
        }
    }

    private static void handlePacket(Packet packet) {
        System.out.println("Received packet: " + packet);
    }

}
