package Util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import Entities.Order;

public final class UtilHelper
{
    public static SimpleDateFormat DateFormat = new SimpleDateFormat("EEEE - dd MMMM yyyy");

    public static List<Order> orders;

    public static Calendar DateToCalendar(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    public static String getPriceString(double value)
    {
        DecimalFormat f = new DecimalFormat("0.00");
        return "€" + f.format(value);
    }
}
