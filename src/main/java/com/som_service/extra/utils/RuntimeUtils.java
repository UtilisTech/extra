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

import java.util.HashMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;

/**
 * 
 * @author Eugene Dementiev
 */
public class RuntimeUtils {

	/**
	 * <pre>
	 * Parses an arguments array into a HashMap of String keys to String values
	 * General rules apply:
	 * • single character arguments must be latin alphanumeric
	 * • single character arguments must be preceded by a single dash
	 * • single character argument can be concatenated and the whole group can then be preceded by a single dash
	 * • double dash arguments are checked are allowed to have latin alphanumerics, dash and underscore
	 * • values for doble dash argument keys can be anything, as long as they are parsed as a single entry in the arguments array
	 * • arguments that do not begin with a dash and are not preceded by a double dash argument will be treated as if they had an empty string key
	 * 
	 * Note that this method is unsuitable for multiple non-dashed arguments, e.g. [-v, input.file, output.file]
	 * Note that repeating arguments will overwrite preceding arguments with the same key
	 * For example:
 	 * Arguments like '-qwe' are expanded into -q, -w and -e, and added as keys with an empty String value
	 * Arguments starting with double hyphen like '--verbose' are allowed; if followed by a string that does not begin with a hyphen, this string will be set as the argument's value, e.g. '--file out.log' will be parsed as argument '--file' with value 'out.log'
	 * </pre>
	 * @param args arguments array passed to the application from command line arguments parser
	 * @return a list valued map of the arguments and values
	 * @throws IllegalArgumentException if the above rules are not satisfied
	 */
	public static HashMap<String,String> parseArguments(String[] args) throws IllegalArgumentException {
		HashMap<String,String> map = new HashMap();
		parseArgumentsInto(map, args);
		return map;
	}

	/**
	 * <pre>
	 * Parses an arguments array into a ArrayListValuedHashMap of String keys to ArrayList&lt;String&gt; values
	 * General rules apply:
	 * • single character arguments must be latin alphanumeric
	 * • single character arguments must be preceded by a single dash
	 * • single character argument can be concatenated and the whole group can then be preceded by a single dash
	 * • double dash arguments are checked are allowed to have latin alphanumerics, dash and underscore
	 * • values for doble dash argument keys can be anything, as long as they are parsed as a single entry in the arguments array
	 * • arguments that do not begin with a dash and are not preceded by a double dash argument will be treated as if they had an empty string key
	 * 
	 * Note that each argument key corresponds to a list of values for that key
	 * For example (using pseudo-JSON format):
	 * Arguments: [--fruit, apple, --fruit, banana, --color, red, --color, yellow] will be parsed as:
	 * {--fruit: [apple, banana], --color:[red, yellow]}
	 * </pre>
	 * @param args arguments array passed to the application from command line arguments parser
	 * @return a list valued map of the arguments and values
	 * @throws IllegalArgumentException if the above rules are not satisfied
	 */
	public static ArrayListValuedHashMap<String,String> parseArgumentsRepeating(String[] args) throws IllegalArgumentException {
		ArrayListValuedHashMap<String, String> map = new ArrayListValuedHashMap<>();
		parseArgumentsInto(map, args);
		return map;
	}
	
	/**
	 * <pre>
	 * Parses arguments and adds them to the given collection.
	 * This is void because ArrayListValuedHashMap and HashMap do not share any common ancestor except Object, even though they both provide the same interface for adding elements.
	 * General rules that apply to arguments:
	 * • single character arguments must be latin alphanumeric
	 * • single character arguments must be preceded by a single dash
	 * • single character argument can be concatenated and the whole group can then be preceded by a single dash
	 * • double dash arguments are checked are allowed to have latin alphanumerics, dash and underscore
	 * • values for doble dash argument keys can be anything, as long as they are parsed as a single entry in the arguments array
	 * </pre>
	 * @param collection
	 * @param args 
	 */
	private static void parseArgumentsInto(Object collection, String[] args) throws IllegalArgumentException{
		if (!(collection instanceof HashMap || collection instanceof ArrayListValuedHashMap)){
			throw new IllegalArgumentException("Invalid usage of this method, first argument must either be HashMap or ArrayListValuedHashMap");
		}
		
		if (args == null){
			throw new IllegalArgumentException("Null arguments array provided");
		}
		
		for (int i = 0; i < args.length;) {
			String arg = args[i];
			
			if (arg == null){
				throw new IllegalArgumentException("Invalid null argument");
			}
			
			String arg_next = "";
			
			if ((i+1) < args.length){
				arg_next = args[i+1];
			}
			
			if (arg.matches("^\\-{3,}.*")){
				throw new IllegalArgumentException("Invalid argument: " + arg);
			}
			else if (arg.startsWith("--")){
				if (arg.length() == 2){
					// Argument has no key
					throw new IllegalArgumentException("Invalid argument: " + arg);
				}
				
				if (!arg.matches("^\\-{2}[aA-zZ0-9_\\-]+")){
					// Only latin alphanumeric single-letter arguments are allowed
					throw new IllegalArgumentException("Invalid argument: " + arg);
				}
				
				if (arg_next != null && !arg_next.startsWith("-")){
					// Next token is not null and is not another argument key, so this is a key-value pair
					if (collection instanceof HashMap){
						((HashMap)collection).put(arg, arg_next);
					}
					else {
						((ArrayListValuedHashMap)collection).put(arg, arg_next);
					}
					i += 2;
				}
				else {
					// Next token is not a value, so add this key with an empty string value
					if (collection instanceof HashMap){
						((HashMap)collection).put(arg, "");
					}
					else {
						((ArrayListValuedHashMap)collection).put(arg, "");
					}
					i++;
				}
			}
			else if (arg.startsWith("-")){
				// Single character argument key
				
				if (arg.length() == 1){
					// Argument has no key
					throw new IllegalArgumentException("Invalid argument: " + arg);
				}
				
				if (!arg.matches("^\\-[aA-zZ0-9]+")){
					// Only latin alphanumeric single-letter arguments are allowed
					throw new IllegalArgumentException("Invalid argument: " + arg);
				}
				
				// Expand to individual characters and add them as keys with empty string value
				char[] chars = arg.substring(1).toCharArray();
				for(char ch: chars){
					if (collection instanceof HashMap){
						((HashMap)collection).put("-" + ch, "");
					}
					else {
						((ArrayListValuedHashMap)collection).put("-" + ch, "");
					}
				}
				i++;
			}
			else {
				if (collection instanceof HashMap){
					((HashMap)collection).put("", arg);
				}
				else {
					((ArrayListValuedHashMap)collection).put("", arg);
				}
				i++;
			}

		}
	}

}
