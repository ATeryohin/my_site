package integration.com.repositories;

import com.entity.Driver;
import com.repositories.DriverRepository;

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

@RunWith(SpringJUnit4ClassRunner.class)
@EnableAutoConfiguration
@EntityScan(basePackages = "com.entity")
@EnableJpaRepositories("com.repositories")
@Transactional
@TestPropertySource("classpath:application-test.properties")
public class DriverRepositoryIT {

    private DriverRepository driverRepository;

    @Test
    public void addDriver(){
        //Given
        Driver driver = new Driver("OKD1234567", "Harry", "Potter");

        //When
        Driver dbDriver = driverRepository.save(driver);

        //Then
        Assert.assertEquals(driver, dbDriver);
    }

    @Test
    public void createDriveReturnId(){
        //Given
        Driver driver = new Driver("OHHdk", "Hop", "Rock");

        //When
        driver = driverRepository.save(driver);

        //Then
        Assert.assertNotNull(driver.getId());
    }

    @Test
    public void driverAddNextDriver(){
        //Given
        Driver driver = new Driver("OHHdk", "Hop", "Rock");
        Driver driver1 = new Driver("YYUSF", "Por", "Sop");

        //When
        driver = driverRepository.save(driver);
        driver1 = driverRepository.save(driver1);

        //Then
        Integer result = driver1.getId();
        Assert.assertEquals(driver.getId(), --result);
    }

    @Test
    public void addDriveWithTheSameLicense(){
        //Given
        Driver driver = new Driver("RRRERR", "Joi", "Jingle");
        Driver driver2 = new Driver("RRRERR", "Joi", "Jingle");

        //When
        driver = driverRepository.save(driver);
        driver2 = driverRepository.save(driver2);

        //Then
        Assert.assertEquals(driver, driver2);
        Assert.assertNotSame(driver.getId(), driver2.getId());
    }

    @Test
    public void addDriveWithTheSameId(){
        //Given
        Driver driver = new Driver("RRRERR", "Joi", "Jingle");
        driver = driverRepository.save(driver);

        Driver driver2 = new Driver("TOTOTO", "Joi", "Jingle");
        driver2.setId(driver.getId());

        //When
        driverRepository.save(driver2);

        //Then
        Assert.assertEquals(driverRepository.findOne(driver.getId()), driver2);
    }

    @Test
    public void addDriveAndFindOneByLicense(){
        //Given
        Driver driver = new Driver("RRRERR", "Joi", "Jingle");

        //When
        driverRepository.save(driver);

        //Then
        Assert.assertEquals(driverRepository.findOneByLicense("RRRERR"), driver);
    }

    public DriverRepository getDriverRepository() {
        return driverRepository;
    }

    @Autowired
    public void setDriverRepository(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }
}