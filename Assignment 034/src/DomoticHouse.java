import java.util.Scanner;

public class DomoticHouse {
    private static Scanner scanner = new Scanner(System.in);
    private static HouseController house;
    private static boolean programRunning = true;
    
    public static void main(String[] args) {
        // Code
    }
}

class HouseController {
    private Room[] rooms;
    private Roomba roomba;
    private TemperatureController tempController;
    
    public HouseController() {
        // Create the rooms in our house
        this.rooms = new Room[3];
        this.rooms[0] = new Room("Living Room", 2, 2); // 2 lights, 2 windows
        this.rooms[1] = new Room("Kitchen", 2, 1); // 2 lights, 1 window
        this.rooms[2] = new Room("Bedroom", 2, 1); // 2 lights, 1 window
        
        // Create Roomba and Temperature Controller
        this.roomba = new Roomba();
        this.tempController = new TemperatureController();
    }
}

class Room {
    private String name;
    private Light[] lights;
    private Window[] windows;
    
    public Room(String name, int lightCount, int windowCount) {
        this.name = name;
        this.lights = new Light[lightCount];
        this.windows = new Window[windowCount];
    }
}

class Light {
    private String name;
    private boolean isOn;
    private int brightness;
    
    public Light(String name) {
        this.name = name;
        this.isOn = false;
        this.brightness = 0;
    }
}

class Window {
    private String name;
    private boolean isOpen;
    private int openness;
    
    public Window(String name) {
        this.name = name;
        this.isOpen = false;
        this.openness = 0;
    }
}

class Roomba {
    private boolean isCleaning;
    private int batteryLevel;
    private String status;
    
    public Roomba() {
        this.isCleaning = false;
        this.batteryLevel = 85;
        this.status = "Ready";
    }
}

class TemperatureController {
    private double temperature;
    private boolean acOn;
    private boolean heaterOn;
    private String mode; // Cooling, heating, off
    
    public TemperatureController() {
        this.temperature = 21.5;
        this.acOn = false;
        this.heaterOn = false;
        this.mode = "Off";
    }
}