package unit.com.entity;

import com.entity.Ticket;
import org.junit.Assert;
import org.junit.Test;

public class TicketTest {

    @Test
    public void newTicket(){
        //Given
        Ticket ticket = new Ticket();

        //Then
        Assert.assertEquals(ticket.getId(), null);
        Assert.assertEquals(ticket.getPlace(), null);
        Assert.assertEquals(ticket.getPrice(), null);
        Assert.assertEquals(ticket.isPaid(), false);
        Assert.assertEquals(ticket.getVoyage(), null);
    }

    @Test
    public void newTicketWithConstructor(){
        //Given
        Ticket ticket = new Ticket(1, 30);

        //Then
        Assert.assertEquals(ticket.getId(), null);
        Assert.assertEquals(ticket.getPlace(), new Integer(1));
        Assert.assertEquals(ticket.getPrice(), new Integer(30));
        Assert.assertEquals(ticket.isPaid(), false);
        Assert.assertEquals(ticket.getVoyage(), null);
    }

    @Test
    public void newTicketWithConstructorWithSetId(){
        //Given
        Ticket ticket = new Ticket(1, 30);

        //When
        ticket.setId(1);

        //Then
        Assert.assertEquals(ticket.getId(), new Integer(1));
        Assert.assertEquals(ticket.getPlace(), new Integer(1));
        Assert.assertEquals(ticket.getPrice(), new Integer(30));
        Assert.assertEquals(ticket.isPaid(), false);
        Assert.assertEquals(ticket.getVoyage(), null);
    }

    @Test
    public void newTicketWithConstructorWithSetIdWithSetIsPaid(){
        //Given
        Ticket ticket = new Ticket(1, 30);

        //When
        ticket.setId(1);
        ticket.setPaid(true);

        //Then
        Assert.assertEquals(ticket.getId(), new Integer(1));
        Assert.assertEquals(ticket.getPlace(), new Integer(1));
        Assert.assertEquals(ticket.getPrice(), new Integer(30));
        Assert.assertEquals(ticket.isPaid(), true);
        Assert.assertEquals(ticket.getVoyage(), null);
    }

    @Test
    public void newTicketWithConstructorSetAll(){
        //Given
        Ticket ticket = new Ticket();

        //When
        ticket.setId(1);
        ticket.setPaid(true);
        ticket.setPrice(78);
        ticket.setPlace(2);

        //Then
        Assert.assertEquals(ticket.getId(), new Integer(1));
        Assert.assertEquals(ticket.getPlace(), new Integer(2));
        Assert.assertEquals(ticket.getPrice(), new Integer(78));
        Assert.assertEquals(ticket.isPaid(), true);
        Assert.assertEquals(ticket.getVoyage(), null);
    }

    @Test
    public void twoTicketEquals(){
        //Given
        Ticket ticket = new Ticket(1, 30);
        Ticket ticket1 = new Ticket(1, 30);

        //Then
        Assert.assertEquals(ticket, ticket1);
    }

    @Test
    public void twoTicketNotEquals(){
        //Given
        Ticket ticket = new Ticket(1, 30);
        Ticket ticket1 = new Ticket(1, 30);

        //When
        ticket1.setPaid(true);

        //Then
        Assert.assertNotEquals(ticket, ticket1);
    }

    @Test
    public void twoTicketEqualsOneWithId(){
        //Given
        Ticket ticket = new Ticket(1, 30);
        Ticket ticket1 = new Ticket(1, 30);

        //When
        ticket.setId(1);

        //Then
        Assert.assertEquals(ticket, ticket1);
    }

    @Test
    public void twoTicketHashCode(){
        //Given
        Ticket ticket = new Ticket(1, 30);
        Ticket ticket1 = new Ticket(1, 30);

        //Then
        Assert.assertTrue(ticket.hashCode() == ticket1.hashCode());
    }

    @Test
    public void emptyTicketToString(){
        //Given
        Ticket ticket= new Ticket();

        //Then
        String expected = "Ticket{id=null, place=null, price=null, isPaid=false}";
        Assert.assertEquals(expected, ticket.toString());
    }

    @Test
    public void newTicketWithConstructorToString(){
        //Given
        Ticket ticket = new Ticket(1, 30);

        //Then
        String expected = "Ticket{id=null, place=1, price=30, isPaid=false}";
        Assert.assertEquals(expected, ticket.toString());
    }

    @Test
    public void newTicketWithConstructorWithSetIdToString(){
        //Given
        Ticket ticket = new Ticket(1, 30);

        //When
        ticket.setId(1);

        //Then
        String expected = "Ticket{id=1, place=1, price=30, isPaid=false}";
        Assert.assertEquals(expected, ticket.toString());
    }
}