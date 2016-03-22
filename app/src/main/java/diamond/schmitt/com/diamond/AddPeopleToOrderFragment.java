package diamond.schmitt.com.diamond;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;

import java.util.List;

import Adapters.PeopleToOrderAdapter;
import Entities.Order;
import Entities.People;
import Util.Util;

public class AddPeopleToOrderFragment extends BaseListFragment
{
    private Order order;
    private List<People> peoples;

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState, R.layout.fragment_list, "Add People");
        floatingActionButton.setImageDrawable(ContextCompat.getDrawable(getActivity(), (R.drawable.ic_done_white_24dp)));

        floatingActionButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Util.orders.get(order.getPosition()).setPeoples(peoples);
                ((MainActivity) getActivity()).saveOrders(Util.orders);
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
        order = (Order) bundle.getSerializable("Order");
    }

    @Override
    protected void fillList()
    {
        if (order.getPeoples() == null)
            peoples = ((MainActivity) getActivity()).retrievePeoples();
        else
            peoples = order.getPeoples();

        if (!(peoples != null ? peoples.isEmpty() : false))
        {
            setAndFillListAdapter(peoples);
        }
    }

    private void setAndFillListAdapter(List<People> list)
    {
        if (list != null)
        {
            PeopleToOrderAdapter adapter = new PeopleToOrderAdapter(getActivity(), R.layout.fragment_list, list);
            listView.setAdapter(adapter);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                CheckBox cb = (CheckBox) v.findViewById(R.id.fragment_add_people_to_order_list_item_checkbox);
                cb.setChecked(!cb.isChecked());
                peoples.get(position).setIsSelected(cb.isChecked());
            }
        });
    }
}
