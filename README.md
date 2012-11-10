kata-iweb-datacenter
====================

## Technical Test Description

Write a simple application that distributes virtual machines on servers in a datacenter. The application can be written in any language.

### Specifications

- A server have space to host virtual machines (pre-defined capacity)
- When hosted on a server, a virtual machine occupy a certain amount of space (size)
- The number of virtual machines a server can host vary with the capacity of the server and the size of the virtual machines
- The virtual machines must installed on a server in the order they are received as input
- The utilization percentage of each server must be as evenly distributed as possible in the datacenter
- Servers cannot host virtual machines beyond their capacity
- **NEW: Instances of servers and load balancer must run on separate physical hosts**
