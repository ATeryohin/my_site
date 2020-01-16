package integration.com.repositories;

import com.entity.*;
import com.repositories.*;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@EnableAutoConfiguration
@EntityScan(basePackages = "com.entity")
@EnableJpaRepositories("com.repositories")
@Transactional
@TestPropertySource("classpath:application-test.properties")
public class VoyageRepositoryIT {

    private VoyageRepository voyageRepository;
    private TicketRepository ticketRepository;
    private BusRepository busRepository;

    @Test
    public void addVoyage(){
        //Given
        Voyage voyage = new Voyage("Tt83883");

        //When
        Voyage dbVoyage = voyageRepository.save(voyage);

        //Then
        Assert.assertEquals(voyage, dbVoyage);
    }

    @Test
    public void updateBusOnVoyage(){
        //Given
        Voyage voyage = new Voyage("Tt83883");
        voyageRepository.save(voyage);

        //When
        Voyage result = voyageRepository.findOneByNumber("Tt83883");

        //THen
        Assert.assertNull(result.getBus());

        //Given
        Bus bus = new Bus("AA1234AA", "Porsche");
        bus = busRepository.save(bus);

        result.setBus(bus);

        //When
        Voyage secondResult = voyageRepository.save(result);

        //Then
        Assert.assertEquals(bus, secondResult.getBus());
    }

    @Test
    public void addBusAddVoyagesDeleteBus(){
        //Given
        Bus bus = new Bus("AA1234AA", "Porsche");
        Bus bus1 = new Bus("HH5555CC", "Ferrari");

        Bus dbDriver = busRepository.save(bus);
        busRepository.save(bus1);

        Voyage voyage = new Voyage("EEE8889");

        Voyage voyage1 = new Voyage("REW8989");

        Voyage dbVoyage = voyageRepository.save(voyage);
        Voyage dbVoyage1 = voyageRepository.save(voyage1);

        dbVoyage.setBus(bus);
        dbVoyage1.setBus(bus1);

        dbVoyage = voyageRepository.save(dbVoyage);
        voyageRepository.save(dbVoyage1);

        dbVoyage.setBus(null);

        //When
        dbVoyage = voyageRepository.save(dbVoyage);

        busRepository.delete(dbDriver.getId());

        //Then
        Assert.assertEquals(dbVoyage.getBus(), null);
    }

    @Test
    public void createVoyageReturnId(){
        //Given
        Voyage voyage = new Voyage("EW78787");

        //When
        Voyage dbVoyage = voyageRepository.save(voyage);

        //Then
        Assert.assertNotNull(dbVoyage.getId());
    }

    @Test
    public void voyageAddNextVoyage(){
        //Given
        Voyage voyage = new Voyage("EW78787");
        Voyage voyage1 = new Voyage("PP66757");

        //When
        voyage = voyageRepository.save(voyage);
        voyage1 = voyageRepository.save(voyage1);

        //Then
        Integer result = voyage1.getId();
        Assert.assertEquals(voyage.getId(), --result);
    }

    @Test
    public void addVoyageWithTheSameNumber(){
        //Given
        Voyage voyage = new Voyage("EW78787");
        Voyage voyage1 = new Voyage("EW78787");

        //When
        voyage = voyageRepository.save(voyage);
        voyage1 = voyageRepository.save(voyage1);

        //Then
        Assert.assertEquals(voyage, voyage1);
        Assert.assertNotSame(voyage.getId(), voyage1.getId());
    }

    @Test
    public void addVoyageWithTheSameId(){
        //Given
        Voyage voyage = new Voyage("EW78787");
        voyage = voyageRepository.save(voyage);

        Voyage voyage1 = new Voyage("DD2323");
        voyage1.setId(voyage.getId());

        //When
        voyage1 = voyageRepository.save(voyage1);

        //Then
        Assert.assertEquals(voyageRepository.findOne(voyage.getId()), voyage1);
    }

    @Test
    public void addVoyageAndFindOneByNumber(){
        //Given
        Voyage voyage = new Voyage("EW78787");

        //When
        voyage = voyageRepository.save(voyage);

        //When
        Assert.assertEquals(voyageRepository.findOneByNumber("EW78787"), voyage);
    }

