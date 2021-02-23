package cz.cvut.fel.ts1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PlocivojTest {

    @Test
    public void factorialTest() {
        Plocivoj plocivoj = new Plocivoj();
        int n = 5;
        long expectedResult = 120;

        long result = plocivoj.factorial(n);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    public void factorialRecursiveTest() {
        Plocivoj plocivoj = new Plocivoj();
        int n = 6;
        long expectedResult = 720;

        long result = plocivoj.factorialRecursive(n);
        Assertions.assertEquals(expectedResult, result);
    }
}
