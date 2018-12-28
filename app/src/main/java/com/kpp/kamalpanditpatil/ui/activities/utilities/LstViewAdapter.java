package com.kpp.kamalpanditpatil.ui.activities.utilities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.kpp.kamalpanditpatil.R;

import java.util.ArrayList;

public class LstViewAdapter extends ArrayAdapter<String> {
    int groupid;
    String[] item_list1;
    ArrayList<String> desc;
    Context context;

    public LstViewAdapter(Context context, int vg, int id, ArrayList<String> item_list) {
        super(context, vg, id, item_list);
        this.context = context;
        groupid = vg;
        this.desc = item_list;
        String[] desc1 = new String[desc.size()];
        item_list1 = desc.toArray(desc1);

    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = convertView;
        // Inflate the rowlayout.xml file if convertView is null
        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(groupid, parent, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.textname = rowView.findViewById(R.id.attributes);
            viewHolder.textprice = rowView.findViewById(R.id.details);
            rowView.setTag(viewHolder);

        }
        // Set text to each TextView of ListView item
        String[] items = item_list1[position].split("__");
        ViewHolder holder = (ViewHolder) rowView.getTag();
        holder.textname.setText(items[0]);
        holder.textprice.setText(items[1]);
        return rowView;
    }

    // Hold views of the ListView to improve its scrolling performance
    static class ViewHolder {
        public TextView textname;
        public TextView textprice;

    }

}
