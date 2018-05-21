package com.moraydata.general.management.util;

import javax.servlet.http.HttpSession;

import com.moraydata.general.primary.entity.dto.UserExtension;

public class HttpSessionUtils {

	public static void saveCurrentUser(HttpSession session, UserExtension userExtension) {
		session.setAttribute(Constants.CURRENT.USER, userExtension);
	}
	
	public static UserExtension getCurrentUser(HttpSession session) {
		return (UserExtension) session.getAttribute(Constants.CURRENT.USER);
	}
	
	public static boolean isCurrentUserExist(HttpSession session) {
		return getCurrentUser(session) != null;
	}
	
	public static long getLongCurrentUserId(HttpSession session) {
		return getCurrentUser(session).getUserId();
	}
	
	public static String getStringCurrentUserId(HttpSession session) {
		return String.valueOf(getLongCurrentUserId(session));
	}
	
//	public static User getTraditionalUser(HttpSession session) {
//		return new User(getLongUserId(session));
//	}
	
	public static void trySavingCurrentUser(HttpSession session, Object user) {
		if (user instanceof UserExtension) saveCurrentUser(session, (UserExtension)user);
	}
}
