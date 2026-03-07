package main.java.com.airtribe.meditrack.entity;

public abstract class MedicalEntity {

    protected int id;

    protected MedicalEntity(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}

