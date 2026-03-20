import java.util.*;

// Custom Exception
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// Reservation
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

// Inventory Service
class RoomInventory {
    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
    }

    public void addRoomType(String type, int count) {
        inventory.put(type, count);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    public boolean isValidRoomType(String type) {
        return inventory.containsKey(type);
    }

    public void reduceRoom(String type) throws InvalidBookingException {
        int current = getAvailability(type);

        if (current <= 0) {
            throw new InvalidBookingException("No rooms available for type: " + type);
        }

        inventory.put(type, current - 1);
    }
}

// Validator
class InvalidBookingValidator {

    public static void validate(Reservation r, RoomInventory inventory) throws InvalidBookingException {

        if (r.getGuestName() == null || r.getGuestName().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty");
        }

        if (!inventory.isValidRoomType(r.getRoomType())) {
            throw new InvalidBookingException("Invalid room type: " + r.getRoomType());
        }

        if (inventory.getAvailability(r.getRoomType()) <= 0) {
            throw new InvalidBookingException("Room not available: " + r.getRoomType());
        }
    }
}

// Booking Service
class BookingService {

    private int roomCounter = 1;

    public void bookRoom(Reservation r, RoomInventory inventory) {

        try {
            // Validation (Fail-Fast)
            InvalidBookingValidator.validate(r, inventory);

            // Allocation
            String roomId = r.getRoomType() + "-" + roomCounter++;

            // Update inventory
            inventory.reduceRoom(r.getRoomType());

            System.out.println("Booking Confirmed!");
            System.out.println("Guest: " + r.getGuestName());
            System.out.println("Room ID: " + roomId);

        } catch (InvalidBookingException e) {
            // Graceful error handling
            System.out.println("Booking Failed: " + e.getMessage());
        }
    }
}

// Main Class
public class BookMyStayApp {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single", 1);
        inventory.addRoomType("Double", 0);

        BookingService service = new BookingService();

        // Valid booking
        service.bookRoom(new Reservation("Tharun", "Single"), inventory);

        // Invalid: no availability
        service.bookRoom(new Reservation("Rahul", "Single"), inventory);

        // Invalid: wrong room type
        service.bookRoom(new Reservation("Anu", "Suite"), inventory);

        // Invalid: empty name
        service.bookRoom(new Reservation("", "Single"), inventory);
    }
}