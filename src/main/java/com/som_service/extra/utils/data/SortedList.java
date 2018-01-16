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

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Eugene Dementiev
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
