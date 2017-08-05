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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Arrays;
import java.util.Scanner;
import org.apache.commons.io.output.FileWriterWithEncoding;

/**
 *
 * @author Eugene Dementiev
 */
public class FileUtils {
	public static int number_of_lines(String filename) throws FileNotFoundException, IOException{
		int lines = 0;
		try (LineNumberReader lnr = new LineNumberReader(new FileReader(new File(filename)))) {
			lnr.skip(Long.MAX_VALUE);
			lines = lnr.getLineNumber() + 1;
		}
		catch (IOException e) {
			throw e;
		}
		
		return lines;
	}
	
	public static String[] file_as_array_trimmed(String filename, String encoding) throws FileNotFoundException, IOException{
		Scanner scanner;
		if (null == encoding){
			scanner = new Scanner(new File(filename));
		}
		else {
			scanner = new Scanner(new File(filename), encoding);
		}
		
		String[] data = new String[0];
				
		try {
			data = new String[number_of_lines(filename)];
		}
		catch(IOException e){
			scanner.close();
			throw e;
		}
		
		int i = 0;
		while(scanner.hasNextLine()){
			String string = scanner.nextLine();
			if (!"".equals(string)){
				data[i++] = string.trim();
			}
		}
		scanner.close();
		
		return Arrays.copyOfRange(data, 0, i);
	}
	
	public static void file_output(String file_path, String content, String encoding, boolean append) throws IOException{
		File file = new File(file_path);
		
		file.getParentFile().mkdirs();
		
		try (FileWriterWithEncoding fw = new FileWriterWithEncoding(file.getAbsoluteFile(), encoding, append)) {
			try (BufferedWriter bw = new BufferedWriter(fw)){
				bw.write(content);
			}
			catch(IOException e){
				throw e;
			}
		}
		catch (IOException e){
			throw e;
		}
	}
}
