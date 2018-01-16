/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.som_service.extra.utils.data;

import java.util.ArrayList;
import java.util.HashMap;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Asuka
 */
public class NPT implements Comparable<NPT>{
	public final String[] labels_self, labels_parent;
	public final Cell[][] table;
	public final Cell[] cells;
	public final String node;
	
	public final HashMap<String, HashMap<String, Cell>> map = new HashMap();
	
	public final ArrayList<Column> columns = new ArrayList<>();
	
	public NPT(String node, String[] labels_self, String[] labels_parent){
		this.labels_self = labels_self;
		this.labels_parent = labels_parent;
		this.node = node;
		
		this.table = new Cell[this.labels_self.length][this.labels_parent.length];
		this.cells = new Cell[this.labels_self.length * this.labels_parent.length];
		
		int i = 0;
		for(int c = 0; c < this.table[0].length; c++){
			
			Column column = new Column(this.labels_self.length,c);
			this.columns.add(column);

			int r = 0;
			for(Cell cell: column.get_cells()){
				if (!map.containsKey(labels_self[r])){
					map.put(labels_self[r], new HashMap());
				}
				map.get(labels_self[r++]).put(labels_parent[c], cell);
				
				table[cell.row][cell.column] = cell;
				cells[i++] = cell;
				
			}
		}
		
	}
	
	public Column get_column(String label){
		for(int i = 0; i < labels_parent.length; i++){
			if (labels_parent[i].equals(label)){
				return columns.get(i);
			}
		}
		return null;
	}
	
	public Cell get_cell(String NPT_label, String parent_label){
		for(int r = 0; r < this.labels_self.length; r++){
			if(this.labels_self[r].equalsIgnoreCase(NPT_label)){
				for(int c = 0; c < this.labels_parent.length; c++){
					if(this.labels_parent[c].equalsIgnoreCase(parent_label)){
						return this.table[r][c];
					}
				}
			}
		}
		
		return null;
	}
	
	public void normalise(){
		for(Column column: this.columns){
			column.normalise();
		}
	}
	
	public void replace_zeros(double replacement){
		for(Column column: columns){
			column.replace_zeros(replacement);
		}
	}
	
	public String toString(){
		String npt = this.node+"\t"+StringUtils.join(this.labels_parent,"\t")+"\n";
		for(int r = 0; r < this.labels_self.length; r++){
			String row = this.labels_self[r]+"\t";
			
			for(int c = 0; c < this.labels_parent.length; c++){
				row += this.table[r][c] + "\t";
			}
			
			row = row.trim()+"\n";
			
			npt += row;
		}
		/*
		String string = ArrayUtils.toString(table);
		string = string.replaceAll("\\},\\{", "\n").replaceAll(",", "\t").replaceAll("[\\p{Ps}\\p{Pe}]", "");
		return string;
		*/
		return npt;
	}
	
	/**
	 * Destructively modifies this NPT (npt1) to absorb another NPT (npt2) of the same dimensions, by adding npt2[y,x] to npt1[y,x]
	 * @param absorbent NPT of the same dimensions
	 * @return this (absorbing) NPT
	 */
	public NPT absorb(NPT absorbent){
		for(Cell cell: absorbent.cells){
			table[cell.row][cell.column].add(cell.get_value());
		}
		return this;
	}

	@Override
	public int compareTo(NPT npt) {
		
		if(!(npt instanceof NPT)){
			return 0;
		}
		
		return this.node.compareTo(npt.node);
	}
	
	public void round(int precision){
		for(Column column: columns){
			column.round(precision);
		}
	}
	
	public static String combine_out(NPT a, NPT b){
		String out = "";
		
		if (a.labels_parent.length != b.labels_parent.length || a.labels_self.length != b.labels_self.length){
			// SizeMismatch
		}
		
		out += "\t"+a.node+StringUtils.repeat("\t", a.labels_parent.length)+ "\t" + b.node +"\n";
		out += "OUT"+"\t\t"+StringUtils.join(a.labels_parent,"\t")+"\t"+StringUtils.join(a.labels_parent,"\t")+"\n";
		out += "\n";
		
		for(int r = 0; r < a.labels_self.length; r++){
			String row = a.labels_self[r]+"\t\t";
			String row_right = "";
			
			for(int c = 0; c < a.labels_parent.length; c++){
				row += a.table[r][c] + "\t";
				row_right += b.table[r][c] + "\t";
			}
			
			row = row.trim()+"\t"+row_right.trim()+"\n";
			
			out += row;
		}
	
		return out;
	}
}
