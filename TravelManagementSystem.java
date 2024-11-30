import java.util.*;

// Represents a hotel with details such as name, cost per night, distance, and food options
class Hotel {
    private final String name;
    private final double costPerNight;
    private final double distance;
    private final Map<String, Double> foodOptions;

    public Hotel(String name, double costPerNight, double distance, Map<String, Double> foodOptions) {
        this.name = name;
        this.costPerNight = costPerNight;
        this.distance = distance;
        this.foodOptions = foodOptions;
    }

    public String getName() {
        return name;
    }

    public double getCostPerNight() {
        return costPerNight;
    }

    public double getDistance() {
        return distance;
    }

    public Map<String, Double> getFoodOptions() {
        return foodOptions;
    }
}

// Represents user data, including credentials, visited destinations, and booked tickets
class UserData {
    private Map<String, String> userCredentials;
    private Set<String> destinationsVisited;
    private Map<String, String> bookedTickets;

    public UserData() {
        userCredentials = new HashMap<>();
        destinationsVisited = new HashSet<>();
        bookedTickets = new HashMap<>();
        initializeData();
    }

    private void initializeData() {
        userCredentials.put("user1", "pass123");
        userCredentials.put("user2", "pass456");
        userCredentials.put("user3", "pass789");

        destinationsVisited.add("gujarat");
        destinationsVisited.add("rajasthan");
        destinationsVisited.add("jammu and kashmir");
        destinationsVisited.add("goa");
        destinationsVisited.add("kerala");
        destinationsVisited.add("banglore");
    }

    public Map<String, String> getUserCredentials() {
        return userCredentials;
    }

    public Set<String> getDestinationsVisited(String username) {
        return destinationsVisited;
    }

    public void recordDestination(String destination) {
        destinationsVisited.add(destination);
    }

    public void bookTicket(String username, String ticketDetails) {
        bookedTickets.put(username, ticketDetails);
    }

    public String getTicketDetails(String username) {
        return bookedTickets.get(username);
    }
}

// Manages travel-related operations, including user login, trip planning, hotel booking, and ticket display
class TravelManagementSystem {
    private UserData userData;
    private Map<String, Hotel> hotelDetails;
    Scanner scanner=new Scanner(System.in);
    public TravelManagementSystem() {
        userData = new UserData();
        hotelDetails = initializeHotelData();
    }

    private Map<String, Hotel> initializeHotelData() {
        Map<String, Hotel> hotels = new HashMap<>();
        Map<String, Double> foodOptionsA = new HashMap<>();
        foodOptionsA.put("Breakfast", 500.0);
        foodOptionsA.put("Lunch", 750.0);
        foodOptionsA.put("Dinner", 1000.0);
        hotels.put("Hotel A", new Hotel("Hotel A", 2000.0, 5.0, foodOptionsA));

        Map<String, Double> foodOptionsB = new HashMap<>();
        foodOptionsB.put("Breakfast", 500.0);
        foodOptionsB.put("Lunch", 750.0);
        foodOptionsB.put("Dinner", 850.0);
        hotels.put("Hotel B", new Hotel("Hotel B", 1500.0, 3.0, foodOptionsB));

        Map<String, Double> foodOptionsC = new HashMap<>();
         foodOptionsB.put("Breakfast", 200.0);
        foodOptionsC.put("Lunch", 460.0);
        foodOptionsC.put("Dinner", 1200.0);
        hotels.put("Hotel C", new Hotel("Hotel C", 3000.0, 8.0, foodOptionsC));

        return hotels;
    }

     // Calculates the cost of a trip based on destination, distance, and transportation mode
    private double calculateTripCost(String destination, double distance, String transportationMode) {
        double baseCost = 500.0;
        double distanceFactor = 0.1;
        double adjustedCost = baseCost + distance * distanceFactor;

        if ("flight".equalsIgnoreCase(transportationMode)) {
            adjustedCost += 200.0;
        } else if ("train".equalsIgnoreCase(transportationMode)) {
            adjustedCost += 100.0;
        }
        else if ("bus".equalsIgnoreCase(transportationMode)) {
            adjustedCost += 70.0;
        } else {
            System.out.println("Unknown transportation mode. Default adjustment applied.");
            adjustedCost += 50.0;
        }

        return adjustedCost;
    }

    
    // Handles user login and initiates the travel planning process
    public boolean loginPage(String username, String password) {
        boolean flag=true;
        do{
        if (userData.getUserCredentials().containsKey(username) && userData.getUserCredentials().get(username).equals(password)) {
            flag=false;
            System.out.println("Login successful!");

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter your current location: ");
            String currentLocation = scanner.nextLine();
            System.out.println("\n\nPLACES:\n1.Gujarat\n2.Rajasthan\n3.Jammu and Kashmir\n4.Kerala\n5.Banglore\n6.Goa");
            System.out.print("Enter your destination location: ");
            String destination = scanner.nextLine().toLowerCase();

            double distance = 0.0;
            try {
                System.out.print("Enter the distance to your destination (in km): ");
                distance = Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid distance input. Using default value.");
            }

            System.out.print("Enter the transportation mode (flight/train/bus/other): ");
            String transportationMode = scanner.nextLine().toLowerCase();

            double tripCost = calculateTripCost(destination, distance, transportationMode);
            System.out.println("Estimated trip cost: $" + tripCost);

            displayAvailableHotels(tripCost);

            userData.recordDestination(destination);

            System.out.print("Do you want to book a hotel? (yes/no): ");
            String bookHotelChoice = scanner.nextLine().toLowerCase();
            if ("yes".equals(bookHotelChoice)) {
                bookHotel(username, destination);
                return flag;
            }
        } else {
             flag =true;
            System.out.println("Login failed. Please check your credentials."); 
            return flag;
        }
    }while(flag);

    return flag;
}

    // Displays available hotels within budget
    private void displayAvailableHotels(double tripCost) {
        System.out.println("Available Hotels within Budget:");
        System.out.println("Name : Hotel A , Cost per Night : 2000 , Distance : 5 km");
        System.out.println("Name : Hotel B , Cost per Night : 1500 , Distance : 3 km");
        System.out.println("Name : Hotel C , Cost per Night : 3000 , Distance : 8 km");
    }

     // Books a hotel based on user input and calculates the total cost
    void bookHotel(String username, String destination) {
        System.out.print("Enter the name of the hotel you want to book: ");
        String selectedHotelName = scanner.nextLine();

        if (hotelDetails.containsKey(selectedHotelName)) {
            Hotel selectedHotel = hotelDetails.get(selectedHotelName);

            boolean includeFood = new FoodOption(scanner).includeFood();

            double hotelCost = selectedHotel.getCostPerNight();

            // Ask user for the number of days they want to stay
            int numberOfDays = getNumberOfDays();

            // Calculate total hotel cost
            double totalHotelCost = hotelCost * numberOfDays;

            // Calculate food cost
            double foodCost;
            if (includeFood) {
                foodCost = calculateFoodCost(selectedHotel, numberOfDays);
            } else {
                foodCost = 0.0;
            }

            // Calculate transportation cost
            double transportationCost = calculateTripCost(destination, selectedHotel.getDistance(), "flight");

            // Calculate the total cost
            double totalCost = totalHotelCost + foodCost + transportationCost;

            StringBuilder ticketDetailsBuilder = new StringBuilder();
            ticketDetailsBuilder.append("Destination: ").append(destination)
                    .append("\nHotel: ").append(selectedHotel.getName())
                    .append("\nCost per night: $").append(hotelCost)
                    .append("\nNumber of days: ").append(numberOfDays)
                    .append("\nTotal hotel cost: $").append(totalHotelCost)
                    .append("\nDistance: ").append(selectedHotel.getDistance()).append(" km");

            if (includeFood) {
                new FoodOption(scanner).includeFood(selectedHotel, ticketDetailsBuilder, numberOfDays);
            }

            PaymentMethod.processPayment(totalCost);

            // Append total estimated trip cost to the ticket details
            ticketDetailsBuilder.append("\nTotal estimated trip cost: $").append(totalCost);
            System.out.println("\n\nTotal cost is : Total estimated trip cost + Total food cost");

            String ticketDetails = ticketDetailsBuilder.toString();
            userData.bookTicket(username, ticketDetails);
            System.out.println("Booking successful!");

            System.out.println("Your Booking Details:");
            System.out.println(ticketDetails);
        } else {
            System.out.println("Invalid hotel selection. Please choose a valid hotel.");
        }
    }

