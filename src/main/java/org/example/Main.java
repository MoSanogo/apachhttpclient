package org.example;

import httpclient.Client;
import httpclient.Post;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

         Client cl=new Client();
            var result=cl.getAsync("https://jsonplaceholder.typicode.com/posts",Post.class)
                .join();
        System.out.println(result);
        var re=cl.getOneAsync("https://jsonplaceholder.typicode.com/posts/1", Post.class)
                .join();
        System.out.println(re.getTitle());
        var post =new Post();
        post.setBody("Modibo");
        post.setUserId("2");
        post.setTitle("Sanogo");
      Post p= cl.PostAsync("https://jsonplaceholder.typicode.com/posts",post)
               .join();
        System.out.println(p.getId());
        var test=cl.getOneAsync("https://jsonplaceholder.typicode.com/posts/"+ p.getId(), Post.class)
                .join();
        System.out.println(test.getId());
        // print logback's internal status
         var rs= cl.DeleteAsync("https://jsonplaceholder.typicode.com/posts/1")
                  .join();
        System.out.println(rs);


    }

}