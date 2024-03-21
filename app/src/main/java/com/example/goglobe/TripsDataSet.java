package com.example.goglobe;

import java.util.Date;

public class TripsDataSet {
    public int tripID;
    public String tripName;
    public String tripLocation;
    public Date startDate;
    public Date endDate;
    public int userID;

    public TripsDataSet(int tripID, String tripName, String tripLocation, Date startDate, Date endDate, int userID) {
        this.tripID = tripID;
        this.tripName = tripName;
        this.tripLocation = tripLocation;
        this.startDate = startDate;
        this.endDate = endDate;
        this.userID = userID;
    }

    public int getTripID() { return tripID; }
    public String getTripName() { return tripName; }
    public String getTripLocation() { return tripLocation; }
    public Date getStartDate() { return startDate; }
    public Date getEndDate() { return endDate; }
    public int getUserID() { return userID; }

}
