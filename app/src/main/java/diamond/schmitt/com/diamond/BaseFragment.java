package diamond.schmitt.com.diamond;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BaseFragment extends Fragment
{
    protected View view;
    protected ViewGroup container;

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState, int resource, String toolbarTitle)
    {
        super.onCreateView(inflater, container, savedInstanceState);
        this.container = container;
        view = inflater.inflate(resource, container, false);
        ButterKnife.bind(this, view);
        setToolbarTitle(toolbarTitle);

        return view;
    }

    private void setToolbarTitle(String title)
    {
        if (((MainActivity) getActivity()).getSupportActionBar() != null)
        {
            ((MainActivity) getActivity()).getSupportActionBar().setTitle(title);
        }
    }

    protected void showSnackbarShort(String message)
    {
        Snackbar.make(container, message, Snackbar.LENGTH_LONG).show();
    }

    protected void showSnackbarLong(String message)
    {
        Snackbar.make(container, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void hideKeyboard()
    {
        // Check if no view has focus:
        View view = getActivity().getCurrentFocus();
        if (view != null)
        {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}