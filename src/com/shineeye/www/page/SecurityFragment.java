package com.shineeye.www.page;

import com.shineeye.www.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 安防页面
 * @author yuanlf 
 */
public class SecurityFragment extends Fragment{

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_security, container, false);
		return view;
	}
	
}
