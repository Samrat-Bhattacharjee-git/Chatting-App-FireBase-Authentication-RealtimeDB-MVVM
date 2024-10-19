package com.example.whatsapp_clone.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.whatsapp_clone.R;
import com.example.whatsapp_clone.databinding.ActivityChatBinding;
import com.example.whatsapp_clone.modelChat.ChatMessage;
import com.example.whatsapp_clone.viewModel.MyViewModel;
import com.example.whatsapp_clone.views.adapter.ChatAdapter;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    ChatAdapter adapter;
    RecyclerView recyclerView;
    ActivityChatBinding binding;
    MyViewModel myViewModel;
    List<ChatMessage> messagesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat);

        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);

        // RecyclerView with DataBinding
        recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        // Getting the Group Name from the Clicked Item in the GroupsActivity
        String groupName = getIntent().getStringExtra("GROUP_NAME");

        myViewModel.getMessagesLiveData(groupName).observe(this, new Observer<List<ChatMessage>>() {
            @Override
            public void onChanged(List<ChatMessage> chatMessages) {
                messagesList = new ArrayList<>();
                messagesList.addAll(chatMessages);

                adapter = new ChatAdapter(messagesList,getApplicationContext());

                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                // Scroll to the latest message added:
                int latestPosition = adapter.getItemCount() -1;
                if (latestPosition > 0) {
                    recyclerView.smoothScrollToPosition(latestPosition);
                }
            }
        });

        binding.setVModel(myViewModel);

        binding.sendBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String msg = binding.edittextChatMessage.getText().toString();
                myViewModel.sendMessage(msg, groupName);

                binding.edittextChatMessage.getText().clear();
            }
        });
    }
}