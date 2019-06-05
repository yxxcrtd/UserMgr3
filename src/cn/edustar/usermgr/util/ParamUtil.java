package cn.edustar.usermgr.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

/**
 * 辅助类
 * 
 * @author Yang XinXin
 * @version 2.0.0, 2010-09-02 10:01:02
 */
@SuppressWarnings("rawtypes")
public class ParamUtil {
	public static final Integer ZERO_INT = new Integer(0);
	public static final String EMPTY_STRING = "";
	private Map param_map;

	public ParamUtil(ServletRequest request) {
		this(request.getParameterMap());
	}

	public ParamUtil(Map param_map) {
		this.param_map = param_map;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		if (param_map == null)
			return "{}";

		StringBuffer strbuf = new StringBuffer(1024);
		strbuf.append("Params{");
		boolean first = true;

		for (Object key : param_map.keySet()) {
			if (first == false)
				strbuf.append(", ");
			strbuf.append(key).append("=");
			Object value = param_map.get(key);
			if (value == null || !(value instanceof Object[]))
				strbuf.append(value);
			else {
				Object[] a = (Object[]) value;
				strbuf.append("[");
				for (int j = 0; j < a.length; ++j) {
					strbuf.append(a[j]);
					if (j < a.length - 1)
						strbuf.append(",");
				}
				strbuf.append("]");
			}
			first = false;
		}
		strbuf.append("}");
		return strbuf.toString();
	}

	public boolean existParam(String key) {
		return this.param_map.containsKey(key);
	}

	public String getRequestParam(String key) {
		Object v = param_map.get(key);
		if (v == null)
			return null;
		if (v instanceof String)
			return (String) v;
		if (v instanceof String[]) {
			String[] sa = (String[]) v;
			if (sa.length == 0)
				return EMPTY_STRING;
			if (sa.length == 1)
				return sa[0];
			if (sa.length == 2)
				return sa[0] + "," + sa[1];
			StringBuilder strbuf = new StringBuilder();
			strbuf.append(sa[0]);
			for (int i = 1; i < sa.length; ++i)
				strbuf.append(",").append(sa[i]);
			return strbuf.toString();
		}
		return v.toString();
	}

	public String[] getRequestParamValues(String key) {
		Object val = this.param_map.get(key);
		if (val == null)
			return null;
		if (val instanceof String)
			return new String[] { (String) val };
		return (String[]) val;
	}

	public String generateUrlPattern() {
		StringBuffer strbuf = new StringBuffer();
		strbuf.append("?");
		boolean appendAmp = false;
		for (Object key : param_map.keySet()) {
			if (key == null)
				continue;
			Object v = getRequestParamValues(key.toString());
			if (!(v instanceof String[]))
				continue;
			String[] values = (String[]) v;
			if (values == null || values.length == 0)
				continue;
			if ("page".equals(key))
				continue;
			if (appendAmp)
				strbuf.append("&");
			for (int i = 0; i < values.length; ++i) {
				strbuf.append(key).append("=")
						.append(CommonUtil.urlUtf8Encode(values[i]));
				if (i < values.length - 1)
					strbuf.append("&");
			}
			appendAmp = true;
		}
		if (appendAmp)
			strbuf.append("&");
		strbuf.append("page={page}");

		return strbuf.toString();
	}

	public Pager createPager() {
		Pager pager = new Pager();
		int page = this.getIntParam("page");
		if (page <= 0)
			page = 1;
		pager.setCurrentPage(page);
		pager.setPageSize(20);
		pager.setItemNameAndUnit("记录", "条");
		pager.setUrlPattern(generateUrlPattern());
		return pager;
	}

	public String getParam(String key) {
		return getStringParam(key);
	}

	public String getStringParam(String key) {
		String result = this.safeGetStringParam(key, EMPTY_STRING);
		return (result == null) ? EMPTY_STRING : result;
	}

	public String safeGetStringParam(String key) {
		return safeGetStringParam(key, EMPTY_STRING);
	}

	public String safeGetStringParam(String key, String defval) {
		String val = getRequestParam(key);
		if (val == null)
			return defval;
		return val.trim();
	}

	public boolean isIntegerParam(String key) {
		String val = getRequestParam(key);
		return isInteger(val);
	}

	public int getIntParam(String key) {
		Integer i = safeGetIntParam(key, ZERO_INT);
		return i == null ? 0 : i.intValue();
	}

	public Integer getIntParamZeroAsNull(String key) {
		Integer i = safeGetIntParam(key, null);
		if (i == null)
			return null;
		if (i.intValue() == 0)
			return null;
		return i;
	}

	public Integer safeGetIntParam(String key) {
		return safeGetIntParam(key, ZERO_INT);
	}

	public Integer safeGetIntParam(String key, Integer defval) {
		String val = getRequestParam(key);
		return safeParseIntegerWithNull(val, defval);
	}

	public List<Integer> getIdList(String key) {
		return this.safeGetIntValues(key);
	}

