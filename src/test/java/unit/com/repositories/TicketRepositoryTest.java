package unit.com.repositories;

import com.entity.Ticket;

import com.repositories.TicketRepository;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

import static org.mockito.Mockito.*;

public class TicketRepositoryTest {

    private TicketRepository ticketRepository = mock(TicketRepository.class);

    @Test
    public void save(){
        //Given
        Ticket ticket = new Ticket(1, 49);
        Ticket ticketDb = new Ticket(1, 49);
        ticketDb.setId(1);

        //When
        when(ticketRepository.save(ticket)).thenReturn(ticketDb);

        //Then
        Assert.assertEquals(ticketDb, ticketRepository.save(ticket));
    }

    @Test
    public void findOneByLicense(){
        //Given
        Ticket ticket = new Ticket(1, 49);
        Ticket ticketDb = new Ticket(1, 49);
        ticketDb.setId(1);

        //When
        when(ticketRepository.findOneByPlace(1)).thenReturn(ticketDb);

        //Then
        Assert.assertEquals(ticketDb, ticketRepository.findOneByPlace(1));
    }

    @Test
    public void findAllByVoyage_id(){
        //Given
        Ticket ticket = new Ticket(1, 49);

        //When
        when(ticketRepository.findAllByVoyage_id(1)).thenReturn(Arrays.asList(ticket));

        //Then
        Assert.assertEquals(Arrays.asList(ticket), ticketRepository.findAllByVoyage_id(1));
    }
}