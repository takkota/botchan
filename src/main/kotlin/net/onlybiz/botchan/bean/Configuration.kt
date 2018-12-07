package net.onlybiz.botchan.bean

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.springframework.context.annotation.Configuration
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.boot.web.client.RestTemplateCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory
import org.springframework.http.converter.json.GsonHttpMessageConverter
import org.springframework.web.client.RestOperations
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.DefaultUriBuilderFactory
import org.springframework.web.util.DefaultUriTemplateHandler

@Configuration
class Configuration {

    @Bean
    fun lineTemplate(builder: RestTemplateBuilder): RestOperations {
        val accessToken = ""
        return builder
                .requestFactory { OkHttp3ClientHttpRequestFactory(
                        OkHttpClient.Builder()
                                .addInterceptor {  chain ->
                                    var request = chain.request()
                                    request = request.newBuilder()
                                            .header("Authorization", "Bearer " + accessToken)
                                            .build()
                                    chain.proceed(request)
                                }
                                .build()
                )}
                .additionalMessageConverters(GsonHttpMessageConverter().apply {
                    gson = GsonBuilder().create()
                })
                .uriTemplateHandler(DefaultUriBuilderFactory().apply {
                    encodingMode = DefaultUriBuilderFactory.EncodingMode.TEMPLATE_AND_VALUES
                })
                .rootUri("https://handler.line.me/v2")
                .build()
    }

}