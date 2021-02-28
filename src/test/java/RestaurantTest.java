import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    RestaurantService service = new RestaurantService();
    Restaurant restaurantMocked;
    Restaurant restaurant;

    @BeforeEach
    public void mockedRestaurant() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");

        restaurant = new Restaurant("Amelie's cafe", "Chennai", openingTime, closingTime);
        restaurantMocked= Mockito.mock(Restaurant.class);
        restaurantMocked.openingTime=restaurant.openingTime;
        restaurantMocked.closingTime=restaurant.closingTime;

    }
    //REFACTOR ALL THE REPEATED LINES OF CODE

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){

        LocalTime nowMocked = LocalTime.parse("10:31:00");

        Mockito.when(restaurantMocked.getCurrentTime()).thenReturn(nowMocked);

        Mockito.when(restaurantMocked.isRestaurantOpen()).thenCallRealMethod();

        Boolean b=restaurantMocked.isRestaurantOpen();

        assertTrue(b);
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){

        Mockito.when(restaurantMocked.isRestaurantOpen()).thenCallRealMethod();

        //To test with time that falls after closing time.
        LocalTime afterClosingTime = LocalTime.parse("22:30:00");
        Mockito.when(restaurantMocked.getCurrentTime()).thenReturn(afterClosingTime);
        Boolean b=restaurantMocked.isRestaurantOpen();
        assertFalse(b);

        //To test with time that falls before opening time.
        LocalTime beforeOpeningTime = LocalTime.parse("05:30:00");
        Mockito.when(restaurantMocked.getCurrentTime()).thenReturn(beforeOpeningTime);
        Boolean c=restaurantMocked.isRestaurantOpen();
        assertFalse(c);


    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }

    //Testcase failing because totalAmount() is not yet implemented.
    //total cost of gulabjamun and Icecream is 60.
    @Test
    public void totalAmount_should_be_60(){
        List<String> selectedItems = new ArrayList<String>();
        selectedItems.add("GulabJamun");
        selectedItems.add("Icecream");
        restaurant.addToMenu("Biryani",70);
        restaurant.addToMenu("Icecream",30);
        restaurant.addToMenu("GulabJamun",30);

        assertEquals(60,restaurant.totalAmount(selectedItems));


    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}