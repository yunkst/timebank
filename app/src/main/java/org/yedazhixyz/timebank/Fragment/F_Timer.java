package org.yedazhixyz.timebank.Fragment;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.yedazhixyz.timebank.Model.ProgramState;
import org.yedazhixyz.timebank.R;
import org.yedazhixyz.timebank.time_deal;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by KFEB4 on 2016/7/3.
 */
//主要功能显示控制
public class F_Timer extends F_super {
    public static final int FreshControl =0x1245;
    private static Handler mHandler;
    static TextView text;
    static Timer time;
    static ProgramState state;
    Button btn_save;
    Button btn_use;
    Button btn_stop;
    static final int NOTIFICATION_ID = 0x123;
    NotificationManager nm;
    boolean noticed=false;
    public F_Timer(time_deal.PlaceholderFragment owner,ProgramState state){
        super(owner);
        this.state= state;
        nm = (NotificationManager) owner.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
    }
    private String getTimeExp(long time){
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
    @Override
    public  View onCreate(LayoutInflater inflater, ViewGroup container,
                          Bundle savedInstanceState){
        View rootView;

        mHandler=new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if(msg.what== FreshControl) {
                    FreshControl();
                }
            }
        };
        rootView=inflater.inflate(R.layout.fragment_time_deal, container, false);
        btn_save=   (Button) rootView.findViewById(R.id.btn_save);
        btn_use=   (Button) rootView.findViewById(R.id.btn_use);
        btn_stop=(Button) rootView.findViewById(R.id.btn_stop);
        text=(TextView) rootView.findViewById(R.id.section_label);
        mHandler.sendEmptyMessage(FreshControl);
        btn_save.setText(owner.getString(R.string.btn_save));
        btn_use.setText(owner.getString(R.string.btn_use));
        btn_stop.setText(owner.getString(R.string.btn_off));
        btn_save.setOnClickListener(new click_Btn_Time(btn_save,5));
        btn_use.setOnClickListener(new click_Btn_Time(btn_use,-1));
        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time.cancel();
                state.time_state.isOn=false;
                FreshControl();
            }
        });
        FreshControl();
        return rootView;
    }
    //根据状态刷新控件信息
    public void FreshControl(){
        text.setText(getTimeExp(time_deal.state.haveTime));
        if (time_deal.state.haveTime>0)
            text.setTextColor(owner.getResources().getColor(R.color.Poistive_Time,null));
        else
            text.setTextColor(owner.getResources().getColor(R.color.negative_Time,null));
        if(state.time_state.isOn){
            btn_save.setVisibility(View.INVISIBLE);
            btn_use.setVisibility(View.INVISIBLE);
            btn_stop.setVisibility(View.VISIBLE);
        }else{
            btn_save.setVisibility(View.VISIBLE);
            btn_use.setVisibility(View.VISIBLE);
            btn_stop.setVisibility(View.INVISIBLE);
        }
    }
    private class click_Btn_Time implements View.OnClickListener{

        int rate;
        public click_Btn_Time(Button owner,int rate){
            this.rate = rate;
        }
        @Override
        public void onClick(View view) {
            Button btn;
            if (view instanceof Button)
                btn= (Button)view;
            else
                return;
            if(state.time_state.isOn){//停止运行
                time.cancel();
                state.time_state.isOn=false;
                btn_save.setText(owner.getString(R.string.btn_save));
                btn_use.setText(owner.getString(R.string.btn_use));
            }else{
                time=new Timer();
                state.time_state.isOn=true;
                state.time_state.PreTime= time_deal.state.haveTime;
                state.time_state.startTime= new Date().getTime();
                time.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        time_deal.state.haveTime=state.time_state.PreTime+(new Date().getTime()-state.time_state.startTime)/(1000*rate);
                        if(time_deal.state.haveTime<0){
                            if (!noticed){
                                Intent intent = new Intent(owner.getContext(),owner.getContext().getClass());
                                PendingIntent pi = PendingIntent.getActivities(owner.getContext(),0, new Intent[]{intent},0);
                                Notification notify = new Notification.Builder(owner.getContext())
                                        .setAutoCancel(true)
                                        .setContentText("您的使用时间已经透支！")
                                        .setContentIntent(pi)
                                        .setDefaults(Notification.DEFAULT_LIGHTS|Notification.DEFAULT_SOUND|Notification.DEFAULT_VIBRATE)
                                        .setSmallIcon(R.drawable.icon)
                                        .setContentTitle("TimeBank")
                                        .build();
                                nm.notify(NOTIFICATION_ID,notify);
                                noticed=true;
                            }
                        }else
                            noticed=false;
                        mHandler.sendEmptyMessage(FreshControl);
                    }
                },0,1000);

            }
        }
    }

}
