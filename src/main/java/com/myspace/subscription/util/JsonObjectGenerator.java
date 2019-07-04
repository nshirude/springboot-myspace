package com.myspace.subscription.util;

import java.io.File;
import java.io.IOException;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;


import org.jsonschema2pojo.AnnotationStyle;
import org.jsonschema2pojo.DefaultGenerationConfig;
import org.jsonschema2pojo.GenerationConfig;
import org.jsonschema2pojo.InclusionLevel;
import org.jsonschema2pojo.SchemaGenerator;
import org.jsonschema2pojo.SchemaMapper;
import org.jsonschema2pojo.SchemaStore;
import org.jsonschema2pojo.SourceType;
import org.jsonschema2pojo.rules.RuleFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.sun.codemodel.JCodeModel;

public class JsonObjectGenerator {

	private static final Logger log = LoggerFactory.getLogger(JsonObjectGenerator.class);
	
	private static final String JSON_SOURCE_FOLDER = "\\src\\main\\resources\\jsonschema";
	
	private static final String POJO_DOMAIN_FOLDER = "com.myspace.subscription.dto";
	private static final String STR_DOT = ".";
	private static final String STR_BACKSLASH = "\\";

	public static void main(String... args) throws IOException {
		
		/*TODO - README
		 * In method getDomainPackagePath, set subDomainRequired to true/false according to requirements. 
		 * eg : If schema is segregated in multiple folders, which require respective domain packages, then variable will be set to true. 
		 * default set to false. 
		 * Update JSON_SOURCE_FOLDER and POJO_DOMAIN_FOLDER accordingly. 
		 * */
		
		JsonObjectGenerator builder = new JsonObjectGenerator();
		Path currentRelativePath = Paths.get("");
		String absolutePath = currentRelativePath.toAbsolutePath().toString();

		try (Stream<Path> paths = Files.walk(Paths.get(absolutePath + JSON_SOURCE_FOLDER))) {
			paths.filter(Files::isRegularFile).forEach(builder::generateJsonPogo);
		}
	}

	private void generateJsonPogo(Path path) {

		JCodeModel codeModel = new JCodeModel();
		
		try {
			URL url = path.toUri().toURL();
			String domainFolder = getDomainPackagePath(path);				
			GenerationConfig config = new DefaultGenerationConfig() {

				Path currentRelativePath = Paths.get("");
				String absolutePath = currentRelativePath.toAbsolutePath().toString();				
				

				@Override
				public AnnotationStyle getAnnotationStyle() {
					return AnnotationStyle.JACKSON;
				}
				
				@Override
				public File getTargetDirectory() {
					return new File(absolutePath.concat("\\src\\main\\java"));
				}

				@Override
				public boolean isIncludeAdditionalProperties() {
					return false;
				}

				@Override
				public boolean isIncludeJsr303Annotations() {
					return true;
				}

				@Override
				public boolean isGenerateBuilders() {
					return true;
				}
				
				@Override
				public InclusionLevel getInclusionLevel() {
					return InclusionLevel.NON_NULL;
				}				

				@Override
				public SourceType getSourceType() {
					return SourceType.JSONSCHEMA;
				}
			};
			SchemaMapper mapper = new SchemaMapper(new RuleFactory(config, new CustomAnnonator(), new SchemaStore()),new SchemaGenerator());
			
			mapper.generate(codeModel, path.getFileName().toString().split(".json")[0],domainFolder, url);

			codeModel.build(config.getTargetDirectory());
			
		} catch (IOException e) {
			log.info(e.getMessage());
		}
	}
	
	private String getDomainPackagePath(Path path){
		String returnStr = POJO_DOMAIN_FOLDER;
		boolean subDomainRequired = true;
		if(subDomainRequired) {
			String jsonSourcePackagePath = Paths.get("").toAbsolutePath().toString()+JSON_SOURCE_FOLDER;
			//Retrieve subDomain package name from 
			String subDomainPackgePath = path.toAbsolutePath().toString().substring(jsonSourcePackagePath.length()).split(path.getFileName().toString())[0]; 		
			String str = subDomainPackgePath.replace(STR_BACKSLASH, STR_DOT);
			int index = str.lastIndexOf(STR_DOT);
			String subDomainPackage = str.subSequence(0, index).toString();
			returnStr+=subDomainPackage;
		}	
		
		return returnStr;
	}
}
