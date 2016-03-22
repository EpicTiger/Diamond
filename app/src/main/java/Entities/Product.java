package Entities;

import java.io.Serializable;
import java.text.DecimalFormat;

public class Product implements Serializable
{
    public Product(String name, double price, double quantity, int position)
    {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.position = position;
    }

    private String name;
    private double price;
    private double quantity;
    private int position;
    private int peopleQuantity;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public double getPrice()
    {
        return price;
    }

    public String getPriceString()
    {
        DecimalFormat f = new DecimalFormat("0.00");
        return f.format(price);
    }

    public void setPrice(double price)
    {
        this.price = price;
    }

    public double getQuantity()
    {
        return quantity;
    }

    public String getQuantitystring()
    {
        DecimalFormat f = new DecimalFormat("0.00");
        return f.format(quantity);
    }

    public void setQuantity(double quantity)
    {
        this.quantity = quantity;
    }

    public int getPosition()
    {
        return position;
    }

    public void setPosition(int position)
    {
        this.position = position;
    }

    public int getPeopleQuantity()
    {
        return peopleQuantity;
    }

    public void setPeopleQuantity(int peopleQuantity)
    {
        this.peopleQuantity = peopleQuantity;
    }
}