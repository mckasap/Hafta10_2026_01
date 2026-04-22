package com.example.hafta10_2026_01;

import org.json.JSONArray;

import java.util.Date;

public class HackerNewsItem {
    private  int id;	//The item's unique id.
    private boolean deleted; //	true if the item is deleted.
    private String type;  //The type of item. One of "job", "story", "comment", "poll", or "pollopt".
    private String by; // The username of the item's author.
    private int time; //Creation date of the item, in Unix Time.
    private String text; //	The comment, story or poll text. HTML.
    private boolean dead; //	true if the item is dead.
    private int parent; //	The comment's parent: either another comment or the relevant story.
    private int  poll; //	The pollopt's associated poll.
    private JSONArray kids;//	The ids of the item's comments, in ranked display order.
    private String url; //url	The URL of the story.
    private int score; //score	The story's score, or the votes for a pollopt.
    private String title;//	The title of the story, poll or job. HTML.
    private JSONArray parts; //parts	A list of related pollopts, in display order.

    public HackerNewsItem(String title, String url, int id, String type) {
        this.title = title;
        this.url = url;
        this.id = id;
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    private int descendants; //	In the case of stories or polls, the total comment count.
}
