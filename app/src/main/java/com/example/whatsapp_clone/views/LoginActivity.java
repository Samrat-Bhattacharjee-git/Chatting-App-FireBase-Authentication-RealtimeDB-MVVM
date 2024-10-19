package com.example.whatsapp_clone.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.example.whatsapp_clone.R;
import com.example.whatsapp_clone.databinding.ActivityLoginBinding;
import com.example.whatsapp_clone.viewModel.MyViewModel;

public class LoginActivity extends AppCompatActivity {

    MyViewModel viewModel;
    ActivityLoginBinding activityLoginBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        viewModel=new ViewModelProvider(this).get(MyViewModel.class);
        activityLoginBinding= DataBindingUtil.setContentView(this,R.layout.activity_login);
        activityLoginBinding.setVModel(viewModel);

    }
}