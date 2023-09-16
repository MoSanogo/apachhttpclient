package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import httpclient.Client;
import httpclient.Post;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {

         Client cl=new Client();
            var result=cl.fetch("https://jsonplaceholder.typicode.com/posts",Post.class)
                .join();
        System.out.println(result);
        var re=cl.fetchOne("https://jsonplaceholder.typicode.com/posts/1", Post.class)
                .join();
        System.out.println(re.getTitle());
        var post =new Post();
        post.setBody("Modibo");
        post.setUserId("2");
        post.setTitle("Sanogo");
      Post p= cl.Post("https://jsonplaceholder.typicode.com/posts",post)
               .join();
        System.out.println(p.getId());
        var test=cl.fetchOne("https://jsonplaceholder.typicode.com/posts/"+ p.getId(), Post.class)
                .join();
        System.out.println(test.getId());
        // print logback's internal status



    }

}