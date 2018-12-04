package com.example.tweak.danabusbooking.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.example.tweak.danabusbooking.R;
import com.example.tweak.danabusbooking.first_function.TicketBookingActivity;
import com.example.tweak.danabusbooking.database_management.controllers.ghe_ngoi.GheNgoi;

import java.util.ArrayList;

public class GheNgoiThroughActivityAdapter extends ArrayAdapter<GheNgoi> {

    private Context m_Context;
    private int m_Layout;
    private ArrayList<GheNgoi> m_List;

    public GheNgoiThroughActivityAdapter(Context context, int resource, ArrayList<GheNgoi> list) {
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

            viewHolder.bookFinalChairButton = convertView.findViewById(R.id.bookFinalChairButton);

            //save
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Putting data
        final GheNgoi gheNgoi = m_List.get(position);

        viewHolder.bookFinalChairButton.setText(gheNgoi.soHieuGhe);

        viewHolder.bookFinalChairButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Moving on to the activity of booking a final ticket
                // Save the maGheNgoi to create a ticket
                Intent intent = new Intent(getContext(), TicketBookingActivity.class);
                intent.putExtra("maGheNgoiBooking", gheNgoi.maGheNgoi);
                m_Context.startActivity(intent);
            }
        });

        return convertView;
    }

    private class ViewHolder {
        public Button bookFinalChairButton;
    }

}
