package org.inspirerobotics.sumobots.driverstation.config;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.inspirerobotics.sumobots.SumobotsRuntimeException;

import java.util.Arrays;
import java.util.Objects;

public class Config {

    private static final Logger logger = LogManager.getLogger(Config.class);
    private static Config instance;

    private String robotIP;
    private Level logLevel;

    Config(String[] args) {
        setRobotIP("localhost");
        setLogLevel(Level.DEBUG);

        for(String arg : args) {
            parseArg(arg);
        }
    }

    private void parseArg(String arg) {
        if(!arg.contains("=")) {
            throw new SumobotsRuntimeException("Illegal Argument: " + arg +
                    ". Expected format arg=value (i.e. robotIP=localhost)");
        }

        String[] argArray = arg.split("=");

        switch(argArray[0]) {
            case "robotIP":
                setRobotIP(argArray[1]);
                break;
            case "logLevel":
                setLogLevel(Level.valueOf(argArray[1]));
                break;
            default:
                throw new SumobotsRuntimeException("Unknown Argument: " + argArray[0] +
                        ". Allowed Arguments: robotIP, logLevel");
        }
    }

    public static void init(String[] args) {
        logger.info("Args: {}", Arrays.toString(args));
        synchronized(Config.class) {
            instance = new Config(args);
        }
        logger.info("{}", instance);
    }

    public String getRobotIP() {
        return robotIP;
    }

    public void setRobotIP(String robotIP) {
        this.robotIP = Objects.requireNonNull(robotIP);
    }

    public Level getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(Level logLevel) {
        this.logLevel = Objects.requireNonNull(logLevel);

        Configurator.setLevel("org.inspirerobotics", logLevel);
    }

    public static Config getInstance() {
        if(instance == null) {
            synchronized(Config.class) {
                if(instance == null)
                    throw new SumobotsRuntimeException("Config not initialized yet");
                return instance;
            }
        }

        return instance;
    }

    @Override
    public String toString() {
        return "Config{" +
                "robotIP='" + robotIP + '\'' +
                ", logLevel=" + logLevel +
                '}';
    }
}
