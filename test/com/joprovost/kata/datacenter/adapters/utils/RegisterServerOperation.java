package com.joprovost.kata.datacenter.adapters.utils;

import com.joprovost.kata.datacenter.Datacenter;

public class RegisterServerOperation {
   private final String address;
   private final int port;

   public static RegisterServerOperation registeringTheServerAt(String address, int port)
   {
      return new RegisterServerOperation(address, port);
   }

   private RegisterServerOperation(String address, int port) {
      this.address = address;
      this.port = port;
   }

   public boolean to(Datacenter datacenter) {
      return datacenter.registerServer(address, port);
   }
}
