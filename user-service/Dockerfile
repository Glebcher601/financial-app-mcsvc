FROM openjdk:8-jdk-alpine
VOLUME /tmp

ARG DEBUG_AGENT=-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005
ARG EXPLODED_JAR_DIR=target/dependency

COPY ${EXPLODED_JAR_DIR}/BOOT-INF/lib /app/lib
COPY ${EXPLODED_JAR_DIR}/META-INF /app/META-INF
COPY ${EXPLODED_JAR_DIR}/BOOT-INF/classes /app
EXPOSE 8991 5005
ENV PROFILE=dev
ENV DEV_HOST_ADDRESS=10.10.34.153
ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005", "-cp","app:app/lib/*", "com.nixsolutions.userservice.UserServiceApplicationKt", "SPRING_PROFILES_ACTIVE=${PROFILE}"]