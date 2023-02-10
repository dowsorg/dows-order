package org.dows.order.json.config.converter;

import cn.hutool.core.date.DateUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * <code>
 * <pre>
 * 日期转换器,将请求参数的日期字符串转换成java.util.Date类型
 * 日期格式顺序:
 * 	1.yyyy-MM-dd HH:mm:ss:S
 * 	2.yyyy-MM-dd HH:mm:ss
 * 	3.yyyy-MM-dd HH:mm
 * 	4.yyyy-MM-dd HH
 *	5.yyyy-MM-dd
 * </pre>
 * </code>
 * @author magang
 * @date 2018-11-08
 */
public class StringToDateUtil{
	/**
	 * 日期格式化数组
	 */
	private static String[] dateFormats = {
			("yyyy-MM-dd HH:mm:ss"),
			("yyyy-MM-dd HH:mm"),
			("yyyy-MM-dd"),
			("yyyy-MM"),
			("HH:mm"),
			("yyyy-MM-dd HH:mm:ss:S")
	};

	/**
	 * <code>
	 * <pre>
	 * 1.如果日期字符串为空,则直接返回空
	 * 2.使用格式化组进行格式化,如果解析成功,则直接返回
	 * 4.否则,抛出非法参数异常
	 * @param source 请求的日期参数
	 * @return 解析后的日期类型:java.util.Date
	 * @exception IllegalArgumentException 非法参数异常
	 * </pre>
	 * </code>
	 */
	public static Date convert(String source) {
		if (StringUtils.isBlank(source)){
			return null;
		}
		source = source.trim();

		try{
			int timeLength = source.length();
			Long time = Long.parseLong(source);
			if (timeLength == 10){
				time = time * 1000;
			}
			Date date = new Date(time);
			return date;
		}catch (Exception e){

		}

		Date date = null;
		boolean flag = false;
		for (String dateFormat : dateFormats) {
			try {
				// dubbo环境使用DateTime传参，收到的永远是当前时间，转原生Date解决 详情：https://gitee.com/dromara/hutool/issues/I1818X
				date = DateUtil.parse(source, dateFormat).toJdkDate();
				flag = true;
				break;
			}catch (Exception e){

			}
		}

		if (flag) {
			return date;
		}else{
			throw new IllegalArgumentException("不能解析日期:" + source);
		}

	}
}
