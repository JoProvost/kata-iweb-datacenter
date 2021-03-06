package com.joprovost.kata.datacenter.adapters.smilerpc;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.smile.SmileFactory;
import com.googlecode.jsonrpc4j.JsonRpcClient;
import com.googlecode.jsonrpc4j.ProxyUtil;
import com.joprovost.kata.datacenter.adapters.EntityProxyFactory;

import javax.net.SocketFactory;
import java.io.IOException;
import java.net.Socket;
import java.net.URI;

public class SmileRpcEntityProxyFactory implements EntityProxyFactory<URI> {
   @Override
   public <T> T createEntityProxy(URI entryPoint, Class<T> entityInterface) throws IOException {
      ObjectMapper mapper = new ObjectMapper(new SmileFactory());
      mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
      Socket clientSocket = SocketFactory.getDefault().createSocket(entryPoint.getHost(), entryPoint.getPort());
      JsonRpcClient jsonRpcClient = new JsonRpcClient(mapper);

      return ProxyUtil.createClientProxy(
            getClass().getClassLoader(),
            entityInterface,
            jsonRpcClient, clientSocket);
   }
}
