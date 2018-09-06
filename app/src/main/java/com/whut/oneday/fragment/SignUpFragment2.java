package com.whut.oneday.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.whut.oneday.R;
import com.whut.oneday.activity.SignUpActivity;

public class SignUpFragment2 extends Fragment {
    SignUpActivity signUpActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signUpActivity=(SignUpActivity)getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=View.inflate(signUpActivity,R.layout.fragment_sign_up2,null);
        Button button=view.findViewById(R.id.sign_up_psw_ok);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpActivity.signUpComplete();
            }
        });
        return view;
    }
}
