package org.yedazhixyz.timebank.Model;

import android.app.Activity;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KFEB4 on 2016/7/8.
 * 用于存储程序运行时需要的状态信息（保持下一次启动恢复现场）
 * 以及保存一些永久数据
 */
// TODO: 2016/7/8  需求：1.增加定时功能（存入定时）2.增加数据详细（单次存入详细信息）3.统计数据分析
//4.增加最大拥有量查看，5.修改按钮显示，绘制动画效果
//
public class ProgramState {
    public interface StateListener{
        public void run();
    }
    //在永久数据中有关的关键字
    public static class keyWord{
        public static final String filename="Config";
        public static final String HaveTime="HaveTime";
        public static final String state_flag="state_flag";
        public static final String state_starttime="state_starttime";
        public static final String state_rate_savein="state_rate_savein";
        public static final String state_rate_use="state_rate_use";
        public static final String state_PreTime="state_PreTime";
        public static final int flag_off=0;
        public static final int flag_savein=1;
        public static final int flag_use=-1;

    }
    //自动从Preference数据中读取相关信息
    public ProgramState(Activity owner){
        this.owner = owner;
        preferences = owner.getSharedPreferences(keyWord.filename,owner.MODE_PRIVATE);
        editor = preferences.edit();
    }
    //从永久数据中读取数据
    public void getData(){
        haveTime = preferences.getLong(keyWord.HaveTime,0);
        time_state.PreTime=preferences.getLong(keyWord.state_PreTime,0);
        time_state.flag=preferences.getInt(keyWord.state_flag,0);
        time_state.startTime=preferences.getLong(keyWord.state_starttime,0);
        time_state.rate_savein=preferences.getFloat(keyWord.state_rate_savein,5);
        time_state.rate_use=preferences.getFloat(keyWord.state_rate_use,-1);

    }
    public void save(){
        callChange();
        editor.putLong(keyWord.HaveTime,haveTime);
        editor.putLong(keyWord.state_PreTime,time_state.PreTime);
        editor.putInt(keyWord.state_flag,time_state.flag);
        editor.putLong(keyWord.state_starttime,time_state.startTime);
        editor.putFloat(keyWord.state_rate_savein,time_state.rate_savein);
        editor.putFloat(keyWord.state_rate_use,time_state.rate_use);
        editor.commit();
    }
    //收听列表
    public List<StateListener> listeners=new ArrayList<>();
    //唤醒所有监听者
    public void callChange(){
        if (listeners.size()>0){
            for (StateListener item:
                 listeners) {
                item.run();
            }
        }
    }
    //计时器有关的状态信息
    public class _timerSate {
        public int flag;//标志运行状态
        public long startTime;
        public float rate_savein=5;
        public float rate_use=-1;
        public long PreTime;
    }
    public long haveTime=0;
    public _timerSate time_state =new _timerSate();
    private SharedPreferences preferences=null;
    private SharedPreferences.Editor editor=null;
    private Activity owner=null;
}
