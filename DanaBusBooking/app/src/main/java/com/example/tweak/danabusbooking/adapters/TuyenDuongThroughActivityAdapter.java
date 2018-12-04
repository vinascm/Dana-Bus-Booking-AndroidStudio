package com.example.tweak.danabusbooking.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.tweak.danabusbooking.first_function.FirstFunction_2_Activity;
import com.example.tweak.danabusbooking.R;
import com.example.tweak.danabusbooking.database_management.MainController;
import com.example.tweak.danabusbooking.database_management.controllers.tuyen_duong.TuyenDuong;
import com.example.tweak.danabusbooking.database_management.controllers.xe_buyt.XeBuyt;
import com.example.tweak.danabusbooking.database_management.controllers.xe_buyt.XeBuytController;
import com.example.tweak.danabusbooking.utils.SharedVariables;

import java.util.ArrayList;

public class TuyenDuongThroughActivityAdapter extends ArrayAdapter<TuyenDuong> {

    private Context m_Context;
    private int m_Layout;
    private ArrayList<TuyenDuong> m_List;

    public TuyenDuongThroughActivityAdapter(Context context, int resource, ArrayList<TuyenDuong> list) {
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

            viewHolder.maTuyenDuongTextView = convertView.findViewById(R.id.maTuyenDuongTextView);
            viewHolder.tenTuyenDuongTextView = convertView.findViewById(R.id.tenTuyenDuongTextView);
            viewHolder.benDiTextView = convertView.findViewById(R.id.benDiTextView);
            viewHolder.benDoTextView = convertView.findViewById(R.id.benDoTextView);
            viewHolder.khoangCachVatLyTextView = convertView.findViewById(R.id.khoangCachVatLyTextView);
            viewHolder.giaVeTextView = convertView.findViewById(R.id.giaVeTextView);
            viewHolder.bookChairButton = convertView.findViewById(R.id.bookChairButton);

            //save
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Putting data
        final TuyenDuong tuyenDuong = m_List.get(position);

        viewHolder.maTuyenDuongTextView.setText("Tuyến " + SharedVariables.convertIntToString(tuyenDuong.maTuyenDuong));
        viewHolder.tenTuyenDuongTextView.setText(tuyenDuong.tenTuyenDuong);
        viewHolder.benDiTextView.setText(tuyenDuong.benDi);
        viewHolder.benDoTextView.setText(tuyenDuong.benDo);
        viewHolder.khoangCachVatLyTextView.setText(tuyenDuong.khoangCachVatLy + " km");
        viewHolder.giaVeTextView.setText(SharedVariables.convertIntToString(Math.round(tuyenDuong.giaVe)) + " vnd");
        if (tuyenDuong.daHetCho) {
            viewHolder.bookChairButton.setText("Hết ghế trống. Vui lòng ghé lại sau!");
            viewHolder.bookChairButton.setEnabled(false);
        } else {
            viewHolder.bookChairButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // After clicking, we show up all the buses that has available seats
                    // for user to choose

                    // First of all, we get all the buyts with the corresponding maTuyenDuong
                    ArrayList<XeBuyt> listXeBuytWithAvailableSeats = XeBuytController.getInstance()
                            .getListXeBuytByMaTuyenDuong(tuyenDuong.maTuyenDuong);

                    // Because we haven't found out whether it has available seats or not so
                    // we have to filter it out to return only buyts with available seats

                    // Save to this, cool!!!
                    SharedVariables.theListOfBusesThroughActivity = MainController.getInstance().
                            getListXeBuytsWithAvailableSeats(listXeBuytWithAvailableSeats);

                    Intent intent = new Intent(getContext(), FirstFunction_2_Activity.class);
                    m_Context.startActivity(intent);
                }
            });
        }

        return convertView;
    }

    private class ViewHolder {
        public TextView maTuyenDuongTextView;
        public TextView tenTuyenDuongTextView;
        public TextView benDiTextView;
        public TextView benDoTextView;
        public TextView khoangCachVatLyTextView;
        public TextView giaVeTextView;
        public Button bookChairButton;
    }

}
