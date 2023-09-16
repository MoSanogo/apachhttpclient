package httpclient;



public class Post  {
    private String  id;

    public void setId(String  id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setUserId(String  userId) {
        this.userId = userId;
    }

    public String  getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String  getUserId() {
        return userId;
    }

    private  String title;
    private String body;
    private  String  userId;

    public Post(String id, String title, String body, String userId) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.userId = userId;
    }

    public Post() {
    }
}
