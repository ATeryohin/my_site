package com.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

import javax.persistence.*;

import java.io.Serializable;

import java.util.Set;

@Entity
@Table(name = "voyages")
@Getter
public class Voyage implements Serializable {

    @Id
    @SequenceGenerator(name = "sequence", sequenceName = "voyages_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    @Column(name = "id")
    private Integer id;

    @Column(name = "number", nullable = false)
    private String number;

    @Column(name = "info", nullable = false)
    private String info;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "bus_id")
    private Bus bus;

    @OneToMany (mappedBy = "voyage", fetch = FetchType.EAGER, targetEntity = Ticket.class)
    private Set<Ticket> tickets;

    public Voyage() {
    }

    @JsonCreator
    public Voyage(@JsonProperty("number")String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    public Set<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Set<Ticket> tickets) {
        this.tickets = tickets;
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

        Voyage voyage = (Voyage) o;

        if (number != null ? !number.equals(voyage.number) : voyage.number != null) return false;
        if (bus != null ? !bus.equals(voyage.bus) : voyage.bus != null) return false;
        return tickets != null ? tickets.equals(voyage.tickets) : voyage.tickets == null;
    }

    @Override
    public int hashCode() {
        int result = number != null ? number.hashCode() : 0;
        result = 31 * result + (bus != null ? bus.hashCode() : 0);
        result = 31 * result + (tickets != null ? tickets.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Voyage{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", bus=" + bus +
                ", tickets=" + tickets +
                '}';
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
