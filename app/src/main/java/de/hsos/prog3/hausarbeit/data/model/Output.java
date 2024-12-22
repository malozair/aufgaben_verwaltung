package de.hsos.prog3.hausarbeit.data.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "output_table")
public class Output {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "group_id")
    private int groupId;

    @NonNull
    @ColumnInfo(name = "person_id")
    private int personId;

    @NonNull
    @ColumnInfo(name = "output_name")
    private String outputName;

    @NonNull
    @ColumnInfo(name = "amount")
    private double amount;

    @NonNull
    @ColumnInfo(name = "date")
    private String date;

    // Constructor
    public Output(int groupId, int personId, @NonNull String outputName, double amount, @NonNull String date) {
        this.groupId = groupId;
        this.personId = personId;
        this.outputName = outputName;
        this.amount = amount;
        this.date = date;
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @NonNull
    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(@NonNull int groupId) {
        this.groupId = groupId;
    }

    @NonNull
    public int getPersonId() {
        return personId;
    }

    public void setPersonId(@NonNull int personId) {
        this.personId = personId;
    }

    @NonNull
    public String getOutputName() {
        return outputName;
    }


    public double getAmount() {
        return amount;
    }

    @NonNull
    public String getDate() {
        return date;
    }


}
