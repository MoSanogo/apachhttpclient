package httpclient;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.google.gson.Gson;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.ByteArrayEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.List;

public class Client {
    private final CloseableHttpClient _httpClient;
    private final ObjectMapper _mapper;

    private final Logger logger = LoggerFactory.getLogger(Client.class);

    public Client() {
        _mapper = new ObjectMapper();
        _httpClient = HttpClients.createDefault();


    }


/*    public <T> CompletableFuture<List<T>> fetch(String url, TypeReference<List<T>> targetType) {
        return CompletableFuture.supplyAsync(() -> {

            HttpGet httpGet = new HttpGet(url);
            try {
                return _httpClient.execute(httpGet, (response) -> {
                    var result = EntityUtils.toByteArray(response.getEntity());

                    return _mapper.readValue(result, targetType);
                });

            } catch (IOException e) {
                logger.error(e.getMessage(), e);
                throw new RuntimeException(e);
            }

        });


    }*/


    public <T> CompletableFuture<List<T>> fetch(String url, Class<T> clazz) {
        return CompletableFuture.supplyAsync(() -> {
            CollectionType TType=_mapper.getTypeFactory().constructCollectionType(List.class,clazz);
            HttpGet httpGet = new HttpGet(url);
            try {
                return _httpClient.execute(httpGet, (response) -> {
                    logger.info("Fecthing data from "+ url);
                    var result = EntityUtils.toByteArray(response.getEntity());
                    return (List<T>) _mapper.readValue(result, TType);
                });

            } catch (IOException e) {
                logger.error(e.getMessage(), e);
                throw new RuntimeException(e);
            }

        });


    }
    public <T> CompletableFuture<T> fetchOne(String url, Class<T> clazz) {
        return CompletableFuture.supplyAsync(() -> {
            logger.info("Fecthing data from "+ url);
            HttpGet httpGet = new HttpGet(url);
            try {
                return _httpClient.execute(httpGet, (response) -> {
                    var result = EntityUtils.toByteArray(response.getEntity());

                    return (T) _mapper.readValue(result,clazz);
                });

            } catch (IOException e) {
                logger.error(e.getMessage(), e);
                throw new RuntimeException(e);
            }

        });


    }

    public <T> CompletableFuture<T> Post(String url, T data) {
        return CompletableFuture.supplyAsync(()->{
            HttpPost httpPost = new HttpPost(url);

            try {
                var d = _mapper.writeValueAsBytes(data);
                HttpEntity entity = new ByteArrayEntity(d, ContentType.APPLICATION_JSON);
                httpPost.setEntity(entity);
              return  _httpClient.execute(httpPost,response -> {
                    System.out.println(response.getEntity());
                   var result=  EntityUtils.toByteArray(response.getEntity());
                    return (T) _mapper.readValue(result,data.getClass());
                });

            } catch (Exception e) {
                throw  new RuntimeException(e.getMessage());
            }




        });
    }

}