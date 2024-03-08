#
# Build stage
#
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /build
COPY pom.xml .
COPY src ./src
RUN mvn clean package

#
# Package stage
#
FROM openjdk:17-slim
COPY --from=build /build/target/*.jar /app/petstoreproductservice.jar
COPY agent/applicationinsights-agent-3.5.0.jar applicationinsights-agent-3.5.0.jar
EXPOSE 8080
ENTRYPOINT ["java","-javaagent:applicationinsights-agent-3.5.0.jar","-jar","/app/petstoreproductservice.jar"]
