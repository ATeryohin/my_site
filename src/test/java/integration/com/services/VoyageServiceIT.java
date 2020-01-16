package integration.com.services;

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
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@EnableAutoConfiguration
@EntityScan(basePackages = "com.entity")
@EnableJpaRepositories("com.repositories")
@ComponentScan(basePackages = "com.services")
@Transactional
@TestPropertySource("classpath:application-test.properties")
public class VoyageServiceIT {

    private VoyageService voyageService;
    private DriverService driverService;
    private BusService busService;
    private TicketService ticketService;

    @Test
    public void addVoyage() {
        //Given
        Voyage voyage = new Voyage("23334YY");

        //When
        Voyage result = voyageService.addVoyage(voyage);

        //Then
        Assert.assertNotNull(result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addVoyageWithTheSameNumber() {
        //Given
        Voyage voyage = new Voyage("GG8888PP");

        //When
        voyageService.addVoyage(voyage);
        voyageService.addVoyage(voyage);

        //Then
        Assert.fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void addVoyageNull() {
        //When
        voyageService.addVoyage(null);

        //Then
        Assert.fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void addVoyageWithTheSameId() {
        //Given
        Voyage voyage = new Voyage("GG8888PP");
        Integer voyageId = voyageService.addVoyage(voyage).getId();

        Voyage voyage1 = new Voyage("YY89898WW");
        voyage1.setId(voyageId);

        //When
        voyageService.addVoyage(voyage1);

        //Then
        Assert.fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void addVoyageWithEmptyFields() {
        //When
        voyageService.addVoyage(new Voyage());

        //Then
        Assert.fail();

    }

    @Test
    public void addVoyageAndFindAll() {
        //Given
        Voyage voyage = new Voyage("GG8888PP");
        Voyage voyage1 = new Voyage("JJ7767RR");
        Voyage voyage2 = new Voyage("KK9999PP");

        Integer voyageId = voyageService.addVoyage(voyage).getId();
        Integer voyageId1 = voyageService.addVoyage(voyage1).getId();
        Integer voyageId2 = voyageService.addVoyage(voyage2).getId();

        ///When
        List<Voyage> voyages = voyageService.findAll();

        //Then
        Assert.assertEquals("[Voyage{id=" + voyageId + ", number='GG8888PP', bus=null, tickets=null}, " +
                "Voyage{id=" + voyageId1 + ", number='JJ7767RR', bus=null, tickets=null}, " +
                "Voyage{id=" + voyageId2 + ", number='KK9999PP', bus=null, tickets=null}]", voyages.toString());
    }

    @Test
    public void findAllVoyagesInEmptyTable() {
        //When
        List<Voyage> voyages = voyageService.findAll();

        //Then
        Assert.assertEquals("[]", voyages.toString());
    }

    @Test
    public void changeBusOnVoyage() {
        //Given
        Voyage voyage = new Voyage("GG8888PP");
        Voyage voyage1 = new Voyage("JJ7767RR");
        Bus bus = new Bus("EE7878II", "Joi");
        Bus bus1 = new Bus("PP7777UU", "Inna");
        Bus bus2 = new Bus("WW6544GG", "Monty");

        Integer voyageId = voyageService.addVoyage(voyage).getId();
        Integer voyageId1 = voyageService.addVoyage(voyage1).getId();
        Integer busId = busService.addBus(bus).getId();
        Integer busId1 = busService.addBus(bus1).getId();
        Integer busId2 = busService.addBus(bus2).getId();

        //When
        try {
            voyageService.changeBusOnVoyage(voyageId, busId);
            voyageService.changeBusOnVoyage(voyageId1, busId1);
            voyageService.changeBusOnVoyage(voyageId, busId1);

            //Then
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Bus " + busId1 + " can't be on different Voyages", e.getMessage());

            //When
            Voyage dbVoyage = voyageService.changeBusOnVoyage(voyageId, busId2);

            //Then
            Assert.assertEquals(new Bus("WW6544GG", "Monty"), dbVoyage.getBus());
        }
    }

    @Test
    public void changeBusWithDriverOnVoyage() {
        //Given
        Voyage voyage = new Voyage("GG8888PP");
        Driver driver = new Driver("II6636UU", "Dima", "Polter");
        Bus bus = new Bus("EE7878II", "Joi");
        Bus bus1 = new Bus("PP7777UU", "Inna");

        Integer voyageId = voyageService.addVoyage(voyage).getId();
        Integer driverId = driverService.addDriver(driver).getId();
        Integer busId = busService.addBus(bus).getId();

        busService.changeDriverOnBus(busId, driverId);
        Integer busId1 = busService.addBus(bus1).getId();

        //When
        voyageService.changeBusOnVoyage(voyageId, busId);
        voyageService.changeBusOnVoyage(voyageId, busId1);

        //Then
        Bus busResult = busService.findOne(busId);
        Bus busResult2 = busService.findOne(busId1);

        Assert.assertEquals(busResult.getDriver(), null);
        Assert.assertEquals(busResult2.getDriver(), driver);
    }

    @Test(expected = IllegalArgumentException.class)
    public void changeBusOnVoyageImportNull() {
        //When
        voyageService.changeBusOnVoyage(null, null);

        //Then
        Assert.fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void changeBusOnVoyageImportNotCorrectId() {
        //When
        voyageService.changeBusOnVoyage(1, 0);

        //Then
        Assert.fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void changeBusOnVoyageImportNotExistId() {
        //When
        voyageService.changeBusOnVoyage(1, 1);

        //Then
        Assert.fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void addTicketsOnVoyageNullInput() {
        //When
        voyageService.addTicketsOnVoyage(null, null);

        //Then
        Assert.fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void addTicketsOnVoyageImportNotCorrectId() {
        //When
        voyageService.addTicketsOnVoyage(-1, new HashSet<>());

        //Then
        Assert.fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void addTicketsOnVoyageImportNotExistId() {
        //When
        voyageService.addTicketsOnVoyage(1, new HashSet<>());

        //Then
        Assert.fail();
    }

    @Test
    public void findAllTicketsInEmptyTable() {
        //When
        List<Ticket> tickets = ticketService.findAll();

        //Then
        Assert.assertEquals("[]", tickets.toString());
    }

    @Test
    public void addTicketsOnVoyage() {
        //Given
        Set<Ticket> tickets = new HashSet<>();
        for (int i = 1; i < 5; i++) {
            tickets.add(new Ticket(i, 2000));
        }

        Voyage voyage = new Voyage("JJD009");
        Integer bdVoyageID = voyageService.addVoyage(voyage).getId();

        //When
        Voyage dbVoyage = voyageService.addTicketsOnVoyage(bdVoyageID, tickets);

        //Then
        Assert.assertEquals(4, dbVoyage.getTickets().size());
    }

    @Test
    public void addTicketsOnVoyageAddMore() {
        //Given
        Set<Ticket> tickets = new HashSet<>();
        for (int i = 1; i < 5; i++) {
            tickets.add(new Ticket(i, 2000));
        }

        Set<Ticket> tickets1 = new HashSet<>();
        for (int i = 5; i < 10; i++) {
            tickets1.add(new Ticket(i, 2000));
        }

        Voyage voyage = new Voyage("JJD009");
        Integer bdVoyageID = voyageService.addVoyage(voyage).getId();

        //When
        voyageService.addTicketsOnVoyage(bdVoyageID, tickets);
        Voyage dbVoyage1 = voyageService.addTicketsOnVoyage(bdVoyageID, tickets1);

        //Then
        Assert.assertEquals(9, dbVoyage1.getTickets().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void addTicketsOnVoyageAddMoreWithSamePlace() {
        //Given
        Set<Ticket> tickets = new HashSet<>();
        for (int i = 1; i < 5; i++) {
            tickets.add(new Ticket(i, 2000));
        }

        Voyage voyage = new Voyage("JJD009");
        Integer bdVoyageID = voyageService.addVoyage(voyage).getId();

        //When
        voyageService.addTicketsOnVoyage(bdVoyageID, tickets);
        voyageService.addTicketsOnVoyage(bdVoyageID, tickets);

        //Then
        Assert.fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void addTicketsOnVoyageNoTickets() {
        //Given
        Voyage voyage = new Voyage("JJD009");
        Integer bdVoyageID = voyageService.addVoyage(voyage).getId();

        //When
        voyageService.addTicketsOnVoyage(bdVoyageID, new HashSet<>());

        //Then
        Assert.fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void sellTicketImportNull() {
        //When
        voyageService.sellTicket(null, null);

        //Then
        Assert.fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void sellTicketImportNotCorrectId() {
        //When
        voyageService.sellTicket(1, 0);

        //Then
        Assert.fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void sellTicketImportNotExistId() {
        //When
        voyageService.sellTicket(1, 1);

        //Then
        Assert.fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void sellTicketImportSoldId() {
        //Given
        Set<Ticket> tickets = new HashSet<>();
        for (int i = 1; i < 5; i++) {
            if (i == 1) {
                Ticket ticket = new Ticket(i, 2000);
                ticket.setPaid(true);

                tickets.add(ticket);

            }
            tickets.add(new Ticket(i, 2000));
        }

        Voyage voyage = new Voyage("JJD009");
        Integer bdVoyageID = voyageService.addVoyage(voyage).getId();

        Voyage dbVoyage = voyageService.addTicketsOnVoyage(bdVoyageID, tickets);

        Integer ticketId = null;

        for (Ticket ticket : dbVoyage.getTickets()) {
            if (ticket.isPaid()) {
                ticketId = ticket.getId();
                break;
            }
        }

        //When
        voyageService.sellTicket(bdVoyageID, ticketId);

        //Then
        Assert.fail();
    }

    @Test
    public void sellTicket() {
        //Given
        Set<Ticket> tickets = new HashSet<>();
        for (int i = 1; i < 5; i++) {
            tickets.add(new Ticket(i, 2000));
        }

        Voyage voyage = new Voyage("JJD009");
        Integer bdVoyageID = voyageService.addVoyage(voyage).getId();

        //When
        Voyage dbVoyage = voyageService.addTicketsOnVoyage(bdVoyageID, tickets);

        //Then
        Integer ticketId = dbVoyage.getTickets().iterator().next().getId();
        Assert.assertEquals(ticketService.findOne(ticketId).isPaid(), false);

        //When
        voyageService.sellTicket(bdVoyageID, ticketId);

        //Then
        Assert.assertEquals(ticketService.findOne(ticketId).isPaid(), true);
    }

    @Test
    public void findOneVoyageNoEntity() {
        //When
        Voyage voyage = voyageService.findOne(1);

        //Then
        Assert.assertEquals(null, voyage);
    }

    @Test
    public void findOneVoyageWithEntity() {
        //Given
        Voyage voyage = new Voyage("YYY8498");

        //When
        Integer voyageId = voyageService.addVoyage(voyage).getId();

        //Then
        Assert.assertEquals(voyage, voyageService.findOne(voyageId));
    }

    @Autowired
    public void setVoyageService(VoyageService voyageService) {
        this.voyageService = voyageService;
    }

    @Autowired
    public void setDriverService(DriverService driverService) {
        this.driverService = driverService;
    }

    @Autowired
    public void setBusService(BusService busService) {
        this.busService = busService;
    }

    @Autowired
    public void setTicketService(TicketService ticketService) {
        this.ticketService = ticketService;
    }
}