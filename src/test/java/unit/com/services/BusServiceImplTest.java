package unit.com.services;

import com.entity.Bus;
import com.entity.Driver;

import com.repositories.BusRepository;
import com.repositories.DriverRepository;

import com.services.BusServiceImpl;
import com.services.RequestValidator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BusServiceImplTest {

    private BusRepository busRepository = mock(BusRepository.class);
    private DriverRepository driverRepository = mock(DriverRepository.class);
    private RequestValidator requestValidator = mock(RequestValidator.class);

    private BusServiceImpl busService = new BusServiceImpl();

    @Before
    public void start(){
        busService.setDriverRepository(driverRepository);
        busService.setRequestValidator(requestValidator);
        busService.setRepository(busRepository);
    }

    @Test
    public void addBus(){
        //Given
        Bus bus = new Bus("FF5656UU", "Ferrari");
        Bus bus1 = new Bus("FF5656UU", "Ferrari");
        bus1.setId(1);

        //When
        when(busRepository.save(bus)).thenReturn(bus1);

        //Then
        Assert.assertEquals(bus1.getId(), busService.addBus(new Bus("FF5656UU", "Ferrari")).getId());
    }

    @Test
    public void changeDriverOnBus(){
        //Given
        Bus bus1 = new Bus("FF5656UU", "Ferrari");
        bus1.setId(1);

        Bus bus2 = new Bus("FF5656UU", "Ferrari");
        bus2.setId(2);

        Driver driver = new Driver("UU88989", "Niki", "Lauda");
        driver.setId(1);

        bus2.setDriver(driver);

        //When
        when(busRepository.findOne(1)).thenReturn(bus1);
        when(driverRepository.findOne(1)).thenReturn(driver);
        when(busRepository.save(bus1)).thenReturn(bus2);

        //Then
        Assert.assertEquals(bus2, busService.changeDriverOnBus(1, 1));
    }

    @Test
    public void findOne(){
        //Given
        Bus bus = new Bus("FF5656UU", "Ferrari");
        bus.setId(1);

        //When
        when(busRepository.findOne(1)).thenReturn(bus);

        //Then
        Assert.assertEquals(bus, busService.findOne(1));
    }

    @Test
    public void findAll(){
        //Given
        Bus bus = new Bus("FF5656UU", "Ferrari");
        Bus bus1 = new Bus("OO6652PP", "Volvo");

        //When
        when(busRepository.findAll()).thenReturn(Arrays.asList(bus, bus1));

        //Then
        Assert.assertEquals(Arrays.asList(bus, bus1), busService.findAll());
    }
}