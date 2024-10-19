package com.example.whatsapp_clone.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.whatsapp_clone.R;
import com.example.whatsapp_clone.databinding.ActivityGroupBinding;
import com.example.whatsapp_clone.databinding.DialogLayoutBinding;
import com.example.whatsapp_clone.modelChat.ChatGroup;
import com.example.whatsapp_clone.viewModel.MyViewModel;
import com.example.whatsapp_clone.views.adapter.GroupAdapter;

import java.util.ArrayList;
import java.util.List;

public class GroupActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    GroupAdapter groupAdapter;
    ActivityGroupBinding activityGroupBinding;
    MyViewModel myViewModel;
    ArrayList<ChatGroup> chatGroupArrayList;
    DialogLayoutBinding dialogLayoutBinding;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        activityGroupBinding= DataBindingUtil.setContentView(this,R.layout.activity_group);
        //define ViewModel
        myViewModel=new ViewModelProvider(this).get(MyViewModel.class);

        //RecyclerView With Data Binding
        recyclerView=activityGroupBinding.recycleview;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //setup an observer to listen for changes in a live data object
        myViewModel.getChatGroups().observe(this, new Observer<List<ChatGroup>>() {
            @Override
            public void onChanged(List<ChatGroup> chatGroups) {
                chatGroupArrayList =new ArrayList<>();
                chatGroupArrayList.addAll(chatGroups);

                groupAdapter =new GroupAdapter(chatGroupArrayList);
                recyclerView.setAdapter(groupAdapter);
                groupAdapter.notifyDataSetChanged();
            }
        });

        activityGroupBinding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
    }
    public void showDialog(){
        dialog=new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view= LayoutInflater.from(this).inflate(
                R.layout.dialog_layout,
                null
        );
        dialog.setContentView(view);
        dialog.show();

        Button submit=view.findViewById(R.id.dialogbtn);
        EditText ed=view.findViewById(R.id.ChatGroupEditBox);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String groupName=ed.getText().toString();
                Toast.makeText(GroupActivity.this, "Your Chat Group: "+groupName, Toast.LENGTH_SHORT).show();

                myViewModel.createNewChatGroup(groupName);
                dialog.dismiss();
            }
        });
    }
}