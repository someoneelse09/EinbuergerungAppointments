FROM arm32v7/gradle:8.5-jdk17 AS build
LABEL authors="lizaoreshkina"

COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN ./gradlew build --no-daemon

FROM arm32v7/eclipse-temurin:17-focal

RUN apt update && apt add chromium-chromedriver

RUN mkdir /app
ENV TOKEN=XXX
ENV ChatId=XXX
ARG JAR_FILE=build/libs/shadow-1.0-SNAPSHOT-all.jar
COPY --from=build /home/gradle/src/build/libs/ /app/

ENTRYPOINT ["java","-jar","/app/shadow-1.0-SNAPSHOT-all.jar"]
