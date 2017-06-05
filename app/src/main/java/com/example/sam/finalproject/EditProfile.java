package com.example.sam.finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import Firebase.FirebaseDatabaseHelper;
import Firebase.FirebaseUserEntity;
import Helper.Helper;

public class EditProfile extends AppCompatActivity {

    private static final String TAG = EditProfile.class.getSimpleName();

    private EditText profileNameET;

    private EditText phoneNumberET;

    private EditText profileHobbyET;

    private EditText profileCountryET;

    private EditText profileBirthdayET;

    private FirebaseAuth.AuthStateListener authStateListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

        setTitle("Edit Profile Information");

        profileNameET = (EditText) findViewById(R.id.profile_name);

        phoneNumberET = (EditText) findViewById(R.id.profile_phone);

        profileHobbyET = (EditText) findViewById(R.id.profile_hobby);

        profileCountryET = (EditText) findViewById(R.id.profile_country);

        profileBirthdayET = (EditText) findViewById(R.id.profile_birth);

        Button saveEditButton = (Button) findViewById(R.id.save_edit_button);

        saveEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String profileName = profileNameET.getText().toString();

                String phoneNumber = phoneNumberET.getText().toString();

                String profileHobby = profileHobbyET.getText().toString();

                String profileCountry = profileCountryET.getText().toString();

                String profileBirthday = profileBirthdayET.getText().toString();

                if(TextUtils.isEmpty(profileName) || TextUtils.isEmpty(profileCountry) || TextUtils.isEmpty(phoneNumber)
                        || TextUtils.isEmpty(profileHobby) || TextUtils.isEmpty(profileBirthday)){
                    Helper.displayMessageToast(EditProfile.this, "All fields must be filled");
                }

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                if (user == null) {
                    Intent firebaseUserIntent = new Intent(EditProfile.this, LoginActivity.class);
                    startActivity(firebaseUserIntent);
                    finish();
                }else{

                    String userId = user.getProviderId();
                    String id = user.getUid();
                    String profileEmail = user.getEmail();

                    FirebaseUserEntity userEntity = new FirebaseUserEntity(id,profileEmail,profileName,phoneNumber,profileHobby,
                            profileCountry,profileBirthday);

                    FirebaseDatabaseHelper firebaseDatabaseHelper = new FirebaseDatabaseHelper();

                    firebaseDatabaseHelper.createUserInFirebaseDatabase(id,userEntity);

                        profileNameET.setText("");

                        phoneNumberET.setText("");

                        profileHobbyET.setText("");

                        profileCountryET.setText("");

                        profileBirthdayET.setText("");
                }

            }
        });

    }
}
