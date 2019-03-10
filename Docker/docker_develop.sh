#! /bin/bash
 
cp Dockerfile ../

cd ..

# Clean and build the pakcage
docker run -it --rm -v `pwd`:/usr/src/mymaven -w /usr/src/mymaven jtim/maven-non-root:3.5.4-jdk-8-alpine mvn clean package

# Generate the image using the Dockerfile 
docker build -t santatecla/bibliografia .

rm Dockerfile

cd Docker
