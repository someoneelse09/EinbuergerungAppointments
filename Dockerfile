FROM eclipse-temurin:17-focal

RUN mkdir /app
ENV TOKEN=XXX
ENV ChatId=XXX
COPY ./build/libs/ /app/
RUN ln -s /tmp /home/sbx_user1051/.cache

ENTRYPOINT ["java","-jar","/app/shadow-1.0-SNAPSHOT-all.jar"]
