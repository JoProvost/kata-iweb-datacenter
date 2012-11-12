package com.joprovost.kata.datacenter.core.datacenter;

import com.joprovost.kata.datacenter.adapters.SecondaryAdapterFactory;
import com.joprovost.kata.datacenter.Datacenter;
import com.joprovost.kata.datacenter.Server;
import com.joprovost.kata.datacenter.Vm;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.*;

public class DatacenterCore implements Datacenter {
   private transient final SecondaryAdapterFactory<SocketAddress> secondaryAdapterFactory;

   public DatacenterCore(SecondaryAdapterFactory secondaryAdapterFactory) {
      this.secondaryAdapterFactory = secondaryAdapterFactory;
   }

   private final List<Server> servers =
         Collections.synchronizedList(new ArrayList<Server>());

   private final transient List<Server> sortedServers =
         Collections.synchronizedList(new ArrayList<Server>());

   private final transient Comparator<Server> comparator =
         new LoadBalanceComparator();

   public boolean add(Vm vm) {
      for (Server server : sortedServers) {
         if (server.add(vm)) {
            Collections.sort(sortedServers, comparator);
            return true;
         }
      }
      return false;
   }

   @Override
   public boolean registerServer(String address, int port) {
      try {
         Server server = secondaryAdapterFactory.createSecondaryAdapter(new InetSocketAddress(address, port), Server.class);
         addServer(server);
         return true;
      } catch (IOException e) {
         e.printStackTrace();
         return false;
      }
   }

   public void addServer(Server server) {
      this.servers.add(server);
      this.sortedServers.add(server);
      Collections.sort(this.sortedServers, comparator);
   }

   public void registerServers(Collection<Server> servers) {
      this.servers.addAll(servers);
      this.sortedServers.addAll(servers);
      Collections.sort(this.sortedServers, comparator);
   }

}
