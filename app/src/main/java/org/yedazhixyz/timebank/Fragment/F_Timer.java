package org.yedazhixyz.timebank.Fragment;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.yedazhixyz.timebank.Model.GlobalData;
import org.yedazhixyz.timebank.Model.ProgramState;
import org.yedazhixyz.timebank.R;
import org.yedazhixyz.timebank.Tool.tool_time;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by KFEB4 on 2016/7/3.
 */
//主要功能显示控制
public class F_Timer extends Fragment{
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
    boolean ison = false;
    public F_Timer(){
        this.state = GlobalData.state;
        ison=false;
    }

    @Override
    public  View onCreateView(LayoutInflater inflater, ViewGroup container,
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
        btn_save.setText(getString(R.string.btn_save));
        btn_use.setText(getString(R.string.btn_use));
        btn_stop.setText(getString(R.string.btn_off));
        btn_save.setOnClickListener(new click_Btn_Time(btn_save,state.time_state.rate_savein));
        btn_use.setOnClickListener(new click_Btn_Time(btn_use,state.time_state.rate_use));
        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time.cancel();
                state.time_state.flag=0;
                ison=false;
                state.save();
                FreshControl();
            }
        });
        FreshControl();
        return rootView;
    }
    //根据状态刷新控件信息
    public void FreshControl(){
        text.setText(tool_time.getTimePeriodExp(state.haveTime));
        if (state.haveTime>0)
            text.setTextColor(getResources().getColor(R.color.Poistive_Time,null));
        else
            text.setTextColor(getResources().getColor(R.color.negative_Time,null));
        if(state.time_state.flag!=ProgramState.keyWord.flag_off){
            btn_save.setVisibility(View.INVISIBLE);
            btn_use.setVisibility(View.INVISIBLE);
            btn_stop.setVisibility(View.VISIBLE);
            if (!ison){
                if (state.time_state.flag==ProgramState.keyWord.flag_savein){
                    startTime(state.time_state.rate_savein);
                }else if (state.time_state.flag==ProgramState.keyWord.flag_use){
                    startTime(state.time_state.rate_use);
                }
            }
        }else{
            btn_save.setVisibility(View.VISIBLE);
            btn_use.setVisibility(View.VISIBLE);
            btn_stop.setVisibility(View.INVISIBLE);
        }
    }
    private class click_Btn_Time implements View.OnClickListener{

        float rate;
        public click_Btn_Time(Button owner,float rate){
            this.rate = rate;
        }
        @Override
        public void onClick(View view) {
            if(ison){//停止运行
                time.cancel();
                state.time_state.flag=0;
            }else{
                state.time_state.PreTime= state.haveTime;
                state.time_state.startTime= new Date().getTime();//初始化计时开始时间
                startTime(rate);
                startSurfaceDraw();
            }
        }
    }
    private  void startTime(float r){
        time=new Timer();
        if(state.time_state.flag==ProgramState.keyWord.flag_off) {
            if (r == state.time_state.rate_savein)
                state.time_state.flag = ProgramState.keyWord.flag_savein;
            else if (r == state.time_state.rate_use)
                state.time_state.flag = ProgramState.keyWord.flag_use;
        }
        ison=true;
       final float rate = r;
        time.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                state.haveTime= (long) (state.time_state.PreTime+(new Date().getTime()-state.time_state.startTime)/(1000*rate));
                if(state.haveTime<0){
                    if (!noticed){
                        if(nm==null)
                            nm = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
                        Intent intent = new Intent(getContext(),getContext().getClass());
                        PendingIntent pi = PendingIntent.getActivities(getContext(),0, new Intent[]{intent},0);
                        Notification notify = new Notification.Builder(getContext())
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
                state.save();
                mHandler.sendEmptyMessage(FreshControl);
            }
        },0,1010);

    }
    @Override
    public void onPause() {
        super.onPause();
        stopSurfaceDraw();
        state.save();
    }

    @Override
    public void onResume() {
        super.onResume();
        startSurfaceDraw();
    }

    //开始绘制动画
    private  void startSurfaceDraw(){


    }
    //停止绘制动画
    private void stopSurfaceDraw(){

    }

}
