/*
 * MIT License
 * 
 * Copyright (c) 2017 Eugene Dementiev
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.som_service.extra.utils.data;

/**
 *
 * @author Eugene Dementiev
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
