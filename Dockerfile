# Reference: https://spring.io/guides/topicals/spring-boot-docker/

#############################################
################ Build Stage ################
#############################################
FROM eclipse-temurin:17-jdk-alpine AS builder
WORKDIR /workspace
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src
RUN ./mvnw install -DskipTests
RUN mkdir target/extracted \
    && java -Djarmode=layertools -jar target/*.jar extract --destination target/extracted

#############################################
################ Final Stage ################
#############################################
FROM eclipse-temurin:17-jre-alpine
ARG EXTRACTED=/workspace/target/extracted
WORKDIR /application
COPY --from=builder ${EXTRACTED}/dependencies/ ./
COPY --from=builder ${EXTRACTED}/spring-boot-loader/ ./
COPY --from=builder ${EXTRACTED}/snapshot-dependencies/ ./
COPY --from=builder ${EXTRACTED}/application/ ./
COPY run ./
RUN addgroup -S spring \
    && adduser -S spring -G spring \
    && chmod -R 005 .
USER spring:spring
ENTRYPOINT [ "./run" ]