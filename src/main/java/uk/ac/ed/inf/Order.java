package uk.ac.ed.inf;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
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
    private DateFormat formatter = new SimpleDateFormat("MM/yy");
    private String restaurant;

    public Order(String orderNo, String orderDate, String customer, String creditCardNumber, String creditCardExpiry, String cvv, int priceTotalInPence, String[] orderItems, OrderOutcome outcome) throws ParseException {

        this.orderNo = orderNo;
        this.orderDate = orderDate;
        this.customer = customer;
        this.creditCardNumber = creditCardNumber;
        this.creditCardExpiry = creditCardExpiry;
        this.cvv = cvv;
        this.priceTotalInPence = priceTotalInPence;
        this.orderItems = orderItems;
        this.outcome = outcome;

    }

    public Order() {
    }

    public void validateOrder() throws ParseException {
        Date date = formatter.parse(this.creditCardExpiry);
        Date today = new Date();
        if (today.compareTo(date) > 0) {
            this.outcome = OrderOutcome.InvalidExpiryDate;
        }
        if (this.creditCardNumber.length() != 16) {
            this.outcome = OrderOutcome.InvalidCardNumber;
        }
        if (this.cvv.length() != 3){
            this.outcome = OrderOutcome.InvalidCvv;
        }
        if (this.orderItems.length > 4 || this.orderItems.length < 1){
            this.outcome = OrderOutcome.InvalidPizzaCount;
        }


    }

    /**
     * @param participants restaurants in the program
     * @param orderItems   customer's orders
     * @return total cost of an order in pence including delivery cost
     * @throws Exception IncorrectPizzaCombinationException if not every item is from the same restaurant
     */

    public int getDeliveryCost(Restaurant[] participants, String[] orderItems) throws Exception {
        String chosenRestaurant = "";


        int total = 100;
        for (String i : orderItems) {
            // check if pizza ordered exists on a menu
            boolean pizzaDefined = false;
            for (Restaurant r : participants) {
                for (Menu item : r.getMenu()) {
                    if (Objects.equals(item.name, i)) {
                        pizzaDefined = true;
                        if (Objects.equals(chosenRestaurant, "")) {
                            chosenRestaurant = r.name;
                        } else if (!Objects.equals(r.name, chosenRestaurant)) {
                            this.outcome = OrderOutcome.InvalidPizzaCombinationMultipleSuppliers;
                            throw (new IncorrectPizzaCombinationException("all pizzas in order must come from one restaurant"));
                        }
                        total += item.priceInPence;
                    }
                }
            }
            if (!pizzaDefined){
                this.outcome = OrderOutcome.InvalidPizzaNotDefined;
            }
        }

        return total;
    }
}
