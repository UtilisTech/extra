/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.som_service.extra.utils.data;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Asuka
 * @param <T>
 */
public  class SortedList<T extends Comparable<? super T>> {
	
	public final int max_length;
	
	public final ArrayList<T> list = new ArrayList();
	
	public SortedList(int max_length){
		this.max_length = max_length;
	}
	
	public SortedList(){
		this(-1);
	}
	
	public SortedList add(T element){
		list.add(element);
		Collections.sort(list);
		if (max_length > 1){
			while(list.size() > max_length){
				list.remove(list.size()-1);
			}
		}
		return this;
	}
	
	public String toString(){
		return list.toString();
	}
}
