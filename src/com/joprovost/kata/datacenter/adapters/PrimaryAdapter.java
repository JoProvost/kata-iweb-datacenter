package com.joprovost.kata.datacenter.adapters;

public interface PrimaryAdapter<T> {
   void start();
   void stop() throws InterruptedException;
   T entryPoint();
}
