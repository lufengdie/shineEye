
package com.shineeye.www.page;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.shineeye.www.R;
import com.shineeye.www.SocketService;

public class MainTabAcitivity extends FragmentActivity {

    // 定义FragmentTabHost对象
    private FragmentTabHost mTabHost;

    // 定义一个布局
    private LayoutInflater layoutInflater;

    // 定义数组来存放Fragment界面
    private Class fragmentArray[] = {
            LedMainFragment.class, AppliancesFragment.class, SecurityFragment.class,
            CarFragment.class, SettingFragment.class};

    private String[] ledNameArray = {
            "照明", "家电", "安防", "汽车", "设置"
    };

    // 定义数组来存放按钮图片
    private int mImageViewArray[] = {
            R.drawable.tab_led_btn, R.drawable.tab_appliances_btn, R.drawable.tab_security_btn,
            R.drawable.tab_car_btn, R.drawable.tab_setting_btn
    };

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_main_tab);
        initView();
//        startService(new Intent(this, SocketService.class));
    }

    /**
     * 初始化组件
     */
    private void initView() {
        // 实例化布局对象
        layoutInflater = LayoutInflater.from(this);

        // 实例化TabHost对象，得到TabHost
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        // 得到fragment的个数
        int count = fragmentArray.length;

        for (int i = 0; i < count; i++) {
            // 为每一个Tab按钮设置图标、文字和内容
            TabSpec tabSpec = mTabHost.newTabSpec(ledNameArray[i]).setIndicator(getTabItemView(i));
            // 将Tab按钮添加进Tab选项卡中
            mTabHost.addTab(tabSpec, fragmentArray[i], null);
            // 设置Tab按钮的背景
            // mTabHost.getTabWidget().getChildAt(i)
            // .setBackgroundResource(R.drawable.selector_tab_background);
        }
    }

    /**
     * 给Tab按钮设置图标和文字
     */
    private View getTabItemView(int index) {
        View view = layoutInflater.inflate(R.layout.tab_item_view, null);

        ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
        imageView.setImageResource(mImageViewArray[index]);

        TextView textView = (TextView) view.findViewById(R.id.textview);
        textView.setText(ledNameArray[index]);

        return view;
    }

}
