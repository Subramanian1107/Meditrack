package main.java.com.airtribe.meditrack.entity;

import main.java.com.airtribe.meditrack.util.Validator;

public abstract class Person extends MedicalEntity {

    protected String name;
    protected int age;

    public Person(int id, String name, int age) {
        super(id);
        Validator.validateName(name);
        Validator.validateAge(age);
        this.name = name;
        this.age = age;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getAge() { return age; }

    public void setName(String name) {
        Validator.validateName(name);
        this.name = name;
    }
    public void setAge(int age) {
        Validator.validateAge(age);
        this.age = age;
    }

    public abstract void display();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
