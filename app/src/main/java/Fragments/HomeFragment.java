package Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import Entities.Order;
import Util.NonScrollListView;
import butterknife.Bind;
import diamond.schmitt.com.diamond.MainActivity;
import diamond.schmitt.com.diamond.R;


public class HomeFragment extends BaseFragment
{
    @Bind(R.id.fragment_home_add_order)
    FloatingActionButton floatingActionButton_AddOrder;
    @Bind(R.id.fragment_home_listview_newest_orders)
    NonScrollListView listView_NewestOrders;
    @Bind(R.id.fragment_home_textview_newest_orders)
    TextView textView_NewestOrders;

    @Bind(R.id.fragment_home_listview_unpaid_orders)
    NonScrollListView listView_UnpaidOrders;
    @Bind(R.id.fragment_home_textview_unpaid_orders)
    TextView textView_UnpaidOrders;

    private List<Order> newestOrders;

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState, R.layout.fragment_home, "Diamond");

        floatingActionButton_AddOrder.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ((MainActivity) getActivity()).addFragment(new AddOrderFragment(), R.string.nav_fragment_add_orders);
            }
        });

        textView_NewestOrders.setVisibility(View.GONE);
        textView_UnpaidOrders.setVisibility(View.GONE);


//        listView_NewestOrders.setOnItemClickListener(new AdapterView.OnItemClickListener()
//        {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
//            {
//                Order orderClicked = newestOrders.get(position);
//                List<Order> orders = Util.orders;
//                Order order = new Order(null, null);
//
//                for (int i = 0; i < orders.size(); i++)
//                {
//                    if (orders.get(i).getCalender().equals(orderClicked.getCalender()))
//                        order = orders.get(i);
//                }
//
//                order.setPosition(position);
//                ((MainActivity) getActivity()).addOrderFragment(order);
//            }
//        });

        return view;
    }

//    @Override
//    public void onResume()
//    {
//        super.onResume();
//        fillList();
//    }

//    protected void fillList()
//    {
//        List<Order> uncutNewestOrders = ((MainActivity) getActivity()).retrieveOrders();
//
//        if (!(uncutNewestOrders != null && uncutNewestOrders.isEmpty()))
//        {
//            Collections.sort(uncutNewestOrders, new Comparator<Order>()
//            {
//                public int compare(Order o1, Order o2)
//                {
//                    if (o1.getCalender() == null || o2.getCalender() == null)
//                        return 0;
//                    return o1.getCalender().compareTo(o2.getCalender());
//                }
//            });
//
//            newestOrders = uncutNewestOrders.subList(Math.max(uncutNewestOrders.size() - 3, 0), uncutNewestOrders.size());
//
//            listView_NewestOrders.setVisibility(View.VISIBLE);
//            textView_NewestOrders.setVisibility(View.VISIBLE);
//
//            setAndFillListAdapter(newestOrders);
//        } else
//        {
//            listView_NewestOrders.setVisibility(View.GONE);
//            textView_NewestOrders.setVisibility(View.GONE);
//        }
//    }

//    private void setAndFillListAdapter(List<Order> list)
//    {
//        if (list != null)
//        {
//            HomeNewestOrderAdapter adapter = new HomeNewestOrderAdapter(getActivity(), R.id.fragment_home_listview_newest_orders, list);
//            listView_NewestOrders.setAdapter(adapter);
//        }
//    }
}
