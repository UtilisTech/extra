package com.som_service.extra.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Eugene Dementiev
 */
public class RuntimeUtilsTest {
	
	HashMap<String, String> hashMap;
	ArrayListValuedHashMap<String, String> valuedMap;
	String[] args;
	static Method methodparseArgumentsInto;
	
	public RuntimeUtilsTest() {
	}
	
	@BeforeClass
	public static void setUpClass() {
		try {
			Class<RuntimeUtils> classRuntimeUtils = RuntimeUtils.class;
			methodparseArgumentsInto = classRuntimeUtils.getDeclaredMethod("parseArgumentsInto", Object.class, String[].class);
			methodparseArgumentsInto.setAccessible(true);
		}
		catch(NoSuchMethodException | SecurityException ex){
			ex.printStackTrace();
			fail("Can't test private method");
		}
	}
	
	@AfterClass
	public static void tearDownClass() {
	}
	
	@Before
	public void setUp() {
		hashMap = new HashMap<>();
		valuedMap = new ArrayListValuedHashMap<>();
		args = new String[0];
	}
	
	@After
	public void tearDown() {
	}
	
	@Test
	public void testNull(){
		String[][] failCases = new String[][]{
			null,
			{null}
		};
		
		for (String[] args: failCases){
			boolean error = false;
			try {
				hashMap = RuntimeUtils.parseArguments(args);
				valuedMap = RuntimeUtils.parseArgumentsRepeating(args);
			}
			catch(IllegalArgumentException ex){
				error = true;
			}
			
			assertTrue("Null not detected", error);
		}
	}
	
	@Test
	public void commonTest1(){
		args = new String[]{"-a"};
		hashMap = RuntimeUtils.parseArguments(args);
		valuedMap = RuntimeUtils.parseArgumentsRepeating(args);
		
		assertEquals(hashMap.size(), 1);
		assertEquals(valuedMap.size(), 1);
		
		assertTrue(hashMap.containsKey("-a"));
		assertTrue(valuedMap.containsKey("-a"));
	}
	
	@Test
	public void commonTest2(){
		args = new String[]{"-asd"};
		hashMap = RuntimeUtils.parseArguments(args);
		valuedMap = RuntimeUtils.parseArgumentsRepeating(args);
		
		assertEquals(hashMap.size(), 3);
		assertEquals(valuedMap.size(), 3);
		
		assertTrue(hashMap.containsKey("-a"));
		assertTrue(hashMap.containsKey("-s"));
		assertTrue(hashMap.containsKey("-d"));
		assertTrue(valuedMap.containsKey("-a"));
		assertTrue(valuedMap.containsKey("-s"));
		assertTrue(valuedMap.containsKey("-d"));
	}
	
	@Test
	public void commonTest3(){
		args = new String[]{"-asd", "--verbose", "--file", "out.log"};
		hashMap = RuntimeUtils.parseArguments(args);
		
		assertEquals(hashMap.size(), 5);
		
		assertTrue(hashMap.containsKey("-a"));
		assertTrue(hashMap.containsKey("-s"));
		assertTrue(hashMap.containsKey("-d"));
		assertTrue(hashMap.containsKey("--verbose"));
		assertTrue(hashMap.containsKey("--file"));
		assertTrue(!hashMap.containsKey("out.log"));
		
		assertEquals(hashMap.get("--file"), "out.log");
	}
	
	@Test
	public void commonTest4(){
		String[][] failCases = new String[][]{
			{"-"},
			{"--"},
			{"---"},
			{"---a"},
			{"-", "--", "---", "---a"},
			{"-a-"}
		};
		
		for (String[] args: failCases){
			boolean error = false;
			try {
				hashMap = RuntimeUtils.parseArguments(args);
				valuedMap = RuntimeUtils.parseArgumentsRepeating(args);
			}
			catch(IllegalArgumentException ex){
				error = true;
			}
			
			assertTrue("Invalid argument not detected", error);
		}
	}
	
