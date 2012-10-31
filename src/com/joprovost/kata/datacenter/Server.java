package com.joprovost.kata.datacenter;

import java.util.Collection;
import java.util.HashSet;

public class Server implements Comparable<Server>{
   private final int capacity;
   private final Collection<Vm> vms = new HashSet<Vm>();

   public Server(final int capacity, final Collection<Vm> vms) {
      this.capacity = capacity;
      this.vms.addAll(vms);
   }

   public boolean add(final Vm vm) {
      return capacity - usedCapacity() >= vm.size() && vms.add(vm);
   }

   private int usedCapacity() {
      int usage = 0;
      for (final Vm vm : vms) {
         usage += vm.size();
      }
      return usage;
   }

   public boolean contains(Vm vm) {
      return vms.contains(vm);
   }

   @Override
   public int compareTo(Server server) {
      if (server.capacity == 0)
         return 1;
      if (capacity == 0)
         return -1;
      return Double.compare((usedCapacity() / (double) capacity), (server.usedCapacity() / (double) server.capacity));
   }
}
