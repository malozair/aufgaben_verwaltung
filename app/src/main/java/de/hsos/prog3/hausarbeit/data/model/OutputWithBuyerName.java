package de.hsos.prog3.hausarbeit.data.model;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

public class OutputWithBuyerName {


    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "groupId")
    private int groupId;

    @ColumnInfo(name = "personId")
    private int personId;

    @ColumnInfo(name = "outputName")
    private String outputName;

    @ColumnInfo(name = "amount")
    private double amount;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "buyerName")
    private String buyerName;


    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getOutputName() {
        return outputName;
    }

    public void setOutputName(String outputName) {
        this.outputName = outputName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

