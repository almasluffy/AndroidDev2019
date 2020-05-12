package com.example.recyclerview;

public class PostItem {
    private String postName;
    private String description;

    public PostItem(){

    }
    public PostItem(String postName, String description){
        this.postName = postName;
        this.description =description;
    }

    public void setPostName(String postName){
        this.postName = postName;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getPostName(){
        return this.postName;
    }

    public String getDescription(){
        return this.description;
    }
}
