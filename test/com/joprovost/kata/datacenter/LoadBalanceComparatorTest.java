package com.joprovost.kata.datacenter;

import org.junit.Test;

import static com.joprovost.kata.datacenter.utils.Helpers.a;
import static com.joprovost.kata.datacenter.utils.ServerBuilder.server;
import static com.joprovost.kata.datacenter.utils.VmBuilder.vm;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class LoadBalanceComparatorTest {

   @Test
   public void comparingTwoServersWithSameLoadIsZero() {
      assertThat(comparing(
            a(server().withCapacity(1).containing(a(vm().withSize(1)))),
            a(server().withCapacity(1).containing(a(vm().withSize(1))))
      ), is(0));
   }

   @Test
   public void comparingALoadedServerToALessLoadedServerIsMinus1() {
      assertThat(comparing(
            a(server().withCapacity(4).containing(a(vm().withSize(3)))),
            a(server().withCapacity(4).containing(a(vm().withSize(1))))
      ), is(1));
   }

   @Test
   public void comparingALessLoadedServerToALoadedServerIsMinus1() {
      assertThat(comparing(
            a(server().withCapacity(4).containing(a(vm().withSize(1)))),
            a(server().withCapacity(4).containing(a(vm().withSize(3))))
      ), is(-1));
   }

   private static int comparing(Server first, Server second) {
      return new LoadBalanceComparator().compare(first, second);
   }
}

