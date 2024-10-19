package com.example.whatsapp_clone.repository;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.whatsapp_clone.modelChat.ChatGroup;
import com.example.whatsapp_clone.modelChat.ChatMessage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.example.whatsapp_clone.views.GroupActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Repository {

    MutableLiveData<List<ChatGroup>> mutableLiveData;

    FirebaseDatabase database;
    DatabaseReference reference;

    MutableLiveData<List<ChatMessage>> messagesLiveData;
    DatabaseReference refGrp;
    public Repository() {
        this.mutableLiveData=new MutableLiveData<>();
        database=FirebaseDatabase.getInstance();
        reference= database.getReference(); //the root reference of instance
        this.messagesLiveData=new MutableLiveData<>();
    }

    //Authentication
    public void firebaseAnonymousAuth(Context context){
        FirebaseAuth.getInstance().signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //Authentication is successful :
                Intent i=new Intent(context, GroupActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //new task should be created for the activity that is being started
                // it also prevents many errors
                //you should call it when moving from one activity to another activity outside the activity
                //here i am moving from mainActivity to GroupActivity but in repository class
                context.startActivity(i);
            }
        });
    }

    //getting Current User ID
    public String getCurrentUserId(){
        return FirebaseAuth.getInstance().getUid();
    }
    //SignOut Functionality
    public void signOut(){
        FirebaseAuth.getInstance().signOut();
    }


    //Getting Chat Groups available from firebase realtime db
    public MutableLiveData<List<ChatGroup>> getMutableLiveData() {
        List<ChatGroup> groupList=new ArrayList<>();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                groupList.clear(); //to prevent duplication of data
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    ChatGroup group=new ChatGroup(dataSnapshot.getKey());
                    groupList.add(group);
                }
                //dataSnapshot is used to present the snapshot of data at a particular location
                mutableLiveData.postValue(groupList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return mutableLiveData;
    }

    //getting a new group
    public void createNewChatGroup(String groupName){
        reference.child(groupName).setValue(groupName);
    }

    public MutableLiveData<List<ChatMessage>> getMessagesLiveData(String groupName) {
        refGrp= database.getReference().child(groupName);
        List<ChatMessage> messageList=new ArrayList<>();
        refGrp.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageList.clear(); //to prevent duplication of data
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    ChatMessage message=dataSnapshot.getValue(ChatMessage.class);//used to convert the data into chat message class
                    messageList.add(message);
                }
                //dataSnapshot is used to present the snapshot of data at a particular location
                messagesLiveData.postValue(messageList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return messagesLiveData;
    }
    public void sendMessage(String messageText, String chatGroup) {

        DatabaseReference ref = database
                .getReference(chatGroup);


        if (!messageText.trim().equals("")) {
            ChatMessage msg = new ChatMessage(
                    FirebaseAuth.getInstance().getCurrentUser().getUid(),
                    messageText,
                    System.currentTimeMillis()
            );

            String randomKey = ref.push().getKey();

            ref.child(randomKey).setValue(msg);

        }
    }
}
