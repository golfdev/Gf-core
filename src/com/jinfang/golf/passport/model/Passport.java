package com.jinfang.golf.passport.model;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jinfang.golf.passport.home.DailySaltHome;
import com.jinfang.golf.utils.CookieUtil;

/**
 * Passport操作类
 * @author Administrator
 *
 */
@Component
public class Passport {
	
	@Autowired
	private DailySaltHome dailySaltHome;

	@Resource(name="md5Signature")
	private Signature signature;
	
	/**
	 * 在cookie中创建userId对应的passport
	 * @param userId
	 * @param resp
	 * @param maxAge
	 * @return
	 */
	public PassportTicket createInCookie(int userId, HttpServletResponse resp, Integer maxAge){
		maxAge = (maxAge!=null ? maxAge : PassportTicket.DEFAULT_TICKET_LIFETIME);
		PassportTicket ticket = createTicket(userId, maxAge);
		CookieUtil.saveCookie(resp, "passport", toCookieValue(ticket), ".hunbola.com", "/", maxAge, false);
		return ticket;
	}
	
	
	public String createAppToken(int userId,Integer maxAge){
		maxAge = (maxAge!=null ? maxAge : PassportTicket.DEFAULT_TICKET_LIFETIME);
		PassportTicket ticket = createTicket(userId, maxAge);
		return toCookieValue(ticket);
	}
	
	/**
	 * 在cookie中创建userId对应的passport
	 * @param userId
	 * @param resp
	 * @param maxAge
	 * @return
	 */
	public PassportTicket createWWWInCookie(int userId, HttpServletResponse resp, Integer maxAge){
		maxAge = (maxAge!=null ? maxAge : PassportTicket.DEFAULT_TICKET_LIFETIME);
		PassportTicket ticket = createTicket(userId, maxAge);
		CookieUtil.saveCookie(resp, "www_passport", toCookieValue(ticket), ".hunbola.com", "/", maxAge, false);
		return ticket;
	}
	/**
	 * cookie中创建以orderid对应的passport
	 * @param orderId
	 * @param resp
	 * @param maxAge
	 * @return
	 */
	public PassportTicket createUserInCookie(int orderId, HttpServletResponse resp, Integer maxAge){
		maxAge = (maxAge!=null ? maxAge : PassportTicket.DEFAULT_TICKET_LIFETIME);
		PassportTicket ticket = createTicket(orderId, maxAge);
		CookieUtil.saveCookie(resp, "user_passport", toCookieValue(ticket), ".hunbola.com", "/", maxAge, false);
		return ticket;
	}
	/**
	 * 从cookie中校验、解析出passport
	 * @param req
	 * @param resp
	 * @return
	 */
	public PassportTicket readInCookie(HttpServletRequest req, HttpServletResponse resp) {
		PassportTicket result = null;
		String cookieValue = CookieUtil.getCookieValue(req, "passport");
		if(cookieValue!=null){
			PassportTicket ticket = valueOfCookieValue(cookieValue);
			if(verifyTicket(ticket)){
				result = ticket;
			}
			if(result==null && resp!=null){
				CookieUtil.deleteCookie(resp, "passport", ".hunbola.com", "/");
			}
		}
		return result;
	}
	
	/**
	 * 从token中校验、解析出passport
	 * @param token
	 * @return
	 */
	public PassportTicket readInToken(String token){
		PassportTicket result = null;
		if(token!=null){
			PassportTicket ticket = valueOfCookieValue(token);
			if(verifyTicket(ticket)){
				result = ticket;
			}
		}
		return result;
	}
	
	/**
	 * 从cookie中校验、解析出passport
	 * @param req
	 * @param resp
	 * @return
	 */
	public PassportTicket readWWWInCookie(HttpServletRequest req, HttpServletResponse resp) {
		PassportTicket result = null;
		String cookieValue = CookieUtil.getCookieValue(req, "www_passport");
		if(cookieValue!=null){
			PassportTicket ticket = valueOfCookieValue(cookieValue);
			if(verifyTicket(ticket)){
				result = ticket;
			}
			if(result==null && resp!=null){
				CookieUtil.deleteCookie(resp, "www_passport", ".hunbola.com", "/");
			}
		}
		return result;
	}
	public PassportTicket readWWWInCookie(String token){
		PassportTicket result = null;
		if(token!=null){
			PassportTicket ticket = valueOfCookieValue(token);
			if(verifyTicket(ticket)){
				result = ticket;
			}
		}
		return result;
	}
	
	public PassportTicket readUserInCookie(HttpServletRequest req, HttpServletResponse resp) {
		PassportTicket result = null;
		String cookieValue = CookieUtil.getCookieValue(req, "user_passport");
		if(cookieValue!=null){
			PassportTicket ticket = valueOfCookieValue(cookieValue);
			if(verifyTicket(ticket)){
				result = ticket;
			}
			if(result==null && resp!=null){
				CookieUtil.deleteCookie(resp, "user_passport", ".hunbola.com", "/");
			}
		}
		return result;
	}
	public PassportTicket readOrderInCookie(String token){
		PassportTicket result = null;
		if(token!=null){
			PassportTicket ticket = valueOfCookieValue(token);
			if(verifyTicket(ticket)){
				result = ticket;
			}
		}
		return result;
	}
	private PassportTicket createTicket(int userId, long maxAge) {
		PassportTicket ticket = new PassportTicket();
		ticket.setUserId(userId);
		long createTime = System.currentTimeMillis();
		ticket.setCreateTime(createTime);
		ticket.setLifeTime(maxAge);
		String salt = dailySaltHome.getOrCreateSalt(createTime);
		signature.sign(ticket, salt);
//		System.out.println("create salt:"+salt);
//		System.out.println("create uid:"+ticket.getUserId());
//		System.out.println("create createTime:"+ticket.getCreateTime());
//		System.out.println("create life:"+ticket.getLifeTime());
//		System.out.println("create sign:"+ticket.getSignatureText());
//		System.out.println("create content:"+ticket.getContentText());
		return ticket;
	}
	
	private boolean verifyTicket(PassportTicket ticket){
		
		String salt = dailySaltHome.getSalt(ticket.getCreateTime());
		return (salt!=null && signature.verify(ticket, salt)) && (ticket.getCreateTime() + (long)ticket.getLifeTime()*1000 > System.currentTimeMillis());
	}

	public PassportTicket valueOfCookieValue(String cookieValue){
		String[] elems = cookieValue.split("-");
		PassportTicket ticket = new PassportTicket();
		ticket.setUserId(Integer.parseInt(elems[0]));
		ticket.setCreateTime(Long.parseLong(elems[1]));
		ticket.setLifeTime(Integer.parseInt(elems[2]));
		ticket.setSignatureText(elems[3]);
		return ticket;
	}
	
	public String toCookieValue(PassportTicket ticket){
		return "" + ticket.getUserId() + "-" + ticket.getCreateTime() + "-" + ticket.getLifeTime() + "-" + ticket.getSignatureText();
	}
	
	public static void main(String[] args){
		Passport passport = new Passport();
		System.out.println(passport.readInToken("8-1374389601851-2147483647-0cc918d30b20461e28c24bbf645ac4c9").getUserId());

	}
	
	
	
}
