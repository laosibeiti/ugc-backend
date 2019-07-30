FROM java:8
VOLUME /tmp
ADD job-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar","--spring.profiles.active=online"]