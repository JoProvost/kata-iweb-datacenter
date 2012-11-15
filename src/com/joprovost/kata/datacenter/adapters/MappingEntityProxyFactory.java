package com.joprovost.kata.datacenter.adapters;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class MappingEntityProxyFactory implements EntityProxyFactory<URI> {
   private final Map<String, EntityProxyFactory<URI>> factories =
         new HashMap<String, EntityProxyFactory<URI>>();

   @Override
   public <T> T createEntityProxy(URI entryPoint, Class<T> entityInterface) throws IOException {
      EntityProxyFactory<URI> factory = factories.get(entryPoint.getScheme());
      return factory.createEntityProxy(entryPoint, entityInterface);
   }

   public void registerScheme(String scheme, EntityProxyFactory<URI> adapterFactory) {
      factories.put(scheme, adapterFactory);
   }
}
