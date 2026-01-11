package com.luxoft.bankapp.tests;

import com.luxoft.bankapp.domain.AbstractAccount;
import com.luxoft.bankapp.domain.AccountCache;
import com.luxoft.bankapp.domain.CheckingAccount;
import com.luxoft.bankapp.domain.SavingAccount;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AccountCacheTest {

    @Before
    public void setUp() {
        AccountCache.clearCache();
        AccountCache.load();
    }

    // ==================== Cache Loading Tests ====================

    @Test
    public void testCacheLoadsPrototypes() {
        AbstractAccount savingPrototype = AccountCache.getAccount(AccountCache.SAVING_ACCOUNT);
        AbstractAccount checkingPrototype = AccountCache.getAccount(AccountCache.CHECKING_ACCOUNT);

        assertNotNull(savingPrototype);
        assertNotNull(checkingPrototype);
    }

    @Test
    public void testCachePrototypesAreCorrectTypes() {
        AbstractAccount savingPrototype = AccountCache.getAccount(AccountCache.SAVING_ACCOUNT);
        AbstractAccount checkingPrototype = AccountCache.getAccount(AccountCache.CHECKING_ACCOUNT);

        assertTrue(savingPrototype instanceof SavingAccount);
        assertTrue(checkingPrototype instanceof CheckingAccount);
    }

    @Test
    public void testCachePrototypesHaveDefaultValues() {
        AbstractAccount savingPrototype = AccountCache.getAccount(AccountCache.SAVING_ACCOUNT);
        AbstractAccount checkingPrototype = AccountCache.getAccount(AccountCache.CHECKING_ACCOUNT);

        assertEquals(0, savingPrototype.getId());
        assertEquals( 0.0, savingPrototype.getBalance(), 0.0);

        assertEquals( 0, checkingPrototype.getId());
        assertEquals(0.0, ((CheckingAccount) checkingPrototype).getOverdraft(), 0.0);
    }

    // ==================== Clone Tests ====================

    @Test
    public void testCloneSavingAccountReturnsNewInstance() {
        AbstractAccount cloned = AccountCache.cloneAccount(AccountCache.SAVING_ACCOUNT);
        AbstractAccount prototype = AccountCache.getAccount(AccountCache.SAVING_ACCOUNT);

        assertNotNull(cloned);
        assertNotSame(prototype, cloned);
    }

    @Test
    public void testCloneCheckingAccountReturnsNewInstance() {
        AbstractAccount cloned = AccountCache.cloneAccount(AccountCache.CHECKING_ACCOUNT);
        AbstractAccount prototype = AccountCache.getAccount(AccountCache.CHECKING_ACCOUNT);

        assertNotNull(cloned);
        assertNotSame(prototype, cloned);
    }

    @Test
    public void testClonedSavingAccountHasSameValues() {
        AbstractAccount cloned = AccountCache.cloneAccount(AccountCache.SAVING_ACCOUNT);
        AbstractAccount prototype = AccountCache.getAccount(AccountCache.SAVING_ACCOUNT);

        assertEquals(prototype.getId(), cloned.getId());
        assertEquals(prototype.getBalance(), cloned.getBalance(), 0.0);
        assertEquals(prototype.getType(), cloned.getType());
    }

    @Test
    public void testClonedCheckingAccountHasSameValues() {
        AbstractAccount cloned = AccountCache.cloneAccount(AccountCache.CHECKING_ACCOUNT);
        AbstractAccount prototype = AccountCache.getAccount(AccountCache.CHECKING_ACCOUNT);

        assertEquals(prototype.getId(), cloned.getId());
        assertEquals(prototype.getBalance(), cloned.getBalance(), 0.0);
        assertEquals(prototype.getType(), cloned.getType());
        assertEquals(((CheckingAccount) prototype).getOverdraft(), ((CheckingAccount) cloned).getOverdraft(), 0.0);
    }

    @Test
    public void testClonedAccountIsCorrectType() {
        AbstractAccount savingClone = AccountCache.cloneAccount(AccountCache.SAVING_ACCOUNT);
        AbstractAccount checkingClone = AccountCache.cloneAccount(AccountCache.CHECKING_ACCOUNT);

        assertTrue(savingClone instanceof SavingAccount);
        assertTrue(checkingClone instanceof CheckingAccount);
    }

    // ==================== Independence Tests ====================

    @Test
    public void testDeepCopySavingAccount() {
        AbstractAccount prototype = AccountCache.getAccount(AccountCache.SAVING_ACCOUNT);
        double originalBalance = prototype.getBalance();

        AbstractAccount cloned = AccountCache.cloneAccount(AccountCache.SAVING_ACCOUNT);
        cloned.deposit(500.0);

        assertEquals(originalBalance, prototype.getBalance(), 0.0);
        assertEquals(500.0, cloned.getBalance(), 0.0);
    }

    @Test
    public void testDeepCopyCheckingAccount() {
        AbstractAccount prototype = AccountCache.getAccount(AccountCache.CHECKING_ACCOUNT);
        double originalBalance = prototype.getBalance();

        AbstractAccount cloned = AccountCache.cloneAccount(AccountCache.CHECKING_ACCOUNT);
        cloned.deposit(1000.0);

        assertEquals(originalBalance, prototype.getBalance(), 0.0);
        assertEquals(1000.0, cloned.getBalance(), 0.0);
    }

    @Test
    public void testMultipleClonesAreIndependent() {
        AbstractAccount clone1 = AccountCache.cloneAccount(AccountCache.SAVING_ACCOUNT);
        AbstractAccount clone2 = AccountCache.cloneAccount(AccountCache.SAVING_ACCOUNT);

        assertNotSame(clone1, clone2);

        clone1.deposit(100.0);
        clone2.deposit(200.0);

        assertEquals(100.0, clone1.getBalance(), 0.0);
        assertEquals(200.0, clone2.getBalance(), 0.0);
    }

    // ==================== Error Handling Tests ====================

    @Test(expected = IllegalArgumentException.class)
    public void testCloneUnknownAccountTypeThrowsException() {
        AccountCache.cloneAccount("UNKNOWN");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCloneNullAccountTypeThrowsException() {
        AccountCache.cloneAccount(null);
    }

    @Test
    public void testGetAccountReturnsNullForUnknownType() {
        AbstractAccount account = AccountCache.getAccount("UNKNOWN");
        assertNull(account);
    }

    // ==================== Clear Cache Tests ====================

    @Test
    public void testClearCacheRemovesAllPrototypes() {
        AccountCache.clearCache();

        assertNull(AccountCache.getAccount(AccountCache.SAVING_ACCOUNT));
        assertNull(AccountCache.getAccount(AccountCache.CHECKING_ACCOUNT));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCloneAfterClearThrowsException() {
        AccountCache.clearCache();
        AccountCache.cloneAccount(AccountCache.SAVING_ACCOUNT);
    }
}
