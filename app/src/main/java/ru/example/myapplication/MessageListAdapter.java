package ru.example.myapplication;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

public class MessageListAdapter extends RecyclerView.Adapter {

    public List<Message> messageList = new ArrayList<>();
    private static final int ASSISTANT_TYPE=0;
    private static final int USER_TYPE=1;
    int itemCounter =0;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == USER_TYPE) {
            //создание сообщения от пользователя
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.user_message,parent,false);

        }
        else {
            //создание сообщения от ассистента
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.assisstant_message,parent,false);

        }

        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((MessageViewHolder)holder).bind(messageList.get(position));
    }

    @Override
    public int getItemCount() {
        return ++itemCounter;
    }
    public int getItemViewType(int index){
        Message message;
        try {
             message = messageList.get(index);
            if (message.isSend){
                return USER_TYPE;
            }
        }
        catch (Exception ex){
            Log.d(TAG, "getItemViewType: Messages are empty");
        }
        return ASSISTANT_TYPE;
    }

}
