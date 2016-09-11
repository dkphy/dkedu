/**
 * 
 */
package com.jspxcms.plug.web.fore;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import com.jspxcms.core.domain.Site;
import com.jspxcms.core.domain.Special;
import com.jspxcms.core.domain.User;
import com.jspxcms.core.service.SpecialService;
import com.jspxcms.core.support.Context;
import com.jspxcms.core.support.ForeContext;
import com.jspxcms.plug.domain.Order;
import com.jspxcms.plug.dto.AddItemDTO;
import com.jspxcms.plug.dto.CreateClearingDTO;
import com.jspxcms.plug.dto.CreateOrderDTO;
import com.jspxcms.plug.pay.AlipayNotify;
import com.jspxcms.plug.service.PayService;
import com.jspxcms.plug.status.OrderStatus;

/**
 * @author YRee
 * 
 */
@Controller
@RequestMapping(value = "miniPay")
public class PayAction {
	protected static Logger logger = LoggerFactory.getLogger(PayAction.class);
	@Resource
	private PayService payService;
	@Autowired
	private SpecialService specialService;

	/**
	 * 支付页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value="pay_index.jspx")
	public String index(Integer courseId, HttpServletRequest request, 
			HttpServletResponse response, org.springframework.ui.Model modelMap){
		Special course = specialService.get(courseId);
		Map<String, Object> data = modelMap.asMap();
		data.put("course", course);
		ForeContext.setData(data, request);
		
		Site site = Context.getCurrentSite(request);
		return site.getTemplate("pay_confirm_order.html");
	}
	
	/**
	 * 确定支付--创建支付订单，跳转到支付宝
	 * @param buyerId
	 * @param productionId
	 * @param skuPrice
	 * @return
	 */
	@RequestMapping(value = "confirmOrder.jspx")
	public RedirectView confirmOrder(@RequestParam("productionId")int productionId,@RequestParam("skuPrice") double skuPrice, HttpServletRequest request, Model model){
		User user = Context.getCurrentUser(request);
		if(user == null) {
			return new RedirectView("/login.jspx?fallback=miniPay/pay_index.jspx?courseId=" + productionId,true,false,false);
		}
		
		Special course = specialService.get(productionId);
		if(course == null) {
			// 无此课程
			return new RedirectView("/miniPay/payFail.jspx",true,false,false);
		}
		String priceStr = course.getCustoms().get("price");
		double price = Double.valueOf(priceStr);
		if(Math.abs(skuPrice - price) > 0.01) {
			// 价格不一致
			return new RedirectView("/miniPay/payFail.jspx",true,false,false);
		}
		
		// 先创建订单，目前关键字段只有buyerId
		// 增加商品销售量
		CreateOrderDTO coDTO = new CreateOrderDTO();
		coDTO.setBuyerId(user.getId());
		coDTO.setBuyerName(user.getUsername());
		coDTO.setOrderItemCount(1);
		coDTO.setTotalMoney(price);
		coDTO.setSubject(course.getTitle());
		coDTO.setSubjectId(productionId);
		Order order = payService.createOrder(coDTO);
		
		// 添加商品
		AddItemDTO aiDTO = new AddItemDTO();
		aiDTO.setOrderId(order.getId());
		aiDTO.setProductionId(productionId);
		aiDTO.setSkuPrice(price);
		aiDTO.setSkuAmmount(1);
		aiDTO.setSkuId(productionId);
		aiDTO.setProductionName(course.getTitle());
		payService.addItem(aiDTO);
		
		//在这里拼接一个参数的跳转
		String url = buildPaymentUrl(order.getId(), order.getSubject(), skuPrice);
		logger.info("jump to alipay, url=" + url);
		return new RedirectView(url,true,false,false);
	}
	
	@RequestMapping(value = "toAlipay.jspx")
	public RedirectView jumpToAlipay(Integer orderId) {
		Order order = payService.findOrderByOrderId(orderId);
		String url = buildPaymentUrl(order.getId(), order.getSubject(), order.getTotalMoney());
		logger.info("jump to alipay, url=" + url);
		return new RedirectView(url,true,false,false);
	}
	
