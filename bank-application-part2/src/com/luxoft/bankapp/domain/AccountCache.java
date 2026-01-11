package com.luxoft.bankapp.domain;

import java.util.HashMap;
import java.util.Map;

public class AccountCache {
    public static final String SAVING_ACCOUNT = "SAVING";
    public static final String CHECKING_ACCOUNT = "CHECKING";

    private static Map<String, AbstractAccount> cache = new HashMap<>();

    public static void load() {
        SavingAccount savingAccount = new SavingAccount(0, 0.00);
        cache.put(SAVING_ACCOUNT, savingAccount);

        CheckingAccount checkingAccount = new CheckingAccount(0, 0.00, 0.00);
        cache.put(CHECKING_ACCOUNT, checkingAccount);
    }

    public static AbstractAccount cloneAccount(String accountType) {
        AbstractAccount cachedAccount = cache.get(accountType);

        if (cachedAccount == null) {
            throw new IllegalArgumentException("Unknown account type: " + accountType);
        }

        return cachedAccount.clone();
    }

    public static AbstractAccount getAccount(String accountType) {
        return cache.get(accountType);
    }

    public static void clearCache() {
        cache.clear();
    }
}
