package diamond.schmitt.com.diamond;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import Entities.Order;
import Entities.People;
import Entities.Product;
import Fragments.AddOrderFragment;
import Fragments.AddPeopleFragment;
import Fragments.AddPeopleToOrderFragment;
import Fragments.AddProductFragment;
import Fragments.AddProductsToPeopleFragment;
import Fragments.HomeFragment;
import Fragments.OrderFragment;
import Fragments.PeopleFragment;
import Fragments.ProductFragment;
import Util.UtilHelper;
import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
{
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.nav_view)
    NavigationView navigationView;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawer;

    private SharedPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mPrefs = getPreferences(MODE_PRIVATE);
        addFragment(new HomeFragment(), R.string.nav_fragment_home);

        setupDrawerLayout();

        UtilHelper.orders = retrieveOrders();
        if (UtilHelper.orders == null)
        {
            List<Order> orders = new ArrayList<>();
            saveOrders(orders);
        }
    }

    private void setupDrawerLayout()
    {
        setSupportActionBar(toolbar);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(MenuItem item)
            {
                return onNavigationDrawerItemSelected(item);
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void onBackPressed()
    {
        int count = getFragmentManager().getBackStackEntryCount();

        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        if (count > 1)
        {
            getFragmentManager().popBackStack();
        } else
        {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        return super.onOptionsItemSelected(item);
    }


    public boolean onNavigationDrawerItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.nav_home:
                replaceFragment(new HomeFragment(), R.string.nav_fragment_home);
                break;
            case R.id.nav_add_order:
                addFragment(new AddOrderFragment(), R.string.nav_fragment_add_orders);
                break;
            case R.id.nav_orders:
                addFragment(new OrderFragment(), R.string.nav_fragment_orders);
                break;
            case R.id.nav_people:
                addFragment(new PeopleFragment(), R.string.nav_fragment_people);
                break;
            case R.id.nav_products:
                addFragment(new ProductFragment(), R.string.nav_fragment_products);
                break;
        }

        return true;
    }

    public void replaceFragment(Fragment fragment, int fragmentLabel)
    {
        if (fragment != null)
        {
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentParentViewGroup, fragment, String.valueOf(fragmentLabel))
                    .commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    public void addFragment(Fragment fragment, int fragmentLabel)
    {
        if (fragment != null)
        {
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentParentViewGroup, fragment, String.valueOf(fragmentLabel))
                    .addToBackStack(String.valueOf(fragmentLabel))
                    .commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    public void addOrderFragment(Order order)
    {
        FragmentTransaction ft = getFragmentManager().beginTransaction();

        AddOrderFragment frag = new AddOrderFragment();

        Bundle bundles = new Bundle();

        if (order != null)
            bundles.putSerializable("Order", order);

        frag.setArguments(bundles);

        ft.replace(R.id.fragmentParentViewGroup, frag, String.valueOf(R.string.nav_fragment_add_orders));
        ft.addToBackStack(String.valueOf(R.string.nav_fragment_add_orders));
        ft.commit();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    public void addPeopleToOrderFragment(Order order)
    {
        FragmentTransaction ft = getFragmentManager().beginTransaction();

        AddPeopleToOrderFragment frag = new AddPeopleToOrderFragment();

        Bundle bundles = new Bundle();

        if (order != null)
            bundles.putSerializable("Order", order);

        frag.setArguments(bundles);

        ft.replace(R.id.fragmentParentViewGroup, frag, String.valueOf(R.string.nav_fragment_add_people_to_orders));
        ft.addToBackStack(String.valueOf(R.string.nav_fragment_add_people_to_orders));
        ft.commit();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    public void addProductsToPeopleFragment(People people)
    {
        FragmentTransaction ft = getFragmentManager().beginTransaction();

        AddProductsToPeopleFragment frag = new AddProductsToPeopleFragment();

        Bundle bundles = new Bundle();

        if (people != null)
            bundles.putSerializable("People", people);

        frag.setArguments(bundles);

        ft.replace(R.id.fragmentParentViewGroup, frag, String.valueOf(R.string.nav_fragment_add_products_to_people));
        ft.addToBackStack(String.valueOf(R.string.nav_fragment_add_products_to_people));
        ft.commit();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    public void addEditProductFragment(Product product)
    {
        FragmentTransaction ft = getFragmentManager().beginTransaction();

        AddProductFragment frag = new AddProductFragment();

        Bundle bundles = new Bundle();

        if (product != null)
            bundles.putSerializable("Product", product);

        frag.setArguments(bundles);

        ft.replace(R.id.fragmentParentViewGroup, frag, String.valueOf(R.string.nav_fragment_add_products));
        ft.addToBackStack(String.valueOf(R.string.nav_fragment_add_products));
        ft.commit();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    public void addEditPeopleFragment(People people)
    {
        FragmentTransaction ft = getFragmentManager().beginTransaction();

        AddPeopleFragment frag = new AddPeopleFragment();

        Bundle bundles = new Bundle();

        if (people != null)
            bundles.putSerializable("People", people);

        frag.setArguments(bundles);

        ft.replace(R.id.fragmentParentViewGroup, frag, String.valueOf(R.string.nav_fragment_add_products));
        ft.addToBackStack(String.valueOf(R.string.nav_fragment_add_products));
        ft.commit();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    public void savePeoples(List<People> object)
    {
        SharedPreferences.Editor editor = mPrefs.edit();
        String connectionsJSONString = new Gson().toJson(object);
        editor.putString("People", connectionsJSONString);
        editor.apply();
    }

    public List<People> retrievePeoples()
    {
        String connectionsJSONString = getPreferences(MODE_PRIVATE).getString("People", null);
        Type type = new TypeToken<List<People>>()
        {
        }.getType();
        List<People> peoples = new Gson().fromJson(connectionsJSONString, type);
        return peoples;
    }

    public void saveProducts(List<Product> object)
    {
        SharedPreferences.Editor editor = mPrefs.edit();
        String connectionsJSONString = new Gson().toJson(object);
        editor.putString("Product", connectionsJSONString);
        editor.apply();

    }

    public List<Product> retrieveProducts()
    {
        String connectionsJSONString = getPreferences(MODE_PRIVATE).getString("Product", null);
        Type type = new TypeToken<List<Product>>()
        {
        }.getType();
        return new Gson().fromJson(connectionsJSONString, type);
    }

    public void saveOrders(List<Order> object)
    {
        SharedPreferences.Editor editor = mPrefs.edit();
        String connectionsJSONString = new Gson().toJson(object);
        editor.putString("Order", connectionsJSONString);
        editor.apply();
    }

    public List<Order> retrieveOrders()
    {
        String connectionsJSONString = getPreferences(MODE_PRIVATE).getString("Order", null);
        Type type = new TypeToken<List<Order>>()
        {
        }.getType();
        return new Gson().fromJson(connectionsJSONString, type);
    }
}
