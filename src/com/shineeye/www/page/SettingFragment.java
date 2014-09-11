
package com.shineeye.www.page;

import com.shineeye.www.R;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * @Class Name : LedControlFragment
 * @Description: LED控制页面左侧菜单
 * @Author yuanliangfeng@ceehoo.cn
 * @Date 2014年6月20日 下午11:22:21
 */
public class SettingFragment extends Fragment implements OnClickListener, OnItemClickListener {

    private Context context;
    private ListView settingListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        context = getActivity().getBaseContext();
        initUI(view);
        return view;
    }

    private void initUI(View view) {
        settingListView = (ListView) view.findViewById(R.id.settingListView);
        String[] names = getResources().getStringArray(R.array.settingNames);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.item_setting,
                R.id.settingName, names);
        settingListView.setAdapter(adapter);
        settingListView.setOnItemClickListener(this);

        // view.findViewById(R.id.backBtn).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.backBtn:

                break;

            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        switch (arg2) {
            case 0:
                startActivity(new Intent(context, ManualActivity.class));
                break;
            case 1:
                startActivity(new Intent(context, PasswordSettingActivity.class));
                break;
            case 2:

                break;
            case 3:

                break;
            case 4:
                startActivity(new Intent(context, AboutUsActivity.class));
                break;

            default:
                break;
        }
    }

}
