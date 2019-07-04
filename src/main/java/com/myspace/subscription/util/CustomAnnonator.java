package com.myspace.subscription.util;


import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.sun.codemodel.JAnnotationArrayMember;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JEnumConstant;
import com.sun.codemodel.JFieldVar;
import com.sun.codemodel.JMethod;

import io.swagger.annotations.ApiModelProperty;

public class CustomAnnonator implements org.jsonschema2pojo.Annotator {

	private static final String JSON_REQUIRED = "required";

	private static final String JSON_DATATYPE = "type";


	@Override
	public void propertyOrder(JDefinedClass clazz, JsonNode propertiesNode) {
		JAnnotationArrayMember annotationValue = clazz.annotate(JsonPropertyOrder.class).paramArray("value");
		for (Iterator<String> properties = propertiesNode.fieldNames(); properties.hasNext();) {
			annotationValue.param(properties.next());
		}

	}

	@Override
	public void propertyInclusion(JDefinedClass clazz, JsonNode schema) {		  
		 clazz.annotate(JsonInclude.class).param("value", JsonInclude.Include.NON_EMPTY);
	}

	@Override
	public void propertyField(JFieldVar field, JDefinedClass clazz, String propertyName, JsonNode propertyNode) {
		boolean required = false;
		if (propertyNode.get(JSON_REQUIRED) != null) {
			required = propertyNode.get(JSON_REQUIRED).asBoolean();
		}

		String dataType = "";
		if (propertyNode.get(JSON_DATATYPE) != null) {
			dataType = propertyNode.get(JSON_DATATYPE).asText();
		}

		

		Path currentRelativePath = Paths.get("");
		//String absolutePath = currentRelativePath.toAbsolutePath().toString();
		try {
			/*InputStream input = new FileInputStream(absolutePath.concat(PROPERTIES_LOCATION));
			Properties properties = new Properties();

			properties.load(input);
*/
			field.annotate(JsonProperty.class).param("value", propertyName);
			
			
			field.annotate(ApiModelProperty.class).param(JSON_REQUIRED, required).param("dataType", dataType);

			if (field.type().erasure().equals(field.type().owner().ref(Set.class))) {
				field.annotate(JsonDeserialize.class).param("as", LinkedHashSet.class);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void propertyGetter(JMethod getter, String propertyName) {
		getter.annotate(JsonProperty.class).param("value", propertyName);

	}

	@Override
	public void propertySetter(JMethod setter, String propertyName) {
		setter.annotate(JsonProperty.class).param("value", propertyName);

	}

	@Override
	public void anyGetter(JMethod getter) {
		getter.annotate(JsonAnyGetter.class);

	}

	@Override
	public void anySetter(JMethod setter) {
		setter.annotate(JsonAnySetter.class);
	}

	@Override
	public void enumCreatorMethod(JMethod creatorMethod) {
		creatorMethod.annotate(JsonCreator.class);
	}

	@Override
	public void enumValueMethod(JMethod valueMethod) {
		valueMethod.annotate(JsonValue.class);
	}

	@Override
	public void enumConstant(JEnumConstant constant, String value) {
	}

	@Override
	public boolean isAdditionalPropertiesSupported() {
		return true;
	}

	@Override
	public void additionalPropertiesField(JFieldVar field, JDefinedClass clazz, String propertyName) {
		field.annotate(JsonIgnore.class);
	}

	@Override
	public void dateTimeField(JFieldVar field, JsonNode propertyNode) {

	}

	@Override
	public void dateField(JFieldVar field, JsonNode propertyNode) {

	}

}