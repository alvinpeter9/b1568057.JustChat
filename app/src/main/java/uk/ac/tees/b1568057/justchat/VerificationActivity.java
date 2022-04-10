package uk.ac.tees.b1568057.justchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;

public class VerificationActivity extends AppCompatActivity {

    CountryCodePicker mcountrycodepicker;
    String countrycode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        final EditText inputMobile = findViewById(R.id.editNumber);
        Button sendOtpBtn = findViewById(R.id.sendotpBtn);
        mcountrycodepicker = findViewById(R.id.countrycodepicker);

        countrycode = mcountrycodepicker.getSelectedCountryCodeWithPlus();

        final ProgressBar progressBar = findViewById(R.id.progressBar);

        mcountrycodepicker.setOnCountryChangeListener(() -> countrycode=mcountrycodepicker.getSelectedCountryCodeWithPlus());

        sendOtpBtn.setOnClickListener(view -> {
            if (inputMobile.getText().toString().trim().isEmpty()) {
                Toast.makeText(VerificationActivity.this, "Enter Mobile Number", Toast.LENGTH_SHORT).show();
                return;
            }
            progressBar.setVisibility(View.VISIBLE);
            sendOtpBtn.setVisibility(View.INVISIBLE);


            PhoneAuthOptions options =
                    PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance()).setPhoneNumber(countrycode+inputMobile.getText().toString())
                            .setTimeout(60L, TimeUnit.SECONDS).setActivity(this)
                            .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                @Override
                                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                    progressBar.setVisibility(View.GONE);
                                    sendOtpBtn.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onVerificationFailed(@NonNull FirebaseException e) {
                                    progressBar.setVisibility(View.GONE);
                                    sendOtpBtn.setVisibility(View.VISIBLE);
                                    Toast.makeText(VerificationActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onCodeSent(@NonNull String Verificationid, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                    progressBar.setVisibility(View.GONE);
                                    sendOtpBtn.setVisibility(View.VISIBLE);
                                    Intent intent = new Intent(getApplicationContext(), OTPActivity.class);
                                    intent.putExtra("mobile", countrycode+inputMobile.getText().toString());
                                    intent.putExtra("Verificationid", Verificationid);
                                    startActivity(intent);
                                }
                            }).build();

            PhoneAuthProvider.verifyPhoneNumber(options);

        });
    }


//    Check if the user is already registered and if YES, redirect to chat instead

//    @Override
//    protected void onStart() {
//        super.onStart();
//        if(FirebaseAuth.getInstance().getCurrentUser()!=null)
//        {
//            Toast.makeText(this, "Welcome back " + FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber(), Toast.LENGTH_SHORT).show();
//            Intent intent= new Intent(getApplicationContext(), MainActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(intent);
//        }
//
//    }
}