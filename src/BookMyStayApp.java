import java.io.*;
import java.util.*;

// Reservation (Serializable)
class Reservation implements Serializable {
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

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

// Inventory (Serializable)
class RoomInventory implements Serializable {
    private Map<String, Integer> inventory = new HashMap<>();

    public void addRoomType(String type, int count) {
        inventory.put(type, count);
    }

    public Map<String, Integer> getInventory() {
        return inventory;
    }

    public void setInventory(Map<String, Integer> data) {
        this.inventory = data;
    }
}

// Booking History (Serializable)
class BookingHistory implements Serializable {
    private List<Reservation> reservations = new ArrayList<>();

    public void addReservation(Reservation r) {
        reservations.add(r);
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> list) {
        this.reservations = list;
    }
}

// Persistence Service
class PersistenceService {

    private static final String FILE_NAME = "hotel_data.ser";

    // Save data
    public void save(RoomInventory inventory, BookingHistory history) {

        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            oos.writeObject(inventory);
            oos.writeObject(history);

            System.out.println("Data saved successfully!");

        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    // Load data
    public void load(RoomInventory inventory, BookingHistory history) {

        File file = new File(FILE_NAME);

        if (!file.exists()) {
            System.out.println("No previous data found. Starting fresh.");
            return;
        }

        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            RoomInventory loadedInventory = (RoomInventory) ois.readObject();
            BookingHistory loadedHistory = (BookingHistory) ois.readObject();

            inventory.setInventory(loadedInventory.getInventory());
            history.setReservations(loadedHistory.getReservations());

            System.out.println("Data loaded successfully!");

        } catch (Exception e) {
            System.out.println("Error loading data. Starting with empty state.");
        }
    }
}

// Main Class
public class BookMyStayApp {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        BookingHistory history = new BookingHistory();
        PersistenceService persistence = new PersistenceService();

        // Load previous state
        persistence.load(inventory, history);

        // Simulate operations
        inventory.addRoomType("Single", 5);
        inventory.addRoomType("Double", 3);

        history.addReservation(new Reservation("Single-1", "Tharun", "Single"));
        history.addReservation(new Reservation("Double-2", "Rahul", "Double"));

        // Save state before exit
        persistence.save(inventory, history);

        // Display data
        System.out.println("\n--- Current Bookings ---");
        for (Reservation r : history.getReservations()) {
            System.out.println(r.getReservationId() + " | " +
                    r.getGuestName() + " | " +
                    r.getRoomType());
        }

        System.out.println("\n--- Inventory ---");
        for (Map.Entry<String, Integer> entry : inventory.getInventory().entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}