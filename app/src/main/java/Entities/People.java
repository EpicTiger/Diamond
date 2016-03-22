package Entities;

import java.io.Serializable;
import java.util.List;

public class People implements Serializable
{
    public People(String name, List<Product> products, int position)
    {
        this.name = name;
        this.products = products;
        this.position = position;

    }

    private String name;
    private boolean isSelected;
    private List<Product> products;
    private int position;
    private double subTotal;
    private int orderPosition;
    private int peopleQuantity;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public boolean isSelected()
    {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected)
    {
        this.isSelected = isSelected;
    }

    public List<Product> getProducts()
    {
        return products;
    }

    public void setProducts(List<Product> products)
    {
        this.products = products;
    }

    public int getPosition()
    {
        return position;
    }

    public void setPosition(int position)
    {
        this.position = position;
    }

    public double getSubTotal()
    {
        return subTotal;
    }

    public void setSubTotal(double subTotal)
    {
        this.subTotal = subTotal;
    }

    public int getOrderPosition()
    {
        return orderPosition;
    }

    public void setOrderPosition(int orderPosition)
    {
        this.orderPosition = orderPosition;
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
