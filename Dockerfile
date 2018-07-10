FROM alpine/git
WORKDIR /
RUN git clone https://github.com/vinitapenmatsa/Follow-The-White-Rabbit.git



FROM maven:3.5-jdk-8-alpine as build
COPY --from=0 /Follow-The-White-Rabbit /
RUN mvn install


FROM openjdk:8-jre-alpine
WORKDIR /
COPY --from=1 /target/Follow-The-White-Rabbit-0.0.1-SNAPSHOT.jar /

ENTRYPOINT java -jar Follow-The-White-Rabbit-0.0.1-SNAPSHOT.jar
