package org.yedazhixyz.timebank.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.yedazhixyz.timebank.Model.GlobalData;
import org.yedazhixyz.timebank.Model.ProgramState;
import org.yedazhixyz.timebank.R;
import org.yedazhixyz.timebank.Tool.tool_time;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by KFEB4 on 2016/7/21.
 * 描述当前计时器的一些设置和参数
 */
public class F_TimerDetail extends Fragment implements ProgramState.StateListener{
    TextView TV_StartTime;
    TextView TV_S_rest;
    TextView TV_N_rest;
    TextView TV_differ;
    TextView TV_PassedTime;
    TextView TV_Radio;
    ProgramState state ;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView;

        rootView=inflater.inflate(R.layout.timer_detail, container, false);
        GlobalData.state.listeners.add(this);
        state=GlobalData.state;
        //初始化控件
        TV_StartTime=(TextView) rootView.findViewById(R.id.TV_startTime);
        TV_S_rest       =       (TextView) rootView.findViewById(R.id.TV_S_rest);
        TV_N_rest       =       (TextView) rootView.findViewById(R.id.TV_N_rest);
        TV_differ       =       (TextView) rootView.findViewById(R.id.TV_differ);
        TV_PassedTime   =      (TextView) rootView.findViewById(R.id.TV_PassedTime);
        TV_Radio        =      (TextView) rootView.findViewById(R.id.TV_Radio);

        return rootView;
    }

    @Override
    public void run() {
        if (GlobalData.state.time_state.flag== ProgramState.keyWord.flag_savein)
            TV_Radio.setText(String.valueOf(GlobalData.state.time_state.rate_savein));
        else if (GlobalData.state.time_state.flag== ProgramState.keyWord.flag_use)
            TV_Radio.setText(String.valueOf(GlobalData.state.time_state.rate_use));
        else//当计时器停止的时候，不再改变详细信息的显示
            return;
        //通过检查当前日期和状态日期，显示相应的数据
        TV_StartTime.setText(tool_time.getTimeExpress(state.time_state.startTime));
        TV_PassedTime.setText(tool_time.getTimePeriodExp(state.time_state.startTime-new Date().getTime()));
        TV_S_rest.setText(tool_time.getTimePeriodExp(state.time_state.PreTime));
        TV_N_rest.setText(tool_time.getTimePeriodExp(state.haveTime));
        TV_differ.setText(tool_time.getTimePeriodExp(state.haveTime-state.time_state.PreTime));
    }
}
