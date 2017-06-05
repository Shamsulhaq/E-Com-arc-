package com.example.sam.finalproject;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import Firebase.FirebaseDatabaseHelper;
import Helper.Helper;
import Helper.SimpleDividerItemDecoration;

public class ProfileFragment extends Fragment {

    private static final String TAG = ProfileFragment.class.getSimpleName();
    private ImageView profilePhoto;

    private TextView profileName;

    private TextView country;

    private TextView userStatus;

    private RecyclerView recyclerView;

    private LinearLayoutManager linearLayoutManager;

    private String id;

    private static final int REQUEST_READ_PERMISSION = 120;

    public ProfileFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_profile_fragment, container, false);

        getActivity().setTitle("My Profile");

        profileName = (TextView)view.findViewById(R.id.profile_name);
        country = (TextView)view.findViewById(R.id.country);
        profileName.setVisibility(View.GONE);
        country.setVisibility(View.GONE);

        profilePhoto = (ImageView)view.findViewById(R.id.circleView);
        profilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, Helper.SELECT_PICTURE);
            }
        });

        recyclerView = (RecyclerView) view.findViewById(R.id.profile_list);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        ((FirebaseApplication)getActivity().getApplication()).getFirebaseAuth();
        id = ((FirebaseApplication)getActivity().getApplication()).getFirebaseUserAuthenticateId();
        FirebaseDatabaseHelper firebaseDatabaseHelper = new FirebaseDatabaseHelper();
        firebaseDatabaseHelper.isUserKeyExist(id, getActivity(), recyclerView);
        return view;
    }
}
