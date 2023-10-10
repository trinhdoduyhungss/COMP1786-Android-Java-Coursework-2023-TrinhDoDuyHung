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
}
