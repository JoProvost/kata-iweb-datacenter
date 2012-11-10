package com.joprovost.kata.datacenter.adapters.jsonrpc;

import com.googlecode.jsonrpc4j.JsonRpcClient;
import com.googlecode.jsonrpc4j.ProxyUtil;
import com.joprovost.kata.datacenter.adapters.SecondaryAdapterFactory;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;

public class JsonRpcSecondaryAdapterFactory implements SecondaryAdapterFactory<SocketAddress> {
   @Override
   public <T> T createSecondaryAdapter(SocketAddress socketAddress, Class<T> secondaryPortClass) throws IOException {
      Socket clientSocket = new Socket();
      clientSocket.connect(socketAddress);
      JsonRpcClient jsonRpcClient = new JsonRpcClient();

      return ProxyUtil.createClientProxy(
            getClass().getClassLoader(),
            secondaryPortClass,
            jsonRpcClient, clientSocket);
   }
}
