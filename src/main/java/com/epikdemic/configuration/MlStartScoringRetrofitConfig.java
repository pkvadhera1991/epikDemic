package com.epikdemic.configuration;

import com.epikdemic.dto.HttpConfig;
import com.epikdemic.retrofit.StartScoringGateway;
import com.epikdemic.retrofit.StartScoringInterface;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class MlStartScoringRetrofitConfig {
    @Autowired
    Environment env;

    @Bean("Start-Scoring")
    public HttpConfig<StartScoringInterface> startScoringHttpConfig() {
        HttpConfig httpConfig = new HttpConfig();
        httpConfig.setBaseUrl(env.getProperty("ml.startscoring.baseurl"));
        httpConfig.setConnectTimeout(60000L);
        httpConfig.setWriteTimeout(60000L);
        httpConfig.setConnectTimeout(60000L);
        httpConfig.setReadTimeout(60000L);
        httpConfig.setPoolSize(20);
        httpConfig.setLevel(HttpLoggingInterceptor.Level.BODY);
        return httpConfig;
    }

    @Bean
    public StartScoringGateway startScoringGateway() {
        return new StartScoringGateway(startScoringHttpConfig());
    }
}
