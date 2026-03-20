import java.util.*;

// Room class (Domain Model)
class Room {
    private String type;
    private double price;

    public Room(String type, double price) {
        this.type = type;
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }
}

// RoomInventory (centralized state)
class RoomInventory {
    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
    }

    public void addRoomType(String type, int count) {
        inventory.put(type, count);
    }

    // Read-only access
    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    public Set<String> getAllRoomTypes() {
        return inventory.keySet();
    }
}

// Search Service (READ ONLY)
class SearchService {

    public void searchAvailableRooms(RoomInventory inventory, Map<String, Room> roomDetails) {

        System.out.println("Available Rooms:\n");

        for (String type : inventory.getAllRoomTypes()) {

            int available = inventory.getAvailability(type);

            // Defensive programming
            if (available > 0 && roomDetails.containsKey(type)) {

                Room room = roomDetails.get(type);

                System.out.println("Room Type: " + room.getType());
                System.out.println("Price: ₹" + room.getPrice());
                System.out.println("Available: " + available);
                System.out.println("--------------------------");
            }
        }
    }
}

// Main class (RENAMED)
public class BookMyStayApp {

    public static void main(String[] args) {

        // Setup inventory
        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single", 10);
        inventory.addRoomType("Double", 0);  // unavailable
        inventory.addRoomType("Suite", 3);

        // Setup room details
        Map<String, Room> roomDetails = new HashMap<>();
        roomDetails.put("Single", new Room("Single", 2000));
        roomDetails.put("Double", new Room("Double", 3500));
        roomDetails.put("Suite", new Room("Suite", 5000));

        // Search service
        SearchService searchService = new SearchService();

        // Perform search (READ ONLY)
        searchService.searchAvailableRooms(inventory, roomDetails);
    }
}