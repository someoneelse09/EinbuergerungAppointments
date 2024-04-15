FROM arm32v7/eclipse-temurin:17-focal

RUN apt update && apt add chromium-chromedriver

RUN mkdir /app
ENV TOKEN=XXX
ENV ChatId=XXX
ARG JAR_FILE=build/libs/shadow-1.0-SNAPSHOT-all.jar
COPY ./build/libs/ /app/

ENTRYPOINT ["java","-jar","/app/shadow-1.0-SNAPSHOT-all.jar"]
