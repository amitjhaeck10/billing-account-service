/*
 * Copyright (C) 2017 TopCoder Inc., All Rights Reserved.
 */

package com.appirio.service.test.manager;

import com.appirio.service.billingaccount.api.*;
import com.appirio.service.billingaccount.dao.BillingAccountDAO;
import com.appirio.service.billingaccount.dao.SequenceDAO;
import com.appirio.service.billingaccount.dto.TCUserDTO;
import com.appirio.service.billingaccount.manager.BillingAccountManager;
import com.appirio.service.test.dao.GenericDAOTest;
import com.appirio.supply.SupplyException;
import com.appirio.supply.dataaccess.QueryResult;
import com.appirio.supply.dataaccess.db.IdGenerator;
import com.appirio.tech.core.api.v3.TCID;
import com.appirio.tech.core.auth.AuthUser;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(PowerMockRunner.class)
public class BillingAccountManagerTest extends GenericDAOTest {

    private BillingAccountDAO billingAccountDAO = mock(BillingAccountDAO.class);
    private IdGenerator generator = mock(IdGenerator.class);
    private SequenceDAO sequenceDAO = mock(SequenceDAO.class);
    private BillingAccountManager unit = new BillingAccountManager(billingAccountDAO, generator, generator, sequenceDAO);

    @Test
    public void testSearchBillingAccounts() {
        QueryResult<List<BillingAccount>> expected = getListQueryResult();
        when(billingAccountDAO.searchBillingAccounts(anyObject())).thenReturn(expected);
        QueryResult<List<BillingAccount>> result = unit.searchBillingAccounts(createQueryParam(""));
        assertEquals(2, result.getData().size());
        verify(billingAccountDAO).searchBillingAccounts(anyObject());
    }

    @Test
    public void testSearchMyBillingAccounts() {
        QueryResult<List<BillingAccount>> expected = getListQueryResult();
        when(billingAccountDAO.searchMyBillingAccounts(anyObject(), anyObject())).thenReturn(expected);
        QueryResult<List<BillingAccount>> result = unit.searchMyBillingAccounts(1L, createQueryParam(""));
        assertEquals(2, result.getData().size());
        verify(billingAccountDAO).searchMyBillingAccounts(anyObject(), anyObject());
    }


    public void testCreateBillingAccount() throws SupplyException {
        QueryResult<List<BillingAccount>> expected = getListQueryResult();
        expected.getData().remove(1);

        when(billingAccountDAO.getBillingAccount(anyObject())).thenReturn(expected);
        when(billingAccountDAO.createBillingAccount(anyObject(),
                anyObject(),
                anyObject(),
                anyObject(),
                anyObject(),
                anyObject(),
                anyObject(),
                anyObject(),
                anyObject(),
                anyObject(),
                anyObject(),
                anyObject(),
                anyObject(),
                anyObject(),
                anyObject(), false)).thenReturn(1L);

        when(generator.getNextId()).thenReturn(1L);
        when(billingAccountDAO.checkCompanyExists(anyObject())).thenReturn(new IdDTO());

        BillingAccount test = new BillingAccount();
        test.setPaymentTerms(new PaymentTermsDTO());

        QueryResult<List<BillingAccount>> result = unit.createBillingAccount(new AuthUser(), test);
        assertEquals(1, result.getData().size());

        verify(generator).getNextId();
        verify(billingAccountDAO).createBillingAccount(anyObject(),
                anyObject(),
                anyObject(),
                anyObject(),
                anyObject(),
                anyObject(),
                anyObject(),
                anyObject(),
                anyObject(),
                anyObject(),
                anyObject(),
                anyObject(),
                anyObject(),
                anyObject(),
                anyObject(), false);
        verify(billingAccountDAO).getBillingAccount(anyObject());
    }

