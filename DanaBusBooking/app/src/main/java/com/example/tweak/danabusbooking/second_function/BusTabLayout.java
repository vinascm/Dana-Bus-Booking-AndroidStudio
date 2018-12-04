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
import com.example.tweak.danabusbooking.adapters.XeBuytLookUpAdapter;
import com.example.tweak.danabusbooking.database_management.MainController;
import com.example.tweak.danabusbooking.database_management.controllers.tuyen_duong.TuyenDuong;
import com.example.tweak.danabusbooking.database_management.controllers.tuyen_duong.TuyenDuongController;
import com.example.tweak.danabusbooking.database_management.controllers.xe_buyt.XeBuyt;
import com.example.tweak.danabusbooking.database_management.controllers.xe_buyt.XeBuytController;

import java.util.ArrayList;

public class BusTabLayout extends Fragment {

    private ArrayList<XeBuyt> listXeBuytShowOnListView;
    private ArrayAdapter<XeBuyt> m_Adapter;

    private ListView busListView;
    private EditText busLookUpEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab_bus, container, false);

        busListView = view.findViewById(R.id.busLookUpListView);
        busLookUpEditText = view.findViewById(R.id.busLookUpEditText);

        //  Crucial
        updateTheListOfXeBuyt();

        m_Adapter = new XeBuytLookUpAdapter(getContext(), R.layout.item_bus_look_up_view,
                listXeBuytShowOnListView);

        busListView.setAdapter(m_Adapter);

        busListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                // Open new activity to show all the information of this route
                int maXeBuytLookUp = m_Adapter.getItem(position).maXeBuyt;

                Intent intent = new Intent(getContext(), BusLookUpActivity.class);
                intent.putExtra("maXeBuytLookUp", maXeBuytLookUp);
                startActivity(intent);
            }
        });

        // Event key up listener
        addEventKeyListenerForEditText();

        return view;
    }

    private void addEventKeyListenerForEditText() {
        busLookUpEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String letter = busLookUpEditText.getText().toString().trim();

                // Update the listView
                // Get all the maXeBuyt according to the letter above
                ArrayList<Integer> lookedUpMaXeBuytList = MainController.getInstance().getTheListOfMaXeBuytByTypingLetter(letter);

                // Get all the XeBuyt object according to the letter above
                ArrayList<XeBuyt> newListXeBuytShowOnListView = XeBuytController.getInstance().getListXeBuytShowOnListView(lookedUpMaXeBuytList);

                listXeBuytShowOnListView.clear();
                listXeBuytShowOnListView.addAll(newListXeBuytShowOnListView);

                // Refresh listView
                m_Adapter = new XeBuytLookUpAdapter(getContext(), R.layout.item_bus_look_up_view,
                        listXeBuytShowOnListView);

                busListView.setAdapter(m_Adapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void updateTheListOfXeBuyt() {
        listXeBuytShowOnListView = new ArrayList<>();

        for (XeBuyt xeBuyt : XeBuytController.getInstance().listXeBuyt) {
            listXeBuytShowOnListView.add(xeBuyt);
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
