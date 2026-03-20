import java.util.*;

// Reservation (from Use Case 5)
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

// Inventory Service (Use Case 3)
class RoomInventory {
    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
    }

    public void addRoomType(String type, int count) {
        inventory.put(type, count);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    public void reduceRoom(String type) {
        int current = getAvailability(type);
        if (current > 0) {
            inventory.put(type, current - 1);
        }
    }
}

// Booking Request Queue (Use Case 5)
class BookingRequestQueue {
    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    public void addRequest(Reservation r) {
        queue.offer(r);
    }

    public Reservation getNextRequest() {
        return queue.poll(); // FIFO
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

// Booking Service (Use Case 6 CORE)
class BookingService {

    private Set<String> allocatedRoomIds = new HashSet<>();
    private Map<String, Set<String>> roomTypeToIds = new HashMap<>();
    private int roomCounter = 1;

    public void processRequests(BookingRequestQueue queue, RoomInventory inventory) {

        while (!queue.isEmpty()) {

            Reservation r = queue.getNextRequest();
            String type = r.getRoomType();

            System.out.println("\nProcessing request for " + r.getGuestName());

            // Check availability
            if (inventory.getAvailability(type) > 0) {

                // Generate unique room ID
                String roomId = type + "-" + roomCounter++;

                // Ensure uniqueness using Set
                allocatedRoomIds.add(roomId);

                // Map room type to IDs
                roomTypeToIds.putIfAbsent(type, new HashSet<>());
                roomTypeToIds.get(type).add(roomId);

                // Update inventory (IMPORTANT)
                inventory.reduceRoom(type);

                // Confirm booking
                System.out.println("Booking Confirmed!");
                System.out.println("Guest: " + r.getGuestName());
                System.out.println("Room Type: " + type);
                System.out.println("Room ID: " + roomId);

            } else {
                System.out.println("Booking Failed! No rooms available for " + type);
            }
        }
    }
}

// Main class
public class BookMyStayApp {

    public static void main(String[] args) {

        // Step 1: Setup inventory
        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single", 2);
        inventory.addRoomType("Double", 1);

        // Step 2: Setup request queue
        BookingRequestQueue queue = new BookingRequestQueue();
        queue.addRequest(new Reservation("Tharun", "Single"));
        queue.addRequest(new Reservation("Rahul", "Single"));
        queue.addRequest(new Reservation("Anu", "Single")); // extra request
        queue.addRequest(new Reservation("Kiran", "Double"));

        // Step 3: Booking service
        BookingService service = new BookingService();

        // Step 4: Process requests (FIFO + Safe Allocation)
        service.processRequests(queue, inventory);
    }
}