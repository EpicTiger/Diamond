package Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

import Adapters.OrderAdapter;
import Entities.Order;
import Util.UtilHelper;
import diamond.schmitt.com.diamond.MainActivity;
import diamond.schmitt.com.diamond.R;

public class OrderFragment extends BaseListFragment
{
    private List<Order> orders;

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState, R.layout.fragment_list, "Orders");
        floatingActionButton.setVisibility(View.GONE);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Order order = UtilHelper.orders.get(position);
                order.setPosition(position);
                ((MainActivity) getActivity()).addOrderFragment(order);
            }
        });

        return view;
    }

    @Override
    protected void fillList()
    {
        orders = ((MainActivity) getActivity()).retrieveOrders();

        if (!(orders != null && orders.isEmpty()))
            setAndFillListAdapter(orders);
        else
            setAndFillListAdapter(new ArrayList<Order>());
    }

    private void setAndFillListAdapter(List<Order> list)
    {
        if (list != null)
        {
            OrderAdapter adapter = new OrderAdapter(getActivity(), R.id.fragment_list_listview, list);
            listView.setAdapter(adapter);
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        fillList();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId())
        {
            case R.id.delete:
                UtilHelper.orders.remove(info.position);
                ((MainActivity) getActivity()).saveOrders(UtilHelper.orders);
                fillList();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        if (v.getId() == R.id.fragment_list_listview)
        {
            MenuInflater inflater = getActivity().getMenuInflater();
            inflater.inflate(R.menu.menu_list_order, menu);
        }
    }
}
