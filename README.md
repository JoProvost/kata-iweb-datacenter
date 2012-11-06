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

### Example Input

As input, the application must receive some servers and some virtual machines:

```javascript
{
    "servers": [{
        "id": "server1",
        "capacity": 4
    }, {
        "id": "server2",
        "capacity": 6
    }],
    "virtualMachines": [{
        "id": "VM1",
        "size": 1
    }, {
        "id": "VM2",
        "size": 4
    }, {
        "id": "VM3",
        "size": 2
    }]
}
```

### Example Output

As output, the application has distributed the virtual machines on the available servers:

```javascript
{
    "servers": [{
        "id": "server1",
        "capacity": 4,
        "usePercentage": 75,
        "virtualMachines": [{
            "id": "VM1",
            "size": 1
        }, {
            "id": "VM3",
            "size": 2
        }]
    }, {
        "id": "server2",
        "capacity": 6,
        "usePercentage": 67,
        "virtualMachines": [{
            "id": "VM2",
            "size": 4
        }]
    }]
}
...