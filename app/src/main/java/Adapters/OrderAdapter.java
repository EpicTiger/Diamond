package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

import Entities.Order;
import Util.UtilHelper;
import diamond.schmitt.com.diamond.R;

public class OrderAdapter extends ArrayAdapter<Order>
{
    private final Context context;
    private List<Order> objects;

    public OrderAdapter(Context context, int resource, List<Order> objects)
    {
        super(context, resource, objects);
        this.context = context;
        this.objects = objects;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        View v = convertView;

        if (v == null)
        {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.fragment_order_list_item, null);
        }

        Order order = getItem(position);

        if (order != null)
        {
            TextView name = (TextView) v.findViewById(R.id.fragment_order_list_item_textview_date);
            TextView price = (TextView) v.findViewById(R.id.fragment_order_list_item_textview_price);

            if (name != null)
            {
                Calendar calendar = order.getCalender();
                if (calendar != null)
                    name.setText(String.valueOf(UtilHelper.DateFormat.format(calendar.getTime())));

                if (price != null)
                    price.setText(Util.PriceCalculator.getTotalPriceString(order));
            }
        }

        return v;
    }
}
