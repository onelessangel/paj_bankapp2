package com.luxoft.bankapp.domain;

public class AccountFactory {

    private AccountFactory() {

    }

    private static Account createSavingAccount(int id, double amount) {
        return new SavingAccount(id, amount);
    }

    private static Account createSavingAccount(int id, double amount, Currency currency) {
        return new SavingAccount(id, amount, currency);
    }

    private static Account createCheckingAccount(int id, double amount, double overdraft) {
        return new CheckingAccount(id, amount, overdraft);
    }

    private static Account createCheckingAccount(int id, double amount, double overdraft, Currency currency) {
        return new CheckingAccount(id, amount, overdraft, currency);
    }

    public static Account createAccount(int accountType, int id, double amount, double overdraft) {
        return switch (accountType) {
            case AbstractAccount.SAVING_ACCOUNT_TYPE -> createSavingAccount(id, amount);
            case AbstractAccount.CHECKING_ACCOUNT_TYPE -> createCheckingAccount(id, amount, overdraft);
            default -> throw new IllegalArgumentException("Invalid account type: " + accountType);
        };
    }

    public static Account createAccount(int accountType, int id, double amount, double overdraft, Currency currency) {
        return switch (accountType) {
            case AbstractAccount.SAVING_ACCOUNT_TYPE -> createSavingAccount(id, amount, currency);
            case AbstractAccount.CHECKING_ACCOUNT_TYPE -> createCheckingAccount(id, amount, overdraft, currency);
            default -> throw new IllegalArgumentException("Invalid account type: " + accountType);
        };
    }

}
