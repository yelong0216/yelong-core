/**
 * 
 */
package org.yelong.core.model;

import java.io.Serializable;

/**
 * 模型支持<br/>
 * 
 * 解决 Model 必须继承 {@link Model} 的问题。<br/>
 * 
 * 任何该类的实现类具有源{@link Model}的功能<br/>
 * 
 * @see Model
 */
public interface Modelable extends Serializable {

}
