/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.som_service.extra.utils.data.table;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Asuka
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
