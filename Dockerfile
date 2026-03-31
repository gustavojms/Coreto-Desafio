FROM maven:3.9-eclipse-temurin-21-alpine AS build

WORKDIR /app

COPY .mvn/settings.xml /root/.m2/settings.xml
COPY pom.xml ./
RUN mvn dependency:resolve

COPY src ./src
RUN mvn package -DskipTests -q

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar
COPY src/main/resources/certs/ /app/certs/

ENV RSA_PUBLIC_KEY=file:/app/certs/public.pem
ENV RSA_PRIVATE_KEY=file:/app/certs/private.pem

EXPOSE 8080

RUN printf '#!/bin/sh\nif [ -n "$PGHOST" ]; then\n  export SPRING_DATASOURCE_URL="jdbc:postgresql://${PGHOST}:${PGPORT:-5432}/${PGDATABASE:-railway}"\n  export SPRING_DATASOURCE_USERNAME="${PGUSER}"\n  export SPRING_DATASOURCE_PASSWORD="${PGPASSWORD}"\n  echo "=== DB: ${SPRING_DATASOURCE_URL} user=${SPRING_DATASOURCE_USERNAME} ==="\nfi\nexec java -jar app.jar\n' > /app/start.sh && chmod +x /app/start.sh

ENTRYPOINT ["/app/start.sh"]
