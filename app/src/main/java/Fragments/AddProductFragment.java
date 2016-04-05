package Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import Entities.Product;
import butterknife.Bind;
import diamond.schmitt.com.diamond.MainActivity;
import diamond.schmitt.com.diamond.R;

public class AddProductFragment extends BaseFragment
{
    @Bind(R.id.fragment_add_product_textview_name)
    EditText editText_Name;
    @Bind(R.id.fragment_add_product_textview_price)
    EditText editText_Price;
    @Bind(R.id.fragment_add_product_floatingactionbutton_done)
    FloatingActionButton floatingActionButton_Done;

    private Product product;

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState, R.layout.fragment_add_product, "Add Product");

        floatingActionButton_Done.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                saveProducts();
            }
        });

        editText_Price.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
            {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE)
                {
                    saveProducts();
                    handled = true;
                }
                return handled;
            }
        });

        if (product != null)
        {
            editText_Name.setText(product.getName());
            editText_Price.setText(product.getPriceString());
        }

        editText_Name.requestFocus();

        return view;
    }

    private void saveProducts()
    {
        List<Product> products = ((MainActivity) getActivity()).retrieveProducts();
        if (products == null)
        {
            products = new ArrayList<Product>();
        }

        String name = String.valueOf(editText_Name.getText());
        Double price = 0.0;

        try
        {
            String replacedPrice = String.valueOf(editText_Price.getText()).replace(",", ".");
            price = Double.parseDouble(replacedPrice);
        } catch (NumberFormatException ex)
        {
            showSnackbarShort(ex.getMessage());
        }

        if (product == null)
            products.add(new Product(name, price, 0, 0));
        else
        {
            products.get(product.getPosition()).setName(name);
            products.get(product.getPosition()).setPrice(price);
        }

        if (price != 0.0)
        {
            ((MainActivity) getActivity()).saveProducts(products);
            hideKeyboard();
            getActivity().onBackPressed();
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null)
            product = (Product) bundle.getSerializable("Product");
    }


    @Override
    public void onResume()
    {
        super.onResume();
    }
}
