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

ADD https://github.com/microsoft/ApplicationInsights-Java/releases/download/3.5.0/applicationinsights-agent-3.5.0.jar /app

EXPOSE 8080
ENTRYPOINT ["java","-javaagent:/app/applicationinsights-agent-3.5.0.jar","-jar","/app/petstoreproductservice.jar"]
