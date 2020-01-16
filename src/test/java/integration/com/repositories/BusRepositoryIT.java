package integration.com.repositories;

import com.entity.*;
import com.repositories.*;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@RunWith(SpringJUnit4ClassRunner.class)
@EnableAutoConfiguration
@EntityScan(basePackages = "com.entity")
@EnableJpaRepositories("com.repositories")
@Transactional
@TestPropertySource("classpath:application-test.properties")
public class BusRepositoryIT {

    private BusRepository busRepository;
    private DriverRepository driverRepository;

    @Test
    public void addBus(){
        //Given
        Bus bus = new Bus("AA1234AA", "Porsche");

        //When
        Bus dbBus = busRepository.save(bus);

        //Then
        Assert.assertEquals(bus, dbBus);
    }

    @Test
    public void updateDriverOnBus(){
        //Given
        Bus bus = new Bus();
        bus.setNumber("Try");
        bus.setModel("Catch");

        //When
        busRepository.save(bus);

        //Then
        Bus result = busRepository.findOneByNumber("Try");
        Assert.assertNull(result.getDriver());

        //Given
        Driver driver = new Driver("OHHdk", "Hop", "Rock");
        driver = driverRepository.save(driver);

        result.setDriver(driver);

        //When
        Bus secondResult = busRepository.save(result);

        //Then
        Assert.assertEquals(driver, secondResult.getDriver());
    }

    @Test
    public void addDriverAddBusesDeleteDriver(){
        //Given
        Driver driver = new Driver("OKD1234567", "Harry", "Potter");
        Driver driver1 = new Driver("IUD9987989", "Tom", "King");

        Driver dbDriver = driverRepository.save(driver);
        driverRepository.save(driver1);

        Bus bus = new Bus();
        bus.setNumber("AA9999BB");
        bus.setModel("Ferrari");

        Bus bus1 = new Bus();
        bus1.setNumber("KK7777OO");
        bus1.setModel("Lada");

        Bus dbBus = busRepository.save(bus);
        Bus dbBus1 = busRepository.save(bus1);

        dbBus.setDriver(driver);
        dbBus1.setDriver(driver1);

        dbBus = busRepository.save(dbBus);
        busRepository.save(dbBus1);

        dbBus.setDriver(null);

        //When
        dbBus = busRepository.save(dbBus);

        driverRepository.delete(dbDriver.getId());

        //Then
        Assert.assertEquals(dbBus.getDriver(), null);
    }

    @Test
    public void createBusReturnId(){
        //Given
        Bus bus = new Bus("AA1234AA", "Porsche");

        //When
        Bus dbBus = busRepository.save(bus);

        //Then
        Assert.assertNotNull(dbBus.getId());
    }

    @Test
    public void busAddNextBus(){
        //Given
        Bus bus = new Bus("AA1234AA", "RedBull");
        Bus bus1 = new Bus("BB1555CA", "Tor");

        //When
        bus = busRepository.save(bus);
        bus1 = busRepository.save(bus1);

        //Then
        Integer result = bus1.getId();
        Assert.assertEquals(bus.getId(), --result);
    }

    @Test
    public void addBusWithTheSameNumber(){
        //Given
        Bus bus = new Bus("AA1234AA", "RedBull");
        Bus bus1 = new Bus("AA1234AA", "RedBull");

        //When
        bus = busRepository.save(bus);
        bus1 = busRepository.save(bus1);

        //Then
        Assert.assertEquals(bus, bus1);
        Assert.assertNotSame(bus.getId(), bus1.getId());
    }

    @Test
    public void addBusWithTheSameId(){
        //Given
        Bus bus = new Bus("AA1234AA", "RedBull");
        bus = busRepository.save(bus);

        Bus bus1 = new Bus("CC4343TT", "Tot");
        bus1.setId(bus.getId());

        //When
        busRepository.save(bus1);

        //Then
        Assert.assertEquals(busRepository.findOne(bus.getId()), bus1);
    }

    @Test
    public void addBusAndFindOneByNumber(){
        //Given
        Bus bus = new Bus("AA1234AA", "RedBull");

        //When
        busRepository.save(bus);

        //Then
        Assert.assertEquals(busRepository.findOneByNumber("AA1234AA"), bus);
    }

    @Test
    public void addBusAddDriverAndChangeDriver(){
        //Given
        Bus bus = new Bus("AA1234AA", "RedBull");
        bus.setDriver( new Driver("TTE93993", "Rock", "Roll"));
        busRepository.save(bus);

        //When
        Bus dbBus = busRepository.findOneByNumber("AA1234AA");

        //Then
        Assert.assertEquals(dbBus, bus);

        //Given
        Driver driver =  new Driver("BBVSV009", "Donald", "Duck");
        dbBus.setDriver(driver);

        //When
        busRepository.save(dbBus);

        //Then
        Assert.assertEquals(dbBus.getDriver(), driver);

        //When
        ArrayList<Driver> drivers = (ArrayList<Driver>)driverRepository.findAll();

        //Then
        Assert.assertTrue(drivers.size() == 2);
    }

    @Autowired
    public void setBusRepository(BusRepository busRepository) {
        this.busRepository = busRepository;
    }

    @Autowired
    public void setDriverRepository(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }
}