package com.mydeo.seckilldemo.config.druid;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * @Author: Chris he
 * @Date: 2020/7/20 16:35
 * 使用druid-spring-boot-starter时设置监控界面登录信息的方法
 */
@Configuration
@Primary
public class DruidDataSourceConfig {

  private Logger logger = LoggerFactory.getLogger(DruidDataSourceConfig.class);

  @Value("${spring.datasource.url}")
  private String dbUrl;

  @Value("${spring.datasource.username}")
  private String username;

  @Value("${spring.datasource.password}")
  private String password;

  @Value("${spring.datasource.driver-class-name}")
  private String driverClassName;

  @Value("${spring.datasource.initial-size}")
  private int initialSize;

  @Value("${spring.datasource.min-idle}")
  private int minIdle;

  @Value("${spring.datasource.max-active}")
  private int maxActive;

  @Value("${spring.datasource.max-wait}")
  private int maxWait;

  @Value("${spring.datasource.time-between-eviction-runs-millis}")
  private int timeBetweenEvictionRunsMillis;

  @Value("${spring.datasource.min-evictable-idle-time-millis}")
  private int minEvictableIdleTimeMillis;

  @Value("${spring.datasource.validation-query}")
  private String validationQuery;

  @Value("${spring.datasource.test-while-idle}")
  private boolean testWhileIdle;

  @Value("${spring.datasource.test-on-borrow}")
  private boolean testOnBorrow;

  @Value("${spring.datasource.test-on-return}")
  private boolean testOnReturn;

  @Value("${spring.datasource.pool-prepared-statements}")
  private boolean poolPreparedStatements;

  @Value("${spring.datasource.max-open-prepared-statements}")
  private int maxPoolPreparedStatementPerConnectionSize;

  @Bean     //声明其为Bean实例
  public DataSource dataSource() {
    DruidDataSource datasource = new DruidDataSource();
    datasource.setUrl(this.dbUrl);
    datasource.setUsername(username);
    datasource.setPassword(password);
    datasource.setDriverClassName(driverClassName);
    //configuration
    datasource.setInitialSize(initialSize);
    datasource.setMinIdle(minIdle);
    datasource.setMaxActive(maxActive);
    datasource.setMaxWait(maxWait);
    datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
    datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
    datasource.setValidationQuery(validationQuery);
    datasource.setTestWhileIdle(testWhileIdle);
    datasource.setTestOnBorrow(testOnBorrow);
    datasource.setTestOnReturn(testOnReturn);
    datasource.setPoolPreparedStatements(poolPreparedStatements);
    datasource
        .setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
    return datasource;
  }

  @Bean
  public ServletRegistrationBean druidServlet() {
    logger.info("init Druid Servlet Configuration ");
    ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
    // IP白名单
    servletRegistrationBean.addInitParameter("allow", "*");
    // IP黑名单(共同存在时，deny优先于allow)
    //servletRegistrationBean.addInitParameter("deny", "192.168.1.100");
    //控制台管理用户
    servletRegistrationBean.addInitParameter("loginUsername", "admin");
    servletRegistrationBean.addInitParameter("loginPassword", "admin");
    //是否能够重置数据 禁用HTML页面上的“Reset All”功能
    servletRegistrationBean.addInitParameter("resetEnable", "false");
    return servletRegistrationBean;
  }

  @Bean
  public FilterRegistrationBean filterRegistrationBean() {
    FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
    //验证所有请求
    filterRegistrationBean.addUrlPatterns("/*");
    //对 *.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/* 不进行验证
    filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
    return filterRegistrationBean;
  }
}