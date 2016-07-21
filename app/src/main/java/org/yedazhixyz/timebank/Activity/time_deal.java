package org.yedazhixyz.timebank.Activity;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.yedazhixyz.timebank.Fragment.F_Ach_List;
import org.yedazhixyz.timebank.Fragment.F_Timer;
import org.yedazhixyz.timebank.Model.Achievement;
import org.yedazhixyz.timebank.Model.ProgramState;
import org.yedazhixyz.timebank.R;

public class time_deal extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    public static List<Achievement> achievementList;
    public static ProgramState state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_deal);

        //读取或初始化achievement
        readAchievement();

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        state = new ProgramState(this);
        state.getData();

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }
    private void readAchievement(){
//        try(FileInputStream fis=openFileInput(getString(R.string.achievement_file))){
//            ObjectInputStream ofs=new ObjectInputStream(fis);
//            achievementList=(List<Achievement>)ofs.readObject();
//            Achievement exampleItem = new Achievement();
//            exampleItem.Title ="示例标题";
//            exampleItem.Describe ="示例内容";
//            achievementList.add(exampleItem);
//            return;
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
        achievementList=new ArrayList<>();
        Achievement[] exampleItem = new Achievement[]{new Achievement(),new Achievement()};
        exampleItem[0].Title ="示例标题";
        exampleItem[0].Describe ="示例内容";
        exampleItem[0].Cost=20;
        exampleItem[1].Title ="示例标题2";
        exampleItem[1].Describe ="示例内容2000000000000000000000000000000000000000000000000000000000000000000";
        exampleItem[1].Cost=10;
        achievementList.add(exampleItem[0]);achievementList.add(exampleItem[1]);
    }
    private void writeAchievement(){
        try(FileOutputStream fos=openFileOutput(getString(R.string.achievement_file),MODE_PRIVATE)){
            ObjectOutputStream ofs=new ObjectOutputStream(fos);
            ofs.writeObject(achievementList);
            //Date lastLogin = new Date(preferences.getLong(getString(R.string.lastLoginKey),0));

            return;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_time_deal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        public static final String ARG_SECTION_NUMBER = "section_number";


        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView=null;

            int kind=getArguments().getInt(ARG_SECTION_NUMBER);


            if (kind==0){
               // rootView= new F_Timer(this,state).onCreate(inflater, container,savedInstanceState);
            }else if (kind==1){
                rootView= new F_Ach_List(this).onCreate(inflater, container,savedInstanceState);
            }else{
                rootView=null;
            }
            return rootView;
        }

    }
    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position );
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "存入";
                case 1:
                    return "使用";
            }
            return null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        state.save();
        writeAchievement();
    }
}
