package org.inspirerobotics.sumobots.driverstation.joystick;

import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.inspirerobotics.sumobots.FmsComponent;
import org.inspirerobotics.sumobots.SumobotsRuntimeException;
import org.inspirerobotics.sumobots.driverstation.util.BackendEventQueue;
import org.inspirerobotics.sumobots.packet.JoystickData;
import org.inspirerobotics.sumobots.packet.Packet;
import org.inspirerobotics.sumobots.packet.PacketFactory;
import org.inspirerobotics.sumobots.packet.PacketPath;

import java.lang.reflect.Constructor;
import java.util.Optional;

public class JoystickThread implements Runnable {

    private static final Logger logger = LogManager.getLogger(JoystickThread.class);

    @Override
    public void run() {
        JoystickLibraryLoader.checkLibrary();

        while(!Thread.interrupted()){
            sendJoystickData(new JoystickData(0f, 0f, 0f, 0f));
            getController().ifPresent(this::runLoop);

            quietSleep(1500);
        }
    }

    private void quietSleep(long ms) {
        try{
            Thread.sleep(ms);
        }catch(InterruptedException e){
            throw new SumobotsRuntimeException("Joystick Thread interrupted while sleeping", e);
        }
    }

    private void runLoop(Controller controller) {
        logger.debug("Using controller!");

        BackendEventQueue.add(worker -> worker.setJoystickStatus(true));
        Gamepad gamepad = new Gamepad(controller);

        while(gamepad.poll()){
            gamepad.update();
        }

        BackendEventQueue.add(worker -> worker.setJoystickStatus(false));
        sendJoystickData(new JoystickData(0f, 0f, 0f, 0f));
        logger.debug("Lost controller!");
    }

    private void sendJoystickData(JoystickData joystickData){
        PacketPath path = new PacketPath(FmsComponent.DRIVER_STATION, FmsComponent.ROBOT);
        Packet packet = PacketFactory.createJoystick(path, joystickData);

        BackendEventQueue.add(worker ->
                worker.getRobotConnection().ifPresent(conn -> conn.getPipe().sendPacket(packet)));
    }

    Optional<Controller> getController(){
        ControllerEnvironment env = getControllerEnvironment();

        for(Controller controller : env.getControllers()){
            if(controller.getType() == Controller.Type.GAMEPAD){
                return Optional.of(controller);
            }
        }

        logger.trace("No controller found!");
        return Optional.empty();
    }

    private ControllerEnvironment getControllerEnvironment() {
        try {
            return rescan();
        } catch (Exception e) {
            throw new SumobotsRuntimeException("Failed to create controller environment. ", e);
        }
    }

    //Manually run the constructor to rescan for devices
    private ControllerEnvironment rescan() throws Exception {
        Constructor<ControllerEnvironment> constructor = (Constructor<ControllerEnvironment>) Class
                .forName("net.java.games.input.DefaultControllerEnvironment").getDeclaredConstructors()[0];

        constructor.setAccessible(true);

        return constructor.newInstance();
    }
}
