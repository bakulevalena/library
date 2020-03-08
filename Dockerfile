FROM openjdk:latest
ADD target/library-0.0.1-SNAPSHOT.jar library.jar
VOLUME /var/log/library
EXPOSE 8090
CMD ["java", "-jar", "library.jar"]