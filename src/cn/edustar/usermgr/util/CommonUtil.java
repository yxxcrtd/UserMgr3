package cn.edustar.usermgr.util;

import java.io.UnsupportedEncodingException;

import java.net.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 通用辅助函数
 * 
 * @author Yang XinXin
 * @version 2.0.0, 2010-09-01 9:26:53
 */
public class CommonUtil {
	public static final String EMPTY_STRING = "";

	private CommonUtil() {
	}

	public static boolean stringEquals(String a, String b) {
		if (null == a && null == b)
			return true;
		if (null == a || null == b)
			return false;
		return a.equals(b);
	}

	public static Boolean stringEqualsIgnoreCase(String a, String b) {
		if (null == a && null == b) {
			return true;
		}
		if (null == a || null == b) {
			return false;
		}
		return a.equalsIgnoreCase(b);
	}

	public static String urlUtf8Encode(String url) {
		try {
			if (null == url || url.length() == 0)
				return "";
			return URLEncoder.encode(url, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return url;
		}
	}

	public static boolean isEmptyString(String str) {
		if (null == str || str.length() == 0)
			return true;
		if (str.trim().length() == 0)
			return true;
		return false;
	}

	public static boolean isValidName(String name) {
		if (null == name)
			return false;
		if (name.length() == 0)
			return false;
		for (int i = 0; i < name.length(); ++i) {
			char ch = name.charAt(i);
			if (isEnglishChar(ch))
				continue;
			if (i > 0 && isDigitChar(ch))
				continue;
			if (i > 0 && ch == '_')
				continue;
			return false;
		}
		return true;
	}

	private static final boolean isDigitChar(char ch) {
		return (ch <= '9' && ch >= '0');
	}

	private static final boolean isEnglishChar(char ch) {
		return (ch <= 'Z' && ch >= 'A') || (ch <= 'z' && ch >= 'a');
	}
	
	public static final Boolean equalPassword(String dbPassword, String inputPassword) {
		String inputMD5Password = MD5.toMD5(inputPassword);
		if (inputMD5Password.equalsIgnoreCase(dbPassword)) {
			return true;
		}
		if (dbPassword.length() == 16) {
			if (inputMD5Password.indexOf(dbPassword) > -1) {
				return true;
			}
		}
		return false;
	}

	public static String getSiteUrl(HttpServletRequest request)
	{
		String root = "";
        if( request.getServerPort() == 80)
        {
            root = request.getScheme() + "://" + request.getServerName() + request.getContextPath() + "/";
        }
        else
        {
            root = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
        }
        return root;
	}
	
	
	public static String getSiteServer(HttpServletRequest request)
	{
		String root = "";
        if( request.getServerPort() == 80)
        {
            root = request.getScheme() + "://" + request.getServerName();
        }
        else
        {
            root = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        }
        return root;
	} 
}
