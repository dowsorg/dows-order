package org.dows.order.json.config.converter;

import org.springframework.core.convert.converter.Converter;

/**
 * <code>
 *
 * </code>
 * @author magang
 * @date 2018-11-08
 */
public class StringToIntegerConverter implements Converter<String, Integer> {

	@Override
	public Integer convert(String source) {
		return StringToIntegerUtil.convert(source);
	}
}
