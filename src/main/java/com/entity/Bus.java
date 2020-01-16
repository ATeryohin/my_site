package com.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

import javax.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "buses")
@Getter
public class Bus implements Serializable {

    @Id
    @SequenceGenerator(name = "sequence", sequenceName = "buses_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    @Column(name = "id")
    private Integer id;

    @Column(name = "number", nullable = false)
    private String number;

    @Column(name = "model", nullable = false)
    private String model;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "driver_id")
    private Driver driver;

    public Bus() {
    }

    @JsonCreator
    public Bus(@JsonProperty("number") String number,
               @JsonProperty("model") String model) {
        this.number = number;
        this.model = model;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
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

        Bus bus = (Bus) o;

        if (number != null ? !number.equals(bus.number) : bus.number != null) return false;
        if (model != null ? !model.equals(bus.model) : bus.model != null) return false;
        return driver != null ? driver.equals(bus.driver) : bus.driver == null;
    }

    @Override
    public int hashCode() {
        int result = number != null ? number.hashCode() : 0;
        result = 31 * result + (model != null ? model.hashCode() : 0);
        result = 31 * result + (driver != null ? driver.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Bus{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", model='" + model + '\'' +
                ", driver=" + driver +
                '}';
    }
}
