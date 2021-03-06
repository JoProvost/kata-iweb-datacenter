package com.joprovost.kata.datacenter.adapters.utils;

import com.joprovost.kata.datacenter.adapters.PrimaryAdapter;
import com.joprovost.kata.datacenter.adapters.PrimaryAdapterFactory;
import com.joprovost.kata.datacenter.adapters.jsonrpc.JsonRpcPrimaryAdapterFactory;
import com.joprovost.kata.datacenter.adapters.smilerpc.SmileRpcPrimaryAdapterFactory;
import com.joprovost.kata.datacenter.utils.Builder;

import javax.net.ServerSocketFactory;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.URI;

import static org.junit.Assert.fail;

public class PrimaryAdapterBuilder implements Builder<PrimaryAdapter> {
   private final PrimaryAdapterFactory<ServerSocket> primaryAdapterFactory;

   private ServerSocket entryPoint;
   private Object portImplementation;

   public static PrimaryAdapterBuilder jsonRpcPrimaryAdapter() {
      return new PrimaryAdapterBuilder(new JsonRpcPrimaryAdapterFactory());
   }

   public static PrimaryAdapterBuilder smileRpcPrimaryAdapter() {
      return new PrimaryAdapterBuilder(new SmileRpcPrimaryAdapterFactory());
   }

   private PrimaryAdapterBuilder(PrimaryAdapterFactory<ServerSocket> primaryAdapterFactory) {
      this.primaryAdapterFactory = primaryAdapterFactory;
   }


   @Override
   public PrimaryAdapter build() {
      return primaryAdapterFactory.createPrimaryAdapter(portImplementation, entryPoint);
   }

   public PrimaryAdapterBuilder onTcpPort(final int port) {
      try {
         this.entryPoint = ServerSocketFactory.getDefault().createServerSocket(port);
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
