package integration.com.services;

import com.entity.Driver;

import com.services.DriverService;

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

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@EnableAutoConfiguration
@EntityScan(basePackages = "com.entity")
@EnableJpaRepositories("com.repositories")
@ComponentScan(basePackages = "com.services")
@Transactional
@TestPropertySource("classpath:application-test.properties")
public class DriverServiceIT {

    private DriverService driverService;

    @Test
    public void addDriver() {
        //Given
        Driver driver = new Driver("YIIIPPO", "Poll", "Banana");

        //When
        Driver result = driverService.addDriver(driver);

        //Then
        Assert.assertNotNull(result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addDriverWithTheSameLicense() {
        //Given
        Driver driver = new Driver("YIIIPPO", "Poll", "Banana");

        //When
        driverService.addDriver(driver);
        driverService.addDriver(driver);

        //Then
        Assert.fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void addDriverNull() {
        //When
        driverService.addDriver(null);

        //Then
        Assert.fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void addDriveWithTheSameId() {
        //Given
        Driver driver = new Driver("RRRERR", "Joi", "Jingle");
        Integer driverId = driverService.addDriver(driver).getId();

        Driver driver2 = new Driver("TOTOTO", "Joi", "Jingle");
        driver2.setId(driverId);

        //When
        driverService.addDriver(driver2);

        //Then
        Assert.fail();
    }

    @Test(expected = IllegalArgumentException.class )
    public void addDriveWithEmptyFields() {
        //When
        driverService.addDriver(new Driver());

        //Then
        Assert.fail();
    }

    @Test
    public void addDrivesAndFindAll() {
        //Given
        Driver driver = new Driver("RRRERR", "Joi", "Jingle");
        Driver driver2 = new Driver("TOTOTO", "Inna", "Ser");
        Driver driver3 = new Driver("MO8980", "Monty", "Dill");

        Integer driverId = driverService.addDriver(driver).getId();
        Integer driverId2 = driverService.addDriver(driver2).getId();
        Integer driverId3 = driverService.addDriver(driver3).getId();

        //When
        List<Driver> drivers = driverService.findAll();

        //Then
        Assert.assertEquals("[Driver{id=" + driverId + ", license='RRRERR', name='Joi', surname='Jingle'}, " +
                "Driver{id=" + driverId2 + ", license='TOTOTO', name='Inna', surname='Ser'}, " +
                "Driver{id=" + driverId3 + ", license='MO8980', name='Monty', surname='Dill'}]", drivers.toString());
    }

    @Test
    public void findAllDriversInEmptyTable() {
        //When
        List<Driver> drivers = driverService.findAll();

        //Then
        Assert.assertEquals("[]", drivers.toString());
    }

    @Test
    public void findOneDriverNoEntity() {
        //When
        Driver driver = driverService.findOne(1);

        //Then
        Assert.assertEquals(null, driver);
    }

    @Test
    public void findOneDriverWithEntity() {
        //Given
        Driver driver = new Driver("HHd777", "Joi", "Tirb");

        //When
        Integer driverId = driverService.addDriver(driver).getId();

        //Then
        Assert.assertEquals(driver, driverService.findOne(driverId));
    }

    @Autowired
    public void setDriverService(DriverService driverService) {
        this.driverService = driverService;
    }
}