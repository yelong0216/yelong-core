/**
 * 
 */
package org.yelong.core.model;

import java.util.Date;

/**
 * 
 * model中属性在保存时表示为null的属性值
 * 在根据可选的属性进行save和modify时，属性设置为此类中对应的属性值，会被设置为null，而不是忽略
 * model中的对象必须为包装类型，而不是基本类型
 * 注意：model的属性不要是基础数据类型
 * @author 彭飞
 * @date 2019年10月24日上午9:59:36
 * @version 1.2
 */
public final class ModelNullProperty {
	
	public static final Byte BYTE_NULL = (Byte) ModelNullPropertyEnum.BYTE_NULL.getValue();
	
	public static final Short SHORT_NULL = (Short) ModelNullPropertyEnum.SHORT_NULL.getValue();
	
	public static final Integer INTEGER_NULL = (Integer) ModelNullPropertyEnum.INTEGER_NULL.getValue();
	
	public static final Long LONG_NULL = (Long) ModelNullPropertyEnum.LONG_NULL.getValue();
	
	public static final Float FLOAT_NULL = (Float) ModelNullPropertyEnum.FLOAT_NULL.getValue();
	
	public static final Double DOUBLE_NULL = (Double) ModelNullPropertyEnum.DOUBLE_NULL.getValue();
	
	public static final Character CHAR_NULL = (Character) ModelNullPropertyEnum.CHAR_NULL.getValue();
	
	public static final Boolean BOOLEAN_NULL = (Boolean) ModelNullPropertyEnum.BOOLEAN_NULL.getValue();
	
	public static final String STRING_NULL = (String) ModelNullPropertyEnum.STRING_NULL.getValue();
	
	public static final Date DATE_NULL = (Date) ModelNullPropertyEnum.DATE_NULL.getValue();
	
	
	/**
	 * 验证model属性值是否是伪装的null
	 * @author 彭飞
	 * @date 2019年10月24日上午11:56:48
	 * @version 1.2
	 * @param value
	 * @return <tt>true</tt>是伪装Null值
	 */
	public static boolean isPretendNull(Object value) {
		Class<?> type = value.getClass();
		ModelNullPropertyEnum[] values = ModelNullPropertyEnum.values();
		for (ModelNullPropertyEnum modelNullProperty : values) {
			if( type == modelNullProperty.getType() ) {
				if( value == modelNullProperty.getValue() ) {
					return true;
				} else {
					return false;
				}
			}
		}
		//throw new IllegalStateException("未找到："+type+"类型的model伪装空值");
		return false;
	}
	
	private enum ModelNullPropertyEnum{
		
		BYTE_NULL(Byte.class,new Byte((byte)0)),
		SHORT_NULL(Short.class,new Short((short)0)),
		INTEGER_NULL(Integer.class,new Integer(0)),
		LONG_NULL(Long.class,new Long(0)),
		FLOAT_NULL(Float.class,new Float(0F)),
		DOUBLE_NULL(Double.class,new Double(0)),
		CHAR_NULL(Character.class,new Character(' ')),
		BOOLEAN_NULL(Boolean.class,new Boolean(false)),
		STRING_NULL(String.class,new String("")),
		DATE_NULL(Date.class,new Date(0));
		
		private final Class<?> type;
		
		private final Object value;
		
		private <C> ModelNullPropertyEnum(Class<C> type,C value) {
			this.type = type;
			this.value = value;
		}

		public Class<?> getType() {
			return type;
		}

		public Object getValue() {
			return value;
		}
		
	}
	
	
}
