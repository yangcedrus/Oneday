package com.whut.oneday.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.whut.oneday.R;
import com.whut.oneday.activity.SignUpActivity;

public class SignUpFragment1 extends Fragment {
    SignUpActivity signActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signActivity = (SignUpActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = View.inflate(signActivity, R.layout.fragment_sign_up1, null);
        Button button = view.findViewById(R.id.sign_up_phone_next);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signActivity.nextFragment();
            }
        });
        return view;
    }

}
