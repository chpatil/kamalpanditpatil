package com.kpp.kamalpanditpatil.ui.activities.utilities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.kpp.kamalpanditpatil.R;

import java.util.ArrayList;

public class LstViewOvertimewiseAdapter extends ArrayAdapter<String> {
    int groupid;
    String[] item_list1;
    ArrayList<String> desc;
    Context context;

    public LstViewOvertimewiseAdapter(Context context, int vg, int id, ArrayList<String> item_list) {
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
            LstViewOvertimewiseAdapter.ViewHolder viewHolder = new LstViewOvertimewiseAdapter.ViewHolder();
            viewHolder.textdate = rowView.findViewById(R.id.overtime_dates);
            viewHolder.textname = rowView.findViewById(R.id.overtime_name);
            viewHolder.textpieceordaily = rowView.findViewById(R.id.overtime_pieceordaily);
            viewHolder.textotshift = rowView.findViewById(R.id.overtime_overtimeshift);
            rowView.setTag(viewHolder);
        }
        String[] items = item_list1[position].split("__");
        LstViewOvertimewiseAdapter.ViewHolder holder = (LstViewOvertimewiseAdapter.ViewHolder) rowView.getTag();
        holder.textdate.setText(items[0]);
        holder.textname.setText(items[1]);
        holder.textotshift.setText(items[2]);
        holder.textpieceordaily.setText(items[3]);
        return rowView;
    }

    static class ViewHolder {
        public TextView textdate;
        public TextView textname;
        public TextView textpieceordaily;
        public TextView textotshift;
    }
}
