package uk.ac.ed.inf;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * Customer orders from participating restaurants.
 */
public class Order {
    @JsonProperty("orderNo")
    public String orderNo;

    @JsonProperty("orderDate")
    public String orderDate;

    @JsonProperty("customer")
    public String customer;



    @JsonProperty("creditCardNumber")
    public String creditCardNumber;

    @JsonProperty("creditCardExpiry")
    public String creditCardExpiry;

    @JsonProperty("cvv")
    public String cvv;

    @JsonProperty("priceTotalInPence")
    public int priceTotalInPence;

    @JsonProperty("orderItems")
    public String[] orderItems;

    public OrderOutcome outcome;

    public Order(String orderNo, String orderDate, String customer, String creditCardNumber, String creditCardExpiry, String cvv, int priceTotalInPence, String[] orderItems, OrderOutcome outcome) {
        this.orderNo = orderNo;
        this.orderDate = orderDate;
        this.customer = customer;
        this.creditCardNumber = creditCardNumber;
        this.creditCardExpiry = creditCardExpiry;
        this.cvv = cvv;
        this.priceTotalInPence = priceTotalInPence;
        this.orderItems = orderItems;
        this.outcome = outcome;

        System.out.println(customer);
    }
    public Order(){
        System.out.println(customer);
    }
    /**
     *
     * @param participants restaurants in the program
     * @param orderItems customer's orders
     * @return total cost of an order in pence including delivery cost
     * @throws Exception IncorrectPizzaCombinationException if not every item is from the same restaurant
     */
    public static int getDeliveryCost(Restaurant[] participants, String[] orderItems) throws Exception {
        String chosenRestaurant="";

        assert(orderItems.length>0 && orderItems.length<5);

        int total=100;
        for (String i : orderItems){
            for (Restaurant r : participants){
                for (Menu item : r.getMenu()){
                    if (Objects.equals(item.name, i)){
                        if (Objects.equals(chosenRestaurant, "")){
                            chosenRestaurant = r.name;
                        }else if(!Objects.equals(r.name, chosenRestaurant)){
                            throw(new Exception("IncorrectPizzaCombinationException"));
                        }
                        total+=item.priceInPence;

                    }
                }
            }
        }

        return total;
    }
}
