package com.joprovost.kata.datacenter;

import java.util.*;

public class Datacenter {
   private final List<Server> servers =
         Collections.synchronizedList(new ArrayList<Server>());

   private final transient List<Server> sortedServers =
         Collections.synchronizedList(new ArrayList<Server>());

   private final transient Comparator<Server> comparator =
         new LoadBalanceComparator();

   public Datacenter(Collection<Server> servers) {
      this.servers.addAll(servers);
      this.sortedServers.addAll(servers);
      Collections.sort(this.sortedServers, comparator);
   }

   public boolean add(Vm vm) {
      for (Server server : sortedServers) {
         if (server.add(vm)) {
            Collections.sort(sortedServers, comparator);
            return true;
         }
      }
      return false;
   }
}
