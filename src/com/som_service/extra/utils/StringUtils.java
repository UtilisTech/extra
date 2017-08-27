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
package com.som_service.extra.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

/**
 *
 * @author Eugene Dementiev
 */
public class StringUtils {
	public static int string_overlaps(String needle, String haystack, int overlap_length){
		int overlaps = 0;
		
		if (needle.length() < overlap_length && needle.equalsIgnoreCase(haystack)){
			return 1;
		}
		
		if (needle.equalsIgnoreCase(haystack)){
			return string_overlaps_max(needle.length(), haystack.length(), overlap_length);
		}
		
		if (needle.length() < overlap_length || haystack.length() < overlap_length){
			overlap_length = Math.min(needle.length(), haystack.length());
		}
		
		for (int i = 0; i < needle.length() - overlap_length + 1; i++){
			if (haystack.contains(needle.substring(i, i+overlap_length))){
				overlaps++;
			}
		}
		
		return overlaps;
	}
	
	public static int string_overlaps_max(int needle_length, int haystack_length, int overlap_length){
		if (haystack_length >= needle_length){
			return needle_length + 1 - overlap_length;
		}
		return haystack_length + 1 - overlap_length;
	}
	
	public static String[] nGrams(String string, int n){
		String[] ngrams = new String[string.length()+1-n];
		for (int i = 0; i < ngrams.length; i++){
			ngrams[i] = string.substring(i, i+n);
		}
		return ngrams;
	}
	
	public static <K extends Comparable<? super K>, V> String toStringSorted(final HashMap<K, V> map, boolean ascending, String kv_separator, String pair_separator){
		
		Comparator<K> comparator = new Comparator<K>() {

			private int ascending;
			
			@Override
			public int compare(K o1, K o2) {
				return o1.compareTo(o2) * ascending;
			}
			
			public Comparator<K> setOrder(boolean asc){
				if (asc){
					this.ascending = 1;
				}
				else {
					this.ascending = -1;
				}
				return this;
			}
		}.setOrder(ascending);
		
		ArrayList<K> keys = new ArrayList<>(map.keySet());
		Collections.sort(keys, comparator);

		String string = "";
		for(K key: keys){
			string += key + kv_separator + map.get(key) + pair_separator;
		}
		
		string = string.replaceFirst(pair_separator+"$", "");
				
		return string;
	}
	
	/**
	 * Checks whether the given character is a white space or an invisible character.<br/>
	 * Specifically, if its corresponding int value is less then 33 or any from this list:<br/>
	 * 127, 128, 129, 141, 142, 143, 144, 149, 157, 158, 160,<br>
	 * it will return true.
	 * @param c character to check for white space
	 * @return true if c is a white space
	 */
	public static boolean isWhiteSpace(char c){
		if (c <= 32){
			return true;
		}
		
		int character = (int)c;
		
		if (Arrays.binarySearch(SPACES, character) >= 0){
			return true;
		}
		
		return false;
	}
	private static int[] SPACES = new int[]{
		127, 128, 129, 141, 142, 143, 144, 149, 157, 158, 160
	};
}
