package com.unicom.api.cterminal.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
//在生产环境的时候才自动配置进去
//@ConditionalOnExpression("'${spring.profiles.active}'.equals('dev')")
public class DruidConfig {

    /**
     * springboot在启动的时候并不会自动初始化数据源，所以登录druid管理界面后，没有任何关于数据库连接的属性（报property for user to setup）
     * ，发送请求后，数据源被初始化,SQL监控生效，也可以看数据源信息。
     * @Bean(initMethod = "init",destroyMethod = "close") 用来启动springboot项目的时候初始化druid数据源(数据源启动有点慢)
     */
    @Bean(initMethod = "init",destroyMethod = "close")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druidDataSource(){
        return new DruidDataSource();
    }

    /**
     * druid数据源状态监控
     * @return
     */
    @Bean
    public ServletRegistrationBean statViewServlet(){
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
        //IP白名单
        servletRegistrationBean.addInitParameter("allow","127.0.0.1");
        //IP黑名单（存在共同是，deny优于allow）如果满足deny的话提示:Sorry, you are not permitted to view this page.
        //servletRegistrationBean.addInitParameter("deny","");
        //登录查看信息的用户名和密码
        servletRegistrationBean.addInitParameter("loginUsername","root");
        servletRegistrationBean.addInitParameter("loginPassword","root");
        //是否能够重置数据
        servletRegistrationBean.addInitParameter("resetEnable","false");
        return servletRegistrationBean;
    }

    /**
     * 配置Druid过滤器
     * @return
     */
    @Bean
    public FilterRegistrationBean webStatFilter(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions","*.js,*.jpg,*.png,*.gif,*.ico,*.css,/druid/*");
        return filterRegistrationBean;
    }


}
