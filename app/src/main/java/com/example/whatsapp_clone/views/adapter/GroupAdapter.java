package com.example.whatsapp_clone.views.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsapp_clone.R;
import com.example.whatsapp_clone.databinding.ItemCardBinding;
import com.example.whatsapp_clone.modelChat.ChatGroup;
import com.example.whatsapp_clone.views.ChatActivity;

import java.util.ArrayList;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupViewHolder> {

    ArrayList<ChatGroup> arrayList;

    public GroupAdapter(ArrayList<ChatGroup> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCardBinding binding=DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_card,
                parent,
                false
        );
        return new GroupViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        ChatGroup currentUser=arrayList.get(position);
        holder.itemCardBinding.setChatGroup(currentUser);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class GroupViewHolder extends RecyclerView.ViewHolder{
        //chache references to the individual views with in an item layout
        //of a recyclerView list
        ItemCardBinding itemCardBinding;

        public GroupViewHolder(ItemCardBinding itemCardBinding) {
            super(itemCardBinding.getRoot());
            this.itemCardBinding = itemCardBinding;

            itemCardBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    ChatGroup clickedChatGroup= arrayList.get(position);
                    Intent i=new Intent(view.getContext(), ChatActivity.class);
                    i.putExtra("GROUP_NAME",clickedChatGroup.getGroupName());
                    view.getContext().startActivity(i);
                }
            });
        }
    }
}
