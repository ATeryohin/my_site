package integration.com.controllers;

import com.controllers.VoyageController;

import com.entity.*;

import com.services.*;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import org.springframework.context.annotation.ComponentScan;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@EnableAutoConfiguration
@EntityScan(basePackages = "com.entity")
@EnableJpaRepositories("com.repositories")
@ComponentScan(basePackages = "com.services, com.controllers")
@Transactional
@TestPropertySource("classpath:application-test.properties")
public class VoyageControllerIT {

    private VoyageController voyageController;
    private BusService busService;
    private VoyageService voyageService;
    private TicketService ticketService;

    @Test
    public void addVoyage(){
        //Given
        Voyage voyage = new Voyage("123R");
        
        //When
        ResponseEntity<?> actual = voyageController.addVoyage(voyage);
        
        //Then
        ResponseEntity<?> expected = new ResponseEntity<>(new Voyage("123R"), HttpStatus.OK);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addVoyageError(){
        //Given
        Voyage voyage = new Voyage("123R");

        //When
        voyageController.addVoyage(voyage);
        voyageController.addVoyage(voyage);
        
        //Then
        Assert.fail();
    }

    @Test
    public void changeBusOnVoyage() {
        //Given
        Bus bus = busService.addBus(new Bus("FF0090OO", "Ferrari"));
        Voyage voyage = voyageService.addVoyage(new Voyage("YYY6"));

        //When
        ResponseEntity<?> actual = voyageController.changeBusOnVoyage(voyage.getId(), bus.getId());

        //Then
        Voyage voyage1 = new Voyage("YYY6");
        voyage1.setBus(new Bus("FF0090OO", "Ferrari"));
        ResponseEntity<?> expected = new ResponseEntity<>(voyage1, HttpStatus.OK);

        Assert.assertEquals(expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void changeBusOnVoyageError() {
        //Given
        Bus bus = busService.addBus(new Bus("FF0090OO", "Ferrari"));
        Voyage voyage = voyageService.addVoyage(new Voyage("YYY6"));

        //When
        voyageController.changeBusOnVoyage(voyage.getId(), bus.getId());
        voyageController.changeBusOnVoyage(voyage.getId(), bus.getId());

        //Then
        Assert.fail();
    }

    @Test
    public void addTicketOnVoyage() {
        //Given
        Voyage voyage = voyageService.addVoyage(new Voyage("YYY6"));

        //When
        ResponseEntity<?> actual = voyageController.addTicketOnVoyage(voyage.getId(), new Ticket(1, 2000));

        //Then
        Set<Ticket> tickets = new HashSet<>();
        tickets.add(new Ticket(1, 2000));

        Voyage expectedVoyage = new Voyage("YYY6");
        expectedVoyage.setId(voyage.getId());
        expectedVoyage.setTickets(tickets);

        ResponseEntity<?> expected = new ResponseEntity<>(expectedVoyage, HttpStatus.OK);

        Assert.assertEquals(expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addTicketOnVoyageError() {
        //Given
        Voyage voyage = voyageService.addVoyage(new Voyage("YYY6"));

        //When
        voyageController.addTicketOnVoyage(voyage.getId(), new Ticket(1, 2000));
        voyageController.addTicketOnVoyage(voyage.getId(), new Ticket(1, 2000));

        //Then
        Assert.fail();
    }

    @Test
    public void addTicketsOnVoyage() {
        //Given
        Voyage voyage = voyageService.addVoyage(new Voyage("YYY6"));
        ArrayList<Ticket> tickets = new ArrayList<>();
        for (int i = 1; i < 5; i++){
            tickets.add(new Ticket(i, 2000));
        }

        //When
        ResponseEntity<?> actual = voyageController.addTicketsOnVoyage(voyage.getId(), tickets);

        //Then
        Voyage expectedVoyage = new Voyage("YYY6");
        expectedVoyage.setId(voyage.getId());
        expectedVoyage.setTickets(new HashSet<>(tickets));

        ResponseEntity<?> expected = new ResponseEntity<>(expectedVoyage, HttpStatus.OK);

        Assert.assertEquals(expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addTicketsOnVoyageError() {
        //Given
        Voyage voyage = voyageService.addVoyage(new Voyage("YYY6"));
        ArrayList<Ticket> tickets = new ArrayList<>();
        for (int i = 1; i < 5; i++){
            tickets.add(new Ticket(i, 2000));
        }

        //When
        voyageController.addTicketsOnVoyage(voyage.getId(), tickets);
        voyageController.addTicketsOnVoyage(voyage.getId(), tickets);

        //Then
        Assert.fail();
    }

    @Test
    public void sellTicket() {
        //Given
        Voyage voyage = voyageService.addVoyage(new Voyage("YYY6"));
        ArrayList<Ticket> tickets = new ArrayList<>();
        for (int i = 1; i < 5; i++){
            tickets.add(new Ticket(i, 2000));
        }

        voyageController.addTicketsOnVoyage(voyage.getId(), tickets);

        Voyage dbVoyage  = voyageService.findOne(voyage.getId());

        Integer ticketId = dbVoyage.getTickets().iterator().next().getId();

        //When
        ResponseEntity<?> actual = voyageController.sellTicket(voyage.getId(), ticketId);

        //Then

        String lineSeparator  = System.lineSeparator();
        String expectedString =
                "Voyage{" + lineSeparator +
                        "   number = YYY6" + lineSeparator +
                        "   busNumber = null" + lineSeparator +
                        "   busModel = null" + lineSeparator +
                        "   driverName = null" + lineSeparator +
                        "   driverSurname = null" + lineSeparator +
                        "   ticketPrice = 2000" + lineSeparator +
                        "   ticketPlace = 1" + lineSeparator +
                        "   isPaid = true}";

        ResponseEntity<?> expected = new ResponseEntity<>(expectedString, HttpStatus.OK);
        Assert.assertEquals(expected, actual);
    }

    @Autowired
    public void setVoyageController(VoyageController voyageController) {
        this.voyageController = voyageController;
    }

    @Autowired
    public void setBusService(BusService busService) {
        this.busService = busService;
    }

    @Autowired
    public void setVoyageService(VoyageService voyageService) {
        this.voyageService = voyageService;
    }

    @Autowired
    public void setTicketService(TicketService ticketService) {
        this.ticketService = ticketService;
    }
}