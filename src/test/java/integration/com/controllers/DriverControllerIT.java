package integration.com.controllers;

import com.controllers.DriverController;

import com.entity.Driver;

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
public class DriverControllerIT {

    private DriverController driverController;

    @Test
    public void addDriver(){
        //Given
        Driver driver = new Driver("HHFH33", "Bob", "Pupkon");

        //When
        ResponseEntity<?> actual =  driverController.addDriver(driver);

        //Then
        ResponseEntity<?> expected = new ResponseEntity<>(new Driver("HHFH33", "Bob", "Pupkon"), HttpStatus.OK);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addDriverError(){
        //Given
        Driver driver = new Driver("HHFH33", "Bob", "Pupkon");

        //When
        driverController.addDriver(driver);
        driverController.addDriver(driver);

        //Then
        Assert.fail();
    }

    @Autowired
    public void setDriverController(DriverController driverController) {
        this.driverController = driverController;
    }
}