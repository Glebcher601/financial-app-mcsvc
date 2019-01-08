FROM openjdk:8-jdk-alpine
VOLUME /tmp

ENV DEBUG_AGENT=-agentlib:jdwp\=transport\=dt_socket,server\=y,suspend\=n,address\=5005
ARG EXPLODED_JAR_DIR=exploded

COPY ${EXPLODED_JAR_DIR}/BOOT-INF/lib /app/lib
COPY ${EXPLODED_JAR_DIR}/META-INF /app/META-INF
COPY ${EXPLODED_JAR_DIR}/BOOT-INF/classes /app
EXPOSE 8991 5005
ENV SPRING_PROFILES_ACTIVE=stage
ENTRYPOINT ["sh", "-c", "java ${DEBUG_AGENT} -cp app:app/lib/* com.nixsolutions.authorizationserver.AuthorizationServerApplicationKt"]