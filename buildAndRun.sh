#!/bin/sh
mvn clean package && docker build -t org.example/JakartaEEWebApplicationExample .
docker rm -f JakartaEEWebApplicationExample || true && docker run -d -p 8080:8080 -p 4848:4848 --name JakartaEEWebApplicationExample org.example/JakartaEEWebApplicationExample 
