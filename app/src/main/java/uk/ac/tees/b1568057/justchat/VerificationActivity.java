package uk.ac.tees.b1568057.justchat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class VerificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        final EditText inputMobile = findViewById(R.id.editNumber);
        Button sendOtpBtn = findViewById(R.id.sendotpBtn);

        sendOtpBtn.setOnClickListener(view -> {
            if (inputMobile.getText().toString().trim().isEmpty()) {
                Toast.makeText(VerificationActivity.this, "Enter Mobile Number", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(getApplicationContext(), OTPActivity.class);
            intent.putExtra("mobile", inputMobile.getText().toString());
            startActivity(intent);
        });
    }
}