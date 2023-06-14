import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class LoanStore {
    private static final Logger LOGGER = Logger.getLogger(LoanStore.class.getName());

    /*
    For simplicity here I have stored the loans in a list, ideally they should be stored in a database and
    can be maintained in a in-memory/distributed cache if required.
     */
    private List<Loan> loans;

    public LoanStore() {
        loans = new ArrayList<>();
    }

    // method to verify the validity and add the loan in the storage
    // checks due date as well and logs the warning if required
    public void addLoan(Loan loan) {
        if (loan.getPaymentDate().isAfter(loan.getDueDate())) {
            throw new IllegalArgumentException("Payment date can't be greater than the due date.");
        }
        loans.add(loan);
        checkDueDateAlert(loan);
    }

    // method returns the total amount yet to be received for the given lender
    public double getRemainingAmountByLender(String lenderId) {
        double remainingAmount = 0;
        for (Loan loan : loans) {
            if (loan.getLenderId().equals(lenderId)) {
                remainingAmount += loan.getRemainingAmount();
            }
        }
        return remainingAmount;
    }

    // method returns the total interest received for the given lender
    public double getTotalInterestByLender(String lenderId) {
        double totalInterest = 0;
        for (Loan loan : loans) {
            if (loan.getLenderId().equals(lenderId)) {
                totalInterest += loan.getInterestPerDay();
            }
        }
        return totalInterest;
    }

    // method returns the total interest paid by the given customer
    public double getTotalInterestByCustomer(String customerId) {
        double totalInterest = 0;
        for (Loan loan : loans) {
            if (loan.getCustomerId().equals(customerId)) {
                totalInterest += loan.getInterestPerDay();
            }
        }
        return totalInterest;
    }

    // method returns the total penalty received for the given lender
    public double getTotalPenaltyByLender(String lenderId) {
        double totalPenalty = 0;
        for (Loan loan : loans) {
            if (loan.getLenderId().equals(lenderId)) {
                totalPenalty += loan.getPenaltyPerDay();
            }
        }
        return totalPenalty;
    }

    // method returns the total penalty paid by the given customer
    public double getTotalPenaltyByCustomer(String customerId) {
        double totalPenalty = 0;
        for (Loan loan : loans) {
            if (loan.getCustomerId().equals(customerId)) {
                totalPenalty += loan.getPenaltyPerDay();
            }
        }
        return totalPenalty;
    }

    // method to aggregate interests by customer
    public Map<String, Double> aggregateInterestByCustomer() {
        Map<String, Double> aggregatedInterests = new HashMap<>();
        for (Loan loan : loans) {
            String customerId = loan.getCustomerId();
            double totalInterest = aggregatedInterests.getOrDefault(customerId, 0.0);
            totalInterest += loan.getInterestPerDay();
            aggregatedInterests.put(customerId, totalInterest);
        }
        return aggregatedInterests;
    }

    // method to aggregate interests by lender
    public Map<String, Double> aggregateInterestByLender() {
        Map<String, Double> aggregatedInterests = new HashMap<>();
        for (Loan loan : loans) {
            String lender = loan.getLenderId();
            double totalInterest = aggregatedInterests.getOrDefault(lender, 0.0);
            totalInterest += loan.getInterestPerDay();
            aggregatedInterests.put(lender, totalInterest);
        }
        return aggregatedInterests;
    }

    // method to aggregate amount yet to be received by lender
    public Map<String, Double> aggregateRemainingAmountByLender() {
        Map<String, Double> aggregatedAmounts = new HashMap<>();
        for (Loan loan : loans) {
            String lenderId = loan.getLenderId();
            double remainingAmount = aggregatedAmounts.getOrDefault(lenderId, 0.0);
            remainingAmount += loan.getRemainingAmount();
            aggregatedAmounts.put(lenderId, remainingAmount);
        }
        return aggregatedAmounts;
    }

    // method to aggregate amount yet to be paid by customer
    public Map<String, Double> aggregateRemainingAmountByCustomer() {
        Map<String, Double> aggregatedAmounts = new HashMap<>();

        for (Loan loan : loans) {
            String customerId = loan.getCustomerId();
            double remainingAmount = loan.getRemainingAmount();

            aggregatedAmounts.put(customerId, aggregatedAmounts.getOrDefault(customerId, 0.0) + remainingAmount);
        }

        return aggregatedAmounts;
    }

    // method to aggregate penalty to be received by lender
    public Map<String, Double> aggregatePenaltyByLender() {
        Map<String, Double> aggregatedPenalties = new HashMap<>();
        for (Loan loan : loans) {
            String lenderId = loan.getLenderId();
            double totalPenalty = aggregatedPenalties.getOrDefault(lenderId, 0.0);
            totalPenalty += loan.getPenaltyPerDay();
            aggregatedPenalties.put(lenderId, totalPenalty);
        }
        return aggregatedPenalties;
    }

    // method to aggregate amount yet to be paid by customer
    public Map<String, Double> aggregatePenaltyByCustomer() {
        Map<String, Double> aggregatedPenalties = new HashMap<>();
        for (Loan loan : loans) {
            String customerId = loan.getCustomerId();
            double totalPenalty = aggregatedPenalties.getOrDefault(customerId, 0.0);
            totalPenalty += loan.getPenaltyPerDay();
            aggregatedPenalties.put(customerId, totalPenalty);
        }
        return aggregatedPenalties;
    }

    // method prints a warning when due date is passed for a loan
    private void checkDueDateAlert(Loan loan) {
        if (loan.getDueDate().isBefore(LocalDate.now())) {
            LOGGER.warning("Loan with ID " + loan.getLoanId() + " has crossed the due date.");
        }
    }
}

