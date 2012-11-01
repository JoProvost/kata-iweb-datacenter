package com.joprovost.kata.datacenter;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

public class Server {
   private final int capacity;
   private final Collection<Vm> vms =
         Collections.synchronizedCollection(new HashSet<Vm>());

   public Server(final int capacity, final Collection<Vm> vms) {
      this.capacity = capacity;
      this.vms.addAll(vms);
   }

   public boolean add(final Vm vm) {
      return capacity - usedCapacity() >= vm.size() && vms.add(vm);
   }

   public boolean contains(Vm vm) {
      return vms.contains(vm);
   }

   private int usedCapacity() {
      int usage = 0;
      for (final Vm vm : vms) {
         usage += vm.size();
      }
      return usage;
   }

   public double load() {
      if (capacity == 0) return 1.0;
      return (usedCapacity() / (double) capacity);
   }
}
