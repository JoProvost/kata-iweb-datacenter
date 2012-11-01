package com.joprovost.kata.datacenter;

import java.util.*;

public class Datacenter {
   private final List<Server> servers =
         Collections.synchronizedList(new ArrayList<Server>());

   public Datacenter(Collection<Server> servers) {
      this.servers.addAll(servers);
      Collections.sort(this.servers);
   }

   public boolean add(Vm vm) {
      for (Server server : servers) {
         if (server.add(vm)) {
            Collections.sort(servers);
            return true;
         }
      }
      return false;
   }
}
