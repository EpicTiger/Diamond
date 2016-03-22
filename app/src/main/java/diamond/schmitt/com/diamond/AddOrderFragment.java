package diamond.schmitt.com.diamond;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import Adapters.PeopleProductAdapter;
import Entities.Order;
import Entities.People;
import Entities.PeopleProduct;
import Entities.Product;
import Util.Util;
import butterknife.Bind;

public class AddOrderFragment extends BaseFragment
{
    @Bind(R.id.fragment_add_product_floatingactionbutton_add)
    FloatingActionButton floatingActionButton_Add;
    @Bind(R.id.fragment_add_product_floatingactionbutton_done)
    FloatingActionButton floatingActionButton_Done;
    @Bind(R.id.fragment_add_product_textview_date)
    TextView textView_Date;
    @Bind(R.id.fragment_add_order_button_date)
    ImageView imageView_DateButton;
    @Bind(R.id.fragment_add_order_listview)
    ListView listView;

    private Calendar calendar;
    private Order order;
    private ArrayList<PeopleProduct> peopleProducts;

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState, R.layout.fragment_add_order, "Add Order");
        setHasOptionsMenu(true);
        floatingActionButton_Add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (order == null)
                {
                    order = new Order(calendar, null);
                    Util.orders.add(order);
                    order.setPosition(Util.orders.indexOf(order));
                }

                if (order.getCalender() == null)
                    showSnackbarLong("Please select a date");
                else
                {
                    ((MainActivity) getActivity()).saveOrders(Util.orders);
                    ((MainActivity) getActivity()).addPeopleToOrderFragment(order);
                }
            }
        });

        floatingActionButton_Done.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (order == null)
                {
                    order = new Order(calendar, null);
                    Util.orders.add(order);
                    order.setPosition(Util.orders.indexOf(order));
                }

                if (order.getCalender() == null)
                    showSnackbarLong("Please select a date");
                else
                {
                    ((MainActivity) getActivity()).saveOrders(Util.orders);
                    getActivity().getFragmentManager().popBackStack();
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                if (peopleProducts.get(position).isPeople())
                {
                    People people = peopleProducts.get(position).getPeople();
                    people.setOrderPosition(order.getPosition());
                    int peopleCount = 0;

                    for (PeopleProduct peopleProduct: peopleProducts                         )
                    {
                        if (peopleProduct.isPeople())
                            peopleCount++;
                    }

                    people.setPeopleQuantity(peopleCount);

                    ((MainActivity) getActivity()).addProductsToPeopleFragment(people);
                }
            }
        });

        imageView_DateButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                setDateTimeField();
            }
        });

        return view;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (order != null)
            textView_Date.setText(Util.DateFormat.format(order.getCalender().getTime()));
        fillList();
    }

    private void setDateTimeField()
    {
        Calendar newCalendar = Calendar.getInstance();
        int year = newCalendar.get(Calendar.YEAR);
        int month = newCalendar.get(Calendar.MONTH);
        int day = newCalendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener()
        {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                calendar = newDate;
                textView_Date.setText(Util.DateFormat.format(newDate.getTime()));
                if (order != null)
                    Util.orders.get(order.getPosition()).setCalender(newDate);
            }

        }, year, month, day);

        datePickerDialog.show();
    }

    private void fillList()
    {
        List<People> peoples;
        peopleProducts = new ArrayList<>();

        if (order != null)
        {
            peoples = order.getPeoples();
            if (peoples != null)
            {
                for (People people : peoples)
                {
                    List<Product> products = people.getProducts();

                    double subTotal = 0.0;

                    if (products != null)
                    {
                        for (Product product : products)
                            subTotal = subTotal + product.getPrice() * product.getQuantity();
                    }

                    people.setSubTotal(subTotal);
                    if (people.isSelected())
                    {
                        peopleProducts.add(new PeopleProduct(true, people.getName(), people.getSubTotal(), 0, people.isSelected(), people, null));

                        if (products != null)
                        {
                            for (Product product : products)
                                if (product.getQuantity() != 0)
                                    peopleProducts.add(new PeopleProduct(false, product.getName(), product.getPrice(), product.getQuantity(), false, null, product));
                        }
                    }
                }
            }
            setAndFillListAdapter(peopleProducts);
        }
    }

    private String getOrderInText()
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.valueOf(Util.DateFormat.format(order.getCalender().getTime())) + System.getProperty("line.separator"));
        stringBuilder.append("---------------------------" + System.getProperty("line.separator"));
        stringBuilder.append(System.getProperty("line.separator"));
        for (People people : order.getPeoples())
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

        return stringBuilder.toString();
    }

    private void setAndFillListAdapter(List<PeopleProduct> list)
    {
        if (list != null)
        {
            PeopleProductAdapter adapter = new PeopleProductAdapter(getActivity(), R.id.fragment_add_order_listview, list);
            listView.setAdapter(adapter);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null)
            order = (Order) bundle.getSerializable("Order");
    }

    private void shareToWhatsapp()
    {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, getOrderInText());
        sendIntent.setType("text/plain");
        sendIntent.setPackage("com.whatsapp");
        startActivity(sendIntent);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.share_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menu_item_share:
                shareToWhatsapp();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
