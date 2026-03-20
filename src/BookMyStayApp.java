import java.util.*;

// Reservation
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getGuestName() {
        return guestName;
    }
}

// Inventory
class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public void addRoomType(String type, int count) {
        inventory.put(type, count);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    public void increaseRoom(String type) {
        inventory.put(type, getAvailability(type) + 1);
    }

    public void reduceRoom(String type) {
        int current = getAvailability(type);
        if (current > 0) {
            inventory.put(type, current - 1);
        }
    }
}

// Booking History
class BookingHistory {
    private Map<String, Reservation> bookings = new HashMap<>();

    public void addReservation(Reservation r) {
        bookings.put(r.getReservationId(), r);
    }

    public Reservation getReservation(String id) {
        return bookings.get(id);
    }

    public void removeReservation(String id) {
        bookings.remove(id);
    }

    public boolean exists(String id) {
        return bookings.containsKey(id);
    }
}

// Cancellation Service
class CancellationService {

    // Stack for rollback tracking (LIFO)
    private Stack<String> rollbackStack = new Stack<>();

    public void cancelBooking(String reservationId,
                              BookingHistory history,
                              RoomInventory inventory) {

        // Validate
        if (!history.exists(reservationId)) {
            System.out.println("Cancellation Failed: Reservation not found!");
            return;
        }

        // Get reservation
        Reservation r = history.getReservation(reservationId);

        // Push to rollback stack
        rollbackStack.push(reservationId);

        // Restore inventory
        inventory.increaseRoom(r.getRoomType());

        // Remove booking
        history.removeReservation(reservationId);

        System.out.println("Cancellation Successful!");
        System.out.println("Cancelled Reservation: " + reservationId);
    }

    public void showRollbackStack() {
        System.out.println("\nRollback Stack: " + rollbackStack);
    }
}

// Main Class
public class BookMyStayApp {

    public static void main(String[] args) {

        // Setup inventory
        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single", 1);

        // Booking history (simulate confirmed booking)
        BookingHistory history = new BookingHistory();
        Reservation r1 = new Reservation("Single-1", "Tharun", "Single");

        history.addReservation(r1);

        // Reduce inventory (simulate booking done)
        inventory.reduceRoom("Single");

        // Cancellation service
        CancellationService cancelService = new CancellationService();

        // Cancel booking
        cancelService.cancelBooking("Single-1", history, inventory);

        // Try invalid cancellation
        cancelService.cancelBooking("Single-1", history, inventory);

        // Show rollback stack
        cancelService.showRollbackStack();

        // Check inventory restored
        System.out.println("\nAvailable Single Rooms: " + inventory.getAvailability("Single"));
    }
}