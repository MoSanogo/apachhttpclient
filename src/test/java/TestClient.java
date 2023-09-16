import httpclient.Client;
import httpclient.Post;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestClient {
    private final Client client=new Client();

    @Test
    @DisplayName("Given a uri  and dto Client can fetch and return a list of dto")
    public void givenAUrlAndDtoClientReturnsAListOfDto(){
         String url="https://jsonplaceholder.typicode.com/posts";
         var result=client.fetch(url, Post.class)
                 .join();
         Post candidate=result.get(0);
         assertTrue(candidate instanceof Post);
         assertFalse(result.isEmpty());

    }


    @Test
    @DisplayName("Given a uri  and dto Client can fetch and return a  dto")
    public void givenAUrlAndDtoWithIdClientReturnsDto(){
        String url="https://jsonplaceholder.typicode.com/posts" + 1;
        Post result=client.fetchOne(url, Post.class)
                .join();

        assertTrue(result instanceof Post);
        assertTrue(result !=null);

    }

    @Test
    @DisplayName("Given a uri  and dto Client can fetch and return a  dto")
    public void givenAUrlAndDtoWithIdClientPostsAndReturnsDto(){
        String url="https://jsonplaceholder.typicode.com/posts";
        Post candidate=new Post("","Junit Test","This is a test post from Junit","3") ;       ;
        Post result=client.Post(url, candidate)
                .join();

        assertTrue(result instanceof Post);
        assertTrue(result !=null);
        assertTrue(result.getId()!=null);
        assertEquals(result.getBody(), candidate.getBody());
        assertEquals(result.getTitle(), candidate.getTitle());

    }
}
