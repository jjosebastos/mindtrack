FROM gradle:8.5-jdk17 AS build
WORKDIR /app

COPY --chown=gradle:gradle . .


RUN gradle build --no-daemon -x test


FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/build/libs/*SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]