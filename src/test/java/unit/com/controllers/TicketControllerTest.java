package unit.com.controllers;

import com.controllers.TicketController;

import com.entity.Ticket;

import com.services.TicketService;
import com.services.TicketServiceImpl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

import static org.mockito.Mockito.*;

public class TicketControllerTest {

    private TicketService ticketService = mock(TicketServiceImpl.class);

    private TicketController ticketController = new TicketController();

    @Before
    public void start(){
        ticketController.setService(ticketService);
    }

    @Test
    public void findOneTicket(){
        //Given
        Ticket ticket = new Ticket(1, 39);
        ticket.setId(1);

        Ticket expectedTicket = new Ticket(1, 39);
        expectedTicket.setId(1);

        //When
        ResponseEntity<?> expected = new ResponseEntity<>(expectedTicket, HttpStatus.OK);
        when(ticketService.findOne(1)).thenReturn(ticket);

        //Then
        Assert.assertEquals(expected, ticketController.findOneTicket(1));
    }

    @Test
    public void findAllTickets(){
        //Given
        Ticket ticket = new Ticket(1, 39);
        ticket.setId(1);

        Ticket expectedTicket = new Ticket(1, 39);
        expectedTicket.setId(1);

        //When
        ResponseEntity<?> expected = new ResponseEntity<>(Arrays.asList(expectedTicket), HttpStatus.OK);
        when(ticketService.findAll()).thenReturn(Arrays.asList(ticket));

        //Then
        Assert.assertEquals(expected, ticketController.findAllTickets());
    }
}