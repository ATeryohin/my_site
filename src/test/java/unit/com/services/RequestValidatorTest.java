package unit.com.services;

import com.entity.*;

import com.repositories.*;

import com.services.RequestValidator;
import org.junit.Assert;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.*;

public class RequestValidatorTest {

    private DriverRepository driverRepository = mock(DriverRepository.class);
    private BusRepository busRepository = mock(BusRepository.class);
    private TicketRepository ticketRepository = mock(TicketRepository.class);
    private VoyageRepository voyageRepository = mock(VoyageRepository.class);

    private RequestValidator requestValidator = new RequestValidator();

    @Before
    public void start(){
        requestValidator.setDriverRepository(driverRepository);
        requestValidator.setBusRepository(busRepository);
        requestValidator.setTicketRepository(ticketRepository);
        requestValidator.setVoyageRepository(voyageRepository);
    }

    @Test
    public void driverRequestValidatorDiverNull(){
        //When
        try {
            requestValidator.driverRequestValidator(null);

            //Then
            Assert.fail();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Driver can't be null", e.getMessage());
        }
    }

    @Test
    public void driverRequestValidatorDriverFieldNull(){
        //When
        try {
            requestValidator.driverRequestValidator(new Driver());

            //Then
            Assert.fail();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Driver fields can't be null", e.getMessage());
        }
    }

    @Test
    public void driverRequestValidatorExistDriver(){
        //Given
        Driver driver = new Driver("AKSE", "Niki", "Lauda");
        driver.setId(1);

        when(driverRepository.exists(1)).thenReturn(true);

        //When
        try {
            requestValidator.driverRequestValidator(driver);

            //Then
            Assert.fail();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Driver with id " + driver.getId() + " exist", e.getMessage());
        }
    }

    @Test
    public void driverRequestValidatorDriverWithSameLicense(){
        //Given
        Driver driver = new Driver("AKSE", "Niki", "Lauda");

        when(driverRepository.findOneByLicense(driver.getLicense())).thenReturn(driver);

        //When
        try {
            requestValidator.driverRequestValidator(driver);

            //Then
            Assert.fail();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Driver with license " + driver.getLicense() + " exist", e.getMessage());
        }
    }

    @Test
    public void driverRequestValidatorDriverOk(){
        //Given
        Driver driver = new Driver("AKSE", "Niki", "Lauda");

        //When
        requestValidator.driverRequestValidator(driver);

        //Then
    }

    @Test
    public void busRequestValidatorBusNull(){
        //When
        try {
            requestValidator.busRequestValidator(null);

            //Then
            Assert.fail();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Bus can't be null", e.getMessage());
        }
    }

    @Test
    public void busRequestValidatorBusFieldNull(){
        //When
        try {
            requestValidator.busRequestValidator(new Bus());

            //Then
            Assert.fail();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Bus fields 'number' and 'model' can't be null", e.getMessage());
        }
    }

    @Test
    public void busRequestValidatorExistBus(){
        //Given
        Bus bus = new Bus("AA8888JJ", "Ferrari");
        bus.setId(1);

        when(busRepository.exists(1)).thenReturn(true);

        //When
        try {
            requestValidator.busRequestValidator(bus);

            //Then
            Assert.fail();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Bus with id " + bus.getId() + " exist", e.getMessage());
        }
    }

    @Test
    public void busRequestValidatorBusWithSameNumber(){
        //Given
        Bus bus = new Bus("AA8888JJ", "Ferrari");

        when(busRepository.findOneByNumber(bus.getNumber())).thenReturn(bus);

        //When
        try {
            requestValidator.busRequestValidator(bus);

            //Then
            Assert.fail();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Bus with number " + bus.getNumber() + " exist", e.getMessage());
        }
    }

    @Test
    public void busRequestValidatorBusOk(){
        //Given
        Bus bus = new Bus("AA8888JJ", "Ferrari");

        //When
        requestValidator.busRequestValidator(bus);

        //Then
    }

    @Test
    public void busIdDriverIdValidatorNull(){
        //When
        try {
            requestValidator.busIdDriverIdValidator(null, null);

            //Then
            Assert.fail();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Bus id or Driver id can't be null", e.getMessage());
        }
    }

    @Test
    public void busIdDriverIdValidatorZero(){
        //When
        try {
            requestValidator.busIdDriverIdValidator(0, 0);

            //Then
            Assert.fail();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Bus id or Driver id can't be <= 0", e.getMessage());
        }
    }

    @Test
    public void busIdDriverIdValidatorDriverAndBusExist(){
        //When
        try {
            requestValidator.busIdDriverIdValidator(1, 1);

            //Then
            Assert.fail();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Bus with id 1 or Driver with id 1 not exist", e.getMessage());
        }
    }

