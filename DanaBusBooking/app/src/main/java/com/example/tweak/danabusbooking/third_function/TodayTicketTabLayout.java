package com.example.tweak.danabusbooking.third_function;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.tweak.danabusbooking.R;
import com.example.tweak.danabusbooking.adapters.TodayTicketAdapter;
import com.example.tweak.danabusbooking.database_management.MainController;
import com.example.tweak.danabusbooking.database_management.controllers.ghe_ngoi.GheNgoi;
import com.example.tweak.danabusbooking.database_management.controllers.ghe_ngoi.GheNgoiController;
import com.example.tweak.danabusbooking.database_management.controllers.khach_hang_ve.KhachHangVe;
import com.example.tweak.danabusbooking.database_management.controllers.khach_hang_ve.KhachHangVeController;
import com.example.tweak.danabusbooking.database_management.controllers.tuyen_duong.TuyenDuongController;
import com.example.tweak.danabusbooking.database_management.controllers.xe_buyt.XeBuyt;
import com.example.tweak.danabusbooking.database_management.controllers.xe_buyt.XeBuytController;
import com.example.tweak.danabusbooking.models.Ticket;
import com.example.tweak.danabusbooking.utils.SharedVariables;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class TodayTicketTabLayout extends Fragment {

    private ArrayList<Ticket> listTodayTicketShowOnListView;
    private ArrayAdapter<Ticket> m_Adapter;

    // This is used here to come in handy when searching
    private ArrayList<Ticket> listTodayTicketHolder;

    private ListView todayTicketListView;
    private EditText ticketEditText;

    // Controlling hide show layout
    private ImageView emptyIllustrationImageView;
    private LinearLayout containerLayout;

    // Keeping track of the string letter
    private String letter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab_today_ticket, container, false);

        todayTicketListView = view.findViewById(R.id.todayTicketListView);
        ticketEditText = view.findViewById(R.id.ticketEditText);
        emptyIllustrationImageView = view.findViewById(R.id.emptyIllustrationImageView);
        containerLayout = view.findViewById(R.id.containerLayout);

        letter = "";

        // Event key up listener
        addEventKeyListenerForEditText();

        //  Crucial
        updateTheListOfTodayTicket();

        m_Adapter = new TodayTicketAdapter(getContext(), R.layout.item_today_ticket_view,
                listTodayTicketShowOnListView);

        todayTicketListView.setAdapter(m_Adapter);

        todayTicketListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> adapterView, View view,
                                           final int position, long id) {
                PopupMenu popup = new PopupMenu(getContext(), view, Gravity.RIGHT);
                popup.getMenuInflater().inflate(R.menu.menu_conversation_sentence_information, popup.getMenu());
                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            // Delete the item according to its position
                            case R.id.delete:
                                // Delete in DB
                                Ticket deletingTicketToday = listTodayTicketShowOnListView.get(position);
                                KhachHangVeController.getInstance().deleteData(deletingTicketToday.maVe);

                                // Refresh
                                listTodayTicketShowOnListView.remove(deletingTicketToday);
                                m_Adapter.notifyDataSetChanged();

                                break;
                        }

                        return true;
                    }
                });
                popup.show();
                return true;
            }
        });

        CheckEmptyData();

        return view;
    }

    private void addEventKeyListenerForEditText() {
        ticketEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                letter = ticketEditText.getText().toString().trim();

                refreshTheListViewByLetter();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void updateTheListOfTodayTicket() {
        listTodayTicketHolder = new ArrayList<>();

        // Get data by its type
        ArrayList<KhachHangVe> listKhachHangVeShowOnListView = KhachHangVeController.getInstance().
                getListKhachHangVeByMaKhachHang(SharedVariables.currentMaKhachHang, 1, true);

        // Push data into the main list
        for (KhachHangVe khachHangVe : listKhachHangVeShowOnListView) {
            Ticket ticketToday = new Ticket(khachHangVe.tinhTrang, getTenTuyen(khachHangVe.maGheNgoi),
                    getThongTinXe(khachHangVe.maGheNgoi), getThongTinNgay(khachHangVe), khachHangVe.maVe, khachHangVe.maGheNgoi);
            listTodayTicketHolder.add(ticketToday);
        }

        // Update the exact list today ticket holder
        listTodayTicketHolder = getTheUpdateTheListOfOnlyTodayTicket(listTodayTicketHolder);

        // Update this list
        listTodayTicketShowOnListView = new ArrayList<>();
        for (Ticket ticketToday : listTodayTicketHolder)
            listTodayTicketShowOnListView.add(ticketToday);
    }

    private String getTenTuyen(int maGheNgoi) {
        // Link tables together and get data
        int maTuyenDuongOfThisVe = MainController.getInstance().getMaTuyenDuongByMaGheNgoi(maGheNgoi);
        return "Tên tuyến: " + TuyenDuongController.getInstance().getTenTuyenDuongByMaTuyenDuong(maTuyenDuongOfThisVe);
    }

    private String getThongTinXe(int maGheNgoi) {
        int maXeBuyt = GheNgoiController.getInstance().getMaXeBuytByMaGheNgoi(maGheNgoi);

        GheNgoi gheNgoi = GheNgoiController.getInstance().getGheNgoiByMaGheNgoi(maGheNgoi);
        XeBuyt xebuyt = XeBuytController.getInstance().getXeBuytByMaXeBuyt(maXeBuyt);

        return "Ghế ngồi: " + gheNgoi.soHieuGhe + ". Xe " + xebuyt.soHieuXe + "-" + xebuyt.bienSoXe;
    }

    private String getThongTinNgay(KhachHangVe khachHangVe) {
        return khachHangVe.tinhTrang == 1 ? khachHangVe.ngayDat : khachHangVe.ngayHuy;
    }

    private void CheckEmptyData() {
        if (listTodayTicketShowOnListView.size() != 0) {
            containerLayout.setVisibility(View.VISIBLE);
            emptyIllustrationImageView.setVisibility(View.GONE);
        } else {
            containerLayout.setVisibility(View.GONE);
            emptyIllustrationImageView.setVisibility(View.VISIBLE);
        }
    }

    public void refreshThisFragment() {
        //  Crucial
        updateTheListOfTodayTicket();

        refreshTheListViewByLetter();

        CheckEmptyData();
    }

    private void refreshTheListViewByLetter() {
        // Update the new data
        listTodayTicketShowOnListView.clear();

        for (int i = 0; i < listTodayTicketHolder.size(); i++)
            if (listTodayTicketHolder.get(i).doesThisSearchStringContainThisLetter(letter))
                listTodayTicketShowOnListView.add((listTodayTicketHolder.get(i)));

        // Refreshing data
        m_Adapter = new TodayTicketAdapter(getContext(), R.layout.item_today_ticket_view,
                listTodayTicketShowOnListView);

        todayTicketListView.setAdapter(m_Adapter);
    }

    private ArrayList<Ticket> getTheUpdateTheListOfOnlyTodayTicket(ArrayList<Ticket> listTodayTicketHolder) {
        String todayDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
                .format(Calendar.getInstance().getTime()).toString().split("\\s+")[0];

        ArrayList<Ticket> returnList = new ArrayList<>();

        for (Ticket t : listTodayTicketHolder) {
            String dateBooking = t.thongTinNgay.split("\\s+")[0];

            if (dateBooking.equalsIgnoreCase(todayDate))
                returnList.add(t);
        }

        return returnList;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}