import bg.sofia.uni.fmi.mjt.airbnb.Airbnb;
import bg.sofia.uni.fmi.mjt.airbnb.accommodation.Bookable;
import bg.sofia.uni.fmi.mjt.airbnb.accommodation.Hotel;
import bg.sofia.uni.fmi.mjt.airbnb.accommodation.location.Location;

public class Main {
    public static void main(String[] args) {
        Location testLoc = new Location(1.0,1.0);
        Hotel testHotel1 = new Hotel(testLoc, 150.0);
        Hotel testHotel2 = new Hotel(testLoc, 200.0);
        Hotel testHotel3 = new Hotel(testLoc, 200.0);
        Hotel testHotel4 = new Hotel(testLoc, 200.0);
        Hotel testHotel5 = new Hotel(testLoc, 200.0);
        Hotel testHotel6 = new Hotel(testLoc, 200.0);
        Hotel testHotel7 = new Hotel(testLoc, 200.0);
        Hotel testHotel8 = new Hotel(testLoc, 200.0);
        Hotel testHotel9 = new Hotel(testLoc, 200.0);
        Hotel testHotel10 = new Hotel(testLoc, 200.0);
        Hotel testHotel11 = new Hotel(testLoc, 250.0);

        Bookable[] testArray = {testHotel1, testHotel6, testHotel11};
        Airbnb testAirbnb = new Airbnb(testArray);
        Bookable sample1 = testAirbnb.findAccommodationById("HOT-0");
        System.out.println(sample1.getPricePerNight());
        Bookable sample2 = testAirbnb.findAccommodationById("HOT-5");
        System.out.println(sample2.getPricePerNight());
        Bookable sample3 = testAirbnb.findAccommodationById("HOT-10");
        System.out.println(sample3.getPricePerNight());
//        System.out.println(testHotel.book(
//                LocalDateTime.of(2022, 12,2,12,45),
//                LocalDateTime.of(2022, 12,2,12,45)));
//        System.out.println(testHotel.isBooked());
//        Bookable[] testArray = {testHotel};
//        Airbnb testAirbnb = new Airbnb(testArray);
//        System.out.println(testAirbnb.countBookings());
    }
}