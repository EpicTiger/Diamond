package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import Entities.PeopleProduct;
import diamond.schmitt.com.diamond.MainActivity;
import diamond.schmitt.com.diamond.R;

public class PeopleProductAdapter extends ArrayAdapter<PeopleProduct>
{
    private final Context context;
    private List<PeopleProduct> objects;

    public PeopleProductAdapter(Context context, int resource, List<PeopleProduct> objects)
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
            v = vi.inflate(R.layout.fragment_add_order_item, null);
        }

        PeopleProduct peopleProduct = getItem(position);

        if (peopleProduct != null)
        {
            LinearLayout headerLayout = (LinearLayout) v.findViewById(R.id.fragment_add_order_item_header);
            LinearLayout subHeaderLayout = (LinearLayout) v.findViewById(R.id.fragment_add_order_item_subheader);
            TextView textView_PeopleName = (TextView) v.findViewById(R.id.fragment_add_order_item_people_name);
            TextView textView_PeoplePrice = (TextView) v.findViewById(R.id.fragment_add_order_item_people_price);
            TextView textView_ProductName = (TextView) v.findViewById(R.id.fragment_add_order_item_product_name);
            TextView textView_ProductPrice = (TextView) v.findViewById(R.id.fragment_add_order_item_product_price);
            TextView textView_ProductQuantity = (TextView) v.findViewById(R.id.fragment_add_order_item_product_quantity);

            if (peopleProduct.isPeople())
            {
                textView_PeopleName.setText(peopleProduct.getName());
                textView_PeoplePrice.setText(peopleProduct.getPriceString());
            } else
            {
                textView_ProductName.setText(peopleProduct.getName());
                textView_ProductPrice.setText(peopleProduct.getPriceTimesQuantityString());
                textView_ProductQuantity.setText(peopleProduct.getQuantitystring());
            }

            if (peopleProduct.isPeople())
            {
                headerLayout.setVisibility(View.VISIBLE);
                subHeaderLayout.setVisibility(View.GONE);
            }
            else
            {
                headerLayout.setVisibility(View.GONE);
                subHeaderLayout.setVisibility(View.VISIBLE);
            }
        }

        return v;
    }
}
