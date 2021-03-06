package com.example.gvida.ticketapp2;

import java.io.Serializable;
import java.text.DateFormat;

/**
 * Created by Muheb.moshfiq on 5/5/2018.
 */

public class Tickets implements Serializable {

    private int ticketId;
    private String category;
    private String startingDate;
    private String user;
    private String email;
    private String name;
    private String price;
    private String description;



    public Tickets(int ticketId, String user, String category, String startingDate, String email, String name, String price, String description){


        this.ticketId = ticketId;
        this.category = category;
        this.startingDate = startingDate;
        this.user = user;
        this.email = email;
        this.name = name;
        this.price = price;
        this.description = description;


    }

public Tickets(){
}

    public String getStartingDate()
    {
        return startingDate;
    }
    public String getCategory()
    {
        return category;
    }
    public int getTicketId() {
        return ticketId;
    }

    public String getUser() {
        return user;
    }

    public String getEmail() {
        return email;
    }

    public String getName() { return name;
    }


    public String getPrice() { return price;
    }


    public String getDescription() {
        return description;
    }





    public String toString() {

        return name + "\n" + startingDate + "\n" + category;
    }

}


