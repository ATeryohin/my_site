package unit.com.entity;

import com.entity.Driver;
import org.junit.Assert;
import org.junit.Test;

public class DriverTest {

    @Test
    public void newDriver(){
        //Given
        Driver driver = new Driver();

        //Then
        Assert.assertEquals(driver.getId(), null);
        Assert.assertEquals(driver.getLicense(), null);
        Assert.assertEquals(driver.getName(), null);
        Assert.assertEquals(driver.getSurname(), null);
    }

    @Test
    public void newDriverWithConstructor(){
        //Given
        Driver driver = new Driver("UYT6575", "Bob", "Nio");

        //Then
        Assert.assertEquals(driver.getId(), null);
        Assert.assertEquals(driver.getLicense(), "UYT6575");
        Assert.assertEquals(driver.getName(), "Bob");
        Assert.assertEquals(driver.getSurname(), "Nio");
    }

    @Test
    public void newDriverWithConstructorWithSetId(){
        //Given
        Driver driver = new Driver("UYT6575", "Bob", "Nio");

        //When
        driver.setId(1);

        //Then
        Assert.assertEquals(driver.getId(), new Integer(1));
        Assert.assertEquals(driver.getLicense(), "UYT6575");
        Assert.assertEquals(driver.getName(), "Bob");
        Assert.assertEquals(driver.getSurname(), "Nio");
    }

    @Test
    public void newDriverWithConstructorWithSetIdWithSetLicense(){
        //Given
        Driver driver = new Driver("UYT6575", "Bob", "Nio");

        //When
        driver.setId(1);
        driver.setLicense("RRTD898");

        //Then
        Assert.assertEquals(driver.getId(), new Integer(1));
        Assert.assertEquals(driver.getLicense(), "RRTD898");
        Assert.assertEquals(driver.getName(), "Bob");
        Assert.assertEquals(driver.getSurname(), "Nio");
    }

    @Test
    public void newDriverWithConstructorSetAll(){
        //Given
        Driver driver = new Driver();

        //When
        driver.setId(1);
        driver.setLicense("RRTD898");
        driver.setName("Bob");
        driver.setSurname("Wop");

        //Then
        Assert.assertEquals(driver.getId(), new Integer(1));
        Assert.assertEquals(driver.getLicense(), "RRTD898");
        Assert.assertEquals(driver.getName(), "Bob");
        Assert.assertEquals(driver.getSurname(), "Wop");
    }

    @Test
    public void twoDriversEquals(){
        //Given
        Driver driver = new Driver("UYT6575", "Bob", "Nio");
        Driver driver1 = new Driver("UYT6575", "Bob", "Nio");

        //Then
        Assert.assertEquals(driver, driver1);
    }

    @Test
    public void twoDriversEqualsOneWithId(){
        //Given
        Driver driver = new Driver("UYT6575", "Bob", "Nio");
        Driver driver1 = new Driver("UYT6575", "Bob", "Nio");

        //When
        driver.setId(1);

        //Then
        Assert.assertEquals(driver, driver1);
    }

    @Test
    public void twoDriversHashCode(){
        //Given
        Driver driver = new Driver("UYT6575", "Bob", "Nio");
        Driver driver1 = new Driver("UYT6575", "Bob", "Nio");

        //Then
        Assert.assertTrue(driver.hashCode() == driver1.hashCode());
    }

    @Test
    public void emptyDriverToString(){
        //Given
        Driver driver = new Driver();

        //Then
        String expected = "Driver{id=null, license='null', name='null', surname='null'}";
        Assert.assertEquals(expected, driver.toString());
    }

    @Test
    public void newDriverWithConstructorToString(){
        //Given
        Driver driver = new Driver("UYT6575", "Bob", "Nio");

        //Then
        String expected = "Driver{id=null, license='UYT6575', name='Bob', surname='Nio'}";
        Assert.assertEquals(expected, driver.toString());
    }

    @Test
    public void newDriverWithConstructorWithSetIdToString(){
        //Given
        Driver driver = new Driver("UYT6575", "Bob", "Nio");

        //When
        driver.setId(1);

        //Then
        String expected = "Driver{id=1, license='UYT6575', name='Bob', surname='Nio'}";
        Assert.assertEquals(expected, driver.toString());
    }
}