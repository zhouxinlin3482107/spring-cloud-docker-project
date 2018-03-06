FROM test/openjdk1.8:1.8.0_101
VOLUME /tmp
ADD test-uaa-1.0-SNAPSHOT.war /app.war

EXPOSE 9002

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.war"]