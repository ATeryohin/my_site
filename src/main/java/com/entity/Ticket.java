package com.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

import javax.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "tickets")
@Getter
public class Ticket implements Serializable {

    @Id
    @SequenceGenerator(name = "sequence", sequenceName = "tickets_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    @Column(name = "id")
    private Integer id;

    @Column(name = "place", nullable = false)
    private Integer place;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "is_paid")
    private boolean isPaid;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "voyage_id")
    @JsonIgnore
    private Voyage voyage;

    public Ticket() {
    }

    @JsonCreator
    public Ticket(@JsonProperty("place")Integer place,
                  @JsonProperty("price")Integer price) {
        this.place = place;
        this.price = price;
        this.isPaid = false;
    }

    public Integer getPlace() {
        return place;
    }

    public void setPlace(Integer place) {
        this.place = place;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Voyage getVoyage() {
        return voyage;
    }

    public void setVoyage(Voyage voyage) {
        this.voyage = voyage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ticket ticket = (Ticket) o;

        if (isPaid != ticket.isPaid) return false;
        if (place != null ? !place.equals(ticket.place) : ticket.place != null) return false;
        return price != null ? price.equals(ticket.price) : ticket.price == null;
    }

    @Override
    public int hashCode() {
        int result = place != null ? place.hashCode() : 0;
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (isPaid ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", place=" + place +
                ", price=" + price +
                ", isPaid=" + isPaid +
                '}';
    }
}
