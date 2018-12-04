package com.example.tweak.danabusbooking.second_function;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.tweak.danabusbooking.R;
import com.example.tweak.danabusbooking.adapters.TuyenDuongLookUpAdapter;
import com.example.tweak.danabusbooking.database_management.MainController;
import com.example.tweak.danabusbooking.database_management.controllers.tuyen_duong.TuyenDuong;
import com.example.tweak.danabusbooking.database_management.controllers.tuyen_duong.TuyenDuongController;

import java.util.ArrayList;

public class RouteTabLayout extends Fragment {

    private ArrayList<TuyenDuong> listTuyenDuongShowOnListView;
    private ArrayAdapter<TuyenDuong> m_Adapter;

    private ListView routeListView;
    private EditText routeLookUpEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab_route, container, false);

        routeListView = view.findViewById(R.id.routeLookUpListView);
        routeLookUpEditText = view.findViewById(R.id.routeLookUpEditText);

        //  Crucial
        updateTheListOfTuyenDuong();

        m_Adapter = new TuyenDuongLookUpAdapter(getContext(), R.layout.item_route_look_up_view,
                listTuyenDuongShowOnListView);

        routeListView.setAdapter(m_Adapter);

        routeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                // Open new activity to show all the information of this route
                int maTuyenDuongLookUp = m_Adapter.getItem(position).maTuyenDuong;

                Intent intent = new Intent(getContext(), RouteLookUpActivity.class);
                intent.putExtra("maTuyenDuongLookUp", maTuyenDuongLookUp);
                startActivity(intent);
            }
        });

        // Event key up listener
        addEventKeyListenerForEditText();

        return view;
    }

    private void addEventKeyListenerForEditText() {
        routeLookUpEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String letter = routeLookUpEditText.getText().toString().trim();

                // Update the listView
                // Get all the maTuyenDuong according to the letter above
                ArrayList<Integer> lookedUpMaTuyenDuongList = MainController.getInstance().getTheListOfMaTuyenDuongByTypingLetter(letter);

                // Get all the TuyenDuong object according to the letter above
                ArrayList<TuyenDuong> newListTuyenDuongShowOnListView = TuyenDuongController.getInstance().getListTuyenDuongShowOnListView(lookedUpMaTuyenDuongList);

                listTuyenDuongShowOnListView.clear();
                listTuyenDuongShowOnListView.addAll(newListTuyenDuongShowOnListView);

                // Refresh listView
                m_Adapter = new TuyenDuongLookUpAdapter(getContext(), R.layout.item_route_look_up_view,
                        listTuyenDuongShowOnListView);

                routeListView.setAdapter(m_Adapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void updateTheListOfTuyenDuong() {
        listTuyenDuongShowOnListView = new ArrayList<>();

        for (TuyenDuong tuyenDuong : TuyenDuongController.getInstance().listTuyenDuong) {
            listTuyenDuongShowOnListView.add(tuyenDuong);
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
