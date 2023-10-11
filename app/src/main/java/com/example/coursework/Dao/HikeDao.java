package com.example.coursework.Dao;
import java.util.List;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.coursework.Models.Hike;
import com.example.coursework.Models.Observation;

@Dao
public interface HikeDao {

    @Insert
    void insertHike(Hike hike);

    @Query("SELECT * FROM hikes")
    List<Hike> getAllHikes();

    @Query("DELETE FROM hikes WHERE hike_id=:id")
    void deleteHike(long id);

    @Update
    void updateHike(Hike hike);

    @Query("DELETE FROM hikes")
    void deleteAllHikes();

    // Search for a hike by name or date or location
    @Query("SELECT * FROM hikes WHERE name LIKE '%' || :search || '%' OR doh LIKE '%' || :search || '%' OR location LIKE '%' || :search || '%'")
    List<Hike> searchHikes(String search);

    // -------- Observations --------

    @Query("INSERT INTO observations (name, tob, description, hikeId) VALUES (:name, :tob, :description, :hikeId)")
    void insertObservation(String name, String tob, String description, long hikeId);

    @Query("SELECT * FROM observations WHERE hikeId = :hikeId")
    List<Observation> getObservationsForHike(long hikeId);

    @Query("DELETE FROM observations WHERE observation_id=:id")
    void deleteObservation(long id);

    @Query("UPDATE observations SET name=:name, tob=:tob, description=:description WHERE observation_id=:id")
    void updateObservation(long id, String name, String tob, String description);
}
