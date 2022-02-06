# Run instructions:
# Start docker desktop
    # and wait for it to be up.
# Open powershell/terminal and
    #  cd D:\pigeon\
# To build a image named pigeon from this file :
    # docker image build -t pigeon . -f Dockerfile
# To run container:
    # docker container run pigeon

# NOTE: To remove all runnning containers (in a powershell window - not terminal), execute this.
    # docker container rm -f $(docker container ls -aq)

# Troubleshooting (if you are not able to build image see this:https://stackoverflow.com/questions/65361083/docker-build-failed-to-fetch-oauth-token-for-openjdk
# In docker engine, change - > buildkit": true to false.
ARG DOCKER_BUILDKIT=0
ARG COMPOSE_DOCKER_CLI_BUILD=0

# Below step give us these setup dependencies (a linux machine, which has maven and JDK installed)
FROM maven:3.8.1-jdk-11

LABEL maintainer="pramodyadav027@gmail.com"

ARG HERE='/workspace/app'
WORKDIR /usr/pigeon

ARG USER_HOME_DIR="/usr"
ENV MAVEN_CONFIG "$USER_HOME_DIR/.m2"

# Get latest stable Google Chrome browser (This step installs stable chrome browser for us)
RUN apt-get update || true && apt-get -y install unzip wget curl vim \
    && wget -q -O - https://dl-ssl.google.com/linux/linux_signing_key.pub | apt-key add - \
	&& echo "deb http://dl.google.com/linux/chrome/deb/ stable main" >> /etc/apt/sources.list.d/google-chrome.list \
    && apt-get update \
    && apt-get upgrade --yes \
    && apt-get install --yes google-chrome-stable \
	&& rm /etc/apt/sources.list.d/google-chrome.list \
	&& sed -i 's/"$HERE\/chrome"/"$HERE\/chrome" --headless --disable-dev-shm-usage --no-sandbox/g' /opt/google/chrome/google-chrome \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/* /var/cache/apt/*

# map the volume
VOLUME /dev/shm

# ChromeDriver
# This should be downloaded by the project using webdriver manager and thus is not needed to be a part of environment

# Copy the src code and pom file.
   COPY pom.xml .
   COPY ./src src

# Keep container running (When running a container from this image)
# CMD tail -f /dev/null
CMD mvn clean test
