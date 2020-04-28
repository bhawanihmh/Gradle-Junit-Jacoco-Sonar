package com.adcb.xyz;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class CalculatorJUnit5Test {
    @Tag("slow")
    @Test
    public void testAddMaxInteger() {
    	Assertions.assertEquals(2147483646, Integer.sum(2147183646, 300000));
    }
  
    @Tag("fast")
    @Test
    public void testDivide() {
    	Assertions.assertThrows(ArithmeticException.class, () -> {
            Integer.divideUnsigned(42, 0);
        });
    }
    
    @Test
    void testExpectedException() {
   
      Assertions.assertThrows(NumberFormatException.class, () -> {
        Integer.parseInt("One");
      });
    }
   
    @Test
    void testExpectedExceptionWithSuperType() {
   
      Assertions.assertThrows(IllegalArgumentException.class, () -> {
        Integer.parseInt("One");
      });
    }
    
    
    @Test
    void testExpectedExceptionFail() {
   
    	Assertions.assertThrows(ArithmeticException.class, () -> {
            Integer.divideUnsigned(42, 0);
        });
    }
    
}
