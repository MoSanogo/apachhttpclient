import httpclient.Client;
import httpclient.Post;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestClient {
    private final Client client=new Client();

    @Test
    @DisplayName("Given a uri  and dto Client can fetch and return a list of dto.")
    public void givenAUrlAndDtoClientReturnsAListOfDto(){
         String url="https://jsonplaceholder.typicode.com/posts";
         var result=client.getAsync(url, Post.class)
                 .join();
         Post candidate=result.get(0);
         assertTrue(candidate instanceof Post);
         assertFalse(result.isEmpty());

    }


    @Test
    @DisplayName("Given a uri  and an entity Id Client can fetch and return a  dto")
    public void givenAUrlAndDtoWithIdClientReturnsDto(){
        String Id="1";
        String url="https://jsonplaceholder.typicode.com/posts/%s".formatted(Id);
        Post result=client.getOneAsync(url, Post.class)
                .join();

        assertTrue(result instanceof Post);
        assertTrue(result !=null);

    }

    @Test
    @DisplayName("Given a uri  and dto Client can post and return a  dto")
    public void givenAUrlAndDtoWithIdClientPostsAndReturnsDto(){
        String url="https://jsonplaceholder.typicode.com/posts";
        Post candidate=new Post("","Junit Test","This is a test post from Junit","3") ;
        Post result=client.PostAsync(url, candidate)
                .join();

        assertTrue(result instanceof Post);
        assertTrue(result !=null);
        assertTrue(result.getId()!=null);
        assertEquals(result.getBody(), candidate.getBody());
        assertEquals(result.getTitle(), candidate.getTitle());

    }

    @Test
    @DisplayName("Given a uri  and an entity Id, Client can delete and return a  dto")
    public void givenAUrlContainingTheToBeDeletedEntityIdWithIdClientDeleteAndReturnsBooleanToIndicateStatusOfTheRequest(){
        String url="https://jsonplaceholder.typicode.com/posts/%s";
        String EntityId="1";
        var result=client.DeleteAsync(url.formatted(EntityId))
                .join();
        assertTrue(result);


    }

    @Test
    @DisplayName("Given a uri  and dto, Client can partially update the entity with the given Id  and return a  dto")
    public void givenAUrlAndDtoWithIdClientPartiallyUpdatesAndReturnsDto(){
        String url="https://jsonplaceholder.typicode.com/posts/%s";
        String Id="2";
        String newBody="This is a test post from Junit";
        String newTitle="Junit Test";
        Post candidate=client.getOneAsync(url.formatted(Id), Post.class)
                .join();
        candidate.setBody(newBody);
        candidate.setTitle(newTitle);
        Post result=client.PatchAsync(url.formatted(Id),candidate)
                .join();
        System.out.println(result.getTitle());
        System.out.println(result.getBody());
        assertTrue(candidate instanceof Post);
        assertTrue(candidate !=null);
        assertTrue(result instanceof Post);
        assertTrue(result !=null);
        assertEquals( result.getId(),Id);
        assertEquals( result.getBody(), newBody);
        assertEquals( result.getTitle(),newTitle);

    }
    @Test
    @DisplayName("Given a uri  and dto with an Id, Client can completely update the entity with the given Id and return a  dto")
    public void givenAUrlAndDtoWithIdClientPostsAndReturnsDtos(){
        String url="https://jsonplaceholder.typicode.com/posts/%s";
        String Id="2";
        Post candidate=new Post("","Junit Test","This is a test post from Junit","3") ;       ;

        Post result=client.PutAsync(url.formatted(Id), candidate)
                .join();

        assertTrue(result instanceof Post);
        assertTrue(result !=null);
        assertEquals(result.getId(),Id);
        assertEquals(result.getBody(), candidate.getBody());
        assertEquals(result.getTitle(), candidate.getTitle());

    }

}
