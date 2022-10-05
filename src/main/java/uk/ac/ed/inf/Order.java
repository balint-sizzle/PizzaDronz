package uk.ac.ed.inf;

import java.util.Arrays;
import java.util.Objects;

public class Order {
    public static int getDeliveryCost(Restaurant[] participants, String[] orderItems) throws Exception {
        String chosenRestaurant="";
        int numberOfDeliveries = (int) Math.ceil(((double) orderItems.length)/4);
        int total= numberOfDeliveries*100;
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
