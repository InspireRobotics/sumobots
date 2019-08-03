# Setup (WIP)
This is a brief tutorial on how to setup a Raspberry Pi to be used by Sumobots

### Step 1: Install Raspbian
The first step is to install Raspbian (The default Raspberry Pi OS) onto a microSD card. 

#### [Tutorial](https://thepi.io/how-to-install-raspbian-on-the-raspberry-pi/)

### Step 2: Install Pi4J
Once Raspbian is installed you can begin to install the libraries required by Sumobots. 
The first library you will need is Pi4J. Pi4J is a library based on WiringPi that allows Java programs to interact 
with a Raspberry Pis GPIO Pins. 

To Install Pi4J Run this command:
```
curl -sSL https://pi4j.com/install | sudo bash
```

#### [More Details](https://pi4j.com/1.2/install.html)

### Step 3: Download the RMS
The third step is to install the Robot Management Server from Github. There are currently no pre-built versions
so you will need to clone the repository from Github and build the RMS fat jar. 

(MORE DETAILS COMING SOON)

### Step 4: Set the RMS To Run on Boot
The fourth step is to set the RMS to run on boot. 
This will cause Raspbian to automatically start the code once the Pi is turned on.

Run the following command to open up the config file:
```
nano /home/pi/.config/lxsession/LXDE-pi/autostart
```

Then add this line to the bottom:
```
@lxterminal -e java -jar [PATH TO RMS JAR]

@lxterminal -e java -jar /home/pi/Desktop/rms.jar
```

### Step 5: Set Raspberry Pi's Static IP

Run the following command to open the network configuration file:
```
sudo nano /etc/dhcpcd.conf
```

At the end of the file add the following lines:
```
interface wlan0
static ip_address=ROBOT IP
static routers=ROUTER IP
static domain_name_servers=8.8.8.8 ROUTER IP 8.8.4.4
```

For instance: 
```
interface wlan0
static ip_address=192.168.5.123
static routers=192.168.5.1
static domain_name_servers=8.8.8.8 192.168.5.1 8.8.4.4
```

### Step 6: Deploy Robot Code
Once this is complete, you are ready to write robot code. For more details see
 [/Deploy.md](/https://github.com/InspireRobotics/sumobots/tree/master/docs/Deploy.md)