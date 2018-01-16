/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.som_service.extra.utils.data.table;

/**
 *
 * @author Asuka
 */
public class Cell<T> {
	private T content = null;
	public final int column, row;
	
	public Cell(int column, int row){
		this.column = column;
		this.row = row;
	}
	
	public Cell(int column, int row, T content){
		this(column, row);
		this.setContent(content);
	}

	public T getContent() {
		return content;
	}

	final public void setContent(T content) {
		this.content = content;
	}
	
	public String toString(){
		return toString(null);
	}
	
	public String toString(CellStringifier cs){
		if (cs == null){
			return this.content+"";
		}
		return cs.cellToString(this);
	}
}
