package org.example.benchmark;

import java.util.Objects;

public class TestModel implements Comparable<TestModel> {

    private String name;
    private String lastName;
    private Integer pesel;
    public TestModel() {
    }
    public TestModel(String name, String lastName, Integer pesel) {
        this.name = name;
        this.lastName = lastName;
        this.pesel = pesel;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public Integer getPesel() {
        return pesel;
    }
    public void setPesel(Integer pesel) {
        this.pesel = pesel;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestModel testModel = (TestModel) o;
        if(lastName == null){
            if(testModel.lastName != null)
                return false;
        } else if(!lastName.equals(testModel.lastName))
            return false;
        if(name == null){
            if(testModel.name != null){
                return false;
            }
        } else if (!name.equals(testModel.name))
            return false;
        if(pesel == null){
            if(testModel.pesel != null){
                return false;
            }
        } else if (!pesel.equals(testModel.pesel))
            return false;
        return true;
    }
    @Override
    public int hashCode() {
        return Objects.hash(name, lastName, pesel);
    }
    @Override
    public String toString() {
        return "TestModel{" +
                "name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", pesel='" + pesel + '\'' +
                '}';
    }
    @Override
    public int compareTo(TestModel o) {
        if (o.getPesel().equals(this.pesel))
            return 1;
        return 0;
    }
}
