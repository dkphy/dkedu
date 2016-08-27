package com.jspxcms.plug.service;

/**
 * 验证码服务
 * @author yz
 *
 */
public interface MobileVerifyService {

	/**
	 * 生成验证码
	 * @param mobile
	 * @return
	 */
	String generateNewVerifyCode(String mobile);
	
	/**
	 * 验证并返回结果
	 * @param mobile
	 * @param verifyCode
	 * @return
	 */
	boolean verify(String mobile, String verifyCode);
	
	/**
	 * 验证码短信-新用户注册
	 * @param mobile
	 * @param verifyCode
	 * @return
	 */
	boolean sendSmsVerifyCodeForReg(String mobile, String verifyCode);
	
	/**
	 * 验证码短信-忘记密码
	 * @param mobile
	 * @param verifyCode
	 * @return
	 */
	boolean sendSmsVerifyCodeForForgotPsw(String mobile, String verifyCode);
	
	/**
	 * 验证码短信-修改手机号
	 * @param mobile
	 * @param verifyCode
	 * @return
	 */
	boolean sendSmsVerifyCodeForAlterMobile(String mobile, String verifyCode);
}
