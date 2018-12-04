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
import com.example.tweak.danabusbooking.adapters.BookedCanceledTicketAdapter;
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

import java.util.ArrayList;

public class BookedTicketTabLayout extends Fragment {

    private ArrayList<Ticket> listBookedTicketShowOnListView;
    private ArrayAdapter<Ticket> m_Adapter;

    // This is used here to come in handy when searching
    private ArrayList<Ticket> listBookedTicketHolder;

    private ListView bookedTicketListView;
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
        View view = inflater.inflate(R.layout.fragment_tab_booked_ticket, container, false);

        bookedTicketListView = view.findViewById(R.id.bookedTicketListView);
        ticketEditText = view.findViewById(R.id.ticketEditText);
        emptyIllustrationImageView = view.findViewById(R.id.emptyIllustrationImageView);
        containerLayout = view.findViewById(R.id.containerLayout);

        letter = "";

        // Event key up listener
        addEventKeyListenerForEditText();

        //  Crucial
        updateTheListOfTodayTicket();

        m_Adapter = new BookedCanceledTicketAdapter(getContext(), R.layout.item_booked_ticket_view,
                listBookedTicketShowOnListView);

        bookedTicketListView.setAdapter(m_Adapter);

        bookedTicketListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
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
                                Ticket deletingTicketBooked = listBookedTicketShowOnListView.get(position);
                                KhachHangVeController.getInstance().deleteData(deletingTicketBooked.maVe);

                                // Refresh
                                listBookedTicketShowOnListView.remove(deletingTicketBooked);
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
        listBookedTicketHolder = new ArrayList<>();

        // Get data by its type
        ArrayList<KhachHangVe> listKhachHangVeShowOnListView = KhachHangVeController.getInstance().
                getListKhachHangVeByMaKhachHang(SharedVariables.currentMaKhachHang, 1, false);

        // Push data into the main list
        for (KhachHangVe khachHangVe : listKhachHangVeShowOnListView) {
            Ticket ticketToday = new Ticket(khachHangVe.tinhTrang, getTenTuyen(khachHangVe.maGheNgoi),
                    getThongTinXe(khachHangVe.maGheNgoi), getThongTinNgay(khachHangVe), khachHangVe.maVe, khachHangVe.maGheNgoi);
            listBookedTicketHolder.add(ticketToday);
        }

        // Update this list
        listBookedTicketShowOnListView = new ArrayList<>();
        for (Ticket ticketToday : listBookedTicketHolder)
            listBookedTicketShowOnListView.add(ticketToday);
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
        if (listBookedTicketShowOnListView.size() != 0) {
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
        listBookedTicketShowOnListView.clear();

        for (int i = 0; i < listBookedTicketHolder.size(); i++)
            if (listBookedTicketHolder.get(i).doesThisSearchStringContainThisLetter(letter))
                listBookedTicketShowOnListView.add((listBookedTicketHolder.get(i)));

        // Refreshing data
        m_Adapter = new BookedCanceledTicketAdapter(getContext(), R.layout.item_booked_ticket_view,
                listBookedTicketShowOnListView);

        bookedTicketListView.setAdapter(m_Adapter);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}