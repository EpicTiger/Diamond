package Util;

import java.util.List;

import Entities.Order;
import Entities.People;

public final class PriceCalculator
{
    public static String getTotalPriceString(Order order)
    {
        List<People> peoples;
        double totalPrice = 0.0;

        if (order != null)
        {
            peoples = order.getPeoples();
            if (peoples != null)
            {
                for (People people : peoples)
                {
                    totalPrice += people.getSubTotal();
                }
            }
        }

        return UtilHelper.getPriceString(totalPrice);
    }

    public static double getTotalPrice(Order order)
    {
        List<People> peoples;
        double totalPrice = 0.0;

        if (order != null)
        {
            peoples = order.getPeoples();
            if (peoples != null)
            {
                for (People people : peoples)
                {
                    totalPrice += people.getSubTotal();
                }
            }
        }

        return totalPrice;
    }
}
