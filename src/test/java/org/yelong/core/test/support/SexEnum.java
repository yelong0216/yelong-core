/**
 * 
 */
package org.yelong.core.test.support;

import org.yelong.core.support.Dictionary.DictionaryAttribute;

/**
 *
 */
enum SexEnum implements DictionaryAttribute<String, String> {

	MAN("01", "男", 1), WOMAN("02", "女", 2);

	public String code;

	private String text;

	private Integer order;

	private SexEnum(String code, String text, Integer order) {
		this.code = code;
		this.text = text;
		this.order = order;
	}

	@Override
	public String getValue() {
		return code;
	}

	@Override
	public String getContent() {
		return text;
	}

	@Override
	public int getOrder() {
		return order;
	}

}
