package de.hsos.prog3.hausarbeit.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import de.hsos.prog3.hausarbeit.data.model.Output;
import de.hsos.prog3.hausarbeit.data.model.OutputWithBuyerName;

@Dao
public interface OutputDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Output output);

    @Query("DELETE FROM output_table")
    void deleteAll();

    @Delete
    void deleteOutput(Output output);

    @Update
    void update(Output output);

    @Query("SELECT * from output_table WHERE group_id = :groupId")
    LiveData<List<Output>> getOutputsForGroup(int groupId);

    @Query("SELECT output_table.id AS id, output_table.group_id AS groupId, output_table.person_id AS personId, output_table.output_name AS outputName, output_table.amount AS amount, output_table.date AS date, person_table.name AS buyerName " +
            "FROM output_table " +
            "INNER JOIN person_table ON output_table.person_id = person_table.id " +
            "WHERE output_table.group_id = :groupId " +
            "ORDER BY date DESC")
    LiveData<List<OutputWithBuyerName>>getAllOutputsWithBuyerNames(int groupId);



}
