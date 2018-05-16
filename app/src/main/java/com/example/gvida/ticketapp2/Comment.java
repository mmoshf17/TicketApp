package com.example.gvida.ticketapp2;

/**
 * Created by Muheb.moshfiq on 5/12/2018.
 */

public class Comment {

    //private  String userId;
    private String userId;
    private String comment;
    private String dateCreated;
    //private String ticketId;

    public Comment(String userId, String comment){
        // public Tickets(String name){


        this.userId = userId;
        this.comment = comment;
        //this.dateCreated = dateCreated;
        //this.ticketId = ticketId;

    }

    public Comment(){
    }


    public String toString() {
        //return "Name: " + name + "\n" + "Price: " + price + "\n" + "Description: " + description;
        return userId + ":" + "\n" + comment;
    }

}