	private String buildPaymentUrl(Integer orderId, String subject, Double totalMoney) {
		try {
			subject = URLEncoder.encode(subject, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "http://dkxl.cn/alipay/alipayapi.jsp?WIDout_trade_no="+orderId
				+"&WIDsubject="+subject+"&WIDtotal_fee="+totalMoney+"&WIDbody='DK'";
	}
	
	@RequestMapping(value="alipay/callback.jspx")
	public RedirectView callback(HttpServletRequest request, HttpServletResponse response,
			org.springframework.ui.Model modelMap) {
		CreateClearingDTO result;
		try {
			result = parseAlipayResult(request);
			if(result != null) {
				// 保存支付宝通知结果（不论成功失败，都会保存）
				payService.createClearing(result);
				// 处理支付结果(不论成功失败，都会更新DB)
				payService.processTradeResult(result);
				// 只有成功的结果返回success
				if(OrderStatus.PAID.equals(result.getStatus())) {
					return new RedirectView("/miniPay/paySuccess.jspx",true,false,false);
				}
				// 注意：此位置之后不能有任何成功相关的逻辑
			}
		} catch (Exception e) {
			logger.error("process alipay result failed", e);
		}
		// 无论何种情况，只要前面没有返回成功，都跳转到失败页面
		return new RedirectView("/miniPay/payFail.jspx",true,false,false);
	}
	
	@SuppressWarnings("rawtypes")
	private CreateClearingDTO parseAlipayResult(HttpServletRequest request) {
		try {
			// 获取支付宝GET过来反馈信息
			Map<String, String> params = new HashMap<String, String>();
			Map requestParams = request.getParameterMap();
			for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i]
							: valueStr + values[i] + ",";
				}
				// 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
				valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
				params.put(name, valueStr);
			}
			
			// 验证签名，确保传输过程中没有被篡改
			boolean verify_result = AlipayNotify.verify(params);
			if(!verify_result) {
				logger.error("alipay verify failed!");
				return null;
			}
			
			// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
			String out_trade_no = getFieldValue(request, "out_trade_no");// 商户订单号
			String trade_no = getFieldValue(request, "trade_no"); // 支付宝交易号
			String trade_status = getFieldValue(request, "trade_status");// 交易状态
			String buyer_email = getFieldValue(request, "buyer_email");
			String total_feeStr = getFieldValue(request, "total_fee");
			// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
			Double total_fee = Double.parseDouble(total_feeStr);
			Integer orderId = Integer.parseInt(out_trade_no);
			String status = isTradeSuccess(trade_status) ? OrderStatus.PAID : OrderStatus.FAIL;

			CreateClearingDTO ccDTO = new CreateClearingDTO();
			ccDTO.setChannel("alipay");
			ccDTO.setOrderId(orderId);
			ccDTO.setOutTradeId(trade_no);
			ccDTO.setOutBuyerAccount(buyer_email);
			ccDTO.setTradeMoney(total_fee);
			ccDTO.setStatus(status);
			return ccDTO;
		} catch (UnsupportedEncodingException e) {
			logger.error("process alipay result failed", e);
			return null;
		}
	}
	
	private boolean isTradeSuccess(String tradeStatus) {
		return tradeStatus.equals("TRADE_FINISHED") || tradeStatus.equals("TRADE_SUCCESS");
	}
	
	private String getFieldValue(HttpServletRequest request, String fieldName) {
		try {
			return new String(request.getParameter(fieldName)
					.getBytes("ISO-8859-1"), "UTF-8");
		} catch (Exception e) {
			return "";
		}
	}
	
	@RequestMapping(value="paySuccess.jspx")
	public String showPaySuccess(HttpServletRequest request, HttpServletResponse response,
			org.springframework.ui.Model modelMap) {
		Map<String, Object> data = modelMap.asMap();
		ForeContext.setData(data, request);
		Site site = Context.getCurrentSite(request);
		return site.getTemplate("pay_success.html");
	}
	
	@RequestMapping(value="payFail.jspx")
	public String showPayFail(HttpServletRequest request, HttpServletResponse response,
			org.springframework.ui.Model modelMap) {
		Map<String, Object> data = modelMap.asMap();
		ForeContext.setData(data, request);
		Site site = Context.getCurrentSite(request);
		return site.getTemplate("pay_fail.html");
	}

}
