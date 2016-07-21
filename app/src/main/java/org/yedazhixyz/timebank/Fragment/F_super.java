package org.yedazhixyz.timebank.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.yedazhixyz.timebank.Activity.time_deal;

/**
 * Created by KFEB4 on 2016/7/4.
 */
public abstract class F_super {
    time_deal.PlaceholderFragment owner;
    public F_super(time_deal.PlaceholderFragment owner){this.owner=owner;}
    public abstract View onCreate(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState);
}
