package Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

import Adapters.ProductAdapter;
import Entities.Order;
import Entities.People;
import Entities.Product;
import Util.UtilHelper;
import diamond.schmitt.com.diamond.MainActivity;
import diamond.schmitt.com.diamond.R;

public class ProductFragment extends BaseListFragment
{
    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState, R.layout.fragment_list, "Products");

        floatingActionButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ((MainActivity) getActivity()).addFragment(new AddProductFragment(), R.string.nav_fragment_add_products);
            }
        });

        return view;
    }

    @Override
    protected void fillList()
    {
        List<Product> products = ((MainActivity) getActivity()).retrieveProducts();

        if (!(products != null ? products.isEmpty() : false))
            setAndFillListAdapter(products);
        else
            setAndFillListAdapter(new ArrayList<Product>());
    }

    private void setAndFillListAdapter(List<Product> list)
    {
        if (list != null)
        {
            ProductAdapter adapter = new ProductAdapter(getActivity(), R.layout.fragment_list, list);
            listView.setAdapter(adapter);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        List<Product> products = ((MainActivity) getActivity()).retrieveProducts();
        switch (item.getItemId())
        {
            case R.id.edit:
                Product product = products.get(info.position);
                product.setPosition(info.position);
                ((MainActivity) getActivity()).addEditProductFragment(product);
                return true;
            case R.id.delete:
                deleteProducts(info, products);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void deleteProducts(AdapterView.AdapterContextMenuInfo info, List<Product> products)
    {
        String oldName = products.get(info.position).getName();
        products.remove(info.position);
        ((MainActivity) getActivity()).saveProducts(products);
        fillList();

        List<Order> orders = ((MainActivity) getActivity()).retrieveOrders();
        if (orders != null)
        {
            for (Order order : orders)
            {
                List<People> peoplesOrders = order.getPeoples();
                if (peoplesOrders != null)
                {
                    for (People people : peoplesOrders)
                    {
                        List<Product> productList = people.getProducts();
                        if (productList != null)
                        {
                            for (int i = 0; i < productList.size(); i++)
                            {
                                if (productList.get(i).getName().equals(oldName))
                                    productList.remove(i);
                            }
                        }
                    }
                }
            }
            UtilHelper.orders = orders;
            ((MainActivity) getActivity()).saveOrders(orders);
        }
    }
}
