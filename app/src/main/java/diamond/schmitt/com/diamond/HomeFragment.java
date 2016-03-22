package diamond.schmitt.com.diamond;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;


public class HomeFragment extends BaseFragment
{
    @Bind(R.id.fragment_home_add_order)
    FloatingActionButton floatingActionButton_AddOrder;

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState, R.layout.fragment_home, "Home");

        floatingActionButton_AddOrder.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ((MainActivity) getActivity()).addFragment(new AddOrderFragment(), R.string.nav_fragment_add_orders);
            }
        });

        return view;
    }

    @Override
    public void onResume()
    {
        super.onResume();
    }
}
