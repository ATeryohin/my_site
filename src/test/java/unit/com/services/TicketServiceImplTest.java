package unit.com.services;

import com.entity.Ticket;

import com.repositories.TicketRepository;

import com.services.TicketServiceImpl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TicketServiceImplTest {

    private TicketRepository ticketRepository = mock(TicketRepository.class);

    private TicketServiceImpl ticketService = new TicketServiceImpl();

    @Before
    public void start(){
        ticketService.setRepository(ticketRepository);
    }

    @Test
    public void findOne(){
        //Given
        Ticket ticket = new Ticket(1, 68);
        ticket.setId(1);

        //When
        when(ticketRepository.findOne(1)).thenReturn(ticket);

        //Then
        Assert.assertEquals(ticket, ticketService.findOne(1));
    }

    @Test
    public void findAll(){
        //Given
        Ticket ticket = new Ticket(1, 68);
        Ticket ticket1 = new Ticket(2, 68);

        //When
        when(ticketRepository.findAll()).thenReturn(Arrays.asList(ticket, ticket1));

        //Then
        Assert.assertEquals(Arrays.asList(ticket, ticket1), ticketService.findAll());
    }
}