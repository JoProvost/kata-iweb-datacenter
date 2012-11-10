package com.joprovost.kata.datacenter;

public interface Server {
   public boolean add(final Vm vm);

   public boolean contains(Vm vm);

   public int usePercentage();
}
