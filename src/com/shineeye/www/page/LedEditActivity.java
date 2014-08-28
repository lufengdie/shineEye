
package com.shineeye.www.page;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.shineeye.www.R;

/**
 * @Class Name : LedMainFragment
 * @Description: led主页面
 * @Author yuanliangfeng
 * @Date 2014年7月4日 下午10:28:14
 */
public class LedEditActivity extends Activity implements OnItemClickListener, OnClickListener {

    private String[] ledNames = null;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_led_edit);
    	context = this;
    	getLeds();
    	initUI();
    }
    
    private void initUI(){
    	findViewById(R.id.cancelBtn).setOnClickListener(this);
    	findViewById(R.id.commitBtn).setOnClickListener(this);
    	ListView listView = (ListView) findViewById(R.id.ledListView);
    	listView.setAdapter(new LedNameAdapter());
    	listView.setOnItemClickListener(this);
    }

    private void getLeds() {
        ledNames = getResources().getStringArray(R.array.ledNameArray);
    }

    class LedNameAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return ledNames.length;
        }

        @Override
        public Object getItem(int position) {
            return ledNames[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View contentView, ViewGroup parent) {
        	if (contentView == null) {
        		LayoutInflater layout = LayoutInflater.from(context);
        		contentView = layout.inflate(R.layout.item_led_edit, null);
			}
            TextView ledNameTv = (TextView) contentView.findViewById(R.id.ledNameTv);
            ledNameTv.setText(ledNames[position]);
            CheckBox ledCheckBox = (CheckBox) contentView.findViewById(R.id.ledCheckBox);
            return contentView;
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {

    }

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		
		case R.id.cancelBtn:
			finish();
			break;
		
		case R.id.commitBtn:
			System.out.println("");
			break;

		default:
			break;
		}
	}
	
}