	public List<Integer> safeGetIntValues(String key) {
		String[] values = getRequestParamValues(key);
		java.util.ArrayList<Integer> id_array = new java.util.ArrayList<Integer>();
		if (values == null || values.length == 0)
			return id_array;
		for (int i = 0; i < values.length; ++i) {
			if (values[i] == null || values[i].length() == 0)
				continue;
			String[] val_split = values[i].split(",");
			for (int j = 0; j < val_split.length; ++j) {
				try {
					id_array.add(Integer.parseInt(val_split[j]));
				} catch (NumberFormatException ex) {
				}
			}
		}
		return id_array;
	}

	public boolean getBooleanParam(String key) {
		Boolean b = safeGetBooleanParam(key, null);
		return b == null ? false : b.booleanValue();
	}

	public Boolean safeGetBooleanParam(String key) {
		return safeGetBooleanParam(key, null);
	}

	public Boolean safeGetBooleanParam(String key, Boolean defval) {
		String val = getRequestParam(key);
		return safeParseBooleanWithNull(val, defval);
	}

	public Date getDateParam(String key) {
		return this.safeGetDate(key);
	}

	public Date safeGetDate(String key) {
		return safeGetDate(key, new Date());
	}

	public Date safeGetDate(String key, Date defval) {
		String temp = safeGetStringParam(key);
		if (temp == null || temp.length() == 0)
			return defval;
		try {
			return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(temp);
		} catch (Exception ex) {
		}
		try {
			return DateFormat.getInstance().parse(temp);
		} catch (Exception ex) {
		}
		try {
			@SuppressWarnings("deprecation")
			Date result = new Date(Date.parse(temp));
			return result;
		} catch (Exception ex) {
		}
		return defval;
	}

	public static final boolean isInteger(String val) {
		if (val == null)
			return false;
		if (val.length() == 0)
			return false;
		val = val.trim();
		if (val.length() == 0)
			return false;
		for (int i = 0; i < val.length(); ++i) {
			char c = val.charAt(i);
			if (c == '+' || c == '-')
				continue;
			if (c > '9' || c < '0')
				return false;
		}
		try {
			Integer.parseInt(val);
		} catch (NumberFormatException ex) {
			return false;
		}
		return true;
	}

	public static final boolean isEmptyString(String val) {
		if (val == null)
			return true;
		if (val.length() == 0)
			return true;
		if (val.trim().length() == 0)
			return true;
		return false;
	}

	public static final boolean isBlankString(String val) {
		return isEmptyString(val);
	}

	public static final boolean isBoolean(String val) {
		if (val == null) {
			return false;
		} else if (val.trim().length() < 1) {
			return false;
		} else {
			try {
				Boolean.parseBoolean(val);
			} catch (Exception ex) {
				return false;
			}
			return true;
		}
	}

	public static final boolean isEnglishName(String val) {
		if (val == null || val.length() == 0)
			return false;
		for (int i = 0; i < val.length(); i++) {
			char ch = val.charAt(i);
			if (ch < 'A' || (ch > 'Z' && ch < 'a') || ch > 'z') {
				return false;
			}
		}
		return true;
	}

	public static Integer safeParseIntegerWithNull(String val) {
		return safeParseIntegerWithNull(val, null);
	}

	public static Integer safeParseIntegerWithNull(Object val) {
		return safeParseIntegerWithNull(val, null);
	}

	public static Integer safeParseIntegerWithNull(Object val, Integer defval) {
		if (val == null)
			return defval;
		if (val instanceof Number)
			return ((Number) val).intValue();
		if (val instanceof String)
			return safeParseIntegerWithNull((String) val, defval);
		return safeParseIntegerWithNull(val.toString(), defval);
	}

	public static Integer safeParseIntegerWithNull(String val, Integer defval) {
		if (val == null)
			return defval;
		val = val.trim();
		if (val.length() == 0 || "null".equalsIgnoreCase(val))
			return defval;
		if (isInteger(val) == false)
			return defval;
		return Integer.parseInt(val.trim());
	}

	public static int safeParseInt(String val, int defval) {
		if (val == null || val.length() == 0)
			return defval;
		Integer i = safeParseIntegerWithNull(val, defval);
		return i == null ? defval : i.intValue();
	}

	public static Double safeParseDoubleWithNull(String val, Double defval) {
		if (val == null)
			return defval;
		val = val.trim();
		if (val.length() == 0)
			return defval;
		if ("null".equals(val))
			return null;
		try {
			return Double.parseDouble(val);
		} catch (NumberFormatException ex) {
			return defval;
		}
	}

	public static Boolean safeParseBooleanWithNull(String val, Boolean defval) {
		if (val == null)
			return defval;
		val = val.trim();
		if (val.length() == 0)
			return defval;
		if (val.equals("0"))
			return Boolean.FALSE;
		else if (val.equals("1"))
			return Boolean.TRUE;
		else if (val.equalsIgnoreCase("yes") || "true".equalsIgnoreCase(val))
			return Boolean.TRUE;
		else if (val.equalsIgnoreCase("no") || "false".equalsIgnoreCase(val))
			return Boolean.FALSE;
		else if ("null".equalsIgnoreCase(val))
			return null;
		else {
			try {
				return Boolean.parseBoolean(val);
			} catch (Exception ex) {
				return defval;
			}
		}
	}

	public static boolean safeParseBoolean(String val, boolean defval) {
		Boolean result = safeParseBooleanWithNull(val, defval);
		return result == null ? defval : result;
	}

}
