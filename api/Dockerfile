FROM gradle:8.14.0-jdk21-alpine AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon -q

FROM amazoncorretto:21-alpine3.21 AS final

COPY --from=build /home/gradle/src/build/libs /app
# will be used once the swagger docs are implemented
#COPY --from=build /home/gradle/src/api/docs /app/docs

WORKDIR /app

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "api.jar", "-config", "application.conf"]