    @Test
    public void testGetBillingAccount() {
        QueryResult<List<BillingAccount>> expected = getListQueryResult();
        expected.getData().remove(1);

        when(billingAccountDAO.getBillingAccount(anyObject())).thenReturn(expected);
        QueryResult<List<BillingAccount>> result = unit.getBillingAccount(1L);
        assertEquals(1, result.getData().size());
        verify(billingAccountDAO).getBillingAccount(anyObject());
    }


    public void testUpdateBillingAccount() throws SupplyException {
        QueryResult<List<BillingAccount>> expected = getListQueryResult();
        expected.getData().remove(1);

        when(billingAccountDAO.getBillingAccount(anyObject())).thenReturn(expected);

        BillingAccount test = new BillingAccount();
        test.setPaymentTerms(new PaymentTermsDTO());
        QueryResult<List<BillingAccount>> result = unit.updateBillingAccount(new AuthUser(), test);
        assertEquals(1, result.getData().size());
        verify(billingAccountDAO).updateBillingAccount(anyObject(),
                anyObject(),
                anyObject(),
                anyObject(),
                anyObject(),
                anyObject(),
                anyObject(),
                anyObject(),
                anyObject(),
                anyObject(),
                anyObject(),
                anyObject(),
                anyObject(),
                anyObject(),
                anyObject(), false);
        verify(billingAccountDAO).getBillingAccount(anyObject());
    }

    @Test
    public void testGetBillingAccountUsers() {
        QueryResult<List<BillingAccountUser>> expected = new QueryResult<>(new ArrayList<>());
        when(billingAccountDAO.getBillingAccountUsers(anyObject(), anyObject())).thenReturn(expected);
        QueryResult<List<BillingAccountUser>> result = unit.getBillingAccountUsers(1L, createQueryParam(""));
        assertEquals(0, result.getData().size());
        verify(billingAccountDAO).getBillingAccountUsers(anyObject(), anyObject());
    }

    @Test
    public void testRemoveBillingAccountUsers() throws SupplyException {
        TCUserDTO expectedUser = new TCUserDTO(1L,"handle");
        when(billingAccountDAO.getTCUserById(anyObject())).thenReturn(expectedUser);

        IdDTO expectedId = new IdDTO(1L);
        when(billingAccountDAO.checkUserExists(anyObject())).thenReturn(expectedId);

        IdDTO expectedProjectMngId = new IdDTO(2L);
        when(billingAccountDAO.checkUserBelongsToBillingAccount(anyObject(),anyObject())).thenReturn(expectedProjectMngId);

        unit.removeUserFromBillingAccount(1L, 1L);
        verify(billingAccountDAO).removeUserFromBillingAccount(anyObject(), anyObject());
    }

    @Test
    public void testAddUserToBillingAccount() throws SupplyException {
        QueryResult<List<BillingAccount>> expected = getListQueryResult();
        QueryResult<List<BillingAccountUser>> expectedUsers = new QueryResult<>(new ArrayList<>());
        IdDTO expectedId = new IdDTO(1L);
        TCUserDTO expectedUser = new TCUserDTO(1L,"handle");
        expected.getData().remove(1);

        when(billingAccountDAO.getBillingAccount(anyObject())).thenReturn(expected);
        when(billingAccountDAO.checkUserExists(anyObject())).thenReturn(expectedId);
        //when(billingAccountDAO.addUserToBillingAccount(123L,124L,"James")).thenReturn(expectedUsers);
        when(billingAccountDAO.getTCUserById(anyObject())).thenReturn(expectedUser);

        IdSequence idSequence = new IdSequence();
        idSequence.setNextId(2L);
        when(sequenceDAO.getIdSequence("project_contest_fee_seq")).thenReturn(idSequence);

        AuthUser authUser = new Authorization();
        ((Authorization) authUser).setUserId(new TCID("123"));

        QueryResult<List<BillingAccount>> result = unit.addUserToBillingAccount(authUser, 1L, 1L);

        assertEquals(1, result.getData().size());
        verify(billingAccountDAO).addUserToBillingAccount(anyObject(), anyObject(), anyObject());
        verify(billingAccountDAO).getBillingAccount(anyObject());
        verify(billingAccountDAO).checkUserExists(anyObject());
        //verify(billingAccountDAO).getBillingAccountUsers(anyObject(), createQueryParam(""));
    }

