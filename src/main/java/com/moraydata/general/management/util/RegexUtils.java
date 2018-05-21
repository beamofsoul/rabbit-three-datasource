package com.moraydata.general.management.util;

import java.util.regex.Pattern;

public class RegexUtils {

	public static boolean match(String string, String regex) {
		Pattern pattern = Pattern.compile(regex);
		boolean matched = pattern.matcher(string).matches();
		return matched;
	}
}
