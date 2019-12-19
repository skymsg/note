# Kubernetes Basic
## What can Kubernets do for you ?
* high available
* released and update  application in an easy fast and fast way withotu downtime
## Create Cluster
Kubernetes coordinates a highly available cluster of computers that are connected to work as single unit.
pplication need to be packaged  in a way  that decouples them from  individual  hosts: they need to be 
containerized.
Kubernetes  automates the distribution and scheduling of application containers  across a cluster in a more
efficient way.
A Kubernetes Cluster   coordinates two types of resources:
* The Master  coordinates the cluster. The Master is responsible for managing the master.
* Nodes are workers that run applications. The nodes communinates with Master with Kubernetes API

## Deploy App
### Kubernetes Deployment
deployment configuration instructs Kubernetes how to create and update
instances of your application. Once the application instance created,
a Kubernetes Deployment continuously monitors those instances.This provides a self-healing mechanism to address machine failure or 
mantenance.