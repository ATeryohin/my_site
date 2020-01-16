package unit.com.services;

import com.entity.Driver;
import com.repositories.DriverRepository;

import com.services.DriverServiceImpl;
import com.services.RequestValidator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DriverServiceImplTest {

    private DriverRepository driverRepository = mock(DriverRepository.class);
    private RequestValidator requestValidator = mock(RequestValidator.class);

    private DriverServiceImpl driverService = new DriverServiceImpl();

    @Before
    public void start(){
        driverService.setRepository(driverRepository);
        driverService.setRequestValidator(requestValidator);
    }

    @Test
    public void addDriver(){
        //Given
        Driver driver = new Driver("UU88989", "Niki", "Lauda");
        Driver driver1 = new Driver("UU88989", "Niki", "Lauda");
        driver1.setId(1);

        //When
        when(driverRepository.save(driver)).thenReturn(driver1);

        //Then
        Assert.assertEquals(driver1.getId(), driverService.addDriver(new Driver("UU88989", "Niki", "Lauda")).getId());
    }

    @Test
    public void findOne(){
        //Given
        Driver driver1 = new Driver("UU88989", "Niki", "Lauda");
        driver1.setId(1);

        //When
        when(driverRepository.findOne(1)).thenReturn(driver1);

        //Then
        Assert.assertEquals(driver1, driverService.findOne(1));
    }

    @Test
    public void findAll(){
        //Given
        Driver driver = new Driver("RR4432", "Hol", "Tor");
        Driver driver1 = new Driver("UU88989", "Niki", "Lauda");

        //When
        when(driverRepository.findAll()).thenReturn(Arrays.asList(driver, driver1));

        //Then
        Assert.assertEquals(Arrays.asList(driver, driver1), driverService.findAll());
    }
}