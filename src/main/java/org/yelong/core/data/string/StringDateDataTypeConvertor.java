package org.yelong.core.data.string;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.yelong.commons.lang.ArrayUtilsE;
import org.yelong.commons.lang.Strings;
import org.yelong.commons.util.Dates;
import org.yelong.core.data.DataTypeConvertException;

/**
 * 字符串日期数据类型转换器
 * 
 * @since 2.1.6
 */
public class StringDateDataTypeConvertor implements StringDataTypeConvertor<Date> {

	private final String[] parsePatterns;

	private final String pattern;

	public StringDateDataTypeConvertor() {
		this(ArrayUtils.toArray(Dates.YYYY_MM_DD_BAR, Dates.YYYY_MM_DD_HH_MM_SS), Dates.YYYY_MM_DD_HH_MM_SS);
	}

	/**
	 * @param parsePatterns 解析的日期格式
	 * @param pattern       格式话的日期格式
	 */
	public StringDateDataTypeConvertor(String[] parsePatterns, String pattern) {
		this.parsePatterns = ArrayUtilsE.requireNonEmpty(parsePatterns);
		this.pattern = Strings.requireNonBlank(pattern);
	}

	@Override
	public Date convert(String sourceObject) throws DataTypeConvertException {
		if (null == sourceObject) {
			return null;
		}
		try {
			return DateUtils.parseDate(sourceObject, parsePatterns);
		} catch (ParseException e) {
			throw new DataTypeConvertException(e);
		}
	}

	@Override
	public String reverseConvert(Date requiredObject) throws DataTypeConvertException {
		if (null == requiredObject) {
			return null;
		}
		return DateFormatUtils.format(requiredObject, pattern);
	}

	public static void main(String[] args) throws DataTypeConvertException {
		StringDateDataTypeConvertor stringDateDataTypeConvertor = new StringDateDataTypeConvertor();
		Date date = stringDateDataTypeConvertor.convert("2020-02-16");
		System.out.println(stringDateDataTypeConvertor.reverseConvert(date));
	}

}
