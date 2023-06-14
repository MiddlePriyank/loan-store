import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

class StoreTest {
    private LoanStore loanStore;

    @BeforeEach
    public void setUp() {
        loanStore = new LoanStore();
        // Add test loans
        loanStore.addLoan(new Loan("L1", "C1", "LEN1", 10000, 10000, "05/06/2023", 1, "05/07/2023", 0.01));
        loanStore.addLoan(new Loan("L2", "C1", "LEN1", 20000, 5000, "01/06/2023", 1, "05/08/2023", 0.01));
        loanStore.addLoan(new Loan("L3", "C2", "LEN2", 50000, 30000, "04/04/2023", 2, "04/05/2023", 0.02));
        loanStore.addLoan(new Loan("L4", "C3", "LEN2", 50000, 30000, "04/04/2023", 2, "04/05/2023", 0.02));
    }

    @Test
    void testAddLoan_ValidLoan() {
        Loan loan = new Loan("L5", "C4", "LEN3", 10000, 10000, "05/06/2023", 1, "05/07/2023", 0.01);
        loanStore.addLoan(loan);
        Assertions.assertEquals(10000, loanStore.getRemainingAmountByLender("LEN3"));
    }

    @Test
    void testAddLoan_InvalidLoanPaymentDateGreaterThanDueDate() {
        Loan loan = new Loan("L5", "C4", "LEN4", 10000, 10000, "05/08/2023", 1, "05/07/2023", 0.01);
        Assertions.assertThrows(IllegalArgumentException.class, () -> loanStore.addLoan(loan));
    }

    @Test
    void testGetRemainingAmountByLender() {
        Assertions.assertEquals(15000, loanStore.getRemainingAmountByLender("LEN1"));
    }

    @Test
    void testGetTotalInterestByLender() {
        Assertions.assertEquals(2, loanStore.getTotalInterestByLender("LEN1"));
    }

    @Test
    void testGetTotalInterestByCustomer() {
        Assertions.assertEquals(2, loanStore.getTotalInterestByCustomer("C1"));
    }

    @Test
    public void testAggregateRemainingAmountByLender() {
        Map<String, Double> aggregatedAmounts = loanStore.aggregateRemainingAmountByLender();
        Assertions.assertEquals(15000.0, aggregatedAmounts.get("LEN1"));
        Assertions.assertEquals(60000.0, aggregatedAmounts.get("LEN2"));
        Assertions.assertNull(aggregatedAmounts.get("LEN3"));
    }

    @Test
    public void testAggregateRemainingAmountByCustomer() {
        Map<String, Double> aggregatedAmounts = loanStore.aggregateRemainingAmountByCustomer();
        Assertions.assertEquals(15000.0, aggregatedAmounts.get("C1"));
        Assertions.assertEquals(30000.0, aggregatedAmounts.get("C2"));
        Assertions.assertEquals(30000.0, aggregatedAmounts.get("C3"));
        Assertions.assertNull(aggregatedAmounts.get("C5"));
    }

    @Test
    public void testAggregateInterestByLender() {
        Map<String, Double> aggregatedInterests = loanStore.aggregateInterestByLender();
        Assertions.assertEquals(2, aggregatedInterests.get("LEN1"));
        Assertions.assertEquals(4, aggregatedInterests.get("LEN2"));
        Assertions.assertNull(aggregatedInterests.get("LEN4"));
    }

    @Test
    public void testAggregateInterestByCustomer() {
        Map<String, Double> aggregatedInterests = loanStore.aggregateInterestByCustomer();
        Assertions.assertEquals(2.0, aggregatedInterests.get("C1"));
        Assertions.assertEquals(2.0, aggregatedInterests.get("C2"));
        Assertions.assertEquals(2.0, aggregatedInterests.get("C3"));
        Assertions.assertNull(aggregatedInterests.get("C5"));
    }

    @Test
    public void testAggregatePenaltyByLender() {
        Map<String, Double> aggregatedPenalties = loanStore.aggregatePenaltyByLender();
        Assertions.assertEquals(0.02, aggregatedPenalties.get("LEN1"));
        Assertions.assertEquals(0.04, aggregatedPenalties.get("LEN2"));
        Assertions.assertNull(aggregatedPenalties.get("LEN3"));
    }

    @Test
    public void testAggregatePenaltyByCustomer() {
        Map<String, Double> aggregatedPenalties = loanStore.aggregatePenaltyByCustomer();
        Assertions.assertEquals(0.02, aggregatedPenalties.get("C1"));
        Assertions.assertEquals(0.02, aggregatedPenalties.get("C2"));
        Assertions.assertEquals(0.02, aggregatedPenalties.get("C3"));
        Assertions.assertNull(aggregatedPenalties.get("C5"));
    }

    @Test
    public void testTotalPenaltyByCustomer() {
        // Test case for totalPenaltyByCustomer
        double totalPenaltyC1 = loanStore.getTotalPenaltyByCustomer("C1");
        double totalPenaltyC2 = loanStore.getTotalPenaltyByCustomer("C2");
        double totalPenaltyC3 = loanStore.getTotalPenaltyByCustomer("C3");
        double totalPenaltyC4 = loanStore.getTotalPenaltyByCustomer("C4");

        Assertions.assertEquals(0.02, totalPenaltyC1);
        Assertions.assertEquals(0.02, totalPenaltyC2);
        Assertions.assertEquals(0.02, totalPenaltyC3);
        Assertions.assertEquals(0.0, totalPenaltyC4);
    }

    @Test
    public void testGetTotalPenaltyByLender() {
        // Test case for getTotalPenaltyByLender
        double totalPenaltyLEN1 = loanStore.getTotalPenaltyByLender("LEN1");
        double totalPenaltyLEN2 = loanStore.getTotalPenaltyByLender("LEN2");
        double totalPenaltyLEN3 = loanStore.getTotalPenaltyByLender("LEN3");


        Assertions.assertEquals(0.02, totalPenaltyLEN1);
        Assertions.assertEquals(0.04, totalPenaltyLEN2);
        Assertions.assertEquals(0.0, totalPenaltyLEN3);
    }
}
