package com.joprovost.kata.datacenter.adapters.jsonrpc;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.googlecode.jsonrpc4j.JsonRpcServer;
import com.googlecode.jsonrpc4j.StreamServer;
import com.joprovost.kata.datacenter.adapters.PrimaryAdapter;
import com.joprovost.kata.datacenter.adapters.PrimaryAdapterFactory;

import java.net.ServerSocket;

public class JsonRpcPrimaryAdapterFactory implements PrimaryAdapterFactory<ServerSocket> {
   @Override
   public PrimaryAdapter createPrimaryAdapter(Object portImplementation, ServerSocket serverSocket) {
      return new JsonRpcSocketServerPrimaryAdapter(portImplementation, serverSocket);
   }

   private static class JsonRpcSocketServerPrimaryAdapter implements PrimaryAdapter {
      private final StreamServer streamServer;
      private final int maxThreads = 50;
      private final ServerSocket serverSocket;

      public JsonRpcSocketServerPrimaryAdapter(Object portImplementation, ServerSocket serverSocket) {
         ObjectMapper mapper = new ObjectMapper();
         mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
         JsonRpcServer jsonRpcServer = new JsonRpcServer(mapper, portImplementation);
         this.serverSocket = serverSocket;
         streamServer = new StreamServer(jsonRpcServer, maxThreads, serverSocket);
      }

      public void start() {
         streamServer.start();
      }

      public void stop() throws InterruptedException {
         streamServer.stop();
      }
   }
}
