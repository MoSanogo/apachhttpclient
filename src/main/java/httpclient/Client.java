package httpclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.apache.hc.client5.http.classic.methods.*;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.ByteArrayEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
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


    public <T> CompletableFuture<List<T>> getAsync(String url, Class<T> clazz) {
        return CompletableFuture.supplyAsync(() -> {
            CollectionType TType=_mapper.getTypeFactory().constructCollectionType(List.class,clazz);
            HttpGet httpGet = new HttpGet(url);
            try {
                logger.info("Making  %s  Method to the url %s".formatted(httpGet.getClass().getName(),url));
                return _httpClient.execute(httpGet, (response) -> {
                    var result = EntityUtils.toByteArray(response.getEntity());
                    logger.info("Successfull Response %s Method to the url %s".formatted(httpGet.getClass().getName(),url));
                    return (List<T>) _mapper.readValue(result, TType);
                });

            } catch (IOException e) {
                logger.error(e.getMessage(), e);
                throw new RuntimeException(e);
            }

        });


    }
    public <T> CompletableFuture<T> getOneAsync(String url, Class<T> clazz) {
        return CompletableFuture.supplyAsync(() -> {
            HttpGet httpGet = new HttpGet(url);
            try {
                   logger.info("Making %s Method to the url %s".formatted(httpGet.getClass().getName(),url));
                return _httpClient.execute(httpGet, (response) -> {
                    var result = EntityUtils.toByteArray(response.getEntity());
                    logger.info("Successfull Response %s Method to the url %s".formatted(httpGet.getClass().getName(),url));
                    return (T) _mapper.readValue(result,clazz);
                });

            } catch (IOException e) {
                logger.error(e.getMessage(), e);
                throw new RuntimeException(e);
            }

        });


    }

    public <T> CompletableFuture<T> PostAsync(String url, T data) {
        return CompletableFuture.supplyAsync(()->{
            HttpPost httpPost = new HttpPost(url);
            try {
                var dataAsByteArray = _mapper.writeValueAsBytes(data);
                HttpEntity entity = new ByteArrayEntity(dataAsByteArray, ContentType.APPLICATION_JSON);
                httpPost.setEntity(entity);
                logger.info("Making %s Method to the url %s".formatted(httpPost.getClass().getName(),url));
              return  _httpClient.execute(httpPost,response -> {
                  logger.info("Successfull Response %s Method to the url %s".formatted(httpPost.getClass().getName(),url));
                   var result=  EntityUtils.toByteArray(response.getEntity());
                    return (T) _mapper.readValue(result,data.getClass());
                });

            } catch (Exception e) {
                logger.debug(e.getMessage(),e);
                throw  new RuntimeException(e.getMessage());
            }

        });
    }

    public CompletableFuture<Boolean> DeleteAsync(String url){
        HttpDelete httpDelete=new HttpDelete(url);
       return  CompletableFuture.supplyAsync(()->{
            try{
                logger.info("Making %s Method to the url %s".formatted(httpDelete.getClass().getName(),url));
              return  _httpClient.execute(httpDelete,(response)-> {
                  logger.info("Successfull Response %s Method to the url %s".formatted(httpDelete.getClass().getName(),url));
                  return response.getCode()==200? true :false;
              });
            }catch(IOException e){
                logger.debug(e.getMessage(),e);
                throw  new RuntimeException(e.getMessage());
            }
        });

    }

    public <T> CompletableFuture<T> PatchAsync(String url, T data) {
        return CompletableFuture.supplyAsync(()->{
            HttpPatch httpPatch = new  HttpPatch(url);
            try {
                var dataAsByteArray = _mapper.writeValueAsBytes(data);
                HttpEntity entity = new ByteArrayEntity(dataAsByteArray, ContentType.APPLICATION_JSON);
                httpPatch.setEntity(entity);
                logger.info("Making %s Method to the url %s".formatted(httpPatch.getClass().getName(),url));
                return  _httpClient.execute(httpPatch ,response -> {
                    var result=  EntityUtils.toByteArray(response.getEntity());
                    logger.info("Successfull Response %s Method to the url %s".formatted(httpPatch.getClass().getName(),url));
                    return (T) _mapper.readValue(result,data.getClass());
                });

            } catch (Exception e) {
                logger.debug(e.getMessage(),e);
                throw  new RuntimeException(e.getMessage());
            }
        });
    }


    public <T> CompletableFuture<T> PutAsync(String url, T data) {
        return CompletableFuture.supplyAsync(()->{
            HttpPut httpPut= new HttpPut(url);
            try {
                var dataAsByteArray = _mapper.writeValueAsBytes(data);
                HttpEntity entity = new ByteArrayEntity(dataAsByteArray, ContentType.APPLICATION_JSON);
                httpPut.setEntity(entity);
                logger.info("Making %s Method to the url %s".formatted(httpPut.getClass().getName(),url));
                return  _httpClient.execute(httpPut ,response -> {
                    var result=  EntityUtils.toByteArray(response.getEntity());
                    logger.info("Successfull Response %s Method to the url %s".formatted(httpPut.getClass().getName(),url));
                    return (T) _mapper.readValue(result,data.getClass());
                });

            } catch (Exception e) {
                logger.debug(e.getMessage(),e);
                throw  new RuntimeException(e.getMessage());
            }
        });
    }

}