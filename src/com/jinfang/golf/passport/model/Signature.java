package com.jinfang.golf.passport.model;

import com.jinfang.golf.utils.Md5;


/**
 * 签名逻辑的高层抽象类，可以对<code>Signable</code>对象进行签名。
 * 实现了签名和验证签名的方法，将<code>Signable</code>对需要签名的内容和签名密钥混合进行签名，具体签名算法通过抽象方法委托给子类实现。
 *
 */
public abstract class Signature {

    /**
     * 签名方法：传入需要签名的对象和签名使用的密钥，对该对象进行签名。
     *
     * @param signable 需要签名的对象。
     * @param salt 签名使用的密钥。
     */
	public void sign(Signable signable, String salt) {
		signable.setSignatureText(hash(signable.getContentText() + salt));
	}

    /**
     * 验证签名的方法：传入需要验证签名的对象和签名使用的密钥，对该对象的签名进行验证。
     *
     * @param signable 需要验证签名的对象。
     * @param salt 签名使用的密钥。
     * @return 该需要验证签名对象的签名是否为真。
     */
	public boolean verify(Signable signable, String salt) {
		String signature = hash(signable.getContentText() + salt);
//		System.out.println("md5 content"+signature);
		String targetSignature = signable.getSignatureText();
		return signature.trim().equals(targetSignature.trim());
	}

    /**
     * 签名算法：由子类实现，如md5、sha1等。
     *
     * @param text 需要签名的字符串。
     * @return 字段传内容的签名。
     */
	abstract String hash(String text);
	
	public static void main(String[] args){
		System.out.println(Md5.md5s("3613734684010252147483647"+"d4e498e8e58844c4937200840807719d"));
		
	}
}
