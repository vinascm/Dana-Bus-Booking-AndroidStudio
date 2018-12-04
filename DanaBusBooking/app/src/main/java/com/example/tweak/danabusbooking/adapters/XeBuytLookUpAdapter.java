package com.example.tweak.danabusbooking.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.tweak.danabusbooking.R;
import com.example.tweak.danabusbooking.database_management.controllers.xe_buyt.XeBuyt;
import com.example.tweak.danabusbooking.utils.SharedVariables;

import java.util.ArrayList;

public class XeBuytLookUpAdapter extends ArrayAdapter<XeBuyt> {

    private Context m_Context;
    private int m_Layout;
    private ArrayList<XeBuyt> m_List;

    public XeBuytLookUpAdapter(Context context, int resource, ArrayList<XeBuyt> list) {
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

            viewHolder.maXeBuytButton = convertView.findViewById(R.id.maXeBuytButton);
            viewHolder.thongTinSoHieuXeTextView = convertView.findViewById(R.id.thongTinSoHieuXeTextView);
            viewHolder.thongTinBienSoXeTextView = convertView.findViewById(R.id.thongTinBienSoXeTextView);

            //save
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Putting data
        final XeBuyt xeBuyt = m_List.get(position);

        viewHolder.maXeBuytButton.setText("Xe buýt " + SharedVariables.convertIntToString(xeBuyt.maXeBuyt));
        viewHolder.thongTinSoHieuXeTextView.setText("Số hiệu: " + xeBuyt.soHieuXe);
        viewHolder.thongTinBienSoXeTextView.setText("Biển số xe: " + xeBuyt.bienSoXe);

        return convertView;
    }

    private class ViewHolder {
        public Button maXeBuytButton;
        public TextView thongTinSoHieuXeTextView;
        public TextView thongTinBienSoXeTextView;
    }

}
