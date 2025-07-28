FROM maven:3.8.6-eclipse-temurin-17-alpine AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn package -DskipTests


FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/config-server-*.jar app.jar
ENV SPRING_CLOUD_CONFIG_SERVER_GIT_URI=https://github.com/Yaroslav3577/test
ENV SPRING_CLOUD_CONFIG_SERVER_GIT_SEARCH-PATHS=local-config
ENTRYPOINT ["java", "-jar", "app.jar"]