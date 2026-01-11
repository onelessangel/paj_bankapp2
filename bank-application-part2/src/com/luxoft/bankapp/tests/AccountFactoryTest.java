package com.luxoft.bankapp.tests;

import com.luxoft.bankapp.domain.AbstractAccount;
import com.luxoft.bankapp.domain.Account;
import com.luxoft.bankapp.domain.AccountFactory;
import com.luxoft.bankapp.domain.CheckingAccount;
import com.luxoft.bankapp.domain.Currency;
import com.luxoft.bankapp.domain.SavingAccount;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AccountFactoryTest {
    public static final int ID = 1;
    public static final double AMOUNT = 100.00;
    public static final double OVERDRAFT = 10.00;
    public static final Currency CURRENCY = new Currency("USD");

    @Test
    public void testCreateAccountSaving() {
        Account account = AccountFactory.createAccount(AbstractAccount.SAVING_ACCOUNT_TYPE, ID, AMOUNT, 0.0);

        assertNotNull(account);
        assertEquals(ID, account.getId());
        assertEquals(AMOUNT, account.getBalance(), 0.0);
        assertEquals(AbstractAccount.SAVING_ACCOUNT_TYPE, ((AbstractAccount) account).getType());
    }

    @Test
    public void testCreateAccountSavingWithCurrency() {
        Account account = AccountFactory.createAccount(AbstractAccount.SAVING_ACCOUNT_TYPE, ID, AMOUNT, 0.0, CURRENCY);

        assertNotNull(account);
        assertEquals(ID, account.getId());
        assertEquals(AMOUNT, account.getBalance(), 0.0);
        assertEquals(CURRENCY, ((SavingAccount) account).getCurrency());
        assertEquals(AbstractAccount.SAVING_ACCOUNT_TYPE, ((AbstractAccount) account).getType());
    }

    @Test
    public void testCreateAccountChecking() {
        Account account = AccountFactory.createAccount(AbstractAccount.CHECKING_ACCOUNT_TYPE, ID, AMOUNT, OVERDRAFT);

        assertNotNull(account);
        assertEquals(ID, account.getId());
        assertEquals(AMOUNT, account.getBalance(), 0.0);
        assertEquals(OVERDRAFT, ((CheckingAccount) account).getOverdraft(), 0.0);
        assertEquals(AbstractAccount.CHECKING_ACCOUNT_TYPE, ((AbstractAccount) account).getType());
    }

    @Test
    public void testCreateAccountCheckingWithCurrency() {
        Account account = AccountFactory.createAccount(AbstractAccount.CHECKING_ACCOUNT_TYPE, ID, AMOUNT, OVERDRAFT, CURRENCY);

        assertNotNull(account);
        assertEquals(ID, account.getId());
        assertEquals(AMOUNT, account.getBalance(), 0.0);
        assertEquals(OVERDRAFT, ((CheckingAccount) account).getOverdraft(), 0.0);
        assertEquals(CURRENCY, ((CheckingAccount) account).getCurrency());
        assertEquals(AbstractAccount.CHECKING_ACCOUNT_TYPE, ((AbstractAccount) account).getType());
    }
}
