FROM adoptopenjdk/openjdk11:alpine-jre
ADD . /src
WORKDIR /src
COPY digio-log-insights/build/libs/*.jar DigIoProgrammingTask.jar

RUN addgroup -g 1007 -S digio
RUN adduser -u 1007 -S digio -G digio
USER digio
CMD java -jar DigIoProgrammingTask.jar