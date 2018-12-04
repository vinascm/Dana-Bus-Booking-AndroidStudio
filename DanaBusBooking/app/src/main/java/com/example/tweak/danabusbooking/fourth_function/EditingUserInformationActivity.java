package com.example.tweak.danabusbooking.fourth_function;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.tweak.danabusbooking.R;
import com.example.tweak.danabusbooking.database_management.controllers.khach_hang.KhachHang;
import com.example.tweak.danabusbooking.database_management.controllers.khach_hang.KhachHangController;
import com.example.tweak.danabusbooking.utils.ConvertImageData;
import com.example.tweak.danabusbooking.utils.SharedVariables;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;


public class EditingUserInformationActivity extends AppCompatActivity {

    private final int CAMERA_REQUEST_CODE = 1;
    private final int SELECT_FILE_REQUEST_CODE = 2;

    private EditText tenEditText;
    private EditText tuoiEditText;

    private RadioButton namRadioButton;
    private RadioButton nuRadioButton;
    private RadioButton kxdRadioButton;

    private EditText diaChiEditText;
    private EditText ngheNghiepEditText;
    private EditText passwordEditText;

    private ImageView m_AvatarIllustrationImageView;

    private Button updateUserInformationButton;

    private KhachHang referenceKhachHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        // Initializing variables and assigning data to them
        initializeVariables();

