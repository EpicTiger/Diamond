package diamond.schmitt.com.diamond;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import butterknife.Bind;

public abstract class BaseListFragment extends BaseFragment
{
    @Bind(R.id.fragment_list_listview)
    ListView listView;
    @Bind(R.id.fragment_list_floatingactionbutton)
    FloatingActionButton floatingActionButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState, int resource, String toolbarTitle)
    {
        super.onCreateView(inflater, container, savedInstanceState, resource, toolbarTitle);

        registerForContextMenu(listView);

        return view;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.fragment_list_listview)
        {
            MenuInflater inflater = getActivity().getMenuInflater();
            inflater.inflate(R.menu.menu_list, menu);
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        fillList();
    }

    protected abstract void fillList();
}