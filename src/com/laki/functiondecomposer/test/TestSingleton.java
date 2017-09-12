package com.laki.functiondecomposer.test;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.laki.functiondecomposer.TestMain;

public class TestSingleton {
   private TestMain sone = null, stwo = null;
	public TestSingleton() {
	}

   @Before
   public void setUp() {
      sone = TestMain.getInstance();

      stwo = TestMain.getInstance();
   }
   
   @Test
   public void testUnique() {
      assertEquals(true, sone == stwo);
   }
   
   @Test
   void testCreateInvocationTimes() {
	   
   }
   
   @Test
   void testFactorielCalculation() {
	   
   }
   
   
}