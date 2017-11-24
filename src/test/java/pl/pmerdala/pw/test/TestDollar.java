package pl.pmerdala.pw.test;

import org.junit.Test;
import pl.pmerdala.pw.Bank;
import pl.pmerdala.pw.Expression;
import pl.pmerdala.pw.Money;
import pl.pmerdala.pw.Sum;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/*
TODO 5 USD + 10 CHF = 10 USD przy kursie USD:CHF=2:1
5 USD + 5 USD = 10 USD
DONETODO 5 USD *2 = 10 USD
DONETODO Pole "amount" uczynić prywatnym
DONETODO Co z efektami ubocznymi?
TODO Co z zaokrągleniem kwot?
DONETODO Metoda equals()
TODO Metoda hashCode()
TODO metoda equals() with null or another object czy sprawdzać i zwracać null czy wyjątek
DONETODO 5 CHF * 2 = 10 CHF
DONETODO Duplikacja Dollar/Franc
DONETODO Wspólne porównanie
DONETODO Wspólne mnożenie
DONETODO porownanie klasy dollar z franc
DONETODO Waluta?
DONETODO Usunąć testFrancMultiplication()?
DONETODO konstruktor jest wywoływany w funkcji times zamiast metody fabrykującej
DONETODO przeniesienie konstruktura do Money
BEZUUZYTECZNYTODO obiekt Money jako wynik dodawania 5 USD + 5 USD
DONETODO Bank.reduce(Money)
TODO rzutowanie reduce powinna działać dla każdego parametru zgodnego z Expression a nie tylko Sum
TODO publiczne pola w klasie Sum i dwupoziomowe odwołanie z ich udzałem w Bank.reduce
DONETODO Reducja Money z konwersją
DONETODO Reduce(Bank,String)
DONETODO Expresion.plus()
DONETODO Expresion.times()

 */
public class TestDollar {

    @Test
    public void testMultiplication(){
        Money five = Money.dollar(5);
        assertEquals(Money.dollar(10),five.times(2));
        assertEquals(Money.dollar(15),five.times(3));
    }

    @Test
    public void testEquality() throws Exception {
        assertTrue(Money.dollar(5).equals(Money.dollar(5)));
        assertFalse(Money.dollar(5).equals(Money.dollar(6)));
        assertFalse(Money.franc(5).equals(Money.dollar(5)));
    }


    @Test
    public void testCurrency() throws Exception {
        assertEquals("USD",Money.dollar(1).currency());
        assertEquals("CHF",Money.franc(1).currency());
    }

    @Test
    public void testSimpleAddition() throws Exception {
        Money five = Money.dollar(5);
        Expression sum = five.plus(five);
        Bank bank = new Bank();
        Money reduced = bank.reduce(sum,"USD");
        assertEquals(Money.dollar(10),reduced);
    }

    @Test
    public void testPlusReturnsSum() throws Exception {
        Money five = Money.dollar(5);
        Expression result = five.plus(five);
        Sum sum = (Sum) result;
        assertEquals(five, sum.getAugend());
        assertEquals(five, sum.getAddend());
    }

    @Test
    public void testReduceSum() throws Exception {
        Expression sum = new Sum(Money.dollar(3), Money.dollar(4));
        Bank bank = new Bank();
        Money result = bank.reduce(sum,"USD");
        assertEquals(Money.dollar(7),result);
    }

    @Test
    public void testReduceMoney() throws Exception {
        Bank bank = new Bank();
        Money result = bank.reduce(Money.dollar(1),"USD");
        assertEquals(Money.dollar(1),result);
    }

    @Test
    public void testReduceMoneyDifferentCurrency() throws Exception {
        Bank bank = new Bank();
        bank.addRate("CHF","USD",2);
        Money result = bank.reduce(Money.franc(2),"USD");
        assertEquals(Money.dollar(1),result);
    }


    @Test
    public void testIdentityRate() throws Exception {
        assertEquals(1,new Bank().rate("USD","USD"));
    }


    @Test
    public void testMixedAddition() throws Exception {
        Expression fiveBucks=Money.dollar(5);
        Expression tenFrancs=Money.franc(10);
        Bank bank = new Bank();
        bank.addRate("CHF","USD",2);
        Money result = bank.reduce(fiveBucks.plus(tenFrancs),"USD");
        assertEquals(Money.dollar(10),result);
    }
    @Test
    public void testSumPlusMoney() throws Exception {
        Expression fiveBucks = Money.dollar(5);
        Expression tenFrancs = Money.franc(10);
        Bank bank = new Bank();
        bank.addRate("CHF","USD",2);
        Expression sum = new Sum(fiveBucks,tenFrancs).plus(fiveBucks);
        Money result = bank.reduce(sum,"USD");
        assertEquals(Money.dollar(15),result);
    }
    @Test
    public void testSumTimes() throws Exception {
        Expression fiveBucks = Money.dollar(5);
        Expression tenFrancs = Money.franc(10);
        Bank bank = new Bank();
        bank.addRate("CHF","USD",2);
        Expression sum = new Sum(fiveBucks,tenFrancs).times(2);
        Money result = bank.reduce(sum,"USD");
        assertEquals(Money.dollar(20),result);
    }

}
