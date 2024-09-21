package ac.ku.oloo.businessLogic.accounting;

import ac.ku.oloo.utils.databaseUtil.QueryExecutor;
import ac.ku.oloo.utils.databaseUtil.QueryExecutor.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * FedhaYouthGroupSystem-SCO200_Project (ac.ku.oloo.businessLogic.accounting)
 * Created by: oloo
 * On: 21/09/2024. 19:59
 * Description:
 **/

public class AccountingService {

    public static void processDeposit(int memberId, int accountToCredit, double amount, String currency) throws SQLException {
        // 1. Insert the deposit record
        String depositSql = "INSERT INTO deposits (member_id, account_to_credit, amount) VALUES (?, ?, ?)";
        QueryExecutor.executeInsert(depositSql, rs -> {
            return rs.next() ? rs.getInt(1) : null;
        }, memberId, accountToCredit, amount);

        // 2. Get the account details to credit
        String accountSql = "SELECT account_id, account_type_id, debit, credit FROM accounts WHERE account_id = ?";
        List<Account> accounts = QueryExecutor.executeQuery(accountSql, new AccountMapper(), accountToCredit);

        if (accounts.isEmpty()) {
            throw new SQLException("Account not found");
        }

        Account accountToUpdate = accounts.get(0);
        String accountType = getAccountType(accountToUpdate.getAccountTypeId());

        // 3. Determine the correct accounts to debit and credit
        int debitAccountId, creditAccountId;

        if ("Debit".equalsIgnoreCase(accountType)) {
            debitAccountId = accountToCredit;
            creditAccountId = getCreditAccountId();
        } else {
            creditAccountId = accountToCredit;
            debitAccountId = getDebitAccountId();
        }

        // 4. Update the accounts table with the new balances
        updateAccountBalance(debitAccountId, amount, "debit");
        updateAccountBalance(creditAccountId, amount, "credit");

        // 5. Insert the journal entry
        String journalSql = "INSERT INTO journal_entries (transaction_date, description, reference_id, reference_type_name, debit_account_id, credit_account_id, amount_debited, amount_credited) VALUES (CURRENT_DATE, ?, ?, 'Deposit', ?, ?, ?, ?)";
        QueryExecutor.executeInsert(journalSql, rs -> {
            return rs.next() ? rs.getInt(1) : null;
        }, "Deposit", memberId, debitAccountId, creditAccountId, amount, amount);

        // 6. Insert the money flow record
        String moneyFlowSql = "INSERT INTO money_flow (entry_id, source_type_name, source_details, destination_type_name, destination_details) VALUES (?, 'Member', ?, 'Account', ?)";
        QueryExecutor.executeInsert(moneyFlowSql, rs -> {
            return rs.next() ? rs.getInt(1) : null;
        }, memberId, memberId, accountToCredit);
    }

    public static void processLoan(int memberId, double loanAmount, int loanTypeId, int guarantorId) throws SQLException {
        // 1. Insert the loan record
        String loanSql = "INSERT INTO loans (loan_type_id, member_id, loan_amount, guarantor_id, status) VALUES (?, ?, ?, ?, 'Approved')";
        int loanId = QueryExecutor.executeInsert(loanSql, rs -> {
            return rs.next() ? rs.getInt(1) : null;
        }, loanTypeId, memberId, loanAmount, guarantorId);

        // 2. Get the loan account
        int loanAccountId = getLoanAccountId();

        // 3. Update the loan account balance
        updateAccountBalance(loanAccountId, loanAmount, "credit");

        // 4. Insert the journal entry for the loan
        String loanJournalSql = "INSERT INTO journal_entries (transaction_date, description, reference_id, reference_type_name, debit_account_id, credit_account_id, amount_debited, amount_credited) VALUES (CURRENT_DATE, ?, ?, 'Loan', ?, ?, ?, ?)";
        QueryExecutor.executeInsert(loanJournalSql, rs -> {
            return rs.next() ? rs.getInt(1) : null;
        }, "Loan for Member", memberId, getDebitAccountId(), loanAccountId, loanAmount, loanAmount);
    }

    private static void updateAccountBalance(int accountId, double amount, String type) throws SQLException {
        String sql = "UPDATE accounts SET " + type + " = " + type + " + ? WHERE account_id = ?";
        QueryExecutor.executeUpdate(sql, amount, accountId);
    }

    private static String getAccountType(int accountTypeId) throws SQLException {
        String sql = "SELECT nature FROM account_types WHERE account_type_id = ?";
        return QueryExecutor.executeQuery(sql, rs -> {
            return rs.next() ? rs.getString("nature") : null;
        }, accountTypeId).get(0);
    }

    private static int getDebitAccountId() {
        // TODO: Implement logic to get the default debit account ID, or throw an exception if it cannot be determined
        return 1;
    }

    private static int getCreditAccountId() {
        // TODO: Implement logic to get the default credit account ID, or throw an exception if it cannot be determined
        return 2;
    }

    private static int getLoanAccountId() {
        // TODO: Implement logic to get the loan account ID, or throw an exception if it cannot be determined
        return 3;
    }

    private static class Account {
        private int accountId;
        private int accountTypeId;
        private double debit;
        private double credit;

        // Getters and setters
        public int getAccountId() {
            return accountId;
        }

        public void setAccountId(int accountId) {
            this.accountId = accountId;
        }

        public int getAccountTypeId() {
            return accountTypeId;
        }

        public void setAccountTypeId(int accountTypeId) {
            this.accountTypeId = accountTypeId;
        }

        public double getDebit() {
            return debit;
        }

        public void setDebit(double debit) {
            this.debit = debit;
        }

        public double getCredit() {
            return credit;
        }

        public void setCredit(double credit) {
            this.credit = credit;
        }
    }

    private static class AccountMapper implements RowMapper<Account> {
        @Override
        public Account mapRow(ResultSet rs) throws SQLException {
            Account account = new Account();
            account.setAccountId(rs.getInt("account_id"));
            account.setAccountTypeId(rs.getInt("account_type_id"));
            account.setDebit(rs.getDouble("debit"));
            account.setCredit(rs.getDouble("credit"));
            return account;
        }
    }
}

