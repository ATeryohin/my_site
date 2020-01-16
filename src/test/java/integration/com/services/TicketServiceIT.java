package integration.com.services;

import com.entity.Ticket;

import com.repositories.TicketRepository;

import com.services.TicketService;

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

@RunWith(SpringJUnit4ClassRunner.class)
@EnableAutoConfiguration
@EntityScan(basePackages = "com.entity")
@EnableJpaRepositories("com.repositories")
@ComponentScan(basePackages = "com.services")
@Transactional
@TestPropertySource("classpath:application-test.properties")
public class TicketServiceIT {

    private TicketService ticketService;
    private TicketRepository ticketRepository;

    @Test
    public void findOneTicketNoEntity() {
        //When
        Ticket ticket = ticketService.findOne(1);

        //Then
        Assert.assertEquals(null, ticket);
    }

    @Test
    public void findOneTicketWithEntity() {
        //Given
        Ticket ticket = new Ticket(1, 90);

        //When
        Integer ticketId = ticketRepository.save(ticket).getId();

        //Then
        Assert.assertEquals(ticket, ticketService.findOne(ticketId));
    }

    @Autowired
    public void setTicketService(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @Autowired
    public void setTicketRepository(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }
}