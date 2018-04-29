FROM java:8-jre-alpine

RUN mkdir /app
COPY / /app/
WORKDIR /app
RUN chmod +x gradlew && ./gradlew bootJar

FROM fabric8/java-jboss-openjdk8-jdk:1.2.3

ENV AB_OFF true

EXPOSE 8888

COPY --from=0 /app/build/libs/*.jar /deployments/