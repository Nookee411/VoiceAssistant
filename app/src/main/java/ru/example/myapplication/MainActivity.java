package ru.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Locale;

public class MainActivity extends AppCompatActivity{

    protected Button sendButton;
    protected EditText questionText;
    protected RecyclerView chatMessageList;
    protected TextToSpeech textToSpeech;
    private String TAG = "Voice Generator";
    protected MessageListAdapter messageListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i!= TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(new Locale("ru"));
                    Log.d(TAG, "onInit: TTS attached");
                }
                else
                    Log.d(TAG, "onInit: Failed to attach TTS.");
            }
        });
        sendButton = findViewById(R.id.sendButton);
        questionText = findViewById(R.id.questionField);
        chatMessageList = findViewById(R.id.chatMessageList);
        messageListAdapter = new MessageListAdapter();
        sendButton.setOnClickListener(v -> onSend());

        chatMessageList.setLayoutManager(new LinearLayoutManager(this));
        chatMessageList.setAdapter(messageListAdapter);



    }

    protected void onSend(){
        String text = questionText.getText().toString();
        String answer = AI.getAnswer(text);
        messageListAdapter.messageList.add(new Message(text, true));
        messageListAdapter.messageList.add(new Message(answer, false));
        messageListAdapter.notifyDataSetChanged();
        chatMessageList.scrollToPosition(messageListAdapter.messageList.size()-1);
        questionText.setText("");
        textToSpeech.speak(answer, TextToSpeech.QUEUE_FLUSH,null, null );

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArray("messageArray", new String[]{questionText.getText().toString()});
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }


}