package com.joprovost.kata.datacenter.adapters;

import java.io.IOException;

public interface EntityProxyFactory<EntryPoint> {
   public <T> T createEntityProxy(EntryPoint entryPoint, Class<T> entityInterface) throws IOException;
}
