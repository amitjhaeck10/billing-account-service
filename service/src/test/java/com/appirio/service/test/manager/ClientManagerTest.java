package com.appirio.service.test.manager;

import com.appirio.service.billingaccount.api.BillingAccount;
import com.appirio.service.billingaccount.api.Client;
import com.appirio.service.billingaccount.api.PaymentTermsDTO;
import com.appirio.service.billingaccount.dao.ClientDAO;
import com.appirio.service.billingaccount.manager.ClientManager;
import com.appirio.service.test.dao.GenericDAOTest;
import com.appirio.supply.dataaccess.QueryResult;
import com.appirio.supply.dataaccess.db.IdGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.skife.jdbi.v2.Query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(PowerMockRunner.class)
public class ClientManagerTest extends GenericDAOTest{

    ClientDAO clientDAO = mock(ClientDAO.class);
    IdGenerator idGenerator = mock(IdGenerator.class);

    ClientManager clientManager = new ClientManager(clientDAO,idGenerator);

    @Test
    public void testFindAllClients(){
        QueryResult<List<Client>> expectedList = getListQueryResult();
        when(clientDAO.findAllClients(createQueryParam(""))).thenReturn(expectedList);
        QueryResult<List<Client>> result = clientManager.findAllClients(createQueryParam(""));
        assertNull(result);
    }

    @Test
    public void testAddNewClient(){
        Client expected = new Client(123L,"amit","jha",new Date(),new Date(),"James","Style");
        when(clientDAO.getClientById(123L)).thenReturn(expected);
        Client result = clientManager.getClientById(123L);
        assertNotNull(result);
    }

    private QueryResult<List<Client>> getListQueryResult() {
        List<Client> clients = new ArrayList<>();
        clients.add(new Client(123L,"amit","jha",new Date(),new Date(),"James","Style"));

        QueryResult<List<Client>> expected = new QueryResult<>();
        expected.setData(clients);
        return expected;
    }

}
