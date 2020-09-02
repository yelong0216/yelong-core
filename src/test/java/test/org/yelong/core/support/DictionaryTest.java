/**
 * 
 */
package test.org.yelong.core.support;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.yelong.core.order.OrderDirection;
import org.yelong.core.support.Dictionary;
import org.yelong.core.support.Dictionary.DictionaryAttribute;
import org.yelong.test.ObjectTests;

/**
 *
 */
public class DictionaryTest {

	private static Dictionary<String, String, String> dictionary = Dictionary.build("sex", SexEnum.values());

	@Test
	public void addAttribute() {
		dictionary.addAttribute(null, "123");
		String content = dictionary.getContent(null);
		System.out.println(content);
		System.out.println(dictionary.getContent("01"));
	}

	@Test
	public void addAttribute_2() {
		dictionary.addAttribute("100", "100", -100);
		List<DictionaryAttribute<String, String>> dictionaryAttributes = dictionary
				.getDictionaryAttributes(OrderDirection.DESC);
		System.out.println(dictionaryAttributes);
	}

	@Test
	public void getDictionaryAttribute() {
		DictionaryAttribute<String, String> dictionaryAttribute = dictionary.getDictionaryAttribute("01");
		ObjectTests.printAllField(dictionaryAttribute);
	}

	@Test
	public void getDictionaryAttributeByContent() {
		List<DictionaryAttribute<String, String>> dictionaryAttribute = dictionary.getDictionaryAttributeByContent("男");
		System.out.println(dictionaryAttribute);
	}

	@Test
	public void getContent() {
		String content = dictionary.getContent("01");
		String content1 = dictionary.getContent("03","哈哈");
		System.out.println(content);
		System.out.println(content1);
	}

	@Test
	public void getType() {
		System.out.println(dictionary.getType());
	}
	
}
