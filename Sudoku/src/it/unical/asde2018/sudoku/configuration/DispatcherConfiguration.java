package it.unical.asde2018.sudoku.configuration;

import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@EnableWebMvc
@Configuration
@EnableScheduling
@ComponentScan("it.unical.asde2018.sudoku.components")
public class DispatcherConfiguration implements WebMvcConfigurer {
	@Bean
	public InternalResourceViewResolver resolver() {
		return new InternalResourceViewResolver("WEB-INF/views/", ".jsp");
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("resources/**").addResourceLocations("/WEB-INF/resources/");
	}

	@Bean
	public SessionFactory sessionFactory() {

		LocalSessionFactoryBean lsfb = new LocalSessionFactoryBean();
	//	lsfb.setDataSource(getDataSource());
		lsfb.setHibernateProperties(getHibernateProperties());
		lsfb.setPackagesToScan("it.unical.asde2018.sudoku.model");

		try {
			lsfb.afterPropertiesSet();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lsfb.getObject();

	}

	private static Properties getHibernateProperties() {

		Properties prop = new Properties();

		try
		{
			prop.load(PropertiesLoaderUtils.class.getResourceAsStream("/hibernate.properties"));
			System.out.println(prop.toString());
		} catch (Exception e)
		{
			System.out.println("ERRORE");
			e.printStackTrace();
		}
//		prop.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
//		prop.put("hibernate.show_sql", true);
//		prop.put("hibernate.format_sql", true);
//		prop.put("hibernate.hbm2ddl.auto", "create");
		return prop;
	}

	@Bean
	public DataSource getDataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("org.h2.Driver");
		dataSource.setUrl("jdbc:h2:mem:MyDB");
		dataSource.setUsername("sa");
		dataSource.setPassword("sa");
		return dataSource;
	}
}
