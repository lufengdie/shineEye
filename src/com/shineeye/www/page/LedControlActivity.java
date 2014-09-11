
package com.shineeye.www.page;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.shineeye.www.R;
import com.shineeye.www.SocketService;

/**
 * @Class Name : LedControlActivity
 * @Description: led控制页面
 * @Author yuanliangfeng@ceehoo.cn
 * @Date 2014年6月18日 下午3:08:37
 */
public class LedControlActivity extends FragmentActivity implements OnClickListener,
        OnTouchListener{

	private TextView ledPowerTv;
    private TextView brightnessTv;
    private TextView colorTempTv;
    private TextView delayCloseTv;
    private TextView timingOpenTv;
    private TextView timingCloseTv;
    private TextView ledControlNameTv;
    private LedParameter led;
    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_led_control);
        initData();
        initUI();
    }
    
    private void initData(){
    	led = (LedParameter) getIntent().getSerializableExtra("led");
    }

    private void initUI() {
    	ledPowerTv = (TextView) findViewById(R.id.ledPowerTv);
        colorTempTv = (TextView) findViewById(R.id.colorTempTv);
        brightnessTv = (TextView) findViewById(R.id.brightnessTv);
        delayCloseTv = (TextView) findViewById(R.id.delayCloseTv);
        timingOpenTv = (TextView) findViewById(R.id.timingOpenTv);
        timingCloseTv = (TextView) findViewById(R.id.timingCloseTv);
        ledControlNameTv = (TextView) findViewById(R.id.ledControlNameTv);
        brightnessTv.setOnTouchListener(this);
        colorTempTv.setOnTouchListener(this);
        findViewById(R.id.colorSeletorIv).setOnTouchListener(this);
        findViewById(R.id.backBtn).setOnClickListener(this);
        
        ledControlNameTv.setText(led.getLedName());
    }

    @Override
    public void onClick(View view) {

    	switch (view.getId()) {

        case R.id.delayCloseTv:
        	startActivity(new Intent(this, SetSleepTimeActivity.class));
        	break;
        	
        case R.id.timingOpenTv:
        	startActivity(new Intent(this, SetTimingOpenActivity.class));
        	break;
        	
        case R.id.timingCloseTv:
        	startActivity(new Intent(this, SetTimingCloseActivity.class));
        	break;
    	
		case R.id.backBtn:
			finish();
			break;
			
		case R.id.addBtn:
			Intent intent = new Intent(this, LedSearchActivity.class);
			intent.putExtra("led", led);
			startActivity(intent);
			break;

		case R.id.ledPowerTv:
			if (SocketService.getInstance().sendMessage("test", sendHandler, getMessage(ledPowerTv.getId()))) {
				ledPowerTv.setTextColor(Color.RED);
				ledPowerTv.setClickable(false);
			}else{
				Toast.makeText(this, "正在发送其他命令，请稍后!", Toast.LENGTH_LONG).show();
			}
			break;
			
		default:
			break;
		}
    }
    
    private Message getMessage(int resId){
    	Message msg = new Message();
    	Bundle data = new Bundle();
    	data.putInt("resId", resId);
    	msg.setData(data);
    	return msg;
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {

        float x = event.getX();
        float y = event.getY();
        switch (view.getId()) {

            case R.id.brightnessTv:

                float brightnessX = event.getX();
                System.out.println("brightnessTv x坐标：" + brightnessX);
                showPopWindow(findViewById(R.id.colorSeletorIv), "未知", (int)brightnessX);
                break;

            case R.id.colorTempTv:
                float colorTempX = event.getX();
                System.out.println("colorTempTv x坐标：" + colorTempX);
                break;

            case R.id.colorSeletorIv:
                ImageView img = (ImageView) view;
                img.setDrawingCacheEnabled(true);
                Bitmap b = img.getDrawingCache();
                int color = b.getPixel((int) x, (int) y);
                setColor(color);
                break;

            default:
                break;
        }
        return false;
    }

    private void setColor(int color) {
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);
        System.out.println("r:" + r + ",g:" + g + ",b:" + b);
    }


    final static int Flag_show_sleep_time = 69;
    final static int Flag_show_timing_open = 96;
    final static int Flag_show_timing_close = 99;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {

            switch (msg.what) {

                case Flag_show_sleep_time:
                    int sleepTime = msg.arg1;
                    String sleepTimeStr = String.format(getString(R.string.sleepTimeFormat),
                            sleepTime);
                    delayCloseTv.setText(sleepTimeStr);
                    break;

                case Flag_show_timing_open:
                    int openHours = msg.arg1;
                    int openMinutes = msg.arg2;
                    String openTimeStr = getTime(R.string.timingOpenFormat, openHours, openMinutes);
                    timingOpenTv.setText(openTimeStr);
                    break;

                case Flag_show_timing_close:
                    int closeHours = msg.arg1;
                    int closeMinutes = msg.arg2;
                    String closeTimeStr = getTime(R.string.timingCloseFormat, closeHours,
                            closeMinutes);
                    timingCloseTv.setText(closeTimeStr);
                    break;

                default:
                    break;
            }

        }
    };
    
    private Handler sendHandler = new Handler (){
    	public void handleMessage(Message msg) {
    		Bundle data = msg.getData();
    		String result = data.getString("result");
    		int resId = data.getInt("resId");
    		TextView view = (TextView)findViewById(resId);
    		view.setTextColor(Color.WHITE);
    		view.setClickable(true);
    		Toast.makeText(LedControlActivity.this, result, Toast.LENGTH_LONG).show();
    	};
    };

    private String getTime(int formatId, int hours, int minutes) {
        String time = "";
        String hourStr = String.valueOf(hours);
        String mintueStr = String.valueOf(minutes);
        if (hourStr.length() == 1) {
            hourStr = "0" + hourStr;
        }
        if (mintueStr.length() == 1) {
            mintueStr = "0" + mintueStr;
        }
        time = String.format(getString(formatId), hourStr, mintueStr);
        return time;
    }
    
    private void showPopWindow(View parent, String str, int x){
    	TextView popTextView;
    	if (popupWindow == null) {
    		popTextView = (TextView) LayoutInflater.from(this).inflate(R.layout.pop_window_led_control, null);
    		popupWindow = new PopupWindow(popTextView, 100, 100);
    		popupWindow.setContentView(popTextView);
		}else{
			popTextView = (TextView) popupWindow.getContentView();
		}
    	popTextView.setText(str);
    	popupWindow.showAtLocation(parent, Gravity.NO_GRAVITY, 0, 0);
    }
    
}
