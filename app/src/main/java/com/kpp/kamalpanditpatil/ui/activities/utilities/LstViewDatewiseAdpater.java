package com.kpp.kamalpanditpatil.ui.activities.utilities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.kpp.kamalpanditpatil.R;

import java.util.ArrayList;


public class LstViewDatewiseAdpater extends ArrayAdapter<String> {
    int groupid;
    String[] item_list1;
    ArrayList<String> desc;
    Context context;

    public LstViewDatewiseAdpater(Context context, int vg, int id, ArrayList<String> item_list) {
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
            LstViewDatewiseAdpater.ViewHolder viewHolder = new LstViewDatewiseAdpater.ViewHolder();
            viewHolder.textname = rowView.findViewById(R.id.names);
            viewHolder.textprice = rowView.findViewById(R.id.shift);
            viewHolder.textpiece = rowView.findViewById(R.id.pieceordailyDatewise);
            rowView.setTag(viewHolder);
        }
        String[] items = item_list1[position].split("__");
        LstViewDatewiseAdpater.ViewHolder holder = (LstViewDatewiseAdpater.ViewHolder) rowView.getTag();
        holder.textname.setText(items[0]);
        holder.textprice.setText(items[1]);
        holder.textpiece.setText(items[2]);
        return rowView;
    }

    static class ViewHolder {
        public TextView textname;
        public TextView textprice;
        public TextView textpiece;
    }
}