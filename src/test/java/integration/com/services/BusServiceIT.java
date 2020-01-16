package integration.com.services;

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
public class BusServiceIT {

    private BusService busService;
    private DriverService driverService;

    @Test
    public void addBus() {
        //Given
        Bus bus = new Bus("GG8888PP", "Gin");

        //When
        Bus result = busService.addBus(bus);

        //Then
        Assert.assertNotNull(result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addBusWithTheSameNumber() {
        //Given
        Bus bus = new Bus("GG8888PP", "Gin");
        Bus bus1 = new Bus("GG8888PP", "Harry");

        //When
        busService.addBus(bus);
        busService.addBus(bus1);

        //Then
        Assert.fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void addBusNull() {
        //When
        busService.addBus(null);

        //Then
        Assert.fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void addBusWithTheSameId() {
        //Given
        Bus bus = new Bus("GG8888PP", "Gin");
        Integer busId = busService.addBus(bus).getId();

        Bus bus1 = new Bus("YY89898WW", "Gor");
        bus1.setId(busId);

        //When
        busService.addBus(bus1);

        //Then
        Assert.fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void addBusWithEmptyFields() {
        //When
        busService.addBus(new Bus());

        //Then
        Assert.fail();
    }

    @Test
    public void addBusesAndFindAll() {
        //Given
        Bus bus = new Bus("GG8888PP", "Gin");
        Bus bus1 = new Bus("JJ7767RR", "Pid");
        Bus bus2 = new Bus("KK9999PP", "Due");

        Integer busId = busService.addBus(bus).getId();
        Integer busId1 = busService.addBus(bus1).getId();
        Integer busId2 = busService.addBus(bus2).getId();

        //When
        List<Bus> buses = busService.findAll();

        //Then
        Assert.assertEquals("[Bus{id=" + busId + ", number='GG8888PP', model='Gin', driver=null}, " +
                "Bus{id=" + busId1 + ", number='JJ7767RR', model='Pid', driver=null}, " +
                "Bus{id=" + busId2 + ", number='KK9999PP', model='Due', driver=null}]", buses.toString());
    }

    @Test
    public void findAllBusesInEmptyTable() {
        //When
        List<Bus> buses = busService.findAll();

        //Then
        Assert.assertEquals("[]", buses.toString());
    }

    @Test
    public void changeDriverOnBus() {
        //Given
        Bus bus = new Bus("GG8888PP", "Gin");
        Bus bus1 = new Bus("JJ7767RR", "Pid");
        Driver driver = new Driver("RRRERR", "Joi", "Jingle");
        Driver driver1 = new Driver("TOTOTO", "Inna", "Ser");
        Driver driver2 = new Driver("MO8980", "Monty", "Dill");

        Integer busId = busService.addBus(bus).getId();
        Integer busId1 = busService.addBus(bus1).getId();
        Integer driverId = driverService.addDriver(driver).getId();
        Integer driverId1 = driverService.addDriver(driver1).getId();
        Integer driverId2 = driverService.addDriver(driver2).getId();

        //When
        try {
            busService.changeDriverOnBus(busId, driverId);
            busService.changeDriverOnBus(busId1, driverId1);
            busService.changeDriverOnBus(busId, driverId1);

            //Then
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Driver " + driverId1 + " can't be on different Buses", e.getMessage());

            //When
            Bus dbBus = busService.changeDriverOnBus(busId, driverId2);

            //Then
            Assert.assertEquals(new Driver("MO8980", "Monty", "Dill"), dbBus.getDriver());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void changeDriverOnBusImportNull() {
        //When
        busService.changeDriverOnBus(null, null);

        //Then
        Assert.fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void changeDriverOnBusImportNotCorrectId() {
        //When
        busService.changeDriverOnBus(1, 0);

        //Then
        Assert.fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void changeDriverOnBusImportNotExistId() {
        //When
        busService.changeDriverOnBus(1, 1);

        //Then
        Assert.fail();
    }

    @Test
    public void findOneBusNoEntity() {
        //When
        Bus actualResult = busService.findOne(1);

        //Then
        Assert.assertEquals(null, actualResult);
    }

    @Test
    public void findOneBusWithEntity() {
        //Given
        Bus bus = new Bus("HHd777", "uU898");

        //When
        Integer busId = busService.addBus(bus).getId();

        //Then
        Assert.assertEquals(bus, busService.findOne(busId));
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