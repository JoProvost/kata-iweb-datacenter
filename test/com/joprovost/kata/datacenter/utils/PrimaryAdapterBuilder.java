package com.joprovost.kata.datacenter.utils;

import com.joprovost.kata.datacenter.adapters.PrimaryAdapter;
import com.joprovost.kata.datacenter.adapters.PrimaryAdapterFactory;
import com.joprovost.kata.datacenter.adapters.jsonrpc.JsonRpcPrimaryAdapterFactory;

import javax.net.ServerSocketFactory;
import java.io.IOException;
import java.net.ServerSocket;

import static org.junit.Assert.fail;

public class PrimaryAdapterBuilder implements Builder<PrimaryAdapter<ServerSocket>> {
   private final PrimaryAdapterFactory<ServerSocket> primaryAdapterFactory =
         new JsonRpcPrimaryAdapterFactory();

   private ServerSocket port;
   private Object portImplementation;

   @Override
   public PrimaryAdapter<ServerSocket> build() {
      return primaryAdapterFactory.createPrimaryAdapter(portImplementation, port);
   }

   public PrimaryAdapterBuilder onTcpPort(final int port) {
      try {
         this.port = ServerSocketFactory.getDefault().createServerSocket(port);
      } catch (IOException e) {
         fail(e.getMessage());
      }
      return this;
   }

   public PrimaryAdapterBuilder of(final Object portImplementation) {
      this.portImplementation = portImplementation;
      return this;
   }
}
