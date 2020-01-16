package unit.com.services;

import com.entity.Bus;
import com.entity.Ticket;
import com.entity.Voyage;

import com.repositories.BusRepository;
import com.repositories.TicketRepository;
import com.repositories.VoyageRepository;

import com.services.RequestValidator;
import com.services.VoyageServiceImpl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.mockito.Mockito.*;

public class VoyageServiceImplTest {

    private VoyageRepository repository = mock(VoyageRepository.class);
    private TicketRepository ticketRepository = mock(TicketRepository.class);
    private BusRepository busRepository = mock(BusRepository.class);
    private RequestValidator requestValidator = mock(RequestValidator.class);

    private VoyageServiceImpl voyageService = new VoyageServiceImpl();

    @Before
    public void start(){
        voyageService.setRepository(repository);
        voyageService.setTicketRepository(ticketRepository);
        voyageService.setBusRepository(busRepository);
        voyageService.setRequestValidator(requestValidator);
    }

    @Test
    public void addVoyage(){
        //Given
        Voyage voyage = new Voyage("UUU556");
        Voyage voyage1 = new Voyage("UUU556");
        voyage1.setId(1);

        //When
        when(repository.save(voyage)).thenReturn(voyage1);

        //Then
        Assert.assertEquals(voyage1.getId(), voyageService.addVoyage(new Voyage("UUU556")).getId());
    }

    @Test
    public void changeBusOnVoyage(){
        //Given
        Voyage voyage = new Voyage("UUU556");
        voyage.setId(1);

        Voyage voyage1 = new Voyage("URU556");
        voyage1.setId(2);

        Bus bus = new Bus("FF5656UU", "Ferrari");
        bus.setId(1);

        voyage1.setBus(bus);

        //When
        when(repository.findOne(1)).thenReturn(voyage);
        when(busRepository.findOne(1)).thenReturn(bus);
        when(repository.save(voyage)).thenReturn(voyage1);

        //Then
        Assert.assertEquals(voyage1, voyageService.changeBusOnVoyage(1, 1));
    }

    @Test
    public void addTicketsOnVoyage(){
        //Given
        Voyage voyage = new Voyage("UUU556");
        voyage.setId(1);

        Ticket ticket = new Ticket(1, 444);
        Set<Ticket> tickets = new HashSet<>();
        tickets.add(ticket);

        voyage.setTickets(tickets);

        ArrayList<Ticket> dbTickets = new ArrayList<>();
        dbTickets.add(ticket);

        //When
        when(repository.findOne(1)).thenReturn(voyage);
        when(ticketRepository.save(tickets)).thenReturn(dbTickets);
        when(repository.save(voyage)).thenReturn(voyage);

        //Then
        Assert.assertEquals(voyage, voyageService.addTicketsOnVoyage(1, tickets));
    }

    @Test
    public void sellTicket(){
        //Given
        Voyage voyage = new Voyage("UUU556");
        voyage.setId(1);

        Ticket ticket = new Ticket(1, 444);
        ticket.setId(1);

        Set<Ticket> tickets = new HashSet<>();
        tickets.add(ticket);

        voyage.setTickets(tickets);

        String lineSeparator = System.lineSeparator();
        String expectedString = "Voyage{" + lineSeparator +
                "   number = UUU556" + lineSeparator +
                "   busNumber = null" + lineSeparator +
                "   busModel = null" + lineSeparator +
                "   driverName = null" + lineSeparator +
                "   driverSurname = null" + lineSeparator +
                "   ticketPrice = 444" + lineSeparator +
                "   ticketPlace = 1" + lineSeparator +
                "   isPaid = true}";

        //When
        when(ticketRepository.findOne(1)).thenReturn(ticket);
        when(repository.findOne(1)).thenReturn(voyage);

        //Then
        Assert.assertEquals(expectedString, voyageService.sellTicket(1, 1));
    }

    @Test
    public void findOne(){
        //Given
        Voyage voyage = new Voyage("UUU556");
        voyage.setId(1);

        //When
        when(repository.findOne(1)).thenReturn(voyage);

        //Then
        Assert.assertEquals(voyage, voyageService.findOne(1));
    }

    @Test
    public void findAll(){
        //Given
        Voyage voyage = new Voyage("UUU556");
        Voyage voyage1 = new Voyage("EEE888");

        //When
        when(repository.findAll()).thenReturn(Arrays.asList(voyage, voyage1));

        //Then
        Assert.assertEquals(Arrays.asList(voyage, voyage1), voyageService.findAll());
    }
}