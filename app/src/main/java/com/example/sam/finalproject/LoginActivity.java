package com.example.sam.finalproject;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.provider.SyncStateContract;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import Helper.Helper;

import static Helper.Helper.displayMessageToast;

public class LoginActivity extends AppCompatActivity {
    private static  final String TAG = LoginActivity.class.getSimpleName();

    private EditText emailInput;
    private EditText passwordInput;

    private TextView signUpTV;
    private TextView loginError;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ActionBar actionBar = getSupportActionBar();

        if(actionBar != null){
            actionBar.hide();
        }

        mAuth = ( (FirebaseApplication) getApplication()).getFirebaseAuth();

        ((FirebaseApplication)getApplication()).checkUserLogin(LoginActivity.this);

            loginError = (TextView) findViewById(R.id.login_error);

            emailInput = (EditText) findViewById(R.id.email);
            passwordInput = (EditText) findViewById(R.id.password);

            signUpTV = (TextView) findViewById(R.id.register);
            signUpTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent signUpIntent = new Intent(LoginActivity.this,
                            SignUpActivity.class);
                    startActivity(signUpIntent);
                }
            });

            final Button loginButton = (Button) findViewById(R.id.login_button);

            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String enterEmail = emailInput.getText().toString();
                    String enterPassword = passwordInput.getText().toString();

                    if (TextUtils.isEmpty(enterEmail) ||
                    TextUtils.isEmpty(enterPassword)) {

                        Helper.displayMessageToast(LoginActivity.this,
                                "Login fields must be filled");
                        return;
                    }

                    if (!Helper.isValidEmail(enterEmail)){

                        Helper.displayMessageToast(LoginActivity.this,
                                "Invalidate email must be filled");
                            return;
                    }
                    ((FirebaseApplication)getApplication()).loginAUser(LoginActivity.this,enterEmail,enterPassword
                    ,loginError);

                }
            });

        /*ok now bug thik kor boisa boisa*/
    }

    @Override
    protected void onStart() {
        super.onStart();
        //mAuth.addAuthStateListener(((FirebaseApplication)getApplication()).mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (((FirebaseApplication)getApplication()).mAuthListener != null){

            //mAuth.removeAuthStateListener(((FirebaseApplication)getApplication()
            // ).mAuthListener);
        }
    }
}
