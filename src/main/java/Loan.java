import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

class Loan {
    private String loanId;
    private String customerId;
    private String lenderId;
    private double amount;
    private double remainingAmount;
    private LocalDate paymentDate;
    private double interestPerDay;
    private LocalDate dueDate;
    private double penaltyPerDay;

    public Loan(String loanId, String customerId, String lenderId, double amount, double remainingAmount,
                String paymentDate, double interestPerDay, String dueDate, double penaltyPerDay) {
        this.loanId = loanId;
        this.customerId = customerId;
        this.lenderId = lenderId;
        this.amount = amount;
        this.remainingAmount = remainingAmount;
        this.paymentDate = LocalDate.parse(paymentDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.interestPerDay = interestPerDay;
        this.dueDate = LocalDate.parse(dueDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.penaltyPerDay = penaltyPerDay;
    }

    // Getters for all the fields

    public String getLoanId() {
        return loanId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getLenderId() {
        return lenderId;
    }

    public double getAmount() {
        return amount;
    }

    public double getRemainingAmount() {
        return remainingAmount;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public double getInterestPerDay() {
        return interestPerDay;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public double getPenaltyPerDay() {
        return penaltyPerDay;
    }
}