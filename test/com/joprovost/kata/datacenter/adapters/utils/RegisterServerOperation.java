package com.joprovost.kata.datacenter.adapters.utils;

import com.joprovost.kata.datacenter.Datacenter;

public class RegisterServerOperation {
   private final String uri;

   public static RegisterServerOperation registeringTheServerAt(String uri)
   {
      return new RegisterServerOperation(uri);
   }

   private RegisterServerOperation(String address) {
      this.uri = address;
   }

   public boolean to(Datacenter datacenter) {
      return datacenter.registerServer(uri);
   }
}
