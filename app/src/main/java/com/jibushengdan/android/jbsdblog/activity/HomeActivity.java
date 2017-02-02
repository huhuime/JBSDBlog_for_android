package com.jibushengdan.android.jbsdblog.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.jibushengdan.android.jbsdblog.R;
import com.jibushengdan.android.jbsdblog.fragment.HomePagerFragment;
import com.jibushengdan.android.jbsdblog.model.Te;
import com.jibushengdan.android.jbsdblog.model.TeParam;
import com.litesuits.http.LiteHttp;
import com.litesuits.http.listener.HttpListener;
import com.litesuits.http.request.JsonRequest;
import com.litesuits.http.response.Response;
import com.litesuits.http.utils.HttpUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends BaseActivity {

    @BindView(R.id.tab)
    TabLayout tab;
    @BindView(R.id.pager)
    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);


        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(new HomePagerFragment(0), "home");
        pagerAdapter.addFragment(new HomePagerFragment(1), "see the sun");
        pagerAdapter.addFragment(new HomePagerFragment(2), "music");
        pagerAdapter.addFragment(new HomePagerFragment(3), "就这样吧...");
        pagerAdapter.addFragment(new HomePagerFragment(4), "存档");
        pagerAdapter.addFragment(new HomePagerFragment(5), "杂物间");
        //pagerAdapter.addFragment(new HomePagerFragment(), "小伙伴们");
        pager.setAdapter(pagerAdapter);
        tab.setupWithViewPager(pager);

    }

    private static boolean mBackKeyPressed = false;//记录是否有首次按键

    @Override
    public void onBackPressed() {
        if (!mBackKeyPressed) {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mBackKeyPressed = true;
            new Timer().schedule(new TimerTask() {//延时两秒，如果超出则擦错第一次按键记录
                @Override
                public void run() {
                    mBackKeyPressed = false;
                }
            }, 2000);
        } else {//退出程序
            this.finish();
            //System.exit(0);
        }
    }

    static class PagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> fragmentTitleList = new ArrayList<>();

        public PagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        public void addFragment(Fragment fragment, String title) {
            fragmentList.add(fragment);
            fragmentTitleList.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitleList.get(position);
        }
    }
}
