/**
 * 
 */
package org.yelong.core.model.map;

import org.yelong.core.model.resolve.AbstractFieldAndColumn;

/**
 * @author PengFei
 * @since 1.1.0
 */
public class DefaultMapModelFieldAndColumn extends AbstractFieldAndColumn implements MapModelFieldAndColumn{

	private final MapModelAndTable mapModelAndTable;
	
	private final String column;
	
	public DefaultMapModelFieldAndColumn(MapModelAndTable mapModelAndTable, String column) {
		this.mapModelAndTable = mapModelAndTable;
		this.column = column;
	}

	@Override
	public String getColumn() {
		return this.column;
	}

	@Override
	public MapModelAndTable getMapModelAndTable() {
		return this.mapModelAndTable;
	}

}
