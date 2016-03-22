package diamond.schmitt.com.diamond;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import Entities.People;
import Entities.Product;
import butterknife.Bind;

public class AddPeopleFragment extends BaseFragment
{
    @Bind(R.id.fragment_add_people_textview_name)
    EditText editText_Name;
    @Bind(R.id.fragment_add_people_floatingactionbutton_done)
    FloatingActionButton floatingActionButton_Done;
    private People people;

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState, R.layout.fragment_add_people, "Add People");

        floatingActionButton_Done.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                savePeople();
            }
        });

        editText_Name.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
            {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE)
                {
                    savePeople();
                    handled = true;
                }
                return handled;
            }
        });

        if (people != null)
            editText_Name.setText(people.getName());

        setFocusOnEdittext(editText_Name);

        return view;
    }

    private void savePeople()
    {
        List<People> peoples = ((MainActivity) getActivity()).retrievePeoples();

        if (peoples == null)
        {
            peoples = new ArrayList<People>();
        }

        String name = String.valueOf(editText_Name.getText());
        if (name != null || !name.isEmpty())
        {
            if (people == null)
                peoples.add(new People(name, new ArrayList<Product>(), 0));
            else
                peoples.get(people.getPosition()).setName(name);

            ((MainActivity) getActivity()).savePeoples(peoples);
            hideKeyboard();
            getActivity().onBackPressed();

        } else
        {
            showSnackbarShort("Please add a name");
        }
    }

    private void setFocusOnEdittext(EditText editText)
    {
        editText.setFocusable(true);
        editText.requestFocus();
        editText.findFocus();
        if (editText.requestFocus())
        {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.toggleSoftInputFromWindow(editText.getApplicationWindowToken(), InputMethodManager.SHOW_FORCED, 0);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null)
            people = (People) bundle.getSerializable("People");
    }

    @Override
    public void onResume()
    {
        super.onResume();
    }
}
