package org.yedazhixyz.timebank.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by KFEB4 on 2016/7/3.
 */
public class Achievement implements Serializable{
    public String Title;
    public String Describe;
    public int Cost;
    public ArrayList<FinishItem> history=new ArrayList<>();
    public long setUpTime;//建立时间
    public float achieveed;//达成次数，当日达成>=1则，不能再达成成就
    public float everyTimeAchieve;//每次达成所产生的有效次数，小于1表示一日内可以达成多次，大于1表示几天可以达成一次。
    public class FinishItem implements Serializable{
        long finishTime;
        String addition;//附加信息
    }
}