	@Test
	public void commonTest5(){
		args = new String[]{
			"--path",			// a1k
			"c:\\asd\\tes-t",	// a1v
			"-qwe",				// a2,3,4
			"--test",			// a5k
			"asd-dsa",			// a5v
			"--asd",			// a6k
			"a-b",				// a6v
			"--dsa"				// a7k
		};
		hashMap = RuntimeUtils.parseArguments(args);
		
		assertEquals(hashMap.size(), 7);
		
		assertTrue(hashMap.containsKey("--path"));
		assertTrue(hashMap.containsKey("-q"));
		assertTrue(hashMap.containsKey("-w"));
		assertTrue(hashMap.containsKey("-e"));
		assertTrue(hashMap.containsKey("--test"));
		assertTrue(hashMap.containsKey("--asd"));
		assertTrue(hashMap.containsKey("--dsa"));
		
		assertTrue(!hashMap.containsKey("c:\\asd\\tes-t"));
		assertTrue(!hashMap.containsKey("asd-dsa"));
		assertTrue(!hashMap.containsKey("a-b"));
		
		assertEquals(hashMap.get("--path"), "c:\\asd\\tes-t");
		assertEquals(hashMap.get("--test"), "asd-dsa");
		assertEquals(hashMap.get("--asd"), "a-b");
		assertEquals(hashMap.get("--dsa"), "");
		
	}
	
	@Test
	public void parseArgumentsRepeatingTest1(){
		args = new String[]{"-aa"};
		valuedMap = RuntimeUtils.parseArgumentsRepeating(args);
		
		assertEquals(valuedMap.size(), 2);
		
		assertTrue(valuedMap.containsKey("-a"));
	}
	
	@Test
	public void parseArgumentsRepeatingTest2(){
		args = new String[]{"--fruit", "apple", "--fruit", "banana", "--color", "red", "--color", "yellow"};
		valuedMap = RuntimeUtils.parseArgumentsRepeating(args);
		
		assertEquals(valuedMap.size(), 4);
		assertEquals(valuedMap.keySet().size(), 2);
		assertTrue(valuedMap.containsKey("--fruit"));
		assertTrue(valuedMap.containsKey("--color"));
		
		assertTrue(valuedMap.get("--color").contains("red"));
		assertTrue(valuedMap.get("--color").contains("yellow"));
		assertTrue(!valuedMap.get("--color").contains("apple"));
		assertTrue(!valuedMap.get("--color").contains("banana"));
		
		assertTrue(valuedMap.get("--fruit").contains("apple"));
		assertTrue(valuedMap.get("--fruit").contains("banana"));
		assertTrue(!valuedMap.get("--fruit").contains("red"));
		assertTrue(!valuedMap.get("--fruit").contains("yellow"));
	}

	/**
	 * Basic test of the private methods in RuntimeUtils. Should not be normally run once other tests are in place
	 */
	//@Test
	@Deprecated
	public void testParseArgumentsInto1() {
		
		String[][] failCases = new String[][]{
			{"-"},
			{"--"},
			{"---"},
			{"---a"},
			{"-", "--", "---", "---a"},
			{"-a-"}
		};
		
		for (String[] args: failCases){
			boolean error = false;
			try {
				methodparseArgumentsInto.invoke(null, new Object[]{ valuedMap, args});
			}
			catch(IllegalAccessException | InvocationTargetException | IllegalArgumentException ex){
				System.out.println(ex.getCause().getMessage());
				error = true;
			}

			if (!error){
				System.out.println("Wrongly accepted: "+Arrays.toString(args));
				fail();
			}
		}
		
	}
	
	/**
	 * Basic test of the private methods in RuntimeUtils. Should not be normally run once other tests are in place
	 */
	//@Test
	@Deprecated
	public void testParseArgumentsInto2() {
		
		args = new String[]{
			"--path",			// a1k
			"c:\\asd\\tes-t",	// a1v
			"-qwe",				// a2,3,4
			"--test",			// a5k
			"asd-dsa",			// a5v
			"--asd",			// a6k
			"a-b",				// a6v
			"--dsa"				// a7k
		};

		try {
			methodparseArgumentsInto.invoke(null, new Object[]{valuedMap, args});
		}
		catch(IllegalAccessException | InvocationTargetException | IllegalArgumentException ex){
			fail(ex.getCause().getMessage());
			ex.printStackTrace();
			
		}
		
		if(valuedMap.keySet().size() != 7){
			fail("Some arugments not read");
		}

	}
	
}
