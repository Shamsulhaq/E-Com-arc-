package com.example.sam.finalproject;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by AtiAfi on 6/1/2017.
 */

public class FirebaseApplication extends Application {

    private static final String TAG = FirebaseApplication.class.getSimpleName();

    public FirebaseAuth firebaseAuth;

    public FirebaseAuth.AuthStateListener mAuthListener;

    public FirebaseAuth getFirebaseAuth() {
        return firebaseAuth = FirebaseAuth.getInstance();
    }

    public String getFirebaseAuthenticationId(){
        String userId = null;
        if(firebaseAuth.getCurrentUser() != null){

            userId = firebaseAuth.getCurrentUser().getUid();
        }

        return userId;
    }

    public void checkUserLogin(final Context context){

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (null != user){

                    Intent profileIntent = new Intent (context,ProfileActivity.class);
                    context.startActivity(profileIntent);
                }
            }

            public void isUserCurrentlyLogin(final Context context){

            }
        }

    }
}
