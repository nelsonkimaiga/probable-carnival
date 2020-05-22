package com.iFundi;

import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.iFundi.config.ResourceConfig;
import com.iFundi.handlers.FileStorageProperties;

@SpringBootApplication(exclude = SpringDataWebAutoConfiguration.class)
@EnableAutoConfiguration(exclude = FlywayAutoConfiguration.class)
@EnableConfigurationProperties({FileStorageProperties.class})
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class CompasApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(CompasApplication.class).properties(getProperties());
	}

	public static void main(String[] args) {
		System.out.println("catalina base ###" + ResourceConfig.CATALINA_BASE);
		SpringApplication.run(CompasApplication.class, args);
	}

	static Properties getProperties() {
		Properties props = new Properties();
		props.put("spring.config.location", ResourceConfig.CATALINA_BASE + "/conf/ifundiDev/");
		// props.put("spring.config.location",
		// "D:\\Compulynx\\Projects\\prod_pbu_compas_otc_web\\backend\\src\\main\\resources");
		return props;
	}
}
