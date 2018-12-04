package com.example.tweak.danabusbooking.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.example.tweak.danabusbooking.R;
import com.example.tweak.danabusbooking.database_management.controllers.ghe_ngoi.GheNgoi;
import com.example.tweak.danabusbooking.database_management.controllers.ghe_ngoi.GheNgoiController;
import com.example.tweak.danabusbooking.database_management.controllers.xe_buyt.XeBuyt;
import com.example.tweak.danabusbooking.utils.SharedVariables;

import java.util.ArrayList;

public class XeBuytThroughActivityAdapter extends ArrayAdapter<XeBuyt> {

    private Context m_Context;
    private int m_Layout;
    private ArrayList<XeBuyt> m_List;

    public XeBuytThroughActivityAdapter(Context context, int resource, ArrayList<XeBuyt> list) {
        super(context, resource, list);

        this.m_Context = context;
        this.m_Layout = resource;
        this.m_List = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {

            //create view
            LayoutInflater inflater = LayoutInflater.from(m_Context);
            convertView = inflater.inflate(m_Layout, parent, false);

            //get child
            viewHolder = new ViewHolder();

            viewHolder.maXeBuytTextView = convertView.findViewById(R.id.maXeBuytTextView);
            viewHolder.soHieuXeTextView = convertView.findViewById(R.id.soHieuXeTextView);
            viewHolder.bienSoXeTextView = convertView.findViewById(R.id.bienSoXeTextView);
            viewHolder.gheNgoiGridView = convertView.findViewById(R.id.gheNgoiGridView);
            viewHolder.soGheTrongTextView = convertView.findViewById(R.id.soGheTrongTextView);

            //save
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Putting data
        final XeBuyt xeBuyt = m_List.get(position);

        viewHolder.maXeBuytTextView.setText("Xe buýt " + SharedVariables.convertIntToString(xeBuyt.maXeBuyt));
        viewHolder.soHieuXeTextView.setText("Số hiệu: " + xeBuyt.soHieuXe);
        viewHolder.bienSoXeTextView.setText(xeBuyt.bienSoXe);

        // Set up data for the gridView of seats

        // In reference to every single bus, we show all of the available seats up for user
        // to choose and pick a ticket
        ArrayList<GheNgoi> availableSeats = GheNgoiController.getInstance().getTheListOfGheNgoiTrongShowOnScreen(xeBuyt.maXeBuyt);

        // Need an adapter of gheNgoi object to inflate the data for the gridView
        ArrayAdapter<GheNgoi> m_Adapter = new GheNgoiThroughActivityAdapter(m_Context, R.layout.item_seat_view_through_activity,
                availableSeats);

        viewHolder.gheNgoiGridView.setAdapter(m_Adapter);
        viewHolder.gheNgoiGridView.setNumColumns(availableSeats.size());

        viewHolder.soGheTrongTextView.setText(SharedVariables.convertIntToString(availableSeats.size()));

        return convertView;
    }

    private class ViewHolder {
        public TextView maXeBuytTextView;
        public TextView soHieuXeTextView;
        public TextView bienSoXeTextView;
        public GridView gheNgoiGridView;
        public TextView soGheTrongTextView;
    }

}
