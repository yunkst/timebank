package org.yedazhixyz.timebank.Tool;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by KFEB4 on 2016/7/21.
 */
public class tool_time {
    public static String getTimePeriodExp(long time){
        String res="";
        if (time>=0)
            res= time/ (60) + ":";
        else{
            time*=-1;
            res="-"+time/60+":";
        }
        if (time%60<10)
            res+="0";
        return  res+time%60;
    }
    public static  String getTimeExpress(long time){
        Date now= new Date();
        Date goal = new Date(time);
        SimpleDateFormat format;
        String result;
        if (goal.getYear()!=now.getYear())
            format  = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        else if (goal.getMonth()!=now.getMonth()||goal.getDate()!=now.getDate())
            format = new SimpleDateFormat("MM-dd HH:mm");
        else
            format= new SimpleDateFormat("HH:mm:ss");
        return format.format(goal);
    }
}
