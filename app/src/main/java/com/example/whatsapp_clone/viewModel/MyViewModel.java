package com.example.whatsapp_clone.viewModel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.whatsapp_clone.modelChat.ChatGroup;
import com.example.whatsapp_clone.modelChat.ChatMessage;
import com.example.whatsapp_clone.repository.Repository;

import java.util.List;

public class MyViewModel extends AndroidViewModel {

    Repository repository;
    public MyViewModel(@NonNull Application application) {
        super(application);
        repository=new Repository();
    }
    public void signUpAnonymousUser(){
        Context c=this.getApplication();
        //for using this getApplication method we are using AndroidViewModel instead of ViewModel
        //AndroidViewModel is a child or subset of ViewModel
        repository.firebaseAnonymousAuth(c);
    }
    public String getCurrentUserId(){
        return repository.getCurrentUserId();
    }
    public void signout(){
        repository.signOut();
    }

    //getting chat groups
    public MutableLiveData<List<ChatGroup>> getChatGroups(){
        return repository.getMutableLiveData();

    }
    public void createNewChatGroup(String groupName){
        repository.createNewChatGroup(groupName);
    }

    //message
    public MutableLiveData<List<ChatMessage>> getMessagesLiveData(String groupName){
        return repository.getMessagesLiveData(groupName);
    }
    public void sendMessage(String msg, String chatGroup){
        repository.sendMessage(msg,chatGroup);
    }
}
