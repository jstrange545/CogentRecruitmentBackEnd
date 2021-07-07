package cogent.recruitment;

import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication
@EnableSwagger2
public class CogentRecruitmentAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(CogentRecruitmentAppApplication.class, args);
	}

//	@Configuration
//	public class LocalDateTimeSerializerConfig {
//
//		@Bean
//		public LocalDateTimeSerializer localDateTimeDeserializer() {
//			return new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss"));
//		}
//
//		@Bean
//		public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
//			return builder -> builder.serializerByType(LocalDateTime.class, localDateTimeDeserializer());
//		}
//	}
	
	@Bean
	public MultipartConfigElement multipartConfigElement(){
		MultipartConfigFactory factory = new MultipartConfigFactory ();
		factory.setMaxFileSize(DataSize.of(25, DataUnit.MEGABYTES));
		factory.setMaxRequestSize(DataSize.of(25, DataUnit.MEGABYTES));
		return factory.createMultipartConfig();
	}
}
