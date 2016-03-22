package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import Entities.Product;
import diamond.schmitt.com.diamond.R;

public class ProductAdapter extends ArrayAdapter<Product>
{
    private final Context context;
    private List<Product> objects;

    public ProductAdapter(Context context, int resource, List<Product> objects)
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
            v = vi.inflate(R.layout.fragment_product_list_item, null);
        }

        Product product = getItem(position);

        if (product != null)
        {
            TextView name = (TextView) v.findViewById(R.id.fragment_product_list_item_textview_name);

            if (name != null)
            {
                name.setText(product.getName());
            }

            TextView price = (TextView) v.findViewById(R.id.fragment_product_list_item_textview_price);

            if (price != null)
            {
                price.setText("€" + product.getPriceString());
            }
        }

        return v;
    }

}
