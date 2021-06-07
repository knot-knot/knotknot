package com.cau.knotknot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CalendarActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener
{
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private LocalDate selectedDate;

    private RetrofitClient retrofitClient = RetrofitClient.getInstance();
    private RetrofitInterface retrofitInterface = RetrofitClient.getRetrofitInterface();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        initWidgets();
        selectedDate = LocalDate.now();
        setMonthView();
    }

    private void initWidgets()
    {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
    }

    private void setMonthView()
    {
        monthYearText.setText(monthYearFromDate(selectedDate));
        ArrayList<String> daysInMonth = daysInMonthArray(selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, this);

        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM");
        retrofitInterface.getCalendar(selectedDate.format(f)).enqueue(new Callback<List<Calendar>>() {
            @Override
            public void onResponse(Call<List<Calendar>> call, Response<List<Calendar>> response) {

                List<Calendar> calendar = response.body();
                ArrayList<Boolean> stamp = stampArray(selectedDate, calendar);

                calendarAdapter.setStamps(stamp);
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
                calendarRecyclerView.setLayoutManager(layoutManager);
                calendarRecyclerView.setAdapter(calendarAdapter);
            }

            @Override
            public void onFailure(Call<List<Calendar>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"네트워크가 원활하지 않습니다.",Toast.LENGTH_SHORT).show();

                Log.d("retrofit", "calendar fetch failed");
            }
        });
    }

    private ArrayList<String> daysInMonthArray(LocalDate date)
    {
        ArrayList<String> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);

        int daysInMonth = yearMonth.lengthOfMonth();

        LocalDate firstOfMonth = selectedDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        for(int i = 1; i <= 42; i++)
        {
            if(i <= dayOfWeek || i > daysInMonth + dayOfWeek)
            {
                daysInMonthArray.add("");
            }
            else
            {
                daysInMonthArray.add(String.valueOf(i - dayOfWeek));
            }
        }
        return  daysInMonthArray;
    }

    private ArrayList<Boolean> stampArray(LocalDate date, List<Calendar> calendar)
    {
        ArrayList<Boolean> stamp = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);

        int daysInMonth = yearMonth.lengthOfMonth();

        LocalDate firstOfMonth = selectedDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        int position = 0;
        for(int i = 1; i <= 42; i++)
        {
            if(position == calendar.size() || i <= dayOfWeek || i > daysInMonth + dayOfWeek)
            {
                stamp.add(false);
            }
            else
            {
                int day = i - dayOfWeek;
                if (day != Integer.parseInt(calendar.get(position).getDate().substring(8))) {
                    stamp.add(false);
                }
                else {
                    // 한 명이라도 일기를 적거나 댓글을 적은 경우
                    if (calendar.get(position).getUserCountDiary() > 0 ||
                            calendar.get(position).getUserCountComments() > 0) {
                        stamp.add(true);
                    }
                    // 가족 구성원 모두 일기를 적은 경우
//                            else if (calendar.get(position).getUserCountDiary().equals(calendar.get(position).getUserCountTotal())) {
//                                stamp.add(true);
//                            }
                    else {
                        stamp.add(false);
                    }
                    position++;
                }
            }
        }
        return stamp;
    }

    private String monthYearFromDate(LocalDate date)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }

    public void previousMonthAction(View view)
    {
        selectedDate = selectedDate.minusMonths(1);
        setMonthView();
    }

    public void nextMonthAction(View view)
    {
        selectedDate = selectedDate.plusMonths(1);
        setMonthView();
    }

    @Override
    public void onItemClick(int position, String dayText)
    {
        if(!dayText.equals(""))
        {
            selectedDate = selectedDate.withDayOfMonth(Integer.parseInt(dayText));
            Intent i = new Intent(getApplicationContext(),DiaryActivity.class);
            i.putExtra("selectedDate", selectedDate);
            startActivity(i);
        }
    }
}