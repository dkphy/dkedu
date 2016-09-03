package com.jspxcms.plug.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jspxcms.common.sms.SmsSender;
import com.jspxcms.plug.domain.MobileVerify;
import com.jspxcms.plug.repository.MobileVerifyDao;
import com.jspxcms.plug.service.MobileVerifyService;

/**
 * 验证码Service
 * @author yz
 *
 */
@Service
public class MobileVerifyServiceImpl implements MobileVerifyService{
	
	public static final String linkUrl = "http://www.dkxl.cn/edu.jspx";
	
	private static final Logger logger = LoggerFactory.getLogger(MobileVerifyServiceImpl.class);
	
	@Autowired
	MobileVerifyDao mobileVerifyDao;

	@Transactional
	@Override
	public String generateNewVerifyCode(String mobile) {
		if(StringUtils.isBlank(mobile)) {
			throw new IllegalArgumentException("mobile should not be null");
		}
		String verifyCode = generateCode();
		MobileVerify v = new MobileVerify();
		v.setMobile(mobile);
		v.setVerifyCode(verifyCode);
		v.setStatus(MobileVerify.Status.WAITING);
		Date d = new Date();
		v.setCreateTime(d);
		v.setModifyTime(d);
		mobileVerifyDao.save(v);
		logger.info("generate new verifyCode, mobile={}, code={}", mobile, verifyCode);
		return verifyCode;
	}

	private String generateCode() {
		Random r = new Random();
		StringBuilder b = new StringBuilder();
		for(int i=0; i<6; i++) {
			if(i==0) {// 首位1-9
				b.append(r.nextInt(9) + 1);
			} else {// 其余位0-9
				b.append(r.nextInt(10));
			}
		}
		return b.toString();
	}
	
	@Transactional
	@Override
	public boolean verify(String mobile, String verifyCode) {
		if(StringUtils.isBlank(mobile) || StringUtils.isBlank(verifyCode)) {
			throw new IllegalArgumentException("mobile and verifyCode should not be null");
		}
		Pageable limit = new PageRequest(0, 1, Direction.DESC, "vId");
		List<MobileVerify> vList = this.mobileVerifyDao.findWaitingRecordByMobile(mobile, limit);
		if(vList == null || vList.isEmpty()) {
			logger.info("no waiting smsVerifyCode, mobile={}", mobile);
			return false;
		}
		if(!vList.get(0).getVerifyCode().equals(verifyCode)) {
			logger.info("mobile verify failed, mobile={}, verifyCode={}", mobile, verifyCode);
			return false;
		}
		this.mobileVerifyDao.verify(mobile);
		logger.info("mobile verify successfully, mobile={}, verifyCode={}", mobile, verifyCode);
		return true;
	}

	private boolean sendSms(String mobile, String content) {
		try {
			String contentEncode = URLEncoder.encode(content, "utf-8");
			return SmsSender.sendSms(mobile, contentEncode);
		} catch (UnsupportedEncodingException e) {
			logger.error("send sms failed, mobile=" + mobile, e);
			return false;
		}
	}
	
	public boolean sendSmsVerifyCodeForReg(String mobile, String verifyCode) {
		String format = "用户注册验证码：%s，10分钟内有效。你还可以前往此链接 %s";
		String content = String.format(format, verifyCode, linkUrl);
		return sendSms(mobile, content);
	}
	
	public boolean sendSmsVerifyCodeForForgotPsw(String mobile, String verifyCode) {
		String format = "密码重设验证码：%s，10分钟内有效。你还可以前往此链接 %s";
		String content = String.format(format, verifyCode, linkUrl);
		return sendSms(mobile, content);
	}
	
	public boolean sendSmsVerifyCodeForAlterMobile(String mobile, String verifyCode) {
		String format = "更改绑定手机号验证码：%s，10分钟内有效。你还可以前往此链接 %s";
		String content = String.format(format, verifyCode, linkUrl);
		return sendSms(mobile, content);
	}
}
