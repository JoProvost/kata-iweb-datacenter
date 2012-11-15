package com.joprovost.kata.datacenter.adapters;

public interface PrimaryAdapter {
   void start();
   void stop() throws InterruptedException;
}
