package com.example.coursework.Models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "observations", foreignKeys = @ForeignKey(entity = Hike.class, parentColumns = "hike_id", childColumns = "hikeId", onDelete = ForeignKey.CASCADE))
public class Observation {
    @PrimaryKey(autoGenerate = true)
    public long observation_id;
    public String name;
    public String tob;
    public String description;
    public long hikeId;
}