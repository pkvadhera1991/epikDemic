package com.epikdemic.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.concurrent.TimeUnit;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class HttpConfig<T> {
	private String baseUrl;
	private Long readTimeout;
	private Long writeTimeout;
	private Long connectTimeout;
	private Integer poolSize;
	private HttpLoggingInterceptor.Level level;

	private Retrofit buildRetrofit() {
		ConnectionPool pool = new ConnectionPool(this.getPoolSize(), 5, TimeUnit.MINUTES);

		HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(
				HttpLoggingInterceptor.Logger.DEFAULT).setLevel(this.getLevel());

		OkHttpClient client = new OkHttpClient.Builder().readTimeout(this.getReadTimeout(), TimeUnit.MILLISECONDS)
				.connectTimeout(this.getConnectTimeout(), TimeUnit.MILLISECONDS)
				.writeTimeout(this.getWriteTimeout(), TimeUnit.MILLISECONDS).connectionPool(pool)
				.addInterceptor(httpLoggingInterceptor).build();

		Retrofit retrofit = new Retrofit.Builder().baseUrl(this.getBaseUrl()).client(client)
				.addConverterFactory(JacksonConverterFactory.create()).build();

		return retrofit;
	}

	public <T> T getHttpService(Class<T> clazz) {
		return buildRetrofit().create(clazz);
	}
}
