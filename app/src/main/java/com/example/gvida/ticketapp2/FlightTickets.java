package com.example.gvida.ticketapp2;

import java.io.Serializable;

/**
 * Created by Muheb.moshfiq on 5/21/2018.
 */

public class FlightTickets implements Serializable {

    //private  String userId;
    private int ticketId;
    private String category;
    private String startingDate;
    private String user;
    private String email;
    private String name;
    private String price;
    private String description;
    private String fromFlights;
    private String toFlights;
    //private String events;
    private String isAuction;

    public FlightTickets(int ticketId, String user, String category, String startingDate, String email, String name, String price, String description, String fromFlights, String toFlights){

        this.ticketId = ticketId;
        this.category = category;
        this.startingDate = startingDate;
        this.user = user;
        this.email = email;
        this.name = name;
        this.price = price;
        this.description = description;
        this.fromFlights = fromFlights;
        this.toFlights = toFlights;


    }

    public FlightTickets(){
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

    public String getFromFlights() {
        return fromFlights;
    }

    public String getToFlights() {
        return toFlights;
    }





    public String toString() {

        return "Flight no: " + name  + "\n" + "From: " + fromFlights + "\n" + "To: " + toFlights + "\n" + "Date: " + startingDate;


    } }