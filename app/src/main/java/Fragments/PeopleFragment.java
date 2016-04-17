package Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

import Adapters.PeopleAdapter;
import Entities.Order;
import Entities.People;
import Util.UtilHelper;
import diamond.schmitt.com.diamond.MainActivity;
import diamond.schmitt.com.diamond.R;

public class PeopleFragment extends BaseListFragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState, R.layout.fragment_list, "People");

        floatingActionButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ((MainActivity) getActivity()).addFragment(new AddPeopleFragment(), R.string.nav_fragment_add_people);
            }
        });

        return view;
    }

    @Override
    protected void fillList()
    {
        List<People> peoples = ((MainActivity) getActivity()).retrievePeoples();

        if (!(peoples != null ? peoples.isEmpty() : false))
            setAndFillListAdapter(peoples);
        else
            setAndFillListAdapter(new ArrayList<People>());
    }

    private void setAndFillListAdapter(List<People> list)
    {
        if (list != null)
        {
            PeopleAdapter adapter = new PeopleAdapter(getActivity(), R.layout.fragment_list, list);
            listView.setAdapter(adapter);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        List<People> peoples = ((MainActivity) getActivity()).retrievePeoples();
        switch (item.getItemId())
        {
            case R.id.edit:
                People people = peoples.get(info.position);
                people.setPosition(info.position);
                ((MainActivity) getActivity()).addEditPeopleFragment(people);
                return true;
            case R.id.delete:
                deletePeople(info, peoples);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }


    private void deletePeople(AdapterView.AdapterContextMenuInfo info, List<People> peoples)
    {
        String oldName = peoples.get(info.position).getName();
        peoples.remove(info.position);
        ((MainActivity) getActivity()).savePeoples(peoples);
        fillList();

        List<Order> orders = ((MainActivity) getActivity()).retrieveOrders();
        if (orders != null)
        {
            for (Order order : orders)
            {
                List<People> peoplesOrders = order.getPeoples();
                if (peoplesOrders != null)
                {
                    for (int i = 0; i < peoplesOrders.size(); i++)
                    {
                        if (peoplesOrders.get(i).getName().equals(oldName))
                            peoplesOrders.remove(i);
                    }
                }
            }
            UtilHelper.orders = orders;
            ((MainActivity) getActivity()).saveOrders(orders);
        }
    }
}
