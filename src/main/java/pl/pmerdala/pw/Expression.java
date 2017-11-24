package pl.pmerdala.pw;

public abstract class Expression {
    abstract public Money reduce(Bank bank, String toCurrency);
    abstract public Expression times(int multiplier);

    public Expression plus(Expression addend) {
        return new Sum(this, addend);
    }
}
