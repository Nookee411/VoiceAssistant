package ru.example.myapplication;

import android.content.res.Resources;
import android.util.Log;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.IntToDoubleFunction;

import javax.sql.StatementEvent;

public class AI {
    private static final String TAG = "AI";

    public static String getAnswer(String question){
        Calendar date = Calendar.getInstance();
        date.add(Calendar.MONTH, 1);
        HashMap<String,String> answers = new HashMap<>();
        answers.put("привет","Привет, человек");
        answers.put("как дела","Замечательно");
        answers.put("чем занимаешься","Плохо отвечаю на вопросы");
        answers.put("какой сегодня день",  date.get(Calendar.DATE)+"."+date.get(Calendar.MONTH));
        answers.put("который час", (date.get(Calendar.HOUR_OF_DAY) + 3) + ":"+date.get(Calendar.MINUTE));
        answers.put("какой день недели", String.valueOf(date.get(Calendar.DAY_OF_WEEK)));
        question = question.toLowerCase();
        //question = question.replaceAll("[.,?!/]","");
        String response = answers.get(question);
        if(question.equals("какой день недели")){
            Map<Integer,String> dayConvertor = new HashMap<>();
            dayConvertor.put(1,"Воскресенье");
            dayConvertor.put(2,"Понедельник");
            dayConvertor.put(3,"Вторник");
            dayConvertor.put(4,"Среда");
            dayConvertor.put(5,"Четверг");
            dayConvertor.put(6,"Пятница");
            dayConvertor.put(7,"Суббота");
            response = dayConvertor.get(Integer.parseInt(response));
        }
        if(question.matches("сколько дней до \\d+[,.]\\d+")) {
            String to = question.split("до ")[1];
            Calendar toDate = Calendar.getInstance();
            toDate.set(toDate.get(Calendar.YEAR), Integer.parseInt(to.split("[.,]")[1]),Integer.parseInt(to.split("[.,]")[0]));

            long timeSpan = toDate.getTimeInMillis()-date.getTimeInMillis();
            Log.d(TAG, toDate.get(Calendar.DAY_OF_MONTH)+" . " + toDate.get(Calendar.MONTH));
            Log.d(TAG, date.get(Calendar.DAY_OF_MONTH)+" . " + date.get(Calendar.MONTH));
            response = "Дней осталось: " + Math.round(timeSpan/1000/60/60/24);
        }
        if(response==null)
            response = "не знаю что сказать";

        return response+".";
    }
}
