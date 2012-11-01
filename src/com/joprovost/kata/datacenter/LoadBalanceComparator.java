package com.joprovost.kata.datacenter;

import java.util.Comparator;

public class LoadBalanceComparator implements Comparator<Server> {
   @Override
   public int compare(Server first, Server second) {
      return Double.compare(first.load(), second.load());
   }
}
