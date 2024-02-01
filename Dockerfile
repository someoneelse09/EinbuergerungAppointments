FROM gradle:7.6.0-jdk8 AS build
LABEL authors="lizaoreshkina"

COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM openjdk:8-jre-slim

RUN mkdir /app
ENV TOKEN=XXX

COPY --from=build /home/gradle/src/build/libs/ /app/

ENTRYPOINT ["java","-jar","/app/starter-bot-1.0-SNAPSHOT.jar"]
