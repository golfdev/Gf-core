package com.jinfang.golf.passport.model;

/**
 * 可签名接口。
 * 实现此接口的类，可以使用<code>Signature</code>对象对其签名。
 *
 */
public interface Signable {

    /**
     * 返回对象需要被签名的内容，类型为<code>String</code>。
     *
     * @return 对象需要被签名的内容
     */
	String getContentText();

    /**
     * 返回对象的签名，类型为<code>String</code>。
     *
     * @return 对象的签名
     */
	String getSignatureText();

    /**
     * 签名方法：设置签名字符串，对内容进行签名，类型为<code>String</code>。
     *
     * @param signatureText 设置签名字符串，对内容进行签名。
     */
	void setSignatureText(String signatureText);
}
