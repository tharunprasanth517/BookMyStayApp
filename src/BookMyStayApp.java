import java.util.*;

// Service class (Add-On)
class Service {
    private String name;
    private double cost;

    public Service(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }
}

// Add-On Service Manager
class AddOnServiceManager {

    // Map: Reservation ID -> List of Services
    private Map<String, List<Service>> serviceMap;

    public AddOnServiceManager() {
        serviceMap = new HashMap<>();
    }

    // Add service to a reservation
    public void addService(String reservationId, Service service) {

        serviceMap.putIfAbsent(reservationId, new ArrayList<>());
        serviceMap.get(reservationId).add(service);

        System.out.println("Service added: " + service.getName() + " to " + reservationId);
    }

    // Display services for a reservation
    public void displayServices(String reservationId) {

        System.out.println("\nServices for Reservation: " + reservationId);

        List<Service> services = serviceMap.getOrDefault(reservationId, new ArrayList<>());

        if (services.isEmpty()) {
            System.out.println("No services added.");
            return;
        }

        for (Service s : services) {
            System.out.println("- " + s.getName() + " (₹" + s.getCost() + ")");
        }
    }

    // Calculate total add-on cost
    public double calculateTotalCost(String reservationId) {

        List<Service> services = serviceMap.getOrDefault(reservationId, new ArrayList<>());
        double total = 0;

        for (Service s : services) {
            total += s.getCost();
        }

        return total;
    }
}

// Main class
public class BookMyStayApp {

    public static void main(String[] args) {

        AddOnServiceManager manager = new AddOnServiceManager();

        // Example reservation IDs (from Use Case 6)
        String res1 = "Single-1";
        String res2 = "Double-2";

        // Guest selects services
        manager.addService(res1, new Service("Breakfast", 300));
        manager.addService(res1, new Service("Airport Pickup", 800));

        manager.addService(res2, new Service("Extra Bed", 500));

        // Display services
        manager.displayServices(res1);
        manager.displayServices(res2);

        // Show total cost
        System.out.println("\nTotal Add-On Cost for " + res1 + ": ₹" + manager.calculateTotalCost(res1));
        System.out.println("Total Add-On Cost for " + res2 + ": ₹" + manager.calculateTotalCost(res2));
    }
}