package com.shineeye.www.page;

import java.io.Serializable;

/**
 * 
 * @author yuanlf
 *
 */
public class LedParameter implements Serializable{

	/**
	 * SerializableUID
	 */
	private static final long serialVersionUID = 7382232398233613765L;
	

	/** 灯名称 **/
	private String ledName;
	
	/** 亮度 **/
	private String ledLight;
	
	/** 色温 **/
	private String ledTemp;
	
	/** 定时开灯 **/
	private String timingOpen;
	
	/** 定时关灯 **/
	private String timingClose;
	
	/** 延时关灯 **/
	private String delayClose;
	
	/** 开关状态 **/
	private boolean power;
	
	/** 是否被选中 **/
	private boolean checkd;
	

	public String getLedName() {
		return ledName;
	}

	public void setLedName(String ledName) {
		this.ledName = ledName;
	}

	public String getLedLight() {
		return ledLight;
	}

	public void setLedLight(String ledLight) {
		this.ledLight = ledLight;
	}

	public String getLedTemp() {
		return ledTemp;
	}

	public void setLedTemp(String ledTemp) {
		this.ledTemp = ledTemp;
	}

	public String getTimingOpen() {
		return timingOpen;
	}

	public void setTimingOpen(String timingOpen) {
		this.timingOpen = timingOpen;
	}

	public String getTimingClose() {
		return timingClose;
	}

	public void setTimingClose(String timingClose) {
		this.timingClose = timingClose;
	}

	public String getDelayClose() {
		return delayClose;
	}

	public void setDelayClose(String delayClose) {
		this.delayClose = delayClose;
	}

	public boolean isPower() {
		return power;
	}

	public void setPower(boolean power) {
		this.power = power;
	}

	public boolean isCheckd() {
		return checkd;
	}

	public void setCheckd(boolean checkd) {
		this.checkd = checkd;
	}
	
	
}
