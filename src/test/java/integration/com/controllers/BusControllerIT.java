package integration.com.controllers;

import com.controllers.BusController;

import com.entity.Bus;
import com.entity.Driver;

import com.services.BusService;
import com.services.DriverService;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import org.springframework.context.annotation.ComponentScan;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@EnableAutoConfiguration
@EntityScan(basePackages = "com.entity")
@EnableJpaRepositories("com.repositories")
@ComponentScan(basePackages = "com.services, com.controllers")
@Transactional
@TestPropertySource("classpath:application-test.properties")
public class BusControllerIT {

    private BusController busController;
    private BusService busService;
    private DriverService driverService;

    @Test
    public void addBus(){
        //Given
        Bus bus = new Bus("FF0090OO", "Ferrari");

        //When
        ResponseEntity<?> actual =  busController.addBus(bus);

        //Then
        ResponseEntity<?> expected = new ResponseEntity<>(new Bus("FF0090OO", "Ferrari"), HttpStatus.OK);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addBusError(){
        //Given
        Bus bus = new Bus("FF0090OO", "Ferrari");

        //When
        busController.addBus(bus);
        busController.addBus(bus);

        //Then
        Assert.fail();
    }

    @Test
    public void addDriverOnBus() {
        //Given
        Driver driver = driverService.addDriver(new Driver("HHFH33", "Bob", "Pupkon"));
        Bus bus = busService.addBus(new Bus("FF0090OO", "Ferrari"));

        //When
        ResponseEntity<?> actual = busController.changeDriverOnBus(bus.getId(), driver.getId());

        //Then
        Bus bus1 = new Bus("FF0090OO", "Ferrari");
        bus1.setDriver(new Driver("HHFH33", "Bob", "Pupkon"));
        ResponseEntity<?> expected = new ResponseEntity<>(bus1, HttpStatus.OK);

        Assert.assertEquals(expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addDriverOnBusError() {
        //Given
        Driver driver = driverService.addDriver(new Driver("HHFH33", "Bob", "Pupkon"));
        Bus bus = busService.addBus(new Bus("FF0090OO", "Ferrari"));

        //When
        busController.changeDriverOnBus(bus.getId(), driver.getId());
        busController.changeDriverOnBus(bus.getId(), driver.getId());

        //Then
        Assert.fail();
    }

    @Autowired
    public void setBusController(BusController busController) {
        this.busController = busController;
    }

    @Autowired
    public void setBusService(BusService busService) {
        this.busService = busService;
    }

    @Autowired
    public void setDriverService(DriverService driverService) {
        this.driverService = driverService;
    }
}