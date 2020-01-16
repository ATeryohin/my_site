package integration.com.repositories;

import com.entity.Ticket;
import com.repositories.TicketRepository;

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
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@EnableAutoConfiguration
@EntityScan(basePackages = "com.entity")
@EnableJpaRepositories("com.repositories")
@Transactional
@TestPropertySource("classpath:application-test.properties")
public class TicketRepositoryIT {

    private TicketRepository ticketRepository;

    @Test
    public void addSetTickets(){
        //Given
        Set<Ticket> tickets = new HashSet<>();
        for (int i = 1; i < 31; i++){
            tickets.add(new Ticket(i, 2000));
        }

        //When
        ticketRepository.save(tickets);

        //Then
        Set<Ticket> ticketsDb = new HashSet<>((ArrayList<Ticket>)ticketRepository.findAll());
        Assert.assertEquals(tickets, ticketsDb);
    }

    @Test
    public void addTicket(){
        //Given
        Ticket ticket = new Ticket(1, 30);

        //When
        Ticket dbTicket = ticketRepository.save(ticket);

        //Then
        Assert.assertEquals(ticket, dbTicket);
    }

    @Test
    public void createTicketReturnId(){
        //Given
        Ticket ticket = new Ticket(1, 30);

        //When
        ticket = ticketRepository.save(ticket);

        //Then
        Assert.assertNotNull(ticket.getId());
    }

    @Test
    public void ticketAddNextTicket(){
        //Given
        Ticket ticket = new Ticket(1, 30);
        Ticket ticket1 = new Ticket(2, 34);

        //When
        ticket = ticketRepository.save(ticket);
        ticket1 = ticketRepository.save(ticket1);

        //Then
        Integer result = ticket1.getId();
        Assert.assertEquals(ticket.getId(), --result);
    }

    @Test
    public void addTicketWithTheSamePlace(){
        //Given
        Ticket ticket = new Ticket(1, 30);
        Ticket ticket2 = new Ticket(1, 30);

        //When
        ticket = ticketRepository.save(ticket);
        ticket2 = ticketRepository.save(ticket2);

        //Then
        Assert.assertEquals(ticket, ticket2);
        Assert.assertNotSame(ticket.getId(), ticket2.getId());
    }

    @Test
    public void addTicketWithTheSameId(){
        //Given
        Ticket ticket = new Ticket(1, 30);
        ticket = ticketRepository.save(ticket);

        Ticket ticket2 = new Ticket(1, 30);
        ticket2.setId(ticket.getId());

        //When
        ticketRepository.save(ticket2);

        //Then
        Assert.assertEquals(ticketRepository.findOne(ticket.getId()), ticket2);
    }

    @Test
    public void addTicketAndFindOneByPlace(){
        //Given
        Ticket driver = new Ticket(1, 30);

        //When
        ticketRepository.save(driver);

        //Then
        Assert.assertEquals(ticketRepository.findOneByPlace(1), driver);
    }

    @Autowired
    public void setTicketRepository(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }
}