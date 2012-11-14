package com.joprovost.kata.datacenter.adapters.smilerpc;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.smile.SmileFactory;
import com.googlecode.jsonrpc4j.JsonRpcClient;
import com.googlecode.jsonrpc4j.ProxyUtil;
import com.joprovost.kata.datacenter.adapters.SecondaryAdapterFactory;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;

public class SmileRpcSecondaryAdapterFactory implements SecondaryAdapterFactory<SocketAddress> {
   @Override
   public <T> T createSecondaryAdapter(SocketAddress socketAddress, Class<T> secondaryPortClass) throws IOException {
      ObjectMapper mapper = new ObjectMapper(new SmileFactory());
      mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
      Socket clientSocket = new Socket();
      clientSocket.connect(socketAddress);
      JsonRpcClient jsonRpcClient = new JsonRpcClient(mapper);

      return ProxyUtil.createClientProxy(
            getClass().getClassLoader(),
            secondaryPortClass,
            jsonRpcClient, clientSocket);
   }
}
