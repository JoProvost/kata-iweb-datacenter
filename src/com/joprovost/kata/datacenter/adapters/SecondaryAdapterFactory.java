package com.joprovost.kata.datacenter.adapters;

import java.io.IOException;

public interface SecondaryAdapterFactory<EntryPoint> {
   public <T> T createSecondaryAdapter(EntryPoint socketAddress, Class<T> secondaryPortClass) throws IOException;
}
