
package com.shineeye.www.page;

import java.util.ArrayList;
import java.util.List;

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
import android.widget.ListView;
import android.widget.TextView;

import com.shineeye.www.R;

/**
 * @Class Name : LedMainFragment
 * @Description: 寻灯页面
 * @Author yuanliangfeng
 * @Date 2014年7月4日 下午10:28:14
 */
public class LedSearchActivity extends Activity implements OnItemClickListener {

    private List<LedParameter> ledList;
    private Context context;
    private LedAdapter adapter;
    private TextView backBtn;
    private LedParameter led;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_led_search);
    	context = getApplicationContext();
    	initData();
    	initUI();
    }
    
    private void initUI(){
    	backBtn = (TextView) findViewById(R.id.backBtn);
    	findViewById(R.id.updateBtn).setOnClickListener(onClickListener);
    	ListView listView = (ListView) findViewById(R.id.ledListView);
    	adapter = new LedAdapter();
    	listView.setAdapter(adapter);
    	listView.setOnItemClickListener(this);
    	backBtn.setOnClickListener(onClickListener);
    	backBtn.setText(led.getLedName() + "设置");
    }
    
    private void initData(){
    	led = (LedParameter) getIntent().getSerializableExtra("led");
    	testData();
    }
    
    private void testData(){
    	String[] ledNames = {"灯1"};
    	ledList = new ArrayList<LedParameter>();
    	for (int i = 0; i < ledNames.length; i++) {
    		LedParameter led = new LedParameter();
    		led.setLedName(ledNames[i]);
    		ledList.add(led);
		}
    }


    class LedAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return ledList.size();
        }

        @Override
        public Object getItem(int position) {
            return ledList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View contentView, ViewGroup parent) {
        	Viewholder holder = null;
        	if (contentView == null) {
        		holder = new Viewholder();
        		contentView = LayoutInflater.from(context).inflate(
        				R.layout.item_led_search, null);
        		holder.ledNameTv = (TextView) contentView.findViewById(R.id.ledNameTv);
        		holder.groupATv = (TextView) contentView.findViewById(R.id.groupATv);
        		holder.groupBTv = (TextView) contentView.findViewById(R.id.groupBTv);
        		holder.groupCTv = (TextView) contentView.findViewById(R.id.groupCTv);
        		holder.groupDTv = (TextView) contentView.findViewById(R.id.groupDTv);
        		holder.saveTv = (TextView) contentView.findViewById(R.id.saveTv);
        		contentView.setTag(holder);
			}else{
				holder = (Viewholder) contentView.getTag();
			}
        	LedParameter led = ledList.get(position);
        	holder.ledNameTv.setText(led.getLedName());
            holder.position = position;
            return contentView;
        }
        
        class Viewholder{
        	TextView ledNameTv;
        	TextView groupATv;
        	TextView groupBTv;
        	TextView groupCTv;
        	TextView groupDTv;
        	TextView saveTv;
        	int position;
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
    }

    private OnClickListener onClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View view) {
			switch (view.getId()) {
			
			case R.id.backBtn:
				finish();
				break;
				
			case R.id.updateBtn:
				break;

			default:
				break;
			}
		}
	};
	
}