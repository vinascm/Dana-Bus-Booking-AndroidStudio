package com.example.tweak.danabusbooking.home;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tweak.danabusbooking.first_function.FirstFunctionActivity;
import com.example.tweak.danabusbooking.R;
import com.example.tweak.danabusbooking.database_management.controllers.tuyen_duong.TuyenDuongData;
import com.example.tweak.danabusbooking.utils.SharedVariables;

import java.util.ArrayList;
import java.util.Random;

public class FunctionViewPageFragment1 extends Fragment {

    private static final int MIN_NUMBER_OF_ROUTE_CODE = 2;
    private static final int MAX_NUMBER_OF_ROUTE_CODE = 4 - MIN_NUMBER_OF_ROUTE_CODE;

    private EditText sourcePositionEditText;
    private EditText destinationPositionEditText;
    private Button getTheRoutesButton;

    private AnimationDrawable findAnimation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(
                R.layout.item_first_function_view, container, false);

        sourcePositionEditText = view.findViewById(R.id.sourcePositionEditText);
        destinationPositionEditText = view.findViewById(R.id.destinationPositionEditText);

        getTheRoutesButton = view.findViewById(R.id.getTheRoutesButton);
        getTheRoutesButton.setBackgroundResource(R.drawable.animation);
        findAnimation = (AnimationDrawable) getTheRoutesButton.getBackground();

        // animation setting
        view.post(new Runnable() {
            @Override
            public void run() {
                findAnimation.start();
            }
        });

        // Set event listener for button
        getTheRoutesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sourcePosition = sourcePositionEditText.getText().toString();
                String destinationPosition = destinationPositionEditText.getText().toString();

                if (sourcePosition.trim().length() == 0) {
                    Toast.makeText(getContext(), "Nhập điểm đi", Toast.LENGTH_LONG).show();
                    return;
                } else if (destinationPosition.trim().length() == 0) {
                    Toast.makeText(getContext(), "Nhập điểm đến", Toast.LENGTH_LONG).show();
                    return;
                }

                // Making a loading progress bar
                final ProgressDialog progressDialog = new ProgressDialog(getContext(),
                        R.style.loadingFinding);

                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Analyzing...");
                progressDialog.show();
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                new Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                SharedVariables.lastIndexFunctionPress = 0;

                                // Important data convey
                                SharedVariables.theListOfMaTuyenDuongThroughActivity = getTheListOfMaTuyenDuong();

                                progressDialog.dismiss();

                                Intent intent = new Intent(getContext(), FirstFunctionActivity.class);
                                startActivity(intent);
                            }
                        }, 4000);
            }
        });

        return view;
    }


    private ArrayList<Integer> getTheListOfMaTuyenDuong() {
        ArrayList<Integer> returnList = new ArrayList<>();

        // We calculate the distance between these two positions, then we would have to give
        // a list with the corresponding routes: Done by later
        // For now, return a list of random MaTuyenDuong

        ArrayList<Integer> listRandomMaTuyenDuong = new ArrayList<>();
        for (int i : TuyenDuongData.MA_TUYEN_DUONG)
            listRandomMaTuyenDuong.add(i);

        int numberOfMaTuyenDuong = MIN_NUMBER_OF_ROUTE_CODE + new Random().nextInt(MAX_NUMBER_OF_ROUTE_CODE);
        for (int i = 0; i < numberOfMaTuyenDuong; i++) {
            int randomIndex = new Random().nextInt(listRandomMaTuyenDuong.size());
            returnList.add(listRandomMaTuyenDuong.get(randomIndex));

            // Get rid of this randomIndex out of the list
            listRandomMaTuyenDuong.remove(randomIndex);
        }

        return returnList;
    }

}