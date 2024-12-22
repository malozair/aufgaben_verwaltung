package de.hsos.prog3.hausarbeit.data.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import de.hsos.prog3.hausarbeit.data.model.Group;
import de.hsos.prog3.hausarbeit.data.model.GroupWithPersons;

@Dao
public interface GroupDao {
    // Use group IDs to create Persons
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertGroup(Group group);


    @Update
    void updateGroup(Group group);

    @Delete
    void deleteGroup(Group group);

    @Query("SELECT * FROM `Group`")
    LiveData<List<Group>> getAllGroups();

    @Transaction
    @Query("SELECT * FROM `Group` INNER JOIN person_table ON `Group`.id = person_table.groupId")
    LiveData<List<GroupWithPersons>>getGroupsWithPersons();



}
