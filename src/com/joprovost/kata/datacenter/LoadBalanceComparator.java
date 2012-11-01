package com.joprovost.kata.datacenter;

import java.util.Comparator;

public class LoadBalanceComparator implements Comparator<Server> {
   @Override
   public int compare(Server first, Server second) {
      return Integer.valueOf(first.usePercentage()).compareTo(second.usePercentage());
   }
}
