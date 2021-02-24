package com.example.todoapp.Model;

import android.content.Context;
import android.content.DialogInterface;
import android.provider.Settings;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.todoapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class ChooseDateDialog  {
    private static DatePicker datePicker;
    private static TimePicker timePicker;
    private int mYear;
    private int mMonth;
    private int mDay;
    private int mHour;
    private int mMinute;

    public void showDialog(final Context context, final EditText editText,final String[] result) {
        View view = View.inflate(context, R.layout.calendar_layout, null);
        datePicker = (DatePicker) view.findViewById(R.id.datepicker);
        timePicker = (TimePicker) view.findViewById(R.id.timepicker);
        timePicker.setIs24HourView(true);
        resizePikcer(datePicker);
        resizePikcer(timePicker);
        initData();

        new AlertDialog.Builder(context).setTitle("请选择日期").setView(view).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                GregorianCalendar gregorianCalendar = new GregorianCalendar(mYear,mMonth,mDay,mHour,mMinute);
                result[0] = format.format(gregorianCalendar.getTime());
                editText.setText(result[0]);
                dialog.dismiss();
                Toast.makeText(context,result[0],Toast.LENGTH_SHORT).show();
            }
        }).create().show();

    };
    public void initData(){
        Calendar calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);
        mHour = calendar.get(Calendar.HOUR_OF_DAY);
        mMinute = calendar.get(Calendar.MINUTE);
        datePicker.init(mYear, mMonth, mDay, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mYear = year;
                mMonth = monthOfYear;
                mDay = dayOfMonth;
            }
        });
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                mHour = hourOfDay;
                mMinute = minute;
            }
        });
    }
    private void resizePikcer(FrameLayout tp){
        List<NumberPicker> npList = findNumberPicker(tp);
        for(NumberPicker np:npList){
            resizeNumberPicker(np);
        }
    }

    /**
     * 得到viewGroup里面的numberpicker组件
     * @param viewGroup
     * @return
     */
    private List<NumberPicker> findNumberPicker(ViewGroup viewGroup){
        List<NumberPicker> npList = new ArrayList<NumberPicker>();
        View child = null;
        if(null != viewGroup){
            for(int i = 0; i < viewGroup.getChildCount(); i++){
                child = viewGroup.getChildAt(i);
                if(child instanceof NumberPicker){
                    npList.add((NumberPicker)child);
                }
                else if(child instanceof LinearLayout){
                    List<NumberPicker> result = findNumberPicker((ViewGroup)child);
                    if(result.size()>0){
                        return result;
                    }
                }
            }
        }
        return npList;
    }
    /*
     * 调整numberpicker大小
     */
    private void resizeNumberPicker(NumberPicker np){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        np.setLayoutParams(params);
    }
}
