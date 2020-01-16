package unit.com.repositories;

import com.entity.Voyage;

import com.repositories.VoyageRepository;

import org.junit.Assert;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class VoyageRepositoryTest {

    private VoyageRepository voyageRepository = mock(VoyageRepository.class);

    @Test
    public void save(){
        //Given
        Voyage voyage = new Voyage("YYRET");
        Voyage voyageDb = new Voyage("YYRET");
        voyageDb.setId(1);

        //When
        when(voyageRepository.save(voyage)).thenReturn(voyageDb);

        //Then
        Assert.assertEquals(voyageDb, voyageRepository.save(voyage));
    }

    @Test
    public void findOneByNumber(){
        //Given
        Voyage voyageDb = new Voyage("YYRET");
        voyageDb.setId(1);

        //When
        when(voyageRepository.findOneByNumber("YYRET")).thenReturn(voyageDb);

        //Then
        Assert.assertEquals(voyageDb, voyageRepository.findOneByNumber("YYRET"));
    }
}