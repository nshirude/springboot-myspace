package com.myspace.subscription.configuration;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.myspace.subscription.util.KeyGen;

@EnableJpaRepositories(basePackages = "com.myspace.subscription.repository")
@EnableCaching
@Configuration
public class CacheConfig {

	/**
	 * This is Cache enabling method
	 * @return
	 */
    @Bean
    public CacheManager cacheManager() {
    	EhCacheCacheManager cacheCacheManager = new EhCacheCacheManager();
    	cacheCacheManager.setCacheManager(cacheMangerFactory().getObject());
    	cacheCacheManager.setTransactionAware(true);
        return cacheCacheManager;
    }

    /**
     * This is EHCache enabling method, it specify ehcache configuration
     * @return EhCacheManagerFactoryBean
     */
    @Bean
    public EhCacheManagerFactoryBean cacheMangerFactory() {
        EhCacheManagerFactoryBean bean = new EhCacheManagerFactoryBean();
        bean.setConfigLocation(new ClassPathResource("/cache.xml"));
        bean.setShared(true);
        return bean;
    }
    
    @Bean("KeyGen")
    public KeyGenerator keyGenerator() {
        return new KeyGen();
    }
}
