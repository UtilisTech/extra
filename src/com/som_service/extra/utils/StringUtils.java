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
 * StringUtils provides utility functions for processing strings and characters
 * @author Eugene Dementiev
 */
public class StringUtils {
	
	/**
	 * Calculates the number of common ngrams in the haystack.<br/>
	 * Needle may be longer than the haystack, but in that case the number of common ngrams will be limited by the haystack size.<br/>
	 * The ngrams are not unique.
	 * @param needle the needle string
	 * @param haystack the haystack string
	 * @param ngram_length length of ngrams
	 * @return the number of common ngrams in the haystack
	 */
	public static int commonNGrams(String needle, String haystack, int ngram_length){
		int overlaps = 0;
		
		if (needle.length() < ngram_length && needle.equalsIgnoreCase(haystack)){
			return 1;
		}
		
		if (needle.equalsIgnoreCase(haystack)){
			return maxCommonNGrams(needle.length(), haystack.length(), ngram_length);
		}
		
		if (needle.length() < ngram_length || haystack.length() < ngram_length){
			ngram_length = Math.min(needle.length(), haystack.length());
		}
		
		for (int i = 0; i < needle.length() - ngram_length + 1; i++){
			if (haystack.contains(needle.substring(i, i+ngram_length))){
				overlaps++;
			}
		}
		
		return overlaps;
	}
	
	/**
	 * Calculates maximum possible number of common ngrams given length of haystack and needle.<br/>
	 * Needle may be longer than the haystack, but in that case the max number of common ngrams will be limited by the haystack size.<br/>
	 * The ngrams are not unique.
	 * @param needle_length length of the needle string
	 * @param haystack_length length of the haystack string
	 * @param ngram_length length of the ngram
	 * @return maximum number of common ngrams between given strings
	 */
	public static int maxCommonNGrams(int needle_length, int haystack_length, int ngram_length){
		if (haystack_length >= needle_length){
			return needle_length + 1 - ngram_length;
		}
		return haystack_length + 1 - ngram_length;
	}
	
	/**
	 * Splits the given string into ngrams of given length
	 * @param string string to split
	 * @param n ngram length
	 * @return  array of ngrams
	 */
	public static String[] nGrams(String string, int n){
		String[] ngrams = new String[string.length()+1-n];
		for (int i = 0; i < ngrams.length; i++){
			ngrams[i] = string.substring(i, i+n);
		}
		return ngrams;
	}
	
	/**
	 * Stringifies a map, sorting it by keys in given order.<br/>
	 * Key and value are joined by kv_separator.<br/>
	 * Pairs are joined by pair_separator
	 * @param <K> Key type
	 * @param <V> Value type
	 * @param map map to stringify
	 * @param ascending whether ascending order should be applied to keys
	 * @param kv_separator separator to join key and value
	 * @param pair_separator separator to join pairs (rows)
	 * @return string representation of the map given the arguments
	 */
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
	 * 127, 128, 129, 141, 142, 143, 144, 149, 157, 158, 160,<br/>
	 * it will return true.<br/>
	 * Will also return true if matches [\\p{Z}\\s\\p{javaSpaceChar}\\p{Zs}]
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
		
		String s = c+"";
		if (s.matches("[\\p{Z}\\s\\p{javaSpaceChar}\\p{Zs}]")){
			return true;
		}
		
		return false;
	}
	private static int[] SPACES = new int[]{
		127, 128, 129, 141, 142, 143, 144, 149, 157, 158, 160
	};
}
