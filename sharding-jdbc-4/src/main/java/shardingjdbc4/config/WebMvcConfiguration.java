package shardingjdbc4.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import shardingjdbc4.config.filter.OpenInViewFilter;

@Configuration
@ConditionalOnProperty(prefix = "shardingjdbc4.sharding", name = "enabled", havingValue = "true", matchIfMissing = false)
public class WebMvcConfiguration {

  @Bean
  public FilterRegistrationBean<OpenInViewFilter> openEntityManagerInViewFilter() {
    OpenInViewFilter viewFilter = new OpenInViewFilter();
    viewFilter.addShardingPathPatterns("/api/animal/**");
    FilterRegistrationBean<OpenInViewFilter> registrationBean = new FilterRegistrationBean<>();
    registrationBean.setFilter(viewFilter);
    registrationBean.addUrlPatterns("/api/*");

    return registrationBean;
  }
}
