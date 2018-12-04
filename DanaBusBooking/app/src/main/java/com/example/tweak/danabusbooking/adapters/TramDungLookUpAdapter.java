package com.example.tweak.danabusbooking.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.tweak.danabusbooking.R;
import com.example.tweak.danabusbooking.database_management.controllers.tram_dung.TramDung;
import com.example.tweak.danabusbooking.utils.SharedVariables;

import java.util.ArrayList;

public class TramDungLookUpAdapter extends ArrayAdapter<TramDung> {

    private Context m_Context;
    private int m_Layout;
    private ArrayList<TramDung> m_List;

    public TramDungLookUpAdapter(Context context, int resource, ArrayList<TramDung> list) {
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

            viewHolder.maTramDungTextView = convertView.findViewById(R.id.maTramDungTextView);
            viewHolder.tenTramDungTextView = convertView.findViewById(R.id.tenTramDungTextView);
            viewHolder.diaChiTextView = convertView.findViewById(R.id.diaChiTextView);

            //save
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Putting data
        final TramDung tramDung = m_List.get(position);

        viewHolder.maTramDungTextView.setText("Trạm dừng " + SharedVariables.convertIntToString(tramDung.maTramDung));
        viewHolder.tenTramDungTextView.setText(tramDung.tenTramDung);
        viewHolder.diaChiTextView.setText(tramDung.diaChi);

        return convertView;
    }

    private class ViewHolder {
        public TextView maTramDungTextView;
        public TextView tenTramDungTextView;
        public TextView diaChiTextView;
    }

}
