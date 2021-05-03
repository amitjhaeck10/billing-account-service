package com.appirio.service.test.dao;

import com.appirio.service.billingaccount.api.IdSequence;
import com.appirio.service.billingaccount.dao.SequenceDAO;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.skife.jdbi.v2.Query;
import org.junit.Test;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Query.class)
public class SequenceDAOTest extends GenericDAOTest {

    @Mock
    private SequenceDAO dao;

    @Test
    public void testGetIdSequence(){
        IdSequence idSequence = new IdSequence();
        when(dao.getIdSequence("123")).thenReturn(idSequence);

        assertNotNull(idSequence);
    }
}
