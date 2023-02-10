package org.dows.order.json.config.converter;


import cn.hutool.core.util.StrUtil;

/**
 * <code>
 * <pre>
 * 空字符串("")转换成Double的null
 *
 * </pre>
 * </code>
 * @author magang
 * @date 2018-11-08
 */
public class StringToDoubleUtil {

	public static Double convert(String source) {
		if (StrUtil.isBlank(source)){
			return null;
		}
		Double d = Double.parseDouble(source);
		return d;
	}
}
