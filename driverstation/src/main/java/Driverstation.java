import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.inspirerobotics.sumobots.FmsComponent;
import org.inspirerobotics.sumobots.packet.Packet;
import org.inspirerobotics.sumobots.packet.PacketPath;
import org.inspirerobotics.sumobots.socket.SocketPipe;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

public class Driverstation {

    private static final Logger logger = LogManager.getLogger(Driverstation.class);

    public static void main(String[] args) throws IOException {
        SocketChannel socket = SocketChannel.open(new InetSocketAddress(8080));
        PacketPath path = new PacketPath(FmsComponent.DRIVER_STATION, FmsComponent.FIELD_SERVER);
        SocketPipe pipe = new SocketPipe(socket, Driverstation::handlePacket, path);
        logger.info("Starting driverstation!");

        while (!pipe.isClosed()) {
            pipe.update();
        }

        logger.info("Stopping driverstation!");
    }

    private static void handlePacket(Packet packet) {
        logger.info("Received packet: {}", packet);
    }

}