        referenceKhachHang = KhachHangController.getInstance().getKhachHangByMaKhachHang(SharedVariables.currentMaKhachHang);
        assignDataToViews(referenceKhachHang);
    }

    private void initializeVariables() {
        tenEditText = findViewById(R.id.tenEditText);
        tuoiEditText = findViewById(R.id.tuoiEditText);

        namRadioButton = findViewById(R.id.namRadioButton);
        nuRadioButton = findViewById(R.id.nuRadioButton);
        kxdRadioButton = findViewById(R.id.kxdRadioButton);

        diaChiEditText = findViewById(R.id.diaChiEditText);
        ngheNghiepEditText = findViewById(R.id.ngheNghiepEditText);
        passwordEditText = findViewById(R.id.passwordEditText);

        m_AvatarIllustrationImageView = findViewById(R.id.avatarIllustrationImageView);

        updateUserInformationButton = findViewById(R.id.updateUserInformationButton);

        updateUserInformationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    // Making a loading progress bar
                    final ProgressDialog progressDialog = new ProgressDialog(EditingUserInformationActivity.this,
                            R.style.AppTheme_Dark_Dialog);

                    progressDialog.setIndeterminate(true);
                    progressDialog.setMessage("Xin chờ...");
                    progressDialog.show();
                    progressDialog.setCancelable(false);
                    progressDialog.setCanceledOnTouchOutside(false);

                    new Handler().postDelayed(
                            new Runnable() {
                                public void run() {
                                    // Update in DB
                                    referenceKhachHang.tenKhachHang = tenEditText.getText().toString().trim();
                                    referenceKhachHang.tuoi = tuoiEditText.getText().toString().trim();

                                    if (namRadioButton.isChecked())
                                        referenceKhachHang.gioiTinh = "nam";
                                    else if (nuRadioButton.isChecked())
                                        referenceKhachHang.gioiTinh = "nu";
                                    else if (kxdRadioButton.isChecked())
                                        referenceKhachHang.gioiTinh = "kxd";

                                    referenceKhachHang.maUuDai = getMaUuDaiForKhachHangByTuoi(Integer.parseInt(tuoiEditText.getText().toString()));

                                    referenceKhachHang.diaChi = diaChiEditText.getText().toString().trim();
                                    referenceKhachHang.ngheNghiep = ngheNghiepEditText.getText().toString().trim();
                                    referenceKhachHang.password = passwordEditText.getText().toString().trim();

                                    referenceKhachHang.anhNhanDien = ConvertImageData.imageViewToBytes(m_AvatarIllustrationImageView);

                                    KhachHangController.getInstance().updateData(referenceKhachHang);

                                    System.out.println(referenceKhachHang);
                                    Toast.makeText(getBaseContext(), "Thông tin đã được cập nhật!", Toast.LENGTH_LONG).show();


                                    progressDialog.dismiss();

                                    setResult(RESULT_OK);
                                    // Close this Activity
                                    finish();

                                }
                            }, 2500);
                }
            }
        });
    }

    private int getMaUuDaiForKhachHangByTuoi(int tuoi) {
        if (tuoi <= 5)
            return 1;
        else if (tuoi <= 12)
            return 2;
        else if (tuoi >= 60)
            return 3;
        else return 4;
    }

    private void assignDataToViews(KhachHang khachHang) {
        tenEditText.setText(khachHang.tenKhachHang);
        tuoiEditText.setText(khachHang.tuoi);

        if (khachHang.gioiTinh.equalsIgnoreCase("nam"))
            namRadioButton.setChecked(true);
        else if (khachHang.gioiTinh.equalsIgnoreCase("nu"))
            nuRadioButton.setChecked(true);
        else if (khachHang.gioiTinh.equalsIgnoreCase("kxd"))
            kxdRadioButton.setChecked(true);

        diaChiEditText.setText(khachHang.diaChi);
        ngheNghiepEditText.setText(khachHang.ngheNghiep);
        passwordEditText.setText(khachHang.password);

        if (khachHang.anhNhanDien != null)
            m_AvatarIllustrationImageView.setImageBitmap(ConvertImageData.bytesToBitmap(khachHang.anhNhanDien));
    }

    // Picking an avatar picture
    public void selectImage(View view) {
        final CharSequence[] items = {"Camera", "Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(EditingUserInformationActivity.this, R.style.DialogAnimationTheme1);

        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (items[i].equals("Camera")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, CAMERA_REQUEST_CODE);
                } else if (items[i].equals("Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");

                    startActivityForResult(intent, SELECT_FILE_REQUEST_CODE);
                } else if (items[i].equals("Cancel")) {
                    dialogInterface.dismiss();
                }
            }
        });

        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == CAMERA_REQUEST_CODE) {
                Bundle bundle = data.getExtras();
                Bitmap bmp = (Bitmap) bundle.get("data");

                m_AvatarIllustrationImageView.setImageBitmap(bmp);
            } else if (requestCode == SELECT_FILE_REQUEST_CODE) {
                Uri selectedImageUri = data.getData();

                // Open activity for image cropping
                CropImage.activity(selectedImageUri)
                        .setGuidelines(CropImageView.Guidelines.ON) //enable image guidlines
                        .start(this);
            } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);

                if (resultCode == RESULT_OK) {

                    Uri resultUri = result.getUri();
                    m_AvatarIllustrationImageView.setImageURI(resultUri);
                }
            }
        }
    }

    public boolean validate() {
        boolean valid = true;

        String ten = tenEditText.getText().toString();
        String tuoi = tuoiEditText.getText().toString();
        boolean isGioiTinhChecked = (namRadioButton.isChecked() || nuRadioButton.isChecked() || kxdRadioButton.isChecked());
        String diaChi = diaChiEditText.getText().toString();
        String ngheNghiep = ngheNghiepEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (ten.isEmpty() || ten.trim().length() < 3) {
            tenEditText.setError("Ít nhất 3 kí tự");
            valid = false;
        } else {
            tenEditText.setError(null);
        }

        if (tuoi.trim().length() == 0 || tuoi.isEmpty()) {
            tuoiEditText.setError("Nhập tuổi!");
            valid = false;
        } else {
            tuoiEditText.setError(null);
        }

        if (!isGioiTinhChecked) {
            Toast.makeText(getBaseContext(), "Chọn giới tính!", Toast.LENGTH_LONG).show();
            valid = false;
        }

        if (diaChi.trim().length() == 0 || diaChi.isEmpty()) {
            diaChiEditText.setError("Nhập địa chỉ!");
            valid = false;
        } else {
            diaChiEditText.setError(null);
        }

        if (ngheNghiep.trim().length() == 0 || ngheNghiep.isEmpty()) {
            ngheNghiepEditText.setError("Nhập nghề nghiệp!");
            valid = false;
        } else {
            ngheNghiepEditText.setError(null);
        }

        if (password.trim().length() == 0 || password.isEmpty() || password.length() < 4 || password.length() > 10) {
            passwordEditText.setError("Từ 4 đến 10 kí tự");
            valid = false;
        } else {
            passwordEditText.setError(null);
        }

        return valid;
    }

}
