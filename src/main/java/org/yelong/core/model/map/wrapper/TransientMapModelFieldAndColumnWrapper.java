/**
 * 
 */
package org.yelong.core.model.map.wrapper;

import org.yelong.core.model.map.MapModelFieldAndColumn;

/**
 * 临时Map模型字段列包装器
 * 
 * @since 2.0
 */
public final class TransientMapModelFieldAndColumnWrapper extends MapModelFieldAndColumnWrapper {

	public TransientMapModelFieldAndColumnWrapper(MapModelFieldAndColumn fieldAndColumn) {
		super(fieldAndColumn);
	}

	@Override
	public final boolean isTransient() {
		return true;
	}

}
