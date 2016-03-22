package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import Entities.People;
import diamond.schmitt.com.diamond.R;

public class PeopleToOrderAdapter extends ArrayAdapter<People>
{
    private final Context context;
    private List<People> objects;

    public PeopleToOrderAdapter(Context context, int resource, List<People> objects)
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
            v = vi.inflate(R.layout.fragment_add_people_to_order_list_item, null);
        }

        People people = getItem(position);

        if (people != null)
        {
            TextView name = (TextView) v.findViewById(R.id.fragment_add_people_to_order_list_item_textview_name);
            CheckBox checkBox = (CheckBox) v.findViewById(R.id.fragment_add_people_to_order_list_item_checkbox);

            if (name != null)
                name.setText(people.getName());

            checkBox.setChecked(people.isSelected());
        }

        return v;
    }
}
