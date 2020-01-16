package unit.com.repositories;

import com.entity.Driver;
import com.repositories.DriverRepository;
import org.junit.Assert;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class DriverRepositoryTest {

    private DriverRepository driverRepository = mock(DriverRepository.class);

    @Test
    public void save(){
        //Given
        Driver driver = new Driver("TTER88", "Bob", "Marley");
        Driver driverDb = new Driver("TTER88", "Bob", "Marley");
        driverDb.setId(1);

        //When
        when(driverRepository.save(driver)).thenReturn(driverDb);

        //Then
        Assert.assertEquals(driverDb, driverRepository.save(driver));
    }

    @Test
    public void findOneByLicense(){
        //Given
        Driver driver = new Driver("TTER88", "Bob", "Marley");
        Driver driverDb = new Driver("TTER88", "Bob", "Marley");
        driverDb.setId(1);

        //When
        when(driverRepository.findOneByLicense("TTER88")).thenReturn(driverDb);

        //Then
        Assert.assertEquals(driverDb, driverRepository.findOneByLicense("TTER88"));
    }
}