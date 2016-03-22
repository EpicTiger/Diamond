package Entities;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

public class Order implements Serializable
{
    public Order(Calendar calendar, List<People> peoples)
    {
        this.calender = calendar;
        this.peoples = peoples;
    }

    private int position;
    private Calendar calender;
    private List<People> peoples;

    public Calendar getCalender()
    {
        return calender;
    }

    public void setCalender(Calendar calender)
    {
        this.calender = calender;
    }

    public List<People> getPeoples()
    {
        return peoples;
    }

    public void setPeoples(List<People> peoples)
    {
        this.peoples = peoples;
    }

    public int getPosition()
    {
        return position;
    }

    public void setPosition(int position)
    {
        this.position = position;
    }
}