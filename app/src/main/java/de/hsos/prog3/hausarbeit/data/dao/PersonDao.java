package de.hsos.prog3.hausarbeit.data.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import de.hsos.prog3.hausarbeit.data.model.Person;

@Dao
public interface PersonDao {
    @Insert
    long insertPerson(Person person);

    @Update
    void updatePerson(Person person);

    @Delete
    void deletePerson(Person person);

    @Query("SELECT * FROM person_table WHERE groupId = :groupId")
    LiveData<List<Person>> getGroupMembers(int groupId);

    @Query("SELECT * FROM person_table WHERE id = :personId LIMIT 1")
    LiveData<Person> getPersonById(int personId);


}
