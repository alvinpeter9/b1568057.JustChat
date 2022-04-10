package uk.ac.tees.b1568057.justchat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OTPActivity extends Activity {

    private EditText inputOtp1, inputOtp2, inputOtp3, inputOtp4, inputOtp5, inputOtp6;
    private String verificationId;
    ImageView BackBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_p);

        inputOtp1 = findViewById(R.id.otpCode1);
        inputOtp2 = findViewById(R.id.otpCode2);
        inputOtp3 = findViewById(R.id.otpCode3);
        inputOtp4 = findViewById(R.id.otpCode4);
        inputOtp5 = findViewById(R.id.otpCode5);
        inputOtp6 = findViewById(R.id.otpCode6);

        BackBtn = findViewById(R.id.backBtn);

        BackBtn.setOnClickListener(view -> {
           Intent intent = new Intent(getApplicationContext(), VerificationActivity.class);

           startActivity(intent);
        });

        TextView userNumber = findViewById(R.id.userNumber);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("mobile");
            userNumber.setText(value);
        }

        setupOtpInputs();

        final ProgressBar progressBar = findViewById(R.id.progressBar);
        final Button buttonVerify = findViewById(R.id.verifyOtpBtn);

        verificationId = getIntent().getStringExtra("Verificationid");
        buttonVerify.setOnClickListener(view -> {
            if(inputOtp1.getText().toString().trim().isEmpty()
                    || inputOtp2.getText().toString().trim().isEmpty()
                    || inputOtp3.getText().toString().trim().isEmpty()
                    || inputOtp4.getText().toString().trim().isEmpty()
                    || inputOtp5.getText().toString().trim().isEmpty()
                    || inputOtp6.getText().toString().trim().isEmpty()){
                Toast.makeText(OTPActivity.this, "Please enter a valid code", Toast.LENGTH_SHORT).show();
                return;
            }
            String code =
                    inputOtp1.getText().toString() +
                    inputOtp2.getText().toString() +
                    inputOtp3.getText().toString() +
                    inputOtp4.getText().toString() +
                    inputOtp5.getText().toString() +
                    inputOtp6.getText().toString();

            if(verificationId != null){
                progressBar.setVisibility(View.VISIBLE);
                buttonVerify.setVisibility(View.INVISIBLE);
                PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                        verificationId,
                        code
                );
                FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                buttonVerify.setVisibility(View.VISIBLE);
                                if(task.isSuccessful()){
                                    Intent intent = new Intent(getApplicationContext(), SetProfile.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }else {
                                    Toast.makeText(OTPActivity.this, "The OTP code entered was invalid", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        findViewById(R.id.resend_otp).setOnClickListener(view -> {
            PhoneAuthOptions options =
                    PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance()).setPhoneNumber(getIntent().getStringExtra("mobile"))
                            .setTimeout(60L, TimeUnit.SECONDS).setActivity(OTPActivity.this)
                            .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                @Override
                                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                }

                                @Override
                                public void onVerificationFailed(@NonNull FirebaseException e) {
                                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onCodeSent(@NonNull String newVerificationid, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                   verificationId = newVerificationid;
                                    Toast.makeText(OTPActivity.this, "OTP Sent", Toast.LENGTH_SHORT).show();
                                }
                            }).build();

            PhoneAuthProvider.verifyPhoneNumber(options);
        });

    }

    private void setupOtpInputs() {
        inputOtp1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (!s.toString().trim().isEmpty()){
                    inputOtp2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputOtp2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (!s.toString().trim().isEmpty()){
                    inputOtp3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputOtp3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (!s.toString().trim().isEmpty()){
                    inputOtp4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputOtp4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (!s.toString().trim().isEmpty()){
                    inputOtp5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputOtp5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (!s.toString().trim().isEmpty()){
                    inputOtp6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

}
