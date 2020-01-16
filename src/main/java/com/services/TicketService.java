package com.services;

import com.entity.Ticket;

import java.util.List;

public interface TicketService {

    Ticket findOne(Integer id);
    List<Ticket> findAll();

}
