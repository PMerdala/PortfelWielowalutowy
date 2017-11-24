package pl.pmerdala.pw;

public class Sum extends Expression {
    private final Expression augend;
    private final Expression addend;

    public Sum(Expression augend, Expression addend) {
        this.augend = augend;
        this.addend = addend;
    }


    @Override
    public Money reduce(Bank bank, String toCurrency) {
        int amount = augend.reduce(bank, toCurrency).getAmount() + addend.reduce(bank, toCurrency).getAmount();
        return new Money(amount, toCurrency);
    }

    @Override
    public Expression times(int multiplier) {
        return new Sum(augend.times(multiplier), addend.times(multiplier));
    }

    public Expression getAugend() {
        return augend;
    }

    public Expression getAddend() {
        return addend;
    }
}
