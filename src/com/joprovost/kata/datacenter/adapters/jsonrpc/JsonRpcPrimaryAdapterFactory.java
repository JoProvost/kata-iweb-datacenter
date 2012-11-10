package com.joprovost.kata.datacenter.adapters.jsonrpc;

import com.googlecode.jsonrpc4j.JsonRpcServer;
import com.googlecode.jsonrpc4j.StreamServer;
import com.joprovost.kata.datacenter.adapters.PrimaryAdapter;
import com.joprovost.kata.datacenter.adapters.PrimaryAdapterFactory;

import java.net.ServerSocket;

public class JsonRpcPrimaryAdapterFactory implements PrimaryAdapterFactory<ServerSocket> {
   @Override
   public PrimaryAdapter<ServerSocket> createPrimaryAdapter(Object portImplementation, ServerSocket serverSocket) {
      return new JsonRpcSocketServerPrimaryAdapter(portImplementation, serverSocket);
   }

   private static class JsonRpcSocketServerPrimaryAdapter implements PrimaryAdapter<ServerSocket> {
      private final StreamServer streamServer;
      private final int maxThreads = 50;
      private final ServerSocket serverSocket;

      public JsonRpcSocketServerPrimaryAdapter(Object portImplementation, ServerSocket serverSocket) {
         JsonRpcServer jsonRpcServer = new JsonRpcServer(portImplementation);
         this.serverSocket = serverSocket;
         streamServer = new StreamServer(jsonRpcServer, maxThreads, serverSocket);
      }

      public void start() {
         streamServer.start();
      }

      public void stop() throws InterruptedException {
         streamServer.stop();
      }

      @Override
      public ServerSocket entryPoint() {
         return serverSocket;
      }

   }
}
