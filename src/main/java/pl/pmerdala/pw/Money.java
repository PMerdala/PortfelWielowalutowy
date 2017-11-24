package pl.pmerdala.pw;

public class Money extends Expression {
    private final int amount;

    private final String currency;

    public Money(int amount, String currency) {
        this.amount = amount;
        this.currency = currency;

    }

    int getAmount() {
        return amount;
    }

    @Override
    public Expression times(int multiplier) {
        return new Money(amount * multiplier, currency);
    }

    @Override
    public boolean equals(Object obj) {
        Money money = (Money) obj;
        return money.amount == amount
                && currency().equals(money.currency());
    }

    public String currency() {
        return currency;
    }


    @Override
    public Money reduce(Bank bank,String toCurrency){
        int rate = bank.rate(currency,toCurrency);
        return new Money(amount/rate,toCurrency);
    }

    public static Money dollar(int amount) {
        return new Money(amount, "USD");
    }

    public static Money franc(int amount) {
        return new Money(amount, "CHF");
    }

    @Override
    public String toString() {
        return amount + " " + currency;
    }

}
