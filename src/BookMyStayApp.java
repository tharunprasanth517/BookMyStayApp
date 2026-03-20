import java.util.HashMap;
import java.util.Map;

// RoomInventory class (Version 3.0)
class RoomInventory {

    private HashMap<String, Integer> inventory;

    // Constructor
    public RoomInventory() {
        inventory = new HashMap<>();
    }

    // Add room type
    public void addRoomType(String roomType, int count) {
        inventory.put(roomType, count);
    }

    // Get availability
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Update availability
    public void updateAvailability(String roomType, int change) {
        int current = inventory.getOrDefault(roomType, 0);
        inventory.put(roomType, current + change);
    }

    // Display inventory
    public void displayInventory() {
        System.out.println("Current Room Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

// Main class
public class BookMyStayApp {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();

        // Register room types
        inventory.addRoomType("Single", 10);
        inventory.addRoomType("Double", 5);
        inventory.addRoomType("Suite", 2);

        // Show initial inventory
        inventory.displayInventory();

        // Booking
        System.out.println("\nBooking 1 Single room...");
        inventory.updateAvailability("Single", -1);

        // Cancellation
        System.out.println("Cancelling 1 Double room...");
        inventory.updateAvailability("Double", +1);

        // Show updated inventory
        System.out.println();
        inventory.displayInventory();

        // Check availability
        System.out.println("\nAvailable Suites: " + inventory.getAvailability("Suite"));
    }
}