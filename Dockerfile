FROM busybox AS unpack
# wget Chromedriver and Google Chrome deb
RUN wget https://chromedriver.storage.googleapis.com/114.0.5735.90/chromedriver_linux64.zip
RUN wget -P /tmp https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb
RUN unzip chromedriver_linux64.zip -d /tmp

FROM eclipse-temurin:17-focal

RUN mkdir /app
ENV TOKEN=XXX
ENV ChatId=XXX
COPY ./build/libs/ /app/
# Copy Chromedriver from unpack stage
COPY --from=unpack /tmp/chromedriver /usr/local/bin/chromedriver

# Install Google Chrome
# Install wget
RUN apt-get update && apt-get install -y wget gnupg2

# Get Chrome
# The wget command here is redundant with the busybox stage. Sue me.
RUN wget -q -O - https://dl.google.com/linux/linux_signing_key.pub | apt-key add -
RUN sh -c 'echo "deb http://dl.google.com/linux/chrome/deb/ stable main" >> /etc/apt/sources.list.d/google.list'
RUN apt-get update
RUN apt-get install -y google-chrome-stable

ENTRYPOINT ["java","-jar","/app/shadow-1.0-SNAPSHOT-all.jar"]
