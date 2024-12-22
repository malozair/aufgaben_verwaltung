package de.hsos.prog3.hausarbeit.data.model;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "person_table",
        foreignKeys = @ForeignKey(entity = Group.class,
                parentColumns = "id",
                childColumns = "groupId",
                onDelete = ForeignKey.CASCADE))
public class Person {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(index = true)
    public int groupId;

    @NonNull
    public String name;

    @NonNull
    public String email;

    private double balance;


    // Constructor
    public Person(@NonNull String name, @NonNull String email, int groupId) {
        this.name = name;
        this.email = email;
        this.groupId = groupId;
    }



    // Getters and Setters
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getGroupId() {
        return this.groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return this.getName() ;
    }
}