    @Test
    public void busIdDriverIdValidatorDriverAlreadyOnBus(){
        //Given
        Driver driver = new Driver();
        driver.setId(1);

        Bus bus = new Bus();
        bus.setId(1);
        bus.setDriver(driver);

        when(busRepository.exists(1)).thenReturn(true);
        when(driverRepository.exists(1)).thenReturn(true);
        when(busRepository.findOne(1)).thenReturn(bus);

        //When
        try {
            requestValidator.busIdDriverIdValidator(1, 1);

            //Then
            Assert.fail();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Driver with id 1 already on Bus 1", e.getMessage());
        }
    }

    @Test
    public void busIdDriverIdValidatorTwoDriversOnDifferentBuses(){
        //Given
        Driver driver = new Driver();
        driver.setId(1);

        Driver driver1 = new Driver();
        driver1.setId(2);

        Bus bus = new Bus();
        bus.setId(1);
        bus.setDriver(driver);

        Bus bus1 = new Bus();
        bus1.setId(2);
        bus1.setDriver(driver1);

        when(busRepository.exists(anyInt())).thenReturn(true);
        when(driverRepository.exists(anyInt())).thenReturn(true);
        when(busRepository.findOne(anyInt())).thenReturn(new Bus());
        when(busRepository.findAll()).thenReturn(Arrays.asList(bus, bus1));

        //When
        try {
            requestValidator.busIdDriverIdValidator(1, 2);

            //Then
            Assert.fail();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Driver 2 can't be on different Buses", e.getMessage());
        }
    }

    @Test
    public void voyageRequestValidatorVoyageNull(){
        //When
        try {
            requestValidator.voyageRequestValidator(null);

            //Then
            Assert.fail();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Voyage can't be null", e.getMessage());
        }
    }

    @Test
    public void voyageRequestValidatorVoyageFieldNull(){
        //When
        try {
            requestValidator.voyageRequestValidator(new Voyage());

            //Then
            Assert.fail();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Voyage field 'number' can't be null", e.getMessage());
        }
    }

    @Test
    public void voyageRequestValidatorExistVoyage(){
        //Given
        Voyage voyage = new Voyage("TTRREE");
        voyage.setId(1);

        when(voyageRepository.exists(1)).thenReturn(true);

        //When
        try {
            requestValidator.voyageRequestValidator(voyage);

            //Then
            Assert.fail();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Voyage with id " + voyage.getId() + " exist", e.getMessage());
        }
    }

    @Test
    public void voyageRequestValidatorVoyageWithSameLicense(){
        //Given
        Voyage voyage = new Voyage("EERRTT");

        when(voyageRepository.findOneByNumber(voyage.getNumber())).thenReturn(voyage);

        //When
        try {
            requestValidator.voyageRequestValidator(voyage);

            //Then
            Assert.fail();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Voyage with number " + voyage.getNumber() + " exist", e.getMessage());
        }
    }

    @Test
    public void voyageRequestValidatorVoyageOk(){
        //Given
        Voyage voyage = new Voyage("EERRTT");

        //When
        requestValidator.voyageRequestValidator(voyage);

        //Then
    }

    @Test
    public void voyageIdBusIdValidatorNull(){
        //When
        try {
            requestValidator.voyageIdBusIdValidator(null, null);

            //Then
            Assert.fail();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Voyage id or Bus id can't be null", e.getMessage());
        }
    }

    @Test
    public void voyageIdBusIdValidatorZero(){
        //When
        try {
            requestValidator.voyageIdBusIdValidator(0, 0);

            //Then
            Assert.fail();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Voyage id or Bus id can't be <= 0", e.getMessage());
        }
    }

    @Test
    public void voyageIdBusIdValidatorBusAndVoyageExist(){
        //When
        try {
            requestValidator.voyageIdBusIdValidator(1, 1);

            //Then
            Assert.fail();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Voyage with id 1 or Bus with id 1 not exist", e.getMessage());
        }
    }

    @Test
    public void voyageIdBusIdValidatorBusAlreadyOnVoyage(){
        //Given
        Bus bus = new Bus();
        bus.setId(1);

        Voyage voyage = new Voyage();
        voyage.setId(1);
        voyage.setBus(bus);

        when(voyageRepository.exists(1)).thenReturn(true);
        when(busRepository.exists(1)).thenReturn(true);
        when(voyageRepository.findOne(1)).thenReturn(voyage);

        //When
        try {
            requestValidator.voyageIdBusIdValidator(1, 1);

            //Then
            Assert.fail();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Bus with id 1 already on Voyage 1", e.getMessage());
        }
    }

