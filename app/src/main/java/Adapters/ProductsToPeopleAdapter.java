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

public class ProductsToPeopleAdapter extends ArrayAdapter<Product>
{
    private final Context context;
    private List<Product> objects;

    public ProductsToPeopleAdapter(Context context, int resource, List<Product> objects)
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
            v = vi.inflate(R.layout.fragment_add_products_to_people_list_item, null);
        }

        final Product product = getItem(position);

        if (product != null)
        {
            TextView name = (TextView) v.findViewById(R.id.fragment_add_products_to_people_list_item_textview_name);
            TextView price = (TextView) v.findViewById(R.id.fragment_add_products_to_people_list_item_textview_price);
            TextView quantity = (TextView) v.findViewById(R.id.fragment_add_products_to_people_list_item_textview_quantity);
            Button less = (Button) v.findViewById(R.id.fragment_add_products_to_people_list_item_button_less);
            Button more = (Button) v.findViewById(R.id.fragment_add_products_to_people_list_item_button_more);

            if (name != null)
                name.setText(product.getName());
            if (price != null)
                price.setText(product.getPriceString());
            if (quantity != null)
                quantity.setText(product.getQuantitystring());

            if (less != null)
            {
                less.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        if (product.getQuantity() != 0)
                            if (product.getName().contains("Shisha"))
                            {
                                if (product.getPeopleQuantity() != 0)
                                {
                                    double steps = ((double) 1 / (double) product.getPeopleQuantity());
                                    product.setQuantity(product.getQuantity() - steps);
                                }
                            } else
                                product.setQuantity(product.getQuantity() - 1);
                        notifyDataSetChanged();
                    }
                });
            }

            if (more != null)
            {
                more.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        if (product.getName().contains("Shisha"))
                        {
                            if (product.getPeopleQuantity() != 0)
                            {
                                double steps = ((double) 1 / (double) product.getPeopleQuantity());
                                product.setQuantity(product.getQuantity() + steps);
                            }
                        } else
                            product.setQuantity(product.getQuantity() + 1);
                        notifyDataSetChanged();
                    }
                });
            }
        }

        return v;
    }
}
