package com.joprovost.kata.datacenter;

import java.util.Collection;
import java.util.TreeSet;

public class Datacenter {
   private final Collection<Server> servers = new TreeSet<Server>();

   public Datacenter(Collection<Server> servers) {
      this.servers.addAll(servers);
   }

   public boolean add(Vm vm) {
      for (Server server : servers) {
         if (server.add(vm))
            return true;
      }
      return false;
   }
}
