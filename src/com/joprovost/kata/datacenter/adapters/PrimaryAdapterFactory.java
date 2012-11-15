package com.joprovost.kata.datacenter.adapters;

public interface PrimaryAdapterFactory<T> {
   public PrimaryAdapter createPrimaryAdapter(Object portImplementation, T entryPoint);
}
