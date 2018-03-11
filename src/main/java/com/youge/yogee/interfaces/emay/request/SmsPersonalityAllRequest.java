package com.youge.yogee.interfaces.emay.request;


import com.youge.yogee.interfaces.emay.emay.eucp.inter.framework.dto.PersonalityParams;

/**
 * 批量短信发送参数
 * @author Frank
 *
 */
public class SmsPersonalityAllRequest extends BaseRequest {

	private static final long serialVersionUID = 1L;

	private PersonalityParams[] smses;

	public PersonalityParams[] getSmses() {
		return smses;
	}

	public void setSmses(PersonalityParams[] smses) {
		this.smses = smses;
	}
	

}
