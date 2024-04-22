FROM busybox AS unpack

ARG CHROME_VERSION=114.0.5735.198

# wget Chromedriver and Google Chrome deb
RUN wget https://chromedriver.storage.googleapis.com/114.0.5735.90/chromedriver_linux64.zip
RUN wget -O /tmp/chrome.deb https://dl.google.com/linux/chrome/deb/pool/main/g/google-chrome-stable/google-chrome-stable_${CHROME_VERSION}-1_amd64.deb
RUN unzip chromedriver_linux64.zip -d /tmp

FROM eclipse-temurin:17-focal

# Copy Chromedriver from unpack stage
COPY --from=unpack /tmp/chromedriver /usr/local/bin/chromedriver
COPY --from=unpack /tmp/chrome.deb /tmp/chrome.deb

# Get Chrome
RUN apt-get update
RUN apt-get install -y /tmp/chrome.deb

# Create application
RUN mkdir /app
ENV TOKEN=XXX
ENV ChatId=XXX
COPY ./build/libs/ /app/

USER root

ENTRYPOINT ["java","-jar","/app/shadow-1.0-SNAPSHOT-all.jar"]
