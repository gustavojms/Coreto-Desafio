#!/bin/sh

echo "=== CORETO API Startup ==="
echo "DATABASE_URL: ${DATABASE_URL:-NOT SET}"
echo "PGHOST: ${PGHOST:-NOT SET}"
echo "PGPORT: ${PGPORT:-NOT SET}"
echo "PGDATABASE: ${PGDATABASE:-NOT SET}"
echo "PGUSER: ${PGUSER:-NOT SET}"

# Build JDBC URL from DATABASE_URL or PG* vars
if [ -n "$DATABASE_URL" ]; then
  export SPRING_DATASOURCE_URL="jdbc:${DATABASE_URL}"
  echo "Using DATABASE_URL -> ${SPRING_DATASOURCE_URL}"
elif [ -n "$PGHOST" ]; then
  export SPRING_DATASOURCE_URL="jdbc:postgresql://${PGHOST}:${PGPORT:-5432}/${PGDATABASE:-railway}"
  echo "Using PGHOST -> ${SPRING_DATASOURCE_URL}"
fi

if [ -n "$PGUSER" ]; then
  export SPRING_DATASOURCE_USERNAME="${PGUSER}"
fi

if [ -n "$PGPASSWORD" ]; then
  export SPRING_DATASOURCE_PASSWORD="${PGPASSWORD}"
fi

echo "Final SPRING_DATASOURCE_URL: ${SPRING_DATASOURCE_URL:-NOT SET}"
echo "==========================="

exec java -jar app.jar
