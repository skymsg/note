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
### start cluster
minikube start
### cluster version
kubectl version
### cluster detail
kubectl cluster-info
kubectl get nodes
## Deploy App
### Kubernetes Deployment
deployment configuration instructs Kubernetes how to create and update
instances of your application. Once the application instance created,
a Kubernetes Deployment continuously monitors those instances.This provides a self-healing mechanism to address machine failure or 
mantenance.
### create deployment
kubectl create deployment --images=gcr.io/google-sample/kubernetes-bootcamp:v1
### show deployment info
kubectl get deployment
## Explore your app
### Kubernetes Pods
kubernetes create Pod to host your application instance. A Pod
is a Kubernetes abtraction that represents a group of one or more
application container. And some share resources for those container. Those resources include : Storage,Network,Information about container
### Nodes
A Pod always runs on a Node. A Node is a worker machine in Kubernetes and maybe either a virtual or physical machine,
depending on cluster. Each Node is managed by the Master.
Every Kubernetes Node runs at least :
Kubelet, a process responsible for communication between the Master
and Node . It manages the Pods and the Container that runing on the machine.
### Troubleshoting with kubectl
* kubectl get - list resources. Use 'kubectl api-resources' for a complete list of supported resources.
* kubectl describe - show detailed information about resource
* kubectl logs - print the logs from a container in a pod
* kubectl exec -- execute a command on a container in a pod
### show the app in the termimal
* kubectl describe pods
* kubectl proxy
* curl http://localhost:8001/api/v1/namespaces/default/pods/$POD_NAME/proxy
* kubectl logs $POD_NAME
* kubectl exec $POD_NAME env
* kubectl exec -it $POD_NAME bash
