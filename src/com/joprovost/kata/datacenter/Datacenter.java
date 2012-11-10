package com.joprovost.kata.datacenter;

public interface Datacenter {
   boolean registerServer(String address, int port);
   boolean add(Vm vm);
}
