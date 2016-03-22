package Entities;

import java.text.DecimalFormat;

public class PeopleProduct
{
    public PeopleProduct(boolean isPeople, String name, double price, double quantity, boolean isSelected, People people, Product product)
    {
        this.isPeople = isPeople;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.isSelected = isSelected;
        this.people = people;
        this.product = product;
    }

    private boolean isPeople;
    private String name;
    private double price;
    private double quantity;
    private boolean isSelected;
   private People people;
    private Product product;

    public String getPriceString()
    {
        DecimalFormat f = new DecimalFormat("0.00");
        return "€" + f.format(price);
    }

    public String getPriceTimesQuantityString()
    {
        DecimalFormat f = new DecimalFormat("0.00");
        return "€" + f.format(price * quantity);
    }

    public boolean isPeople()
    {
        return isPeople;
    }

    public void setIsPeople(boolean isPeople)
    {
        this.isPeople = isPeople;
    }

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

    public People getPeople()
    {
        return people;
    }

    public void setPeople(People people)
    {
        this.people = people;
    }

    public Product getProduct()
    {
        return product;
    }

    public void setProduct(Product product)
    {
        this.product = product;
    }

    public boolean isSelected()
    {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected)
    {
        this.isSelected = isSelected;
    }
}
