FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_ARTIFACT
ENV PROFILE = dev
ENV DEV_HOST_ADDRESS = 10.10.34.153
COPY ${JAR_ARTIFACT} app.jar
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom","-jar", "SPRING_PROFILES_ACTIVE=${PROFILE}","/app.jar"]