package com.example.tweak.danabusbooking.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.tweak.danabusbooking.R;
import com.example.tweak.danabusbooking.database_management.controllers.khach_hang_ve.KhachHangVe;
import com.example.tweak.danabusbooking.database_management.controllers.khach_hang_ve.KhachHangVeController;
import com.example.tweak.danabusbooking.models.Ticket;
import com.example.tweak.danabusbooking.third_function.TicketDetailActivity;
import com.example.tweak.danabusbooking.utils.SharedVariables;

import java.util.ArrayList;

public class BookedCanceledTicketAdapter extends ArrayAdapter<Ticket> {

    private Context m_Context;
    private int m_Layout;
    private ArrayList<Ticket> m_List;

    public BookedCanceledTicketAdapter(Context context, int resource, ArrayList<Ticket> list) {
        super(context, resource, list);

        this.m_Context = context;
        this.m_Layout = resource;
        this.m_List = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null) {

            //create view
            LayoutInflater inflater = LayoutInflater.from(m_Context);
            convertView = inflater.inflate(m_Layout, parent, false);

            //get child
            viewHolder = new ViewHolder();

            viewHolder.trangThaiVeButton = convertView.findViewById(R.id.trangThaiVeButton);
            viewHolder.tenTuyenTextView = convertView.findViewById(R.id.tenTuyenTextView);
            viewHolder.thongTinXeTextView = convertView.findViewById(R.id.thongTinXeTextView);
            viewHolder.thongTinNgayTextView = convertView.findViewById(R.id.thongTinNgayTextView);
            viewHolder.viewDetailButton = convertView.findViewById(R.id.viewDetailButton);

            //save
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Putting data
        final Ticket ticketBookedCanceled = m_List.get(position);

        viewHolder.tenTuyenTextView.setText(ticketBookedCanceled.tenTuyen);
        viewHolder.thongTinNgayTextView.setText(ticketBookedCanceled.thongTinNgay);

        final KhachHangVe referenceKhachHangVe = KhachHangVeController.getInstance()
                .getAKhachHangVeByMaVeAndMaKhachHang(ticketBookedCanceled.maVe, SharedVariables.currentMaKhachHang);

        // Setting data and the canceling ticket event listener
        if (ticketBookedCanceled.tinhTrang == 1) {
            viewHolder.trangThaiVeButton.setText("Đã đặt");
            viewHolder.thongTinNgayTextView.setText("Ngày đặt: " + ticketBookedCanceled.thongTinNgay);
        } else {
            viewHolder.trangThaiVeButton.setText("Đã hủy");
            viewHolder.thongTinNgayTextView.setText("Ngày hủy: " + ticketBookedCanceled.thongTinNgay);
        }

        // View detail setting
        viewHolder.viewDetailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Important convey data
                SharedVariables.khachHangVeDetail = referenceKhachHangVe;

                Intent intent = new Intent(getContext(), TicketDetailActivity.class);
                m_Context.startActivity(intent);
            }
        });

        return convertView;
    }

    private class ViewHolder {
        public Button trangThaiVeButton;
        public TextView tenTuyenTextView;
        public TextView thongTinXeTextView;
        public TextView thongTinNgayTextView;
        public Button viewDetailButton;
    }

}
