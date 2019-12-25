# How to Build Docker Images with Dockerfile

## What is Dockerfile ?
A Dockerfile is a text file that contains all the command a user could run on the
command line to create a image. It includes all the instructions needed by the Docker
to build the image.

## Dockerfile instructions
* FROM - The base image for building a new image. This instruction must be the first non-comment instruction in the Dockerfile. The only exception from this rule is when you want to use a variable in the FROM argument. In this case, FROM can be preceded by one or more ARG instructions. 
* ARG - This instructions allows you to define variables that can be passed at a build-time. 
You can also set a default value.
* LABEL - Used to add metadata to an image, such as description, version, author ..etc. You can specify more than one LABEL, and each LABEL instruction is a key-value pair.
* RUN -  The commands specified in this instruction will be executed during the build process. Each RUN instruction creates a new layer on top of the current image.
* ADD - Used to copy files and directories from the specified source to the specified destination on the docker image. The source can be local files or directories or an URL. If the source is a local tar archive, then it is automatically unpacked into the Docker image.
* COPY - Similar to ADD but the source can be only a local file or directory.
* ENV - This instruction allows you to define an environment variable.
* CMD - Used to specify a command that will be executed when you run a container. You can use only one CMD instruction in your Dockerfile.
* ENTRYPOINT - Similar to CMD, this instruction defines what command will be executed when running a container.
* WORKDIR - This directive sets the current working directory for the RUN, CMD, ENTRYPOINT, COPY, and ADD instructions.
* USER - Set the username or UID to use when running any following RUN, CMD, ENTRYPOINT, COPY, and ADD instructions.
* VOLUME - Enables you to mount a host machine directory to the container.
* EXPOSE - Used to specify the port on which the container listens at runtime.

## Create a Dockerfile
```Dockerfile
FROM ubuntu:18.04

RUN apt-get update && \
    apt-get install -y redis-server && \
    apt-get clean

EXPOSE 6379

CMD ["redis-server", "--protected-mode no"]
```
## Building the image
The next step is to build the image. To do so run the following command from the directory where the Dockerfile is located:
The option -t specifies the image name and optionally a username and tag in the ‘username/imagename:tag’ format.
```shell
docker build -t linuxize/redis .
```

## Running a container
Now that the image is created you run a container from it by running:
```shell
docker run -d -p 6379:6379 --name redis linuxize/redis
docker container ls
```