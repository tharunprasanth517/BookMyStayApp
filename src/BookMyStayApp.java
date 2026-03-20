import java.util.*;

// Reservation (Confirmed Booking)
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

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

// Booking History (stores confirmed bookings)
class BookingHistory {

    private List<Reservation> history;

    public BookingHistory() {
        history = new ArrayList<>();
    }

    // Add confirmed booking
    public void addReservation(Reservation r) {
        history.add(r);
    }

    // Get all bookings (read-only)
    public List<Reservation> getAllReservations() {
        return history;
    }
}

// Reporting Service
class BookingReportService {

    // Display all bookings
    public void displayAllBookings(List<Reservation> reservations) {

        System.out.println("\n--- Booking History ---");

        for (Reservation r : reservations) {
            System.out.println("ID: " + r.getReservationId() +
                    ", Guest: " + r.getGuestName() +
                    ", Room: " + r.getRoomType());
        }
    }

    // Generate summary report
    public void generateSummary(List<Reservation> reservations) {

        System.out.println("\n--- Booking Summary ---");

        Map<String, Integer> countMap = new HashMap<>();

        for (Reservation r : reservations) {
            String type = r.getRoomType();
            countMap.put(type, countMap.getOrDefault(type, 0) + 1);
        }

        for (String type : countMap.keySet()) {
            System.out.println(type + " rooms booked: " + countMap.get(type));
        }
    }
}

// Main class
public class BookMyStayApp {

    public static void main(String[] args) {

        // Booking history
        BookingHistory history = new BookingHistory();

        // Simulate confirmed bookings (from Use Case 6)
        history.addReservation(new Reservation("Single-1", "Tharun", "Single"));
        history.addReservation(new Reservation("Single-2", "Rahul", "Single"));
        history.addReservation(new Reservation("Double-3", "Kiran", "Double"));

        // Reporting service
        BookingReportService reportService = new BookingReportService();

        // Display history
        reportService.displayAllBookings(history.getAllReservations());

        // Generate summary
        reportService.generateSummary(history.getAllReservations());
    }
}