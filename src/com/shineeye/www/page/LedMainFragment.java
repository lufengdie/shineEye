
package com.shineeye.www.page;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.shineeye.www.R;

/**
 * @Class Name : LedMainFragment
 * @Description: led主页面
 * @Author yuanliangfeng
 * @Date 2014年7月4日 下午10:28:14
 */
public class LedMainFragment extends Fragment implements OnItemClickListener, OnItemLongClickListener {

    private List<LedParameter> ledList;
    private Context context;
    private LedAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_led_main, container, false);
        context = getActivity().getBaseContext();
        initData();
        initUI(view);
        return view;
    }
    
    private void initUI(View view){
    	ListView listView = (ListView) view.findViewById(R.id.ledListView);
    	adapter = new LedAdapter();
    	listView.setAdapter(adapter);
    	listView.setOnItemClickListener(this);
    	view.findViewById(R.id.addBtn).setOnClickListener(onClickListener);
    	listView.setOnItemLongClickListener(this);
    }
    
    private void initData(){
    	testData();
    }
    
    private void testData(){
    	String[] ledNames = getResources().getStringArray(R.array.ledNameArray);
    	ledList = new ArrayList<LedParameter>();
    	for (int i = 0; i < ledNames.length; i++) {
    		LedParameter led = new LedParameter();
    		led.setLedName(ledNames[i]);
    		led.setLedLight("20%");
    		led.setLedLight("2700K");
    		led.setDelayClose("关");
    		led.setCheckd(false);
    		ledList.add(led);
		}
    }


    class LedAdapter extends BaseAdapter implements OnLongClickListener{

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
        		contentView = LayoutInflater.from(getActivity().getBaseContext()).inflate(
        				R.layout.item_led_main, null);
        		holder.ledNameTv = (TextView) contentView.findViewById(R.id.ledNameTv);
        		holder.deleteBtn = contentView.findViewById(R.id.deleteBtn);
        		holder.ledPowerBtn = contentView.findViewById(R.id.ledPowerBtn);
        		holder.detailslayout = contentView.findViewById(R.id.detailslayout);
        		holder.detailslayout.setOnClickListener(onClickListener);
        		holder.detailslayout.setOnLongClickListener(this);
        		contentView.setTag(holder);
			}else{
				holder = (Viewholder) contentView.getTag();
			}
        	LedParameter led = ledList.get(position);
        	holder.ledNameTv.setText(led.getLedName());
            holder.position = position;
            holder.detailslayout.setTag(position);
//            ObjectAnimator.ofFloat(contentView, "x", 0f).start();
            if (led.isCheckd()) {
            	holder.deleteBtn.setVisibility(View.VISIBLE);
            	holder.ledPowerBtn.setVisibility(View.GONE);
            	holder.deleteBtn.setOnClickListener(onClickListener);
			}else{
				holder.deleteBtn.setVisibility(View.GONE);
				holder.ledPowerBtn.setVisibility(View.VISIBLE);
			}
            return contentView;
        }
        
        class Viewholder{
        	TextView ledNameTv;
        	View deleteBtn;
        	View ledPowerBtn;
        	int position;
        	View detailslayout;
        }

		@Override
		public boolean onLongClick(View view) {
			deletePosition = (Integer) view.getTag();
			return false;
		}

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {}

    private OnClickListener onClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View view) {
			switch (view.getId()) {
			
			case R.id.addBtn:
				startActivity(new Intent(context, LedEditActivity.class));
				break;
				
			case R.id.leftButton:
				dialog.dismiss();
				break;
				
			case R.id.rightButton:
				dialog.dismiss();
				ledList.remove(deletePosition);
				adapter.notifyDataSetChanged();
				break;
				
			case R.id.detailslayout:
				int position = (Integer) view.getTag();
				Intent intent = new Intent(context, LedControlActivity.class);
				intent.putExtra("led", ledList.get(position));
				getActivity().startActivity(intent);
				break;

			default:
				break;
			}
		}
	};
    
	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position,
			long arg3) {
		deletePosition = position;
		showDeleteDialog();
		return false;
	}
	
	private AlertDialog dialog;
	private int deletePosition;
	
	/**
	 * 显示删除对话框
	 */
	private void showDeleteDialog(){
		if (dialog == null) {
			dialog = new AlertDialog.Builder(getActivity()).create();
			View view = LayoutInflater.from(context).inflate(R.layout.dialog_led_delete, null);
			view.findViewById(R.id.leftButton).setOnClickListener(onClickListener);
			view.findViewById(R.id.rightButton).setOnClickListener(onClickListener);
			dialog.setView(view);
		}
		dialog.show();
	}

}
