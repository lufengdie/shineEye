
package com.shineeye.www.page;

import com.shineeye.www.R;
import com.shineeye.www.SocketService;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends FragmentActivity implements OnClickListener, OnItemClickListener,
        OnTouchListener, OnGestureListener {

    private ListView ledListView;
    private String[] ledNameArray;
    private Context context;
    private GestureDetector mygesture;
    private static final int FLING_MIN_DISTANCE = 90;// 移动最小距离
    private static final int FLING_MIN_VELOCITY = 30;// 移动最大速度
    private LedMainFragment ledMainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getBaseContext();
        initUI();
    }

    private void initUI() {
        ledNameArray = getResources().getStringArray(R.array.ledNameArray);
        ledListView = (ListView) findViewById(R.id.ledListView);
        ledListView.setAdapter(new LedAdapter());
        ledListView.setOnItemClickListener(this);
        ledListView.setOnTouchListener(this);
        mygesture = new GestureDetector(this);

        startService(new Intent(this, SocketService.class));
    }

    @Override
    public void onClick(View v) {

    }

    class LedAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return ledNameArray != null ? ledNameArray.length : 0;
        }

        @Override
        public Object getItem(int position) {
            return ledNameArray[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View contentView, ViewGroup parent) {
            if (contentView == null) {
                contentView = LayoutInflater.from(context).inflate(R.layout.item_led_main, null);
            }
            TextView idNameTv = (TextView) contentView.findViewById(R.id.ledNameTv);
            idNameTv.setText(ledNameArray[position]);
            return contentView;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
        String ledName = ledNameArray[position];
        Intent intent = new Intent(this, LedControlActivity.class);
        intent.putExtra("ledName", ledName);
        startActivity(intent);
    }

    private void addFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.add(R.id.fragmentLayout, new LedMainFragment());
        transaction.commit();
    }

    @Override
    public boolean onTouch(View arg0, MotionEvent ev) {
        return mygesture.onTouchEvent(ev);
    }

    @Override
    public boolean onDown(MotionEvent arg0) {
        return false;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (e1.getX() - e2.getX() > FLING_MIN_DISTANCE && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
            hideFragment();
        } else if (e2.getX() - e1.getX() > FLING_MIN_DISTANCE
                && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
            showFragment();
        }
        return false;
    }

    @Override
    public void onLongPress(MotionEvent arg0) {
    }

    @Override
    public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2, float arg3) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent arg0) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent arg0) {
        return false;
    }

    private void showFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        LedMainFragment ledMainFragment = getFragment();
        if (ledMainFragment.isAdded()) {
            transaction.show(ledMainFragment);
        } else {
//            transaction.add(R.id.fragmentLayout, ledMainFragment);
        }
        transaction.commit();
    }

    private LedMainFragment getFragment() {
        if (ledMainFragment == null) {
            ledMainFragment = new LedMainFragment();
        }
        return ledMainFragment;
    }

    private void hideFragment() {
        if (ledMainFragment == null) {
            return;
        } else if (ledMainFragment.isVisible()) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.hide(ledMainFragment);
            transaction.commit();
        }
    }

}
