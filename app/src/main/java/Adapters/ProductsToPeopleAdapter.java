package Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import Entities.Product;
import diamond.schmitt.com.diamond.MainActivity;
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
            TextView quantity = (TextView) v.findViewById(R.id.fragment_add_products_to_people_list_item_textview_quantity);
            Button less = (Button) v.findViewById(R.id.fragment_add_products_to_people_list_item_button_less);
            Button more = (Button) v.findViewById(R.id.fragment_add_products_to_people_list_item_button_more);
            LinearLayout customQuanity = (LinearLayout) v.findViewById(R.id.fragment_add_products_to_people_list_item_custom_quantity);


            if (name != null)
                name.setText(product.getName());
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

            customQuanity.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    final AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                    final EditText input = new EditText(getContext());
                    input.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                    if (product.getQuantity() != 0.0)
                        input.setText(product.getQuantitystring());
                    input.requestFocus();
                    alert.setView(input);
                    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int whichButton)
                        {
                            String value = String.valueOf(input.getText()).trim();
                            try
                            {
                                product.setQuantity(Double.parseDouble(value.replace(",", ".")));
                                input.setText(product.getQuantitystring());
                            } catch (NumberFormatException ex)
                            {
                            }
                            notifyDataSetChanged();
                        }
                    });

                    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int whichButton)
                        {
                            dialog.cancel();
                        }
                    });
                    alert.show();
                }
            });
        }

        return v;
    }
}
