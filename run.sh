#!/bin/bash

# Package with maven
mvn package spring-boot:repackage

# Run with java
java -jar target/enigma-0.0.1-SNAPSHOT.jar