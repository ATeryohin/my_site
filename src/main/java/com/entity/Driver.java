package com.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

import java.io.Serializable;

@Entity
@Table(name = "drivers")
@Getter
public class Driver implements Serializable {

    @Id
    @SequenceGenerator(name = "sequence", sequenceName = "drivers_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    @Column(name = "id")
    private Integer id;

    @Column(name = "license", nullable = false)
    private String license;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    public Driver() {
    }

    @JsonCreator
    public Driver(@JsonProperty("license") String license,
                  @JsonProperty("name") String name,
                  @JsonProperty("surname") String surname) {
        this.license = license;
        this.name = name;
        this.surname = surname;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Driver driver = (Driver) o;

        if (license != null ? !license.equals(driver.license) : driver.license != null) return false;
        if (name != null ? !name.equals(driver.name) : driver.name != null) return false;
        return surname != null ? surname.equals(driver.surname) : driver.surname == null;
    }

    @Override
    public int hashCode() {
        int result = license != null ? license.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "id=" + id +
                ", license='" + license + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
}
