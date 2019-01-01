package com.kpp.kamalpanditpatil.ui.activities.utilities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.kpp.kamalpanditpatil.R;

import java.util.ArrayList;

public class LstViewPersonwiseAdapter extends ArrayAdapter<String> {
    int groupid;
    String[] item_list1;
    ArrayList<String> desc;
    Context context;

    public LstViewPersonwiseAdapter(Context context, int vg, int id, ArrayList<String> item_list) {
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
            LstViewPersonwiseAdapter.ViewHolder viewHolder = new LstViewPersonwiseAdapter.ViewHolder();
            viewHolder.date = rowView.findViewById(R.id.personwise_dates);
            viewHolder.shift = rowView.findViewById(R.id.personwise_shift);
            viewHolder.dailyorpiece = rowView.findViewById(R.id.personwise_pieceordaily);
            rowView.setTag(viewHolder);
        }
        String[] items = item_list1[position].split("__");
        LstViewPersonwiseAdapter.ViewHolder holder = (LstViewPersonwiseAdapter.ViewHolder) rowView.getTag();
        holder.date.setText(items[0]);
        holder.shift.setText(items[1]);
        holder.dailyorpiece.setText(items[2]);
        return rowView;
    }

    static class ViewHolder {
        public TextView date;
        public TextView shift;
        public TextView dailyorpiece;
    }
}
