package org.yedazhixyz.timebank.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.yedazhixyz.timebank.Model.Achievement;
import org.yedazhixyz.timebank.R;
import org.yedazhixyz.timebank.Activity.time_deal;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by KFEB4 on 2016/7/4.
 * 用来描述和控制成就表单
 */
public class F_Ach_List extends F_super {

    public F_Ach_List(time_deal.PlaceholderFragment owner) {
        super(owner);
    }
    List<Achievement> sourceItem;
    String[] showItem ={"Title","Describe","Cost"};
    int[] showControl={R.id.Achievement_Title,R.id.Achievement_Describe,R.id.Achievement_Cost};
    private  String formatTime(long time){
        String date ="";
        Date setUpDay = new Date(time);
        Date today = new Date();
        if (setUpDay.getYear()!=today.getYear())
           date=String.valueOf(setUpDay.getYear())+"年";
        date+=String.valueOf(setUpDay.getMonth())+"月"+String.valueOf(setUpDay.getDate())+"日";
        return date;
    }
    @Override
    public View onCreate(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_achievement, container, false);
        //获取achievement的信息，放到当前的列表当中

        ListView list= (ListView) rootview.findViewById(R.id.Achievement_List);
        List<Map<String,Object>> Items = new ArrayList<>();
        sourceItem = time_deal.achievementList;
        for(int i = 0;i<sourceItem.size();i++){
            Map<String ,Object> Item = new HashMap<String,Object>();
            Item.put(showItem[0],sourceItem.get(i).Title);
            Item.put(showItem[1],sourceItem.get(i).Describe);
            Item.put(showItem[2],(sourceItem.get(i).Cost)+"min");
            Items.add(Item);
            //Item.put(showItem[2],.toString() );

        }
        SimpleAdapter adp = new SimpleAdapter(owner.getContext(),Items,R.layout.achievement_layout,showItem,showControl);
        list.setAdapter(adp);
        return rootview;
    }
}
