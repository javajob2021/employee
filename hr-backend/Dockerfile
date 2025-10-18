# ---------- build stage ----------
# Pick an existing Maven+JDK21 image tag
FROM maven:3.9.9-eclipse-temurin-21 AS builder


# build args (optional)
ARG MAVEN_OPTS="-DskipTests"
ARG APP_DIR=/workspace

WORKDIR ${APP_DIR}

# copy only what is needed for dependency resolution first (cache)
COPY pom.xml .
COPY .mvn/ .mvn
COPY mvnw ./
# copy dependency-relevant files (if you use a multi-module adjust accordingly)
RUN --mount=type=cache,target=/root/.m2 mvn -B -DskipTests -e dependency:go-offline

# copy source and build
COPY src ./src

# run the maven package to produce the fat jar in target/
RUN --mount=type=cache,target=/root/.m2 mvn -B package -DskipTests

# ---------- runtime stage ----------
# smaller runtime image with JDK 21
FROM eclipse-temurin:21-jre

# create non-root user for better security
ENV APP_USER=appuser
ENV APP_HOME=/app

RUN addgroup --system ${APP_USER} && adduser --system --ingroup ${APP_USER} ${APP_USER}
WORKDIR ${APP_HOME}

# copy the jar from builder
# find the jar produced by maven: typically target/*.jar
COPY --from=builder /workspace/target/*.jar app.jar

# set permissions
RUN chown -R ${APP_USER}:${APP_USER} ${APP_HOME} && chmod 755 ${APP_HOME}/app.jar

# switch to non-root
USER ${APP_USER}

# environment defaults (can be overridden in docker-compose or env)
ENV JAVA_OPTS=""
ENV SPRING_PROFILES_ACTIVE=docker
ENV SERVER_PORT=8080

EXPOSE 8080

# simple healthcheck (calls the actuator health endpoint)
#RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

#HEALTHCHECK --interval=30s --timeout=5s --start-period=20s --retries=5 \
#  CMD curl -fsS http://localhost:8080/actuator/health | grep -q '"status":"UP"' || exit 1


ENTRYPOINT [ "sh", "-c", "exec java $JAVA_OPTS -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE} -Dserver.port=${SERVER_PORT} -jar /app/app.jar" ]
