/*
 * Copyright (C) 2017 TopCoder Inc., All Rights Reserved.
 */
package com.appirio.service.test.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.appirio.service.billingaccount.api.*;
import com.appirio.service.billingaccount.dao.BillingAccountDAO;
import com.appirio.service.billingaccount.dto.TCUserDTO;
import com.appirio.supply.dataaccess.QueryResult;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.skife.jdbi.v2.Query;

import com.appirio.supply.SupplyException;

/**
 * Test BillingAccountDAO.
 * <p>
 *  Changes in v 1.1 "FAST 72HRS!! - ADD APIS FOR CLIENTS AND SOME LOGIC CHANGES"
 *   -- Updated to take into consideration the newly added fields to BliingAccount.
 * </p>
 *
 *  <p>
 *  Changes in v 1.2 Fast 48hrs!! Topcoder - Improvement For Billing Account Service
 *  -- Updated to take into consideration the newly added clientId field.
 * </p>
 *
 * @author TCSCODER, TCSCODER.
 * @version 1.2
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Query.class)
public class BillingAccountDAOTest extends GenericDAOTest {

    private BillingAccountDAO dao;

    /**
     * Setup the jdbi instance and required data
     *
     * @throws SupplyException Exception for supply
     */
    @Before
    public void before() throws SupplyException {
        List<BillingAccount> billingAccounts = new ArrayList<>();

        billingAccounts.add(new BillingAccount(1l, "1", "Active", new Date(), new Date(), 500.0f, 1.0f, "po1",
   		    new PaymentTermsDTO(1l, "30 Days"), "description1", "subscription#1", 1l, 0l, 1l, false ));
        billingAccounts.add(new BillingAccount(2l, "2", "Active", new Date(), new Date(), 500.0f, 1.0f, "po2",
   		    new PaymentTermsDTO(1l, "30 Days"), "description2", "subscription#2", 1l, 0l, 1l, false));

        List<Map<String, Object>> unmappedData = new ArrayList<Map<String, Object>>();
        unmappedData.add(new HashMap<>());
        unmappedData.get(0).put("ct", new BigDecimal(2));

        dao = createDAO(billingAccounts, unmappedData, BillingAccountDAO.class);
    }

    @Test
    public void testSearchBillingAccounts() throws IOException {
        // Invoke method
        QueryResult<List<BillingAccount>> billingAccounts = dao.searchBillingAccounts(createQueryParam(""));

        // Verify result
        assertNotNull(billingAccounts);
        assertEquals(billingAccounts.getData().size(), 2);

        // Verify that JDBI was called
        verifyListObjectWithMetadataQuery(mocker);

        // Verify that the generated SQL file is as expected
        //verifyGeneratedSQL(mocker, "expected-sql/billing-account/search-billing-accounts.sql", 0);
    }

    @Test
    public void testSearchMyBillingAccounts() throws IOException {
        // Invoke method
        QueryResult<List<BillingAccount>> billingAccounts = dao.searchMyBillingAccounts(1l, createQueryParam(""));

        // Verify result
        assertNotNull(billingAccounts);
        assertEquals(billingAccounts.getData().size(), 2);

        // Verify that JDBI was called
        verifyListObjectWithMetadataQuery(mocker);

        // Verify that the generated SQL file is as expected
        //verifyGeneratedSQL(mocker, "expected-sql/billing-account/search-my-billing-accounts.sql", 0);
    }

    @Test
    public void testCreateBillingAccount() throws IOException {
        // Invoke method
        dao.createBillingAccount(1l, 500.0f, "testNew", 1l,new Date(), new Date(), 1l, "test", 1.0f, "po1","desc1","subscr1", 1l, 0l, 1l, false);

        // Verify that JDBI was called
        verifySingleUpdate(mocker);

        // Verify that the generated SQL file is as expected
        verifyGeneratedSQL(mocker, "expected-sql/billing-account/create-billing-account.sql", 0);
    }

    @Test
    public void testGetBillingAccount() throws IOException {
        // Invoke method
        QueryResult<List<BillingAccount>> billingAccounts = dao.getBillingAccount(1l);

        // Verify result
        assertNotNull(billingAccounts);
        assertEquals(billingAccounts.getData().size(), 2);

        // Verify that JDBI was called
        verifyListObjectWithMetadataQuery(mocker);

        // Verify that the generated SQL file is as expected
        verifyGeneratedSQL(mocker, "expected-sql/billing-account/get-billing-account-by-id.sql", 0);
    }

    @Test
    public void testGetBillingAccountUsers() throws IOException {
        // Invoke method
        QueryResult<List<BillingAccountUser>> billingAccounts = dao.getBillingAccountUsers(1l, createQueryParam(""));

        // Verify result
        assertNotNull(billingAccounts);
        assertEquals(billingAccounts.getData().size(), 2);

        // Verify that JDBI was called
        verifyListObjectWithMetadataQuery(mocker);

        // Verify that the generated SQL file is as expected
        //verifyGeneratedSQL(mocker, "expected-sql/users/get-users-from-billing-account.sql", 0);
    }

    @Test
    public void testUpdateBillingAccount() throws IOException {
        // Invoke method
        dao.updateBillingAccount(11L,230.10f,"amit",1234L,new Date(),new Date(),1L,"abc123"
        ,123.45f,"908765432","45678","98765",90L,76543L,234L,true);

        // Verify that JDBI was called
        verifySingleUpdate(mocker);

        // Verify that the generated SQL file is as expected
        verifyGeneratedSQL(mocker, "expected-sql/billing-account/update-billing-account.sql", 0);
    }


    @Test
    public void testAddUserToBillingAccount() throws IOException {
        // Invoke method
        dao.addUserToBillingAccount(1l, 1l, "test");

        // Verify that JDBI was called
        verifySingleUpdate(mocker);

        // Verify that the generated SQL file is as expected
        verifyGeneratedSQL(mocker, "expected-sql/users/add-user-to-billing-account.sql", 0);
    }

    @Test
    public void testRemoveUserFromBillingAccount() throws IOException {
        // Invoke method
        dao.removeUserFromBillingAccount(1l, 1l);

        // Verify that JDBI was called
        verifySingleUpdate(mocker);

        // Verify that the generated SQL file is as expected
        verifyGeneratedSQL(mocker, "expected-sql/users/remove-user-from-billing-account.sql", 0);
    }

    @Test
    public void testCheckUserExists() throws IOException, SupplyException {
        buildBillingdAccountDAOForIDTO();
        // Invoke method
        IdDTO idDTO = dao.checkUserExists("amit");

        // Verify result
        assertNotNull(idDTO);

        // Verify that JDBI was called
        // verifyListObjectWithMetadataQuery(mocker);

        // Verify that the generated SQL file is as expected
        verifyGeneratedSQL(mocker, "expected-sql/users/user-exists.sql", 0);
    }

    @Test
    public void testCheckCompanyExists() throws IOException, SupplyException {
        buildBillingdAccountDAOForIDTO();
        // Invoke method
        IdDTO idDTO = dao.checkCompanyExists(1234L);

        // Verify result
        assertNotNull(idDTO);

        // Verify that JDBI was called
        //verifyListObjectWithMetadataQuery(mocker);

        // Verify that the generated SQL file is as expected
        verifyGeneratedSQL(mocker, "expected-sql/billing-account/check-company-exists.sql", 0);
    }

    @Test
    public void testGetTCUserById() throws IOException, SupplyException {
        buildBillingAccountDAOForTCUserId();
        // Invoke method
        TCUserDTO tCUserDTO = dao.getTCUserById(1234L);

        // Verify result
        assertNotNull(tCUserDTO);

        // Verify that JDBI was called
        //verifyListObjectWithMetadataQuery(mocker);

        // Verify that the generated SQL file is as expected
        verifyGeneratedSQL(mocker, "expected-sql/users/get-tc-user-by-id.sql", 0);
    }

    @Test
    public void testCreateUserAccount() throws IOException, SupplyException {
        buildBillingAccountDAOForTCUserId();
        // Invoke method
        dao.createUserAccount(1234L,"james","1098");

        // Verify that JDBI was called
        //verifyListObjectWithMetadataQuery(mocker);

        // Verify that the generated SQL file is as expected
        verifyGeneratedSQL(mocker, "expected-sql/users/create-user-account.sql", 0);
    }

    @Test
    public void testCheckUserBelongsToBillingAccount() throws IOException, SupplyException {
        buildBillingdAccountDAOForIDTO();
        //Invoke method
        IdDTO idDTO = dao.checkUserBelongsToBillingAccount(1234L,5678L);

        //Verify result
        assertNotNull(idDTO);

        //Verify that JDBI was called
        //verifyListObjectWithMetadataQuery(mocker);

        //Verify that the generated SQL file is as expected
        verifyGeneratedSQL(mocker, "expected-sql/users/check-user-belongs-to-billing-account.sql", 0);
    }

    @Test
    public void testCheckClientExists() throws IOException, SupplyException {
        buildBillingdAccountDAOForIDTO();
        //Invoke method
        IdDTO idDTO = dao.checkClientExists(1234L);

        //Verify result
        assertNotNull(idDTO);

        //Verify that JDBI was called
        //verifyListObjectWithMetadataQuery(mocker);

        //Verify that the generated SQL file is as expected
        verifyGeneratedSQL(mocker, "expected-sql/billing-account/check-client-exists.sql", 0);
    }

    @Test
    public void testAddBillingAccountToClient() throws IOException, SupplyException {
        //Invoke method
        dao.addBillingAccountToClient(1234L,6789L,"abc123");

        //Verify that JDBI was called
        //verifyListObjectWithMetadataQuery(mocker);

        //Verify that the generated SQL file is as expected
        verifyGeneratedSQL(mocker, "expected-sql/billing-account/add-billing-account-to-client.sql", 0);
    }

    @Test
    public void testRemoveBillingAccountFromClient() throws IOException {
        //Invoke method
        dao.removeBillingAccountFromClient(1234L);

        //Verify that JDBI was called
        //verifyListObjectWithMetadataQuery(mocker);

        //Verify that the generated SQL file is as expected
        verifyGeneratedSQL(mocker, "expected-sql/billing-account/remove-billing-account-from-client.sql", 0);
    }

    @Test
    public void testCreateChallengeFee() throws IOException {
        //Invoke method
        dao.createChallengeFee(1234L,1098L,1,2301L,1223.23,6785L,"James",false);

        //Verify that JDBI was called
        //verifyListObjectWithMetadataQuery(mocker);

        //Verify that the generated SQL file is as expected
        verifyGeneratedSQL(mocker, "expected-sql/billing-account/challenge-fees/create-challenge-fee.sql", 0);
    }

    @Test
    public void testUpdateChallengeFee() throws IOException {
        //Invoke method
        dao.updateChallengeFee(1234L,1098L,1,2301L,1223.23,6785L,"James",false);

        //Verify that JDBI was called
        //verifyListObjectWithMetadataQuery(mocker);

        //Verify that the generated SQL file is as expected
        verifyGeneratedSQL(mocker, "expected-sql/billing-account/challenge-fees/update-challenge-fee.sql", 0);
    }

    @Test
    public void testCheckChallengeFeeExists() throws IOException, SupplyException {
        buildBillingdAccountDAOForIDTO();
        //Invoke method
        IdDTO idDTO= dao.checkChallengeFeeExists(1234L);

        //Verify result
        assertNotNull(idDTO);

        //Verify that JDBI was called
        //verifyListObjectWithMetadataQuery(mocker);

        //Verify that the generated SQL file is as expected
        verifyGeneratedSQL(mocker, "expected-sql/billing-account/challenge-fees/check-challenge-fee-exists.sql", 0);
    }

    @Test
    public void testCreateChallengeFeePercentage() throws IOException {
        //Invoke method
        dao.createChallengeFeePercentage(123L,456L,500.50,true,9876L);

        //Verify that JDBI was called
        //verifyListObjectWithMetadataQuery(mocker);

        //Verify that the generated SQL file is as expected
        verifyGeneratedSQL(mocker, "expected-sql/billing-account/challenge-fees/create-challenge-fee-percentage.sql", 0);
    }

    @Test
    public void testGetChallengeFeePercentage() throws IOException, SupplyException {
        buildBillingAccntDAOForChallengeFeePcntg();
        //Invoke method
        ChallengeFeePercentage ChallengeFeePercentage = dao.getChallengeFeePercentage(123L);

        //Verify result
        assertNotNull(ChallengeFeePercentage);

        //Verify that JDBI was called
        //verifyListObjectWithMetadataQuery(mocker);

        //Verify that the generated SQL file is as expected
        verifyGeneratedSQL(mocker, "expected-sql/billing-account/challenge-fees/get-challenge-fee-percentage.sql", 0);
    }

    @Test
    public void testUpdateChallengeFeePercentage() throws IOException {
        //Invoke method
        dao.updateChallengeFeePercentage(123L,456L,650.50,true,1234L);

        //Verify that JDBI was called
        //verifyListObjectWithMetadataQuery(mocker);

        //Verify that the generated SQL file is as expected
        verifyGeneratedSQL(mocker, "expected-sql/billing-account/challenge-fees/update-challenge-fee-percentage.sql", 0);
    }

    @Test
    public void testGetProjectCategoriesReplatforming() throws IOException {
        //Invoke method
        List<ChallengeType>  challengeTypeList = dao.getProjectCategoriesReplatforming();

        //Verify result
        assertNotNull(challengeTypeList);

        //Verify that JDBI was called
        //verifyListObjectWithMetadataQuery(mocker);

        //Verify that the generated SQL file is as expected
        verifyGeneratedSQL(mocker, "expected-sql/billing-account/challenge-fees/get-project-categories-replatforming.sql", 0);
    }

    @Test
    public void testCheckBillingAccountExists() throws IOException, SupplyException {
        buildBillingdAccountDAOForIDTO();
        //Invoke method
        IdDTO  idDTO = dao.checkBillingAccountExists(1234L);

        //Verify result
        assertNotNull(idDTO);

        //Verify that JDBI was called
        //verifyListObjectWithMetadataQuery(mocker);

        //Verify that the generated SQL file is as expected
        verifyGeneratedSQL(mocker, "expected-sql/billing-account/challenge-fees/check-billing-account-exists.sql", 0);
    }

    @Test
    public void testDeleteChallengeFee() throws IOException {
        //Invoke method
        dao.deleteChallengeFee(createQueryParam(""));

        //Verify that JDBI was called
        //verifyListObjectWithMetadataQuery(mocker);

        //Verify that the generated SQL file is as expected
        //verifyGeneratedSQL(mocker, "expected-sql/billing-account/challenge-fees/delete-challenge-fee.sql", 0);
    }

    public void buildBillingdAccountDAOForIDTO() throws SupplyException {
        List<IdDTO> idtos = new ArrayList<IdDTO>();

        idtos.add(new IdDTO(1l));
        idtos.add(new IdDTO(2l));

        List<Map<String, Object>> unmappedData = new ArrayList<Map<String, Object>>();
        unmappedData.add(new HashMap<>());
        unmappedData.get(0).put("ct", new BigDecimal(2));

        dao = createDAO(idtos, unmappedData, BillingAccountDAO.class);
    }

    public void buildBillingAccountDAOForTCUserId() throws SupplyException {
        List<TCUserDTO> tcUserDTOs = new ArrayList<TCUserDTO>();

        tcUserDTOs.add(new TCUserDTO(1l,"amit"));
        tcUserDTOs.add(new TCUserDTO(2l,"jha"));

        List<Map<String, Object>> unmappedData = new ArrayList<Map<String, Object>>();
        unmappedData.add(new HashMap<>());
        unmappedData.get(0).put("ct", new BigDecimal(2));

        dao = createDAO(tcUserDTOs, unmappedData, BillingAccountDAO.class);
    }

    public void buildBillingAccntDAOForChallengeFeePcntg() throws SupplyException {
        List<ChallengeFeePercentage> challengeFeePercentageList = new ArrayList<ChallengeFeePercentage>();

        challengeFeePercentageList.add(new ChallengeFeePercentage(123L,456L,535.50,true));
        challengeFeePercentageList.add(new ChallengeFeePercentage(124L,789L,647.60,true));

        List<Map<String, Object>> unmappedData = new ArrayList<Map<String, Object>>();
        unmappedData.add(new HashMap<>());
        unmappedData.get(0).put("ct", new BigDecimal(2));

        dao = createDAO(challengeFeePercentageList, unmappedData, BillingAccountDAO.class);
    }
}
