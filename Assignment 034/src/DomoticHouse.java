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
        
        // Lights and windows in living room
        this.rooms[0].addLight(0, new Light("Main Ceiling Light"));
        this.rooms[0].addLight(1, new Light("Reading Lamp"));
        this.rooms[0].addWindow(0, new Window("Large Window"));
        this.rooms[0].addWindow(1, new Window("Balcony Door"));
        
        // Lights and windows in kitchen
        this.rooms[1].addLight(0, new Light("Ceiling Light"));
        this.rooms[1].addLight(1, new Light("Under Cabinet Light"));
        this.rooms[1].addWindow(0, new Window("Kitchen Window"));
        
        // Lights and windows in bedroom
        this.rooms[2].addLight(0, new Light("Bedside Lamp"));
        this.rooms[2].addLight(1, new Light("Main Light"));
        this.rooms[2].addWindow(0, new Window("Bedroom Window"));

        // Roomba and temperature controller
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

    public String getName() {
        return name;
    }
    
    public void addLight(int position, Light light) {
        if (position >= 0 && position < lights.length) {
            lights[position] = light;
        }
    }
    
    public void addWindow(int position, Window window) {
        if (position >= 0 && position < windows.length) {
            windows[position] = window;
        }
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