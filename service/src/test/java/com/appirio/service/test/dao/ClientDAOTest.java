package com.appirio.service.test.dao;

import com.appirio.service.billingaccount.api.Client;
import com.appirio.service.billingaccount.dao.ClientDAO;
import com.appirio.supply.SupplyException;
import com.appirio.supply.dataaccess.QueryResult;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.skife.jdbi.v2.Query;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Query.class)
public class ClientDAOTest extends GenericDAOTest {
    
    private ClientDAO dao;

    /**
     * Setup the jdbi instance and required data
     *
     * @throws SupplyException Exception for supply
     */
    @Before
    public void before() throws SupplyException {
        List<Client> clients = new ArrayList<>();

        clients.add(new Client(1l, "james", "Active", new Date(), new Date(), "132456", "991234" ));
        clients.add(new Client(2l, "style", "Active", new Date(), new Date(), "123457", "991235"));

        List<Map<String, Object>> unmappedData = new ArrayList<Map<String, Object>>();
        unmappedData.add(new HashMap<>());
        unmappedData.get(0).put("ct", new BigDecimal(2));

        dao = createDAO(clients, unmappedData, ClientDAO.class);
    }


    @Test
    public void testFindAllClients() throws IOException {
        // Invoke method
        QueryResult<List<Client>> clients = dao.findAllClients(createQueryParam(""));

        // Verify result
        assertNotNull(clients);
        assertEquals(clients.getData().size(), 2);

        // Verify that JDBI was called
        verifyListObjectWithMetadataQuery(mocker);

        // Verify that the generated SQL file is as expected
        //verifyGeneratedSQL(mocker, "expected-sql/client/find-all-clients.sql", 0);
    }


    @Test
    public void testAddNewClient() throws IOException {
        // Invoke method
        dao.addNewClient(13L,"amit",1L ,new Date(),new Date(),"C001","user1","098765432");

        // Verify that JDBI was called
        verifySingleUpdate(mocker);

        // Verify that the generated SQL file is as expected
        verifyGeneratedSQL(mocker, "expected-sql/client/create-client.sql", 0);
    }

    @Test
    public void testUpdateClient() throws IOException {
        // Invoke method
        dao.updateClient(13L,"amit",1L ,new Date(),new Date(),"C001","user1","098765432");

        // Verify that JDBI was called
        verifySingleUpdate(mocker);

        // Verify that the generated SQL file is as expected
        verifyGeneratedSQL(mocker, "expected-sql/client/update-client.sql", 0);
    }

    @Test
    public void testGetClientById() throws IOException {
        // Invoke method
        Client client = dao.getClientById(11L);

        // Verify result
        assertNotNull(client);

        // Verify that JDBI was called
        //verifyListObjectWithMetadataQuery(mocker);

        // Verify that the generated SQL file is as expected
        verifyGeneratedSQL(mocker, "expected-sql/client/get-client-by-id.sql", 0);
    }

    @Test
    public void testGetClientByName() throws IOException {
        // Invoke method
        Client client = dao.getClientByName("James");

        // Verify result
        assertNotNull(client);

        // Verify that JDBI was called
        //verifyListObjectWithMetadataQuery(mocker);

        // Verify that the generated SQL file is as expected
        verifyGeneratedSQL(mocker, "expected-sql/client/get-client-by-name.sql", 0);
    }
}
