package com.angel.my.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MappingFastJsonView extends AbstractView {

	/**
	 * Default content type. Overridable as bean property.
	 */
	public static final String DEFAULT_CONTENT_TYPE = "application/json";
	
	private SerializeConfig serializeConfig = null; 

	private String encoding = "UTF-8";

	private Set<String> renderedAttributes;

	private boolean extractValueFromSingleKeyModel = true;

	private boolean disableCaching = true;
	
	/**
	 * Construct a new {@code JacksonJsonView}, setting the content type to {@code application/json}.
	 */
	public MappingFastJsonView() {
		serializeConfig = new SerializeConfig();
		serializeConfig.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss"));
		serializeConfig.put(java.sql.Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss"));
		serializeConfig.put(java.sql.Timestamp.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss"));
		
		setContentType(DEFAULT_CONTENT_TYPE);
	}

	/**
	 * Sets the {@code JsonEncoding} for this converter. By default UTF-8 is used.
	 */
	public void setEncoding(String encoding) {
		Assert.notNull(encoding, "'encoding' must not be null");
		this.encoding = encoding;
	}

	/**
	 * Returns the attributes in the model that should be rendered by this view.
	 */
	public Set<String> getRenderedAttributes() {
		return renderedAttributes;
	}

	/**
	 * Sets the attributes in the model that should be rendered by this view. When set, all other model attributes will be
	 * ignored.
	 */
	public void setRenderedAttributes(Set<String> renderedAttributes) {
		this.renderedAttributes = renderedAttributes;
	}

	/**
	 * Disables caching of the generated JSON.
	 *
	 * <p>Default is {@code true}, which will prevent the client from caching the generated JSON.
	 */
	public void setDisableCaching(boolean disableCaching) {
		this.disableCaching = disableCaching;
	}

	/**
	 * Set whether to serialize models containing a single attribute as a map or whether to 
	 * extract the single value from the model and serialize it directly.
	 * The effect of setting this flag is similar to using 
	 * {@code MappingJacksonHttpMessageConverter} with an {@code @ResponseBody}.
	 * request-handling method.  
	 * 
	 * <p>Default is {@code false}. 
	 */
	public void setExtractValueFromSingleKeyModel(boolean extractValueFromSingleKeyModel) {
		this.extractValueFromSingleKeyModel = extractValueFromSingleKeyModel;
	}

	@Override
	protected void prepareResponse(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType(getContentType());
		response.setCharacterEncoding(encoding);
		if (disableCaching) {
			response.addHeader("Pragma", "no-cache");
			response.addHeader("Cache-Control", "no-cache, no-store, max-age=0");
			response.addDateHeader("Expires", 1L);
		}
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Object value = filterModel(model);
		String jsonString = JSON.toJSONString(value, serializeConfig);
		
		PrintWriter out = response.getWriter();
		out.print(jsonString);
		out.flush();
	}

	/**
	 * Filters out undesired attributes from the given model. The return value can be either another {@link Map}, or a
	 * single value object.
	 *
	 * <p>Default implementation removes {@link BindingResult} instances and entries not included in the {@link
	 * #setRenderedAttributes(Set) renderedAttributes} property.
	 *
	 * @param model the model, as passed on to {@link #renderMergedOutputModel}
	 * @return the object to be rendered
	 */
	protected Object filterModel(Map<String, Object> model) {
		Map<String, Object> result = new HashMap<String, Object>(model.size());
		Set<String> renderedAttributes =
				!CollectionUtils.isEmpty(this.renderedAttributes) ? this.renderedAttributes : model.keySet();
		for (Map.Entry<String, Object> entry : model.entrySet()) {
			if (!(entry.getValue() instanceof BindingResult) && renderedAttributes.contains(entry.getKey())) {
				result.put(entry.getKey(), entry.getValue());
			}
		}
		return (this.extractValueFromSingleKeyModel && result.size() == 1) ? result.values().iterator().next() : result;
	}

}