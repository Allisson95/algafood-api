FROM maven:3.8.3-eclipse-temurin-17 AS build
COPY src /src
COPY pom.xml .
RUN mvn -f /pom.xml clean package -DskipTests=true

FROM eclipse-temurin:17-jre-alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
COPY --from=build /target/*.jar app.jar
ENTRYPOINT [ "java", "-jar", "/app.jar" ]