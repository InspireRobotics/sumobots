package org.inspirerobotics.sumobots;

public class Version {

    /**
     * Major, Minor, Revision
     */

    public static final String VERSION = "0.1.0";

    public static final int MAJOR;
    public static final int MINOR;
    public static final int REVISION;

    static {
        int[] numbers = getVersionFromString(VERSION);

        MAJOR = numbers[0];
        MINOR = numbers[1];
        REVISION = numbers[2];
    }

    public static int[] getVersionFromString(String input){
        String[] numStrings = input.split("\\.");
        int[] output = new int[numStrings.length];

        for(int i = 0; i < numStrings.length; i++){
            String str = numStrings[i];

            output[i] = parse(str);
        }

        return output;
    }

    private static int parse(String str) {
        try{
            int result = Integer.parseInt(str);

            if(result < 0)
                throw new SumobotsRuntimeException("Version cannot have negative numbers!");

            return result;
        }catch (NumberFormatException e){
            throw new SumobotsRuntimeException("Failed to parse version number: " + str);
        }
    }


}
