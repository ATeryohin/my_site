package unit.com.entity;

import com.entity.Bus;
import com.entity.Ticket;
import com.entity.Voyage;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class VoyageTest {

    @Test
    public void newVoyage(){
        //Given
        Voyage voyage = new Voyage();

        //Then
        Assert.assertEquals(voyage.getId(), null);
        Assert.assertEquals(voyage.getNumber(), null);
        Assert.assertEquals(voyage.getBus(), null);
        Assert.assertEquals(voyage.getTickets(), null);
    }

    @Test
    public void newVoyageWithConstructor(){
        //Given
        Voyage voyage = new Voyage("FF7777JJ");

        //Then
        Assert.assertEquals(voyage.getId(), null);
        Assert.assertEquals(voyage.getNumber(), "FF7777JJ");
        Assert.assertEquals(voyage.getBus(), null);
        Assert.assertEquals(voyage.getTickets(), null);
    }

    @Test
    public void newVoyageWithConstructorSetBus(){
        //Given
        Voyage voyage = new Voyage("FF7777JJ");

        //When
        voyage.setBus(new Bus());

        //Then
        Assert.assertEquals(voyage.getId(), null);
        Assert.assertEquals(voyage.getNumber(), "FF7777JJ");
        Assert.assertEquals(voyage.getBus(), new Bus());
        Assert.assertEquals(voyage.getTickets(), null);
    }

    @Test
    public void newVoyageWithConstructorWithSetId(){
        //Given
        Voyage voyage = new Voyage("AH7788IO");

        //When
        voyage.setId(1);

        //Then
        Assert.assertEquals(voyage.getId(), new Integer(1));
        Assert.assertEquals(voyage.getNumber(), "AH7788IO");
        Assert.assertEquals(voyage.getBus(), null);
        Assert.assertEquals(voyage.getTickets(), null);
    }

    @Test
    public void newVoyageWithConstructorWithSetIdWithSetBus(){
        //Given
        Voyage voyage = new Voyage("AH7788IO");

        //When
        voyage.setId(1);
        voyage.setBus(new Bus());

        //Then
        Assert.assertEquals(voyage.getId(), new Integer(1));
        Assert.assertEquals(voyage.getNumber(), "AH7788IO");
        Assert.assertEquals(voyage.getBus(), new Bus());
        Assert.assertEquals(voyage.getTickets(), null);
    }

    @Test
    public void twoVoyagesEquals(){
        //Given
        Voyage voyage = new Voyage("AH7788IO");
        Voyage voyage2 = new Voyage("AH7788IO");

        //Then
        Assert.assertEquals(voyage, voyage2);
    }

    @Test
    public void twoVoyageEqualsOneWithId(){
        //Given
        Voyage voyage = new Voyage("AH7788IO");
        Voyage voyage1 = new Voyage("AH7788IO");

        //When
        voyage.setId(1);

        //Then
        Assert.assertEquals(voyage, voyage1);
    }

    @Test
    public void twoVoyagesNoEqualsOneWithBus(){
        //Given
        Voyage voyage = new Voyage("AH7788IO");
        Voyage voyage1 = new Voyage("AH7788IO");

        //When
        voyage.setBus(new Bus());

        //Then
        Assert.assertNotEquals(voyage, voyage1);
    }

    @Test
    public void twoVoyagesHashCode(){
        //Given
        Voyage voyage = new Voyage("AH7788IO");
        Voyage voyage1 = new Voyage("AH7788IO");

        //When
        voyage.setBus(new Bus());
        voyage1.setBus(new Bus());

        //Then
        Assert.assertTrue(voyage.hashCode() == voyage1.hashCode());
    }

    @Test
    public void twoVoyagesHashCodeOneWithoutBus(){
        //Given
        Voyage voyage = new Voyage("AH7788IO");
        Voyage voyage1 = new Voyage("AH7788IO");

        //When
        voyage.setBus(new Bus("YRYYR", "Tom"));

        //Then
        Assert.assertTrue(voyage.hashCode() != voyage1.hashCode());
    }

    @Test
    public void emptyVoyageToString(){
        //Given
        Voyage voyage = new Voyage();

        //Then
        String expected = "Voyage{id=null, number='null', bus=null, tickets=null}";
        Assert.assertEquals(expected, voyage.toString());
    }

    @Test
    public void newVoyageWithConstructorWithoutBusToString(){
        //Given
        Voyage voyage = new Voyage("AH7788IO");

        //Then
        String expected = "Voyage{id=null, number='AH7788IO', bus=null, tickets=null}";
        Assert.assertEquals(expected, voyage.toString());
    }

    @Test
    public void newVoyageWithConstructorWithBusToString(){
        //Given
        Voyage voyage = new Voyage("AH7788IO");

        //When
        voyage.setBus(new Bus());

        //Then
        String expected = "Voyage{id=null, number='AH7788IO', bus=Bus{id=null, number='null', model='null', driver=null}, tickets=null}";
        Assert.assertEquals(expected, voyage.toString());
    }

    @Test
    public void newVoyageWithConstructorWithSetIdToString(){
        //Given
        Voyage voyage = new Voyage("AH7788IO");

        //When
        voyage.setId(1);

        //Then
        String expected = "Voyage{id=1, number='AH7788IO', bus=null, tickets=null}";
        Assert.assertEquals(expected, voyage.toString());
    }

    @Test
    public void newVoyageWithFullConstructor(){
        //Given
        Voyage voyage = new Voyage("AH7788IO");
        Set<Ticket> tickets = new HashSet<>();
        for (int i = 1; i < 11; i++){
            tickets.add(new Ticket(i, 2000));
        }

        //When
        voyage.setBus(new Bus());
        voyage.setId(1);
        voyage.setTickets(tickets);

        //Then
        Assert.assertEquals(10, voyage.getTickets().size());
    }

    @Test
    public void newVoyageWithFullConstructorPaidOneTicket(){
        //Given
        Voyage voyage = new Voyage("AH7788IO");
        voyage.setBus(new Bus());
        voyage.setId(1);

        Set<Ticket> tickets = new HashSet<>();
        for (int i = 1; i < 12; i++){
            tickets.add(new Ticket(i, 2000));
        }
        voyage.setTickets(tickets);

        //When
        voyage.getTickets().iterator().next().setPaid(true);

        //Then
        Assert.assertTrue(voyage.getTickets().iterator().next().isPaid());
    }
}