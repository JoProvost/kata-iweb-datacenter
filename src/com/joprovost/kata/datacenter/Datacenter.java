package com.joprovost.kata.datacenter;

public interface Datacenter {
   boolean registerServer(String uri);
   boolean add(Vm vm);
}
