package unit.com.repositories;

import com.entity.Bus;

import com.repositories.BusRepository;
import org.junit.Assert;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BusRepositoryTest {

    private BusRepository busRepository = mock(BusRepository.class);

    @Test
    public void save() {
        //Given
        Bus dbBus = new Bus("AA1234AA", "Porsche");
        dbBus.setId(1);

        //When
        when(busRepository.save(new Bus("AA1234AA", "Porsche"))).thenReturn(dbBus);

        //Then
        Assert.assertEquals(
                busRepository.save(new Bus("AA1234AA", "Porsche")).getId(),
                new Integer(1));

    }

    @Test
    public void findOneByNumber(){
        //Given
        Bus bus = new Bus("AA0009OO", "Ferrari");
        Bus busDb = new Bus("AA0009OO", "Ferrari");
        busDb.setId(1);

        //When
        when(busRepository.findOneByNumber("AA0009OO")).thenReturn(busDb);

        //Then
        Assert.assertEquals(busDb, busRepository.findOneByNumber("AA0009OO"));
    }
}