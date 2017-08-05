/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.som_service.extra.utils.data;

import com.som_service.extra.utils.data.VirtualAverageMap;
import java.util.ArrayList;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Asuka
 */
public class Table {
	public final String[] headers;
	public final int width;
	
	public final VirtualAverageMap averages = new VirtualAverageMap();
	
	public ArrayList<String[]> rows = new ArrayList();
	
	public Table(String[] headers){
		this.headers = headers;
		this.width = headers.length;
	}
	
	public Table add_row(Object[] row){
		String[] strings = new String[row.length];
		for(int i = 0; i < headers.length; i++){
			Object o = row[i];
			if (o instanceof Double){
				averages.get(headers[i]).add_number(((Double)o));
			}
			strings[i] = o+"";
		}
		
		rows.add(strings);
		
		return this;
	}
	
	public String toString(){
		return toString("\t", "\n");
	}
	
	public String toString(String separator_column, String separator_row){
		String string = "";
		if (STRING_PRINT_HEADERS){
			string = StringUtils.join(headers,separator_column);
		}
		
		for(String[] row: rows){
			string += separator_row + StringUtils.join(row,separator_column);
		}
		
		if (STRING_PRINT_AVERAGES){
			string += "\n";
			for(int i = 0; i < headers.length; i++){
				String header = headers[i];
				String cell = (averages.map.containsKey(header))?averages.get(headers[i]).toString():"";
				string += (i>0?separator_column:"") + cell;
			}
		}
		
		if (!STRING_PRINT_HEADERS){
			return string.replaceFirst(separator_row, "");
		}
		
		return string;
	}
	
	public static boolean STRING_PRINT_HEADERS = true;
	public static boolean STRING_PRINT_AVERAGES = false;
	
}
