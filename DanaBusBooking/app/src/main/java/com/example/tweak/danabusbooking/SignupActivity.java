package com.example.tweak.danabusbooking;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tweak.danabusbooking.database_management.controllers.khach_hang.KhachHang;
import com.example.tweak.danabusbooking.database_management.controllers.khach_hang.KhachHangController;

public class SignupActivity extends AppCompatActivity {

    private EditText nameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button signUpButton;
    private TextView loginLinkTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        nameEditText = findViewById(R.id.input_name);
        emailEditText = findViewById(R.id.input_email);
        passwordEditText = findViewById(R.id.input_password);
        signUpButton = findViewById(R.id.btn_signup);
        loginLinkTextView = findViewById(R.id.link_login);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        loginLinkTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });
    }

    public void signup() {
        if (!validate()) {
            return;
        }

        signUpButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);

        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Đang khởi tạo...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        final String name = nameEditText.getText().toString().trim();
        final String email = emailEditText.getText().toString().trim();
        final String password = passwordEditText.getText().toString().trim();

        // Inserting new account to DB
        final boolean isThisEmailInDB = KhachHangController.getInstance().isThisEmailInDB(email);
        // Update new user to DB
        if (!isThisEmailInDB) {
            KhachHang newKhachHang = new KhachHang(name, "", "", null, "", "",
                    email, password, 4);
            KhachHangController.getInstance().insertData(newKhachHang);
        }

        new Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        if (!isThisEmailInDB)
                            onSignupSuccess(email, password);
                        else
                            onSignupFailed();

                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    public void onSignupSuccess(String returnEmail, String returnPassword) {
        signUpButton.setEnabled(true);

        // Return back to the login activity
        Intent goingBack = new Intent();

        goingBack.putExtra("returnEmail", returnEmail);
        goingBack.putExtra("returnPassword", returnPassword);

        setResult(RESULT_OK, goingBack);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Địa chỉ email này đã được dăng kí. Vui lòng chọn địa chỉ khác!", Toast.LENGTH_LONG).show();
        signUpButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = nameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (name.isEmpty() || name.trim().length() < 3) {
            nameEditText.setError("Ít nhất 3 kí tự");
            valid = false;
        } else {
            nameEditText.setError(null);
        }

        if (name.trim().length() == 0 || email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Nhập địa chỉ email hợp lệ!");
            valid = false;
        } else {
            emailEditText.setError(null);
        }

        if (password.isEmpty() || password.trim().length() < 4 || password.trim().length() > 10) {
            passwordEditText.setError("Từ 4 đến 10 kí tự");
            valid = false;
        } else {
            passwordEditText.setError(null);
        }

        return valid;
    }
}