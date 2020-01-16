package unit.com.controllers;

import com.controllers.VoyageController;

import com.entity.Bus;
import com.entity.Ticket;
import com.entity.Voyage;

import com.services.VoyageService;
import com.services.VoyageServiceImpl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.*;

public class VoyageControllerTest {

    private VoyageService voyageService = mock(VoyageServiceImpl.class);

    private VoyageController voyageController = new VoyageController();

    @Before
    public void start(){
        voyageController.setService(voyageService);
    }

    @Test
    public void addVoyage(){
        //Given
        Voyage voyage = new Voyage("OOP2");

        //When
        ResponseEntity<?> expected = new ResponseEntity<>(new Voyage("OOP2"), HttpStatus.OK);
        when(voyageService.addVoyage(any())).thenReturn(voyage);

        //Then
        Assert.assertEquals(expected, voyageController.addVoyage(voyage));
    }

    @Test
    public void changeBusOnVoyage() {
        //Given
        Bus bus = new Bus("FF0090OO", "Ferrari");
        bus.setId(1);

        Voyage voyage = new Voyage("YYY6");
        voyage.setId(1);
        voyage.setBus(bus);

        //When
        ResponseEntity<?> expected = new ResponseEntity<>(voyage, HttpStatus.OK);
        when(voyageService.changeBusOnVoyage(1,1)).thenReturn(voyage);

        //Then
        Assert.assertEquals(expected, voyageController.changeBusOnVoyage(1,1));
    }

    @Test
    public void addTicketOnVoyage() {
        //Given
        Voyage voyage = new Voyage("YYY6");

        Set<Ticket> tickets = new HashSet<>();
        tickets.add(new Ticket(1, 2000));

        Voyage expectedVoyage = new Voyage("YYY6");
        expectedVoyage.setId(voyage.getId());
        expectedVoyage.setTickets(tickets);

        //When
        when(voyageService.addTicketsOnVoyage(1, tickets)).thenReturn(expectedVoyage);

        //Then
        ResponseEntity<?> expected = new ResponseEntity<>(expectedVoyage, HttpStatus.OK);
        Assert.assertEquals(expected, voyageController.addTicketOnVoyage(1, new Ticket(1, 2000)));
    }

    @Test
    public void sellTicket() {
        //Given
        Voyage voyage = new Voyage("YYY6");
        voyage.setId(1);

        Ticket ticket = new Ticket(1, 2000);
        ticket.setId(1);
        ticket.setPaid(true);

        Set<Ticket> tickets = new HashSet<>();
        tickets.add(ticket);

        Voyage expectedVoyage = new Voyage("YYY6");
        expectedVoyage.setId(voyage.getId());
        expectedVoyage.setTickets(tickets);

        //When
        String expectedString = "";
        when(voyageService.sellTicket(1, 1)).thenReturn(expectedString);

        //Then
        ResponseEntity<?> expected = new ResponseEntity<>(expectedString, HttpStatus.OK);
        Assert.assertEquals(expected, voyageController.sellTicket(1, 1));
    }

    @Test
    public void findOneDriver(){
        //Given
        Voyage voyage = new Voyage("OOP2");
        voyage.setId(1);

        Voyage expectedVoyage = new Voyage("OOP2");
        expectedVoyage.setId(1);

        //When
        ResponseEntity<?> expected = new ResponseEntity<>(expectedVoyage, HttpStatus.OK);
        when(voyageService.findOne(1)).thenReturn(voyage);

        //Then
        Assert.assertEquals(expected, voyageController.findOneVoyage(1));
    }

    @Test
    public void findAllDrivers(){
        //Given
        Voyage voyage = new Voyage("OOP2");
        voyage.setId(1);

        Voyage expectedVoyage = new Voyage("OOP2");
        expectedVoyage.setId(1);

        //When
        ResponseEntity<?> expected = new ResponseEntity<>(Arrays.asList(expectedVoyage), HttpStatus.OK);
        when(voyageService.findAll()).thenReturn(Arrays.asList(voyage));

        //Then
        Assert.assertEquals(expected, voyageController.findAllVoyages());
    }
}