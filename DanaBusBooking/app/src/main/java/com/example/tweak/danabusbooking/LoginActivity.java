package com.example.tweak.danabusbooking;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tweak.danabusbooking.database_management.controllers.khach_hang.KhachHangController;
import com.example.tweak.danabusbooking.utils.SharedVariables;

public class LoginActivity extends AppCompatActivity {

    private static final int REQUEST_SIGNUP = 0;

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private TextView signUpLinkTextView;
    private CheckBox rememberCheckBox;

    private SharedPreferences sp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.input_email);
        passwordEditText = findViewById(R.id.input_password);
        loginButton = findViewById(R.id.btn_login);
        signUpLinkTextView = findViewById(R.id.link_signup);
        rememberCheckBox = findViewById(R.id.rememberCheckBox);

        // Shared preference
        setSharedPreferences();

        // Event listener
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        signUpLinkTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
    }

    private void setSharedPreferences() {
        sp = getSharedPreferences("Secret", MODE_PRIVATE);

        String email = sp.getString("email", "");
        String password = sp.getString("password", "");
        boolean isChecked = sp.getBoolean("isChecked", false);

        rememberCheckBox.setChecked(isChecked);
        if (isChecked) {
            emailEditText.setText(email);
            passwordEditText.setText(password);
        }
    }

    public void login() {
        if (!validate()) {
            return;
        }

        loginButton.setEnabled(false);

        // Making a loading progress bar
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);

        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Đang xác thực...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        final String email = emailEditText.getText().toString();
        final String password = passwordEditText.getText().toString();
        final boolean isChecked = rememberCheckBox.isChecked();

        // Checking matching DB here
        final boolean idValidated = KhachHangController.getInstance().isThisUserInDB(email, password);

        new Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        if (idValidated) {
                            // Set up this important variable
                            SharedVariables.currentMaKhachHang = KhachHangController.getInstance().getMaKhachHangByEmail(email);
                            SharedVariables.lastIndexFunctionPress = 0;

                            onLoginSuccess();

                            // Save preference
                            saveSharedPreference(email,password,isChecked);
                        } else onLoginFailed();

                        progressDialog.dismiss();
                    }
                }, 3000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(getBaseContext(), "Đăng kí thành công! Đăng nhập ngay", Toast.LENGTH_LONG).show();

                String returnEmail = data.getStringExtra("returnEmail");
                emailEditText.setText(returnEmail);

                String returnPassword = data.getStringExtra("returnPassword");
                passwordEditText.setText(returnPassword);
            }
        }
    }

    private void saveSharedPreference(String email, String password, boolean isChecked) {
        SharedPreferences.Editor editor = sp.edit();

        editor.putString("email", email);
        editor.putString("password", password);
        editor.putBoolean("isChecked", isChecked);

        // Dont' forget to commit
        if (editor.commit()) {
            // return true means saved ok
            // Make toast
        }
    }

    public void onLoginSuccess() {
        loginButton.setEnabled(true);

        // Open home activity
        Intent intent = new Intent(this, HomeActivity.class);
        startActivityForResult(intent, REQUEST_SIGNUP);

        // Finish this activity
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Thông tin đăng nhập không chính xác", Toast.LENGTH_LONG).show();
        loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (email.trim().length() == 0 || email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Nhập địa chỉ email hợp lệ!");
            valid = false;
        } else {
            emailEditText.setError(null);
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