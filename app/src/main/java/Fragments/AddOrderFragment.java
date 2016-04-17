package Fragments;

import android.app.DatePickerDialog;
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
import Util.UtilHelper;
import Util.WhatsappShare;
import butterknife.Bind;
import diamond.schmitt.com.diamond.MainActivity;
import diamond.schmitt.com.diamond.R;

public class AddOrderFragment extends BaseFragment
{
    @Bind(R.id.fragment_add_product_floatingactionbutton_add)
    FloatingActionButton floatingActionButton_Add;
    @Bind(R.id.fragment_add_product_floatingactionbutton_done)
    FloatingActionButton floatingActionButton_Done;
    @Bind(R.id.fragment_add_product_textview_date)
    TextView textView_Date;
    @Bind(R.id.fragment_add_order_textview_total_price)
    TextView textView_TotalPrice;
    @Bind(R.id.fragment_add_order_view_total_price)
    View view_TotalPrice;
    @Bind(R.id.fragment_add_order_button_date)
    ImageView imageView_DateButton;
    @Bind(R.id.fragment_add_order_listview)
    ListView listView;

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
                order.setPosition(UtilHelper.orders.indexOf(order));
                ((MainActivity) getActivity()).saveOrders(UtilHelper.orders);
                ((MainActivity) getActivity()).addPeopleToOrderFragment(order);

            }
        });

        floatingActionButton_Done.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ((MainActivity) getActivity()).saveOrders(UtilHelper.orders);
                getActivity().getFragmentManager().popBackStack();
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

                    for (PeopleProduct peopleProduct : peopleProducts)
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
        view_TotalPrice.setVisibility(View.GONE);
        textView_TotalPrice.setVisibility(View.GONE);

        return view;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (order != null)
        {
            textView_Date.setText(UtilHelper.DateFormat.format(order.getCalender().getTime()));
            fillList();
        } else
        {
            createNewOrder();
        }
    }

    private void createNewOrder()
    {
        Calendar calendar = UtilHelper.DateToCalendar(Calendar.getInstance().getTime());
        order = new Order(calendar, null);
        textView_Date.setText(String.valueOf(UtilHelper.DateFormat.format(order.getCalender().getTime())));
        UtilHelper.orders.add(order);
        ((MainActivity) getActivity()).saveOrders(UtilHelper.orders);
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
                textView_Date.setText(UtilHelper.DateFormat.format(newDate.getTime()));
                if (order != null)
                    UtilHelper.orders.get(order.getPosition()).setCalender(newDate);
            }

        }, year, month, day);

        datePickerDialog.show();
    }


    private void getTotalPrice()
    {
        double totalPrice = Util.PriceCalculator.getTotalPrice(order);
        if (totalPrice != 0.0)
        {
            textView_TotalPrice.setText(UtilHelper.getPriceString(totalPrice));
            view_TotalPrice.setVisibility(View.VISIBLE);
            textView_TotalPrice.setVisibility(View.VISIBLE);
        }
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


    private void setAndFillListAdapter(List<PeopleProduct> list)
    {
        if (list != null)
        {
            PeopleProductAdapter adapter = new PeopleProductAdapter(getActivity(), R.id.fragment_add_order_listview, list);
            listView.setAdapter(adapter);
            getTotalPrice();
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
                WhatsappShare.shareToWhatsapp(order, getActivity());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
