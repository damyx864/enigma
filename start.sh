#!/bin/bash

# Package with maven
mvn package spring-boot:repackage

# Build Docker image
docker build --tag=enigma-java11:latest --rm=true .

# Run Docker container
docker run --detach --name=enigma-java11 --publish=8080:8080 enigma:latest

echo 'Now should be up and running!!'
echo 'Go to http://localhost:8080/enigma/'