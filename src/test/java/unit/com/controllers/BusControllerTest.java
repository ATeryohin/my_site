package unit.com.controllers;

import com.controllers.BusController;

import com.entity.Bus;

import com.entity.Driver;
import com.services.BusService;
import com.services.BusServiceImpl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

import static org.mockito.Mockito.*;

public class BusControllerTest {

    private BusService busService = mock(BusServiceImpl.class);

    private BusController busController = new BusController();

    @Before
    public void start(){
        busController.setService(busService);
    }

    @Test
    public void addBus(){
        //Given
        Bus bus = new Bus("FF0090OO", "Ferrari");

        //When
        ResponseEntity<?> expected = new ResponseEntity<>(new Bus("FF0090OO", "Ferrari"), HttpStatus.OK);
        when(busService.addBus(any())).thenReturn(bus);

        //Then
        Assert.assertEquals(expected, busController.addBus(bus));
    }

    @Test
    public void changeDriverOnBus(){
        //Given
        Bus bus = new Bus("FF0090OO", "Ferrari");
        bus.setId(1);

        Driver driver = new Driver("HHFH33", "Bob", "Pupkon");
        driver.setId(1);

        bus.setDriver(driver);

        //When
        ResponseEntity<?> expected = new ResponseEntity<>(bus, HttpStatus.OK);
        when(busService.changeDriverOnBus(1, 1)).thenReturn(bus);

        //Then
        Assert.assertEquals(expected, busController.changeDriverOnBus(bus.getId(), driver.getId()));
    }

    @Test
    public void findOneBus(){
        //Given
        Bus bus = new Bus("FF0090OO", "Ferrari");
        bus.setId(1);

        Bus expectedBus = new Bus("FF0090OO", "Ferrari");
        expectedBus.setId(1);

        //When
        ResponseEntity<?> expected = new ResponseEntity<>(expectedBus, HttpStatus.OK);
        when(busService.findOne(1)).thenReturn(bus);

        //Then
        Assert.assertEquals(expected, busController.findOneBus(1));
    }

    @Test
    public void findAllBuses(){
        //Given
        Bus bus = new Bus("FF0090OO", "Ferrari");
        bus.setId(1);

        Bus expectedBus = new Bus("FF0090OO", "Ferrari");
        expectedBus.setId(1);

        //When
        ResponseEntity<?> expected = new ResponseEntity<>(Arrays.asList(expectedBus), HttpStatus.OK);
        when(busService.findAll()).thenReturn(Arrays.asList(bus));

        //Then
        Assert.assertEquals(expected, busController.findAllBuses());
    }
}