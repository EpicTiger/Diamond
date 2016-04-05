package Util;

import android.app.Activity;
import android.content.Intent;

import Entities.Order;
import Entities.People;
import Entities.Product;

public final class WhatsappShare
{
    private static String getOrderInText(Order order)
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.valueOf(UtilHelper.DateFormat.format(order.getCalender().getTime())) + System.getProperty("line.separator"));
        stringBuilder.append("---------------------------" + System.getProperty("line.separator"));
        stringBuilder.append(System.getProperty("line.separator"));
        for (People people : order.getPeoples())
        {
            if (people.getSubTotal() != 0.0)
            {
                stringBuilder.append(people.getName() + " - €" + people.getSubTotal() + System.getProperty("line.separator"));

                for (Product product : people.getProducts())
                {
                    if (product.getQuantity() != 0)
                    {
                        stringBuilder.append("-   " + product.getName() + System.getProperty("line.separator"));
                        stringBuilder.append("    " + product.getQuantity() + "x - €" + product.getPriceString() + System.getProperty("line.separator"));
                        stringBuilder.append(System.getProperty("line.separator"));
                    }
                }
                stringBuilder.append("---------------------------" + System.getProperty("line.separator"));
                stringBuilder.append(System.getProperty("line.separator"));
            }
        }

        return stringBuilder.toString();
    }

    public static void shareToWhatsapp(Order order, Activity activity)
    {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, getOrderInText(order));
        sendIntent.setType("text/plain");
        sendIntent.setPackage("com.whatsapp");
        activity.startActivity(sendIntent);
    }
}
