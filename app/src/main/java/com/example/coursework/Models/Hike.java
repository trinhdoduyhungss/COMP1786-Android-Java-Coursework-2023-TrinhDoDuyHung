package com.example.coursework.Models;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "details")
public class Hike {
    @PrimaryKey(autoGenerate = true)
    public long hike_id;
    public String name;
    public String location;
    public String doh;
    public Boolean hasParking;
    public int Loh;
    public String difficulty;
    public String description;
}
