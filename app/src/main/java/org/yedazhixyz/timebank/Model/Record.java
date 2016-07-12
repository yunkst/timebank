package org.yedazhixyz.timebank.Model;

import java.util.List;

/**
 * Created by KFEB4 on 2016/7/11.
 */
public class Record {
    public int maxHave;
    //未来会因为数据量的增大而导致卡顿以及其他情况，可以考虑创建分析数据，按照分钟计算的话占用空间较小
    public class item{
        public long startTime;
        public long Length;
    }
    public List<item> items;
}