    @Test
    public void voyageIdBusIdValidatorTwoBusesOnDifferentVoyages(){
        //Given
        Bus bus = new Bus();
        bus.setId(1);

        Bus bus1 = new Bus();
        bus1.setId(2);

        Voyage voyage = new Voyage();
        voyage.setId(1);
        voyage.setBus(bus);

        Voyage voyage1 = new Voyage();
        voyage1.setId(2);
        voyage1.setBus(bus1);

        when(voyageRepository.exists(anyInt())).thenReturn(true);
        when(busRepository.exists(anyInt())).thenReturn(true);
        when(voyageRepository.findOne(anyInt())).thenReturn(new Voyage());
        when(voyageRepository.findAll()).thenReturn(Arrays.asList(voyage, voyage1));

        //When
        try {
            requestValidator.voyageIdBusIdValidator(1, 2);

            //Then
            Assert.fail();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Bus 2 can't be on different Voyages", e.getMessage());
        }
    }

    @Test
    public void voyageIdTicketIdValidatorNull(){
        //When
        try {
            requestValidator.voyageIdTicketIdValidator(null, null);

            //Then
            Assert.fail();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Voyage id or Ticket id can't be null", e.getMessage());
        }
    }

    @Test
    public void voyageIdTicketIdValidatorZero(){
        //When
        try {
            requestValidator.voyageIdTicketIdValidator(0, 0);

            //Then
            Assert.fail();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Voyage id or Ticket id can't be <= 0", e.getMessage());
        }
    }

    @Test
    public void voyageIdTicketIdValidatorTicketAndVoyageExist(){
        //When
        try {
            requestValidator.voyageIdTicketIdValidator(1, 1);

            //Then
            Assert.fail();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Voyage with id 1 or Ticket with id 1 not exist", e.getMessage());
        }
    }

    @Test
    public void voyageIdTicketIdValidatorTicketAlreadySold(){
        //Given
        Ticket ticket = new Ticket();
        ticket.setId(1);
        ticket.setPaid(true);

        Set<Ticket> tickets = new HashSet<>();
        tickets.add(ticket);

        Voyage voyage = new Voyage();
        voyage.setId(1);
        voyage.setTickets(tickets);

        when(voyageRepository.exists(1)).thenReturn(true);
        when(ticketRepository.exists(1)).thenReturn(true);
        when(ticketRepository.findAllByVoyage_id(1)).thenReturn(Arrays.asList(ticket));

        //When
        try {
            requestValidator.voyageIdTicketIdValidator(1, 1);

            //Then
            Assert.fail();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("The Ticket with id 1 is already sold", e.getMessage());
        }
    }

    @Test
    public void voyageIdSetOfTicketsValidatorNull(){
        //When
        try {
            requestValidator.voyageIdSetOfTicketsValidator(null, null);

            //Then
            Assert.fail();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Voyage id or Set of Tickets can't be null", e.getMessage());
        }
    }

    @Test
    public void voyageIdSetOfTicketsValidatorZero(){
        //When
        try {
            requestValidator.voyageIdSetOfTicketsValidator(0, new HashSet<>());

            //Then
            Assert.fail();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Voyage id can't be <= 0", e.getMessage());
        }
    }

    @Test
    public void voyageIdSetOfTicketsValidatorVoyageExist(){
        //When
        try {
            requestValidator.voyageIdSetOfTicketsValidator(1, new HashSet<>());

            //Then
            Assert.fail();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Voyage with id 1 not exist", e.getMessage());
        }
    }

    @Test
    public void voyageIdSetOfTicketsValidatorNoTickets(){
        //Given
        when(voyageRepository.exists(1)).thenReturn(true);

        //When
        try {
            requestValidator.voyageIdSetOfTicketsValidator(1, new HashSet<>());

            //Then
            Assert.fail();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("No Tickets", e.getMessage());
        }
    }

    @Test
    public void voyageIdSetOfTicketsValidatorSamePlace(){
        //Given
        Ticket ticket = new Ticket(1, 20);
        ticket.setId(1);

        Ticket ticket1 = new Ticket(1, 20);
        ticket1.setId(2);

        Set<Ticket> tickets = new HashSet<>();
        tickets.add(ticket);
        tickets.add(ticket1);

        Voyage voyage = new Voyage();
        voyage.setId(1);
        voyage.setTickets(tickets);

        when(voyageRepository.exists(1)).thenReturn(true);
        when(ticketRepository.exists(1)).thenReturn(true);
        when(voyageRepository.findOne(1)).thenReturn(voyage);

        //When
        try {
            requestValidator.voyageIdSetOfTicketsValidator(1, tickets);

            //Then
            Assert.fail();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Two Tickets with same place can't be", e.getMessage());
        }
    }
}