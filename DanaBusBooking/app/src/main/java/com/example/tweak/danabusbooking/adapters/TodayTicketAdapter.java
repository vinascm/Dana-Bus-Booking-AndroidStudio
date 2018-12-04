package com.example.tweak.danabusbooking.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.tweak.danabusbooking.R;
import com.example.tweak.danabusbooking.database_management.controllers.ghe_ngoi.GheNgoi;
import com.example.tweak.danabusbooking.database_management.controllers.ghe_ngoi.GheNgoiController;
import com.example.tweak.danabusbooking.database_management.controllers.khach_hang_ve.KhachHangVe;
import com.example.tweak.danabusbooking.database_management.controllers.khach_hang_ve.KhachHangVeController;
import com.example.tweak.danabusbooking.database_management.controllers.ve.Ve;
import com.example.tweak.danabusbooking.database_management.controllers.ve.VeController;
import com.example.tweak.danabusbooking.models.Ticket;
import com.example.tweak.danabusbooking.third_function.TicketDetailActivity;
import com.example.tweak.danabusbooking.utils.SharedVariables;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class TodayTicketAdapter extends ArrayAdapter<Ticket> {

    private Context m_Context;
    private int m_Layout;
    private ArrayList<Ticket> m_List;

    public TodayTicketAdapter(Context context, int resource, ArrayList<Ticket> list) {
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
            viewHolder.cancelTicketButton = convertView.findViewById(R.id.cancelTicketButton);
            viewHolder.viewDetailButton = convertView.findViewById(R.id.viewDetailButton);

            //save
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Putting data
        final Ticket ticketToday = m_List.get(position);

        viewHolder.tenTuyenTextView.setText(ticketToday.tenTuyen);
        viewHolder.thongTinXeTextView.setText(ticketToday.thongTinXe);
        viewHolder.thongTinNgayTextView.setText(ticketToday.thongTinNgay);

        final KhachHangVe referenceKhachHangVe = KhachHangVeController.getInstance()
                .getAKhachHangVeByMaVeAndMaKhachHang(ticketToday.maVe, SharedVariables.currentMaKhachHang);

        // Setting data and the canceling ticket event listener
        if (ticketToday.tinhTrang == 1) {
            viewHolder.trangThaiVeButton.setText("Đã đặt");
            viewHolder.trangThaiVeButton.setBackground(getContext().getResources().getDrawable(R.drawable.button_background_rounded_view_ticket_booked));
            viewHolder.thongTinNgayTextView.setText("Ngày đặt: " + ticketToday.thongTinNgay);
            viewHolder.cancelTicketButton.setVisibility(View.VISIBLE);

            viewHolder.cancelTicketButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder deleteDialog = new AlertDialog.Builder(getContext(), R.style.DialogAnimationTheme1);

                    deleteDialog.setTitle("Chú ý!!!");
                    deleteDialog.setMessage("Bạn có chắc chắc hủy vé không?");

                    deleteDialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // Proceed the cancelling process in DB
                            // Once this ticket is canceled, we have to update:

                            // First: the maGhe in DB to be 0 (available)
                            GheNgoi updatedGheNgoi = GheNgoiController.getInstance().getGheNgoiByMaGheNgoi(ticketToday.maGheNgoi);
                            updatedGheNgoi.tinhTrang = 0;
                            GheNgoiController.getInstance().updateData(updatedGheNgoi);

                            // Second: the maVe in DB to be 0 (available)
                            Ve updatedVe = VeController.getInstance().getVeByMaVe(ticketToday.maVe);
                            updatedVe.tinhTrang = 0;
                            VeController.getInstance().updateData(updatedVe);

                            // And third: the ngayDat in KhachHangVe to be now
                            String ngayHuy = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime()).toString();
                            referenceKhachHangVe.ngayHuy = ngayHuy;
                            referenceKhachHangVe.tinhTrang = 0;
                            KhachHangVeController.getInstance().updateData(referenceKhachHangVe);

                            // Finally
                            ticketToday.tinhTrang = 0;
                            ticketToday.thongTinNgay = ngayHuy;

                            // UI Setting
                            viewHolder.trangThaiVeButton.setText("Đã hủy");
                            viewHolder.trangThaiVeButton.setBackground(getContext().getResources().getDrawable(R.drawable.button_background_rounded_view_ticket_canceled));
                            viewHolder.thongTinNgayTextView.setText("Ngày hủy: " + ngayHuy);
                            viewHolder.cancelTicketButton.setVisibility(View.GONE);
                        }
                    });

                    deleteDialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });

                    deleteDialog.show();
                }
            });
        } else {
            viewHolder.trangThaiVeButton.setText("Đã hủy");
            viewHolder.trangThaiVeButton.setBackground(getContext().getResources().getDrawable(R.drawable.button_background_rounded_view_ticket_canceled));
            viewHolder.thongTinNgayTextView.setText("Ngày hủy: " + ticketToday.thongTinNgay);
            viewHolder.cancelTicketButton.setVisibility(View.GONE);
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
        public Button cancelTicketButton;
        public Button viewDetailButton;
    }

}
