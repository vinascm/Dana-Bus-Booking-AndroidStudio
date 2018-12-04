package com.example.tweak.danabusbooking.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.tweak.danabusbooking.R;
import com.example.tweak.danabusbooking.database_management.controllers.tuyen_duong.TuyenDuong;
import com.example.tweak.danabusbooking.utils.SharedVariables;

import java.util.ArrayList;

public class TuyenDuongLookUpAdapter extends ArrayAdapter<TuyenDuong> {

    private Context m_Context;
    private int m_Layout;
    private ArrayList<TuyenDuong> m_List;

    public TuyenDuongLookUpAdapter(Context context, int resource, ArrayList<TuyenDuong> list) {
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

            viewHolder.maTuyenDuongButton = convertView.findViewById(R.id.maTuyenDuongButton);
            viewHolder.tenTuyenDuongTextView = convertView.findViewById(R.id.tenTuyenDuongTextView);

            //save
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Putting data
        final TuyenDuong tuyenDuong = m_List.get(position);

        viewHolder.maTuyenDuongButton.setText("Tuyến " + SharedVariables.convertIntToString(tuyenDuong.maTuyenDuong));
        viewHolder.tenTuyenDuongTextView.setText(tuyenDuong.tenTuyenDuong);

        return convertView;
    }

    private class ViewHolder {
        public Button maTuyenDuongButton;
        public TextView tenTuyenDuongTextView;
    }

}
