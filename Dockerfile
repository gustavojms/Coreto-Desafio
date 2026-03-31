FROM maven:3.9-eclipse-temurin-21-alpine AS build

WORKDIR /app

COPY .mvn/settings.xml /root/.m2/settings.xml
COPY pom.xml ./

RUN --mount=type=cache,target=/root/.m2/repository \
    mvn dependency:resolve

COPY src ./src
RUN --mount=type=cache,target=/root/.m2/repository \
    mvn package -DskipTests -q

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar
COPY src/main/resources/certs/ /app/certs/

ENV SPRING_PROFILES_ACTIVE=prod
ENV RSA_PUBLIC_KEY=file:/app/certs/public.pem
ENV RSA_PRIVATE_KEY=file:/app/certs/private.pem

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
