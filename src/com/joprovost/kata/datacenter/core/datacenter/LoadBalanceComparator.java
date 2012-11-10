package com.joprovost.kata.datacenter.core.datacenter;

import com.joprovost.kata.datacenter.Server;

import java.util.Comparator;

public class LoadBalanceComparator implements Comparator<Server> {
   @Override
   public int compare(Server first, Server second) {
      return Integer.valueOf(first.usePercentage()).compareTo(second.usePercentage());
   }
}
