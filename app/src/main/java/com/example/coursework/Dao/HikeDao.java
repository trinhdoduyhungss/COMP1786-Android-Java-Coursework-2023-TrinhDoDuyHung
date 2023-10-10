package com.example.coursework.Dao;
import java.util.List;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.coursework.Forms.HikeForm;
import com.example.coursework.Models.Hike;
@Dao
public interface HikeDao {

    @Insert
    void insertHike(Hike hike);

    @Query("SELECT * FROM details")
    List<Hike> getAllHikes();

    @Query("DELETE FROM details WHERE hike_id=:id")
    void deleteHike(long id);

    @Update
    void updateHike(Hike hike);

    @Query("DELETE FROM details")
    void deleteAllHikes();

    // Search for a hike by name or date or location
    @Query("SELECT * FROM details WHERE name LIKE '%' || :search || '%' OR doh LIKE '%' || :search || '%' OR location LIKE '%' || :search || '%'")
    List<Hike> searchHikes(String search);
}
