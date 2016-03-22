package diamond.schmitt.com.diamond;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import Adapters.ProductAdapter;
import Entities.Product;
import butterknife.Bind;

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
                products.remove(info.position);
                ((MainActivity) getActivity()).saveProducts(products);
                fillList();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}
