FROM fabric8/java-jboss-openjdk8-jdk:1.2.3

ENV AB_OFF true

EXPOSE 8888

COPY build/libs/*.jar /deployments/