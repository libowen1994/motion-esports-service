FROM openjdk:8-jre-alpine
VOLUME /tmp
ADD build/libs/motion-esports-service-1.0.0.jar app.jar
RUN sh -c 'touch /app.jar'
EXPOSE 8080 9090 9898
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/urandom -jar /app.jar" ]