    @Test
    public void addVoyageAddBusAndChangeBus(){
        //Given
        Voyage voyage = new Voyage("EW78787");
        voyage.setBus(new Bus("DD87878TT", "PPP"));

        //When
        voyageRepository.save(voyage);

        //Then
        Voyage dbVoyage = voyageRepository.findOneByNumber("EW78787");
        Assert.assertEquals(dbVoyage, voyage);

        //Given
        Bus bus =  new Bus("BBVSV009", "Donald");
        dbVoyage.setBus(bus);

        //When
        voyageRepository.save(dbVoyage);

        //Then
        Assert.assertEquals(dbVoyage.getBus(), bus);

        //When
        ArrayList<Bus> buses = (ArrayList<Bus>)busRepository.findAll();

        //Then
        Assert.assertTrue(buses.size() == 2);
    }

    @Test
    public void addVoyageSetNumberSetBus(){
        //Given
        Bus bus = new Bus("hdjh", "djjdj");
        bus.setDriver(new Driver("4254452", "Tom", "Potter"));

        Set<Ticket> tickets = new HashSet<>();
        for (int i = 1; i < 5; i++){
            tickets.add(new Ticket(i, 2000));
        }

        Voyage voyage = new Voyage();
        voyage.setNumber("2344TT");
        voyage.setBus(bus);
        voyage.setTickets(tickets);

        //When
        voyageRepository.save(voyage);

        //Then
        Assert.assertEquals(voyageRepository.findOneByNumber("2344TT"), voyage);

    }

    @Test
    public void UpdateVoyageTicketsTestGetTickets(){
        //Given
        Set<Ticket> tickets = new HashSet<>();
        for (int i = 1; i < 5; i++){
            tickets.add(new Ticket(i, 2000));
        }

        ticketRepository.save(tickets);

        Voyage voyage = new Voyage("JJD009");
        Integer bdVoyageID = voyageRepository.save(voyage).getId();

        Set<Ticket> tickets1 = new HashSet<>((ArrayList<Ticket>)ticketRepository.findAll());
        tickets1.iterator().next().setPaid(true);

        Voyage voyage1 = voyageRepository.findOne(bdVoyageID);
        voyage1.setTickets(tickets1);

        //When
        voyageRepository.save(voyage1);

        //Then
        Assert.assertEquals(new HashSet<> (voyageRepository.findOne(bdVoyageID).getTickets()),
                new HashSet<>((ArrayList<Ticket>)ticketRepository.findAll()));

    }

    @Test
    public void createNewVoyageWithNewBussAndWithNewTickets(){
        //Given
        Set<Ticket> tickets = new HashSet<>();
        for (int i = 1; i < 5; i++){
            tickets.add(new Ticket(i, 2000));
        }

        Voyage voyage = new Voyage("JJD009");
        voyage.setBus( new Bus("KKK", "ydyud"));
        voyage.setTickets(tickets);

        //When
        Integer bdVoyageID = voyageRepository.save(voyage).getId();

        //Then
        Assert.assertNotEquals(new HashSet<> (voyageRepository.findOne(bdVoyageID).getTickets()),
                new HashSet<>((ArrayList<Ticket>)ticketRepository.findAll()));

    }

    @Test
    public void findOneTicketByVoyageId()throws Exception{
        //Given
        Voyage voyage = new Voyage("JJD009");
        Integer bdVoyageID = voyageRepository.save(voyage).getId();

        Ticket ticket = new Ticket(1, 2000);
        ticket.setVoyage(voyage);

        //When
        Integer bdTicketID = ticketRepository.save(ticket).getId();
        List<Ticket> ticketsList = ticketRepository.findAllByVoyage_id(bdVoyageID);

        //Then
        Ticket ticket1 = ticketsList.get(0);
        Assert.assertEquals("Ticket{id=" + bdTicketID + ", place=1, price=2000, isPaid=false}", ticket1.toString());
    }

    @Autowired
    public void setVoyageRepository(VoyageRepository voyageRepository) {
        this.voyageRepository = voyageRepository;
    }

    @Autowired
    public void setTicketRepository(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Autowired
    public void setBusRepository(BusRepository busRepository) {
        this.busRepository = busRepository;
    }
}