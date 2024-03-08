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

RUN curl -o applicationInsightsAgent.jar https://github.com/microsoft/ApplicationInsights-Java/releases/download/3.5.0/applicationinsights-agent-3.5.0.jar
COPY applicationInsightsAgent.jar applicationInsightsAgent.jar

EXPOSE 8080
ENTRYPOINT ["java","-javaagent:applicationInsightsAgent.jar","-jar","/app/petstoreproductservice.jar"]
