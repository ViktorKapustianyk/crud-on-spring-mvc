FROM azul/zulu-openjdk-alpine:17

ENV TZ=UTC

COPY target/root.jar /app/root.jar

CMD ["java", "-jar", "/app/root.jar"]