    // Obtains the number of days the user plans to stay
    int getNumberOfDays() {
        int numberOfDays = 1; // Default value
        try {
            System.out.print("Enter the number of days you want to stay: ");
            numberOfDays = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Using default value of 1 day.");
        }
        return numberOfDays;
    }

    // Calculates the food cost based on selected hotel and number of days
    private double calculateFoodCost(Hotel selectedHotel ,int numberOfDays) {
        double foodCost = 0.0;
        Map<String, Double> foodOptions = selectedHotel.getFoodOptions();

        // Assuming 1 breakfast, 1 lunch, and 1 dinner per day
        for (Map.Entry<String, Double> entry : foodOptions.entrySet()) {
            int quantity = 1; // Fixed quantity for each meal
            foodCost += quantity * entry.getValue();
        }

        return foodCost;
    }

    // Displays booked ticket details for a specific user
    public void displayBookedTicketDetails(String username) {
        String ticketDetails = userData.getTicketDetails(username);
        if (ticketDetails != null) {
            System.out.println("Your Booking Details:");
            System.out.println(ticketDetails);
        } else {
            System.out.println("No booked ticket found for user " + username);
        }
    }

    // Displays all booked ticket details for all users
    public void displayAllTicketDetails() {
        System.out.println("Do you want to book a ticket? (yes/no): ");
        String booking=scanner.nextLine();
        if(booking.equalsIgnoreCase("yes"))
        {
        System.out.println("\n\nAll Ticket Details:");
        System.out.println("\n------------------------------------");
        for (Map.Entry<String, String> entry : userData.getUserCredentials().entrySet()) {
            String username = entry.getKey();
            String ticketDetails = userData.getTicketDetails(username);
            if (ticketDetails != null) {
                System.out.println("Username: " + username);
                System.out.println(ticketDetails);
                System.out.println("------------------------------------");

                }
            }
        }
        else if(booking.equalsIgnoreCase("no")){
            System.out.println("Thank you!");
         }
        else
        {
            System.out.println("Invalid input.");
        }
    }

    // Main method to execute the travel management system
    public static void main(String[] args) {
        TravelManagementSystem tms = new TravelManagementSystem();

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();
        boolean r;
        r= tms.loginPage(username, password);
        if(r==false){

        System.out.print("Do you want to display your booked ticket details? (yes/no): ");
        String displayTicketDetailsChoice = scanner.nextLine().toLowerCase();
        if ("yes".equals(displayTicketDetailsChoice)) {
            tms.displayBookedTicketDetails(username);
        }

        tms.displayAllTicketDetails();
    }
    else{
        System.out.println("\nEnter valid username and password: ");
         System.out.print("Enter your username: ");
        String username1 = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password1 = scanner.nextLine();
        tms.loginPage(username1, password1);
    }
        scanner.close();
    }
}

// Handles payment processing
class PaymentMethod {

    // Processes payment for the total cost
    public static void processPayment(double totalCost) {

        System.out.println("Payment processed successfully. Total cost: $" + totalCost);

    }
}

// Handles food-related options for hotel booking
class FoodOption {
     Scanner scanner;

     public FoodOption(Scanner scanner) {

        this.scanner = scanner;

    }

     // Asks the user if they want to include food in their hotel booking
    public boolean includeFood() {

        System.out.print("Do you want to include food in your hotel booking? (yes/no): ");
        String includeFoodChoice = scanner.nextLine().toLowerCase();
        return "yes".equals(includeFoodChoice);

    }

    // Includes food details in the ticket details based on user input
   public void includeFood(Hotel selectedHotel, StringBuilder ticketDetailsBuilder, int numberOfDays) {
        Map<String, Double> foodOptions = selectedHotel.getFoodOptions();
        double totalFoodCost = 0.0;

        // Assuming 1 breakfast, 1 lunch, and 1 dinner per day
        for (Map.Entry<String, Double> entry : foodOptions.entrySet()) {
            int quantity = 1; // Fixed quantity for each meal
            totalFoodCost += quantity * entry.getValue();
        }

        // Multiply the total food cost by the number of days
        totalFoodCost *= numberOfDays;

        // Append food cost details to the ticket details
        ticketDetailsBuilder.append("\nTotal food cost: $").append(totalFoodCost);
    }
}
