package org.inspirerobotics.sumobots.rms;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.inspirerobotics.sumobots.SumobotsRuntimeException;

import java.io.*;

public class ProcessManager {

    private static final Logger logger = LogManager.getLogger(ProcessManager.class);

    private final Process process;
    private final BufferedReader processInputReader;
    private final BufferedReader processErrorReader;

    public ProcessManager() throws IOException {
        process = createProcess();

        processInputReader = bufferedReaderFromInputStream(process.getInputStream());
        processErrorReader = bufferedReaderFromInputStream(process.getErrorStream());
    }

    private BufferedReader bufferedReaderFromInputStream(InputStream s){
        return new BufferedReader(new InputStreamReader(s));
    }

    private static Process createProcess() throws IOException{
        ProcessBuilder builder = new ProcessBuilder();

        builder.directory(new File(FileUtils.rootDirectoryPath()));
        builder.command("java", "-jar", "robot-code/code.jar");

        logger.info("Running robot code!");

        return builder.start();
    }

    public void update() {
        updateScanner(processInputReader);
//        updateScanner(processErrorReader);

        if(!isAlive()){
            logger.info("Robot code stopped!");
        }
    }

    public void destroy() {
        logger.info("Killing robot code process!");
        process.destroy();

        try {
            Thread.sleep(500);

            if(process.isAlive())
                logger.info("Robot code failed to stop!");
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean isAlive(){
        return process.isAlive();
    }

    private void updateScanner(BufferedReader s) {
        try {
            if(!s.ready())
                return;

            String text = s.readLine();

            if(text != null)
                logger.info("RC: " + text);
        } catch(IOException e) {
            throw new SumobotsRuntimeException("Failed to read from process input stream!", e);
        }
    }
}
