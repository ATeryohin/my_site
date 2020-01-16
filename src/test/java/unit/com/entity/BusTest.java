package unit.com.entity;

import com.entity.Bus;
import com.entity.Driver;
import org.junit.Assert;
import org.junit.Test;

public class BusTest {

    @Test
    public void newBus(){
        //Given
        Bus bus = new Bus();

        //Then
        Assert.assertEquals(bus.getId(), null);
        Assert.assertEquals(bus.getNumber(), null);
        Assert.assertEquals(bus.getModel(), null);
        Assert.assertEquals(bus.getDriver(), null);
    }

    @Test
    public void newBusWithConstructor(){
        //Given
        Bus bus = new Bus("FF7777JJ", "RebBull");

        //Then
        Assert.assertEquals(bus.getId(), null);
        Assert.assertEquals(bus.getNumber(), "FF7777JJ");
        Assert.assertEquals(bus.getModel(), "RebBull");
        Assert.assertEquals(bus.getDriver(), null);
    }

    @Test
    public void newBusWithConstructorWithSetId(){
        //Given
        Bus bus = new Bus("AH7788IO", "Ferrari");

        //When
        bus.setId(1);

        //Then
        Assert.assertEquals(bus.getId(), new Integer(1));
        Assert.assertEquals(bus.getNumber(), "AH7788IO");
        Assert.assertEquals(bus.getModel(), "Ferrari");
        Assert.assertEquals(bus.getDriver(), null);
    }

    @Test
    public void newBusWithConstructorWithSetIdWithSetNumber(){
        //Given
        Bus bus = new Bus("AH7788IO", "Ferrari");

        //When
        bus.setId(1);
        bus.setNumber("AA5554JJ");

        //Then
        Assert.assertEquals(bus.getId(), new Integer(1));
        Assert.assertEquals(bus.getNumber(), "AA5554JJ");
        Assert.assertEquals(bus.getModel(), "Ferrari");
        Assert.assertEquals(bus.getDriver(), null);
    }

    @Test
    public void newBusWithConstructorSetDriver(){
        //Given
        Bus bus = new Bus("AH7788IO", "Ferrari");

        //When
        bus.setId(1);
        bus.setDriver(new Driver());

        //Then
        Assert.assertEquals(bus.getId(), new Integer(1));
        Assert.assertEquals(bus.getNumber(), "AH7788IO");
        Assert.assertEquals(bus.getModel(), "Ferrari");
        Assert.assertEquals(bus.getDriver(), new Driver());
    }

    @Test
    public void twoBusesEquals(){
        //Given
        Bus bus = new Bus("AH7788IO", "Ferrari");
        Bus bus2 = new Bus("AH7788IO", "Ferrari");

        //Then
        Assert.assertEquals(bus, bus2);
    }

    @Test
    public void twoBusesEqualsOneWithId(){
        //Given
        Bus bus = new Bus("AH7788IO", "Ferrari");
        Bus bus1 = new Bus("AH7788IO", "Ferrari");

        //When
        bus.setId(1);

        //Then
        Assert.assertEquals(bus, bus1);
    }

    @Test
    public void twoBusesNoEqualsOneWithDriver(){
        //Given
        Bus bus = new Bus("AH7788IO", "Ferrari");
        Bus bus1 = new Bus("AH7788IO", "Ferrari");

        //When
        bus.setDriver(new Driver());

        //Then
        Assert.assertNotEquals(bus, bus1);
    }

    @Test
    public void twoBusesHashCode(){
        //Given
        Bus bus = new Bus("AH7788IO", "Ferrari");
        Bus bus1 = new Bus("AH7788IO", "Ferrari");

        //When
        bus.setDriver(new Driver());
        bus1.setDriver(new Driver());

        //Then
        Assert.assertTrue(bus.hashCode() == bus1.hashCode());
    }

    @Test
    public void twoBusesHashCodeOneWithoutDriver(){
        //Given
        Bus bus = new Bus("AH7788IO", "Ferrari");
        Bus bus1 = new Bus("AH7788IO", "Ferrari");

        //When
        bus.setDriver(new Driver("YRYYR", "Tom", "Por"));

        //Then
        Assert.assertTrue(bus.hashCode() != bus1.hashCode());
    }

    @Test
    public void emptyBusToString(){
        //Given
        Bus bus = new Bus();
        String expected = "Bus{id=null, number='null', model='null', driver=null}";

        //Then
        Assert.assertEquals(expected, bus.toString());
    }

    @Test
    public void newBusWithConstructorWithoutDriverToString(){
        //Given
        Bus bus = new Bus("AH7788IO", "Ferrari");

        //Then
        String expected = "Bus{id=null, number='AH7788IO', model='Ferrari', driver=null}";
        Assert.assertEquals(expected, bus.toString());
    }

    @Test
    public void newBusWithConstructorWithDriverToString(){
        //Given
        Bus bus = new Bus("AH7788IO", "Ferrari");

        //When
        bus.setDriver(new Driver());

        //Then
        String expected = "Bus{id=null, number='AH7788IO', model='Ferrari', driver=Driver{id=null, license='null', name='null', surname='null'}}";
        Assert.assertEquals(expected, bus.toString());
    }

    @Test
    public void newDriverWithConstructorWithSetIdToString(){
        //Given
        Bus bus = new Bus("AH7788IO", "Ferrari");

        //When
        bus.setId(1);

        //Then
        String expected = "Bus{id=1, number='AH7788IO', model='Ferrari', driver=null}";
        Assert.assertEquals(expected, bus.toString());
    }
}