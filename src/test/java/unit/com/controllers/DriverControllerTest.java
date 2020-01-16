package unit.com.controllers;

import com.controllers.DriverController;

import com.entity.Driver;

import com.services.DriverService;
import com.services.DriverServiceImpl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

import static org.mockito.Mockito.*;

public class DriverControllerTest {

    private DriverService driverService = mock(DriverServiceImpl.class);

    private DriverController driverController = new DriverController();

    @Before
    public void start(){
        driverController.setService(driverService);
    }

    @Test
    public void addDriver(){
        //Given
        Driver driver = new Driver("HHFH33", "Bob", "Pupkon");

        //When
        ResponseEntity<?> expected = new ResponseEntity<>(new Driver("HHFH33", "Bob", "Pupkon"), HttpStatus.OK);
        when(driverService.addDriver(any())).thenReturn(driver);

        //Then
        Assert.assertEquals(expected, driverController.addDriver(driver));
    }

    @Test
    public void findOneDriver(){
        //Given
        Driver driver = new Driver("HHFH33", "Bob", "Pupkon");
        driver.setId(1);

        Driver expectedDriver = new Driver("HHFH33", "Bob", "Pupkon");
        expectedDriver.setId(1);

        //When
        ResponseEntity<?> expected = new ResponseEntity<>(expectedDriver, HttpStatus.OK);
        when(driverService.findOne(1)).thenReturn(driver);

        //Then
        Assert.assertEquals(expected, driverController.findOneDriver(1));
    }

    @Test
    public void findAllDrivers(){
        //Given
        Driver driver = new Driver("HHFH33", "Bob", "Pupkon");
        driver.setId(1);

        Driver expectedDriver = new Driver("HHFH33", "Bob", "Pupkon");
        expectedDriver.setId(1);

        //When
        ResponseEntity<?> expected = new ResponseEntity<>(Arrays.asList(expectedDriver), HttpStatus.OK);
        when(driverService.findAll()).thenReturn(Arrays.asList(driver));

        //Then
        Assert.assertEquals(expected, driverController.findAllDrivers());
    }
}