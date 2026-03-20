import java.util.*;

// Reservation class (represents booking request)
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

// Booking Request Queue (FIFO)
class BookingRequestQueue {

    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    // Add booking request
    public void addRequest(Reservation reservation) {
        queue.offer(reservation);
        System.out.println("Request added: " + reservation.getGuestName() + " -> " + reservation.getRoomType());
    }

    // View all requests (without removing)
    public void displayQueue() {
        System.out.println("\nCurrent Booking Queue:");

        for (Reservation r : queue) {
            System.out.println(r.getGuestName() + " requested " + r.getRoomType());
        }
    }
}

// Main class
public class BookMyStayApp {

    public static void main(String[] args) {

        BookingRequestQueue requestQueue = new BookingRequestQueue();

        // Simulate booking requests (arrival order)
        requestQueue.addRequest(new Reservation("Tharun", "Single"));
        requestQueue.addRequest(new Reservation("Rahul", "Suite"));
        requestQueue.addRequest(new Reservation("Anu", "Double"));

        // Display queue (FIFO order)
        requestQueue.displayQueue();
    }
}