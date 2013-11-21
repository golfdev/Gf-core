package com.jinfang.golf.user.home;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jinfang.golf.user.dao.VerifyCodeDAO;
import com.jinfang.golf.user.model.VerifyCode;
import com.jinfang.golf.utils.UnitOfTime;
/**
 * VerifyCodeHome操作数据库的home对象
 * @author ZK
 *
 */
@Component
public class VerifyCodeHome {
	
	@Autowired
	private VerifyCodeDAO verifyCodeDao;
	
	private static VerifyCodeHome instance =new VerifyCodeHome();
	
	/** 
	 * 获取 VerifyCodeHome实例对象
	 * @return 返回 VerifyCodeHome实例对象
	 */
	public static VerifyCodeHome getInstance(){
		return instance;
	}
	
	/**
	 * 保存VerifyCode对象
	 * @param verifyCode VerifyCode对象参数
	 * @return 返回保存成功后获得的int数
	 */
	public int insert(VerifyCode verifyCode){
		return verifyCodeDao.insert(verifyCode);
	}
	
	/**
	 * 修改 VerifyCode对象
	 * @param verifyCode VerifyCode对象参数
	 */
	public void update(VerifyCode verifyCode){
		verifyCodeDao.update(verifyCode);
	}
	
	/**
	 * 删除VerifyCode对象
	 * @param number 电话号码
	 */
	public void delete(String number){
		verifyCodeDao.delete(number);
	}
	
	/**
	 * 查询VerifyCode对象
	 * @param number 电话号码
	 * @return 返回VerifyCode对象
	 */
	public VerifyCode get(String number){
		List<VerifyCode> list = verifyCodeDao.get(number);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	public String getCode(String phoneNumber){
		int num=(int)(Math.random()*10000);
		String snum=String.valueOf(num);
		while(snum.length()<4){
			snum=snum+(int)(Math.random()*10);
		}
		this.saveCode(phoneNumber, snum);
		
		return snum;
	}
	
	public void saveCode(String number,String code){
		long currentTime=System.currentTimeMillis();
		long lifeTime=UnitOfTime.MILLS_1MINUTE*5*1000;
		long expriedTimes=currentTime+lifeTime;
		Date expriedTime = new Date(expriedTimes);
		VerifyCode verifycode = this.get(number);
		if(verifycode!=null){
			verifycode.setCode(code);
			verifycode.setExpriedTime(expriedTime);
			verifyCodeDao.update(verifycode);
		}else{
			VerifyCode verifyCode=new VerifyCode();
			verifyCode.setNumber(number);
			verifyCode.setCode(code);
			verifyCode.setExpriedTime(expriedTime);
			verifyCodeDao.insert(verifyCode);
		}
	}
}
