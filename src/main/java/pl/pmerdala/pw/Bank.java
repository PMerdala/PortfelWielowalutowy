package pl.pmerdala.pw;

import java.util.Hashtable;

public class Bank {

    private Hashtable<Pair,Integer> rates = new Hashtable<>();

    public Money reduce(Expression source, String toCurrency){
        return source.reduce(this,toCurrency);
    }

    public void addRate(String fromCurrency, String toCurrency,int rate){
        rates.put(new Pair(fromCurrency,toCurrency),rate);

    }

    public int rate(String fromCurrency, String toCurrency){
        if (fromCurrency.equals(toCurrency))
            return 1;
        return rates.get(new Pair(fromCurrency,toCurrency));
    }

    private class Pair{
        String from;
        String to;

        public Pair(String from, String to) {
            this.from = from;
            this.to = to;
        }

        @Override
        public int hashCode() {
            return from.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            Pair pair = (Pair) obj;
            return from.equals(pair.from) && to.equals(pair.to);
        }
    }
}
