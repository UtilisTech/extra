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

import java.lang.reflect.InvocationTargetException;
import java.util.TreeMap;

/**
 *
 * @author Eugene Dementiev
 */
public class VirtualMap<K,V extends Object> {
	
	public final V default_value;
	public final TreeMap<K, V> map;
	
	public VirtualMap(V default_value){
		this.default_value = default_value;
		map = new TreeMap<>();
		
	}
	
	public VirtualMap(V default_value, TreeMap<K, V> map){
		this.default_value = default_value;
		this.map = map;
	}
	
	public V get(K key){
		if (!map.containsKey(key)){
			try {
				map.put(key, (V)default_value.getClass().getMethod("clone").invoke(default_value));
			}
			catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e){
				throw new RuntimeException(e);
			}
		}
		return map.get(key);
	}

}
