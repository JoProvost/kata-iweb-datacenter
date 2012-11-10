package com.joprovost.kata.datacenter.adapters;

public interface PrimaryAdapterFactory<T> {
   public PrimaryAdapter<T> createPrimaryAdapter(Object portImplementation, T entryPoint);
}
