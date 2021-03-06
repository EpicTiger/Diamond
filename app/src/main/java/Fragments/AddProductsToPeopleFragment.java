package Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import Adapters.ProductsToPeopleAdapter;
import Entities.People;
import Entities.PeopleProduct;
import Entities.Product;
import Util.UtilHelper;
import diamond.schmitt.com.diamond.MainActivity;
import diamond.schmitt.com.diamond.R;

public class AddProductsToPeopleFragment extends BaseListFragment
{
    private List<PeopleProduct> peoples;
    private People people;
    private List<Product> products;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState, R.layout.fragment_list, "Add Products");
        floatingActionButton.setImageDrawable(ContextCompat.getDrawable(getActivity(), (R.drawable.ic_done_white_24dp)));

        floatingActionButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                UtilHelper.orders.get(people.getOrderPosition()).getPeoples().get(getPeoplePosition()).setProducts(products);
                ((MainActivity) getActivity()).saveOrders(UtilHelper.orders);
                getActivity().onBackPressed();
            }
        });

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        people = (People) bundle.getSerializable("People");
    }

    @Override
    protected void fillList()
    {
        products = ((MainActivity) getActivity()).retrieveProducts();
        int position = getPeoplePosition();

        List<Product> peopleProducts = UtilHelper.orders.get(people.getOrderPosition()).getPeoples().get(position).getProducts();

        if (products != null && !products.isEmpty())
        {
            for (Product product : products)
            {
                product.setPeopleQuantity(people.getPeopleQuantity());
            }
            if (peopleProducts != null)
            {
                for (Product peopleProduct : peopleProducts)
                {
                    for (Product product : products)
                    {
                        if (peopleProduct.getName().equals(product.getName()))
                        {
                            product.setQuantity(peopleProduct.getQuantity());
                        }
                    }
                }
            }

            setAndFillListAdapter(products);
        }
    }

    private int getPeoplePosition()
    {
        List<People> peopleList = ((MainActivity) getActivity()).retrievePeoples();
        int position = 0;

        for (int i = 0; i < peopleList.size(); i++)
        {
            if (peopleList.get(i).getName().equals(people.getName()))
                position = i;
        }
        return position;
    }

    private void setAndFillListAdapter(List<Product> list)
    {
        if (list != null)
        {
            ProductsToPeopleAdapter adapter = new ProductsToPeopleAdapter(getActivity(), R.layout.fragment_list, list);
            listView.setAdapter(adapter);
        }
    }
}