    //@Test
    public void testCreateBillingAccountFees() throws SupplyException {

        IdDTO expectedId = new IdDTO(1L);
        when(billingAccountDAO.checkBillingAccountExists(123L)).thenReturn(expectedId);

        IdDTO expectId = new IdDTO(0L);
        when(billingAccountDAO.checkChallengeFeeExists(123L)).thenReturn(expectId);

        ChallengeFeePercentage expectedPercentage = new ChallengeFeePercentage();
        when(billingAccountDAO.getChallengeFeePercentage(456L)).thenReturn(expectedPercentage);

        IdSequence idSequence = new IdSequence();
        idSequence.setNextId(2L);
        when(sequenceDAO.getIdSequence("project_contest_fee_seq")).thenReturn(idSequence);
        //when(sequenceDAO.getIdSequence("project_contest_fee_seq").getNextId()).thenReturn(2L);
        //when(idSequence.getNextId()).thenReturn(1L);

        AuthUser authUser = new Authorization();
        ((Authorization) authUser).setUserId(new TCID("123"));

        ChallengeFee challengeFee = new ChallengeFee(123L,567L,789L,45.56 ,true,"Coding",false,"JUnit");
        List<ChallengeFee> challengeFeeList = new ArrayList<>();
        challengeFeeList.add(challengeFee);

        BillingAccountFees billingAccountFees = new BillingAccountFees(true,null,challengeFeeList);
        when(unit.createBillingAccountFees(authUser,billingAccountFees,123L)).thenThrow(new SupplyException());

        verify(billingAccountDAO).checkBillingAccountExists(anyObject());
        verify(billingAccountDAO).getChallengeFeePercentage(anyObject());
        verify(billingAccountDAO).createChallengeFeePercentage(anyObject(),anyObject(),anyObject(),anyObject(),anyObject());
    }

    //@Test
    public void testUpdateBillingAccountFees() throws SupplyException {
        ChallengeFeePercentage expectedPercentage = new ChallengeFeePercentage(123L,567L,560.46,true);
        when(billingAccountDAO.getChallengeFeePercentage(123L)).thenReturn(expectedPercentage);

        ChallengeFee challengeFee = new ChallengeFee(123L,567L,789L,45.56 ,true,"Coding",false,"JUnit");
        List<ChallengeFee> challengeFeeList = new ArrayList<>();
        challengeFeeList.add(challengeFee);

        IdDTO idTO = new IdDTO(123L);
        when(billingAccountDAO.checkBillingAccountExists(123L)).thenReturn(idTO);

        AuthUser authUser = new Authorization();
        ((Authorization) authUser).setUserId(new TCID("123"));

        BillingAccountFees billingAccountFees = new BillingAccountFees(true,null,challengeFeeList);
        BillingAccountFees result = unit.updateBillingAccountFees(authUser,billingAccountFees,123L);

        assertNotNull(result);

        verify(billingAccountDAO).getChallengeFeePercentage(anyObject());
    }

    private QueryResult<List<BillingAccount>> getListQueryResult() {
        List<BillingAccount> billingAccounts = new ArrayList<>();

        billingAccounts.add(new BillingAccount(1L, "1", "Active", new Date(), new Date(), 500.0f, 1.0f, "po1",
        		new PaymentTermsDTO(1L, "30 Days"), "description1", "subscription#1", 1L, 0L, 1L, false ));
        billingAccounts.add(new BillingAccount(2L, "2", "Active", new Date(), new Date(), 500.0f, 1.0f, "po2",
        		new PaymentTermsDTO(1L, "30 Days"), "description2", "subscription#2", 1L, 0L, 1L, false));
        QueryResult<List<BillingAccount>> expected = new QueryResult<>();
        expected.setData(billingAccounts);
        return expected;
    }
}
