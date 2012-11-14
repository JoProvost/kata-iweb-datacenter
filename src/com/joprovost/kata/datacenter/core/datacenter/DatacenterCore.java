package com.joprovost.kata.datacenter.core.datacenter;

import com.joprovost.kata.datacenter.Server;
import com.joprovost.kata.datacenter.Vm;

import java.util.*;

public class DatacenterCore {
   private final List<Server> servers =
         Collections.synchronizedList(new ArrayList<Server>());

   private final transient List<Server> sortedServers =
         Collections.synchronizedList(new ArrayList<Server>());

   private final transient Comparator<Server> comparator =
         new LoadBalanceComparator();

   public boolean add(Vm vm) {
      for (Server server : sortedServers) {
         if (server.add(vm)) {
            Collections.sort(sortedServers, comparator);
            return true;
         }
      }
      return false;
   }

   public void addServer(Server server) {
      this.servers.add(server);
      this.sortedServers.add(server);
      Collections.sort(this.sortedServers, comparator);
   }

   public void addServers(Collection<Server> servers) {
      this.servers.addAll(servers);
      this.sortedServers.addAll(servers);
      Collections.sort(this.sortedServers, comparator);
   }

}
