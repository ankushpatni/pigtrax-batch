package com.pigtrax.batch.util;


public class PrimitiveDataUtil {
	
	private static String alphaNumericPattern = "/^[a-z0-9]+$/i";
	public static Integer returnValidInteger(Integer integer) {
		
		return integer;
	}
	
	public static boolean validateAlphaNumeric(String str)
	{
		return str.matches(alphaNumericPattern);
	}
}
