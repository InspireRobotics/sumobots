# Deploying Robot Code
Once you have completed setting up your Raspberry Pi you can deploy the robot code. 

## Setting up your build.gradle
1. Locate the build.gradle file within the rms/tars folder.
2. Change the follow line (replace `localhost` with your robot's ip) 

```groovy
robotIP = "localhost"
```

## How to Deploy
Once your build.gradle is configured, you can use the following command to deploy:

```cmd
gradlew rms:tars:run
```

If you want to build AND deploy, then use the following command:
```cmd
gradlew robot:examples:build rms:tars:run
```

If you want the console to have less details use the following flags:
```
gradlew --quiet robot:examples:build rms:tars:run
```