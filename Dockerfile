FROM eclipse-temurin:17-focal

RUN mkdir /app
ENV TOKEN=XXX
ENV ChatId=XXX
COPY ./build/libs/ /app/

ENTRYPOINT ["java","-jar","/app/shadow-1.0-SNAPSHOT-all.jar"]
