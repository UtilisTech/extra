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

/**
 * 
 * @author Eugene Dementiev
 */
public class RuntimeUtils {
	
	/**
	 * Parses an arguments array into a HashMap of Strings; arguments like '-qwe' are expanded into -q, -w and -e<br/>
	 * Arguments starting with double hyphen like '--suppress' are allowed; if followed by a string that does not begin with a hyphen, this string will be set as the argument's value, e.g. '--file out.log' will be parsed as argument '--file' with value 'out.log'
	 * @param args arguments passed to the application from command line
	 * @return map of strings where keys are arguments and values are associated values (if any)
	 * @throws IllegalArgumentException if arguments do not start with 1 or 2 hyphens; or contain hyphens anywhere else;
	 * if two or more consecutive values (not starting with a hyphen)
	 */
	public static HashMap<String,String> parse_arguments(String[] args) throws IllegalArgumentException {
		
		HashMap<String,String> map = new HashMap();
		
		for (int i = 0; i < args.length;) {
			String arg = args[i];
			String arg_next = "";
			
			if ((i+1) < args.length){
				arg_next = args[i+1];
			}
			
			if (arg.matches("\\-+") || arg.replaceAll("^-+", "").matches(".*-.*")){
				throw new IllegalArgumentException("Invalid argument: " + arg);
			}
			
			if (arg.startsWith("---")){
				throw new IllegalArgumentException("Invalid argument: " + arg);
			}
			else if (arg.startsWith("--")){
				if (arg_next != null && !arg_next.startsWith("-")){
					map.put(arg, arg_next);
					i += 2;
				}
				else {
					map.put(arg, "");
					i++;
				}
			}
			else if (arg.startsWith("-")){
				char[] chars = arg.substring(1).toCharArray();
				for(char ch: chars){
					map.put("-" + ch, "");
				}
				i++;
			}
			else {
				throw new IllegalArgumentException("Invalid argument: " + arg);
			}

		}
		
		return map;
	}
	
}
