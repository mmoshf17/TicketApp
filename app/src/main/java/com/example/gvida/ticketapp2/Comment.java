package com.example.gvida.ticketapp2;

/**
 * Created by Muheb.moshfiq on 5/12/2018.
 */

public class Comment {

    private String userId;
    private String comment;
    private String dateCreated;


    public Comment(String userId, String comment){
        // public Tickets(String name){


        this.userId = userId;
        this.comment = comment;


    }

    public Comment(){
    }


    public String toString() {

        return userId + ":" + "\n" + comment;
    }

}




