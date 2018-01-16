/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.som_service.extra.utils.data;

/**
 *
 * @author Asuka
 */
public class Cell implements Comparable<Cell>{
	
	public final int row, column;
	public final Column Column;
	
	private double value = 0;
	
	public Cell(Column Column, int row){
		this.row = row;
		this.column = Column.column;
		this.Column = Column;
	}
	
	public double set_value(double value){
		this.value = value;
		return value;
	}
	
	public double add(double delta){
		this.value += delta;
		return this.value;
	}
	
	public double get_value(){
		return this.value;
	}
	
	public String toString(){
		return this.value+"";
	}

	@Override
	public int compareTo(Cell cell) {
		if (this.value > cell.value){
			return 1;
		}
		
		if (this.value < cell.value){
			return -1;
		}
		
		return 0;
	}
	
	private Cell(Column Column, int row, double value){
		this(Column, row);
		this.value = value;
	}
	
	public Cell clone(){
		return new Cell(Column, row, value);
	}
}
