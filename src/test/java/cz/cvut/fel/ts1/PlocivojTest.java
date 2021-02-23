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
}
