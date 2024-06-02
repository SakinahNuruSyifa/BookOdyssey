package com.example.bookodyssey.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bookodyssey.R;
import com.example.bookodyssey.activity.MainActivity;

public class ProfileFragment extends Fragment {

    TextView username;
    Button logout;
    SharedPreferences sharedPreferences;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        ImageView home = view.findViewById(R.id.IV_Home);
        ImageView search = view.findViewById(R.id.IV_Search);
        ImageView saved = view.findViewById(R.id.IV_Saved);

        search.setOnClickListener(v -> {
            SearchFragment searchFragment = new SearchFragment();
            FragmentManager fragmentManager = getParentFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.nav_container, searchFragment)
                    .addToBackStack(null)
                    .commit();
        });
        home.setOnClickListener(v -> {
            HomeFragment homeFragment = new HomeFragment();
            FragmentManager fragmentManager = getParentFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.nav_container, homeFragment)
                    .addToBackStack(null)
                    .commit();
        });
        saved.setOnClickListener(v -> {
            SavedFragment savedFragment = new SavedFragment();
            FragmentManager fragmentManager = getParentFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.nav_container, savedFragment)
                    .addToBackStack(null)
                    .commit();
        });

        sharedPreferences = getActivity().getSharedPreferences("pref", getActivity().MODE_PRIVATE);
        logout = view.findViewById(R.id.button_logout);
        username = view.findViewById(R.id.username);
        String uname = sharedPreferences.getString("Username", "");
        username.setText(uname);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isLoggedIn", false);
                editor.apply();
                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
            }
        });

        return view;

    }
}