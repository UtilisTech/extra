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
package com.som_service.extra.utils.data.table;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Eugene Dementiev
 */
public class Table<T> {
	public final List<List<Cell<T>>> rows = new ArrayList<>();
	
	public boolean set(Cell cell){
		
		for(int r = rows.size(); r < cell.row + 1; ++r){
			rows.add(r, new ArrayList<>());
		}
		
		for(int r = 0; r < rows.size(); r++){
			List row = rows.get(r);
			for(int c = row.size(); c < cell.column + 1; ++c){
				row.add(new Cell(c, r));
			}
		}
		
		List<Cell<T>> row = rows.get(cell.row);
		row.set(cell.column, cell);
		
		return true;
	}
	
	public String toString(){
		return toString("\n", "\t", null);
	}
	
	public String toString(String row_separator, String column_separator, CellStringifier cs){
		StringBuffer string = new StringBuffer();
		for(List<Cell<T>> row: rows){
			for (Cell<T> cell: row){
				String cell_string_value = "";
				if (cs == null){
					cell_string_value = cell.getContent() + "";
				}
				else {
					cell_string_value = cs.cellToString(cell);
				}
				string.append(cell_string_value).append(column_separator);
			}
			string.append(row_separator);
		}
		return string.toString();
	}
}
