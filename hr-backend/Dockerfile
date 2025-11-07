# ---------- build stage ----------
FROM maven:3.9.9-eclipse-temurin-21 AS builder

ARG MAVEN_OPTS="-DskipTests"
ARG APP_DIR=/workspace
WORKDIR ${APP_DIR}

# leverage dependency cache
COPY pom.xml .
COPY .mvn/ .mvn
COPY mvnw ./
RUN --mount=type=cache,target=/root/.m2 mvn -B -DskipTests -e dependency:go-offline

# build sources
COPY src ./src
RUN --mount=type=cache,target=/root/.m2 mvn -B package -DskipTests

# ---------- runtime stage ----------
FROM eclipse-temurin:21-jre

# --- Non-root user (Task 1) ---
ARG APP_USER=appuser
ARG APP_UID=10001
ARG APP_GID=10001
ARG APP_HOME=/app

RUN addgroup --system --gid ${APP_GID} ${APP_USER} \
 && adduser  --system --uid ${APP_UID} --ingroup ${APP_USER} ${APP_USER}

# app dir + writable temp dir for runtime
WORKDIR ${APP_HOME}
RUN mkdir -p /tmp ${APP_HOME}/var && chown -R ${APP_UID}:${APP_GID} /tmp ${APP_HOME}

# copy the jar with correct ownership (avoids separate chown layer)
# NOTE: set your jar name via <finalName> in pom.xml or adjust the glob to match exactly.
COPY --from=builder --chown=${APP_UID}:${APP_GID} /workspace/target/*.jar app.jar

# switch to non-root (Task 1)
USER ${APP_UID}:${APP_GID}

# --- Read-only root readiness (Task 2) ---
# Tell Java/Spring to use a writable tmp; this lets the container run under readOnlyRootFilesystem.
ENV JAVA_TOOL_OPTIONS="-Djava.io.tmpdir=/tmp ${JAVA_TOOL_OPTIONS}"
# (Optional) If your app writes files, point those to ${APP_HOME}/var or mount a volume to it.

# defaults (overridable)
ENV JAVA_OPTS=""
ENV SERVER_PORT=8080
ENV SPRING_PROFILES_ACTIVE=prod

EXPOSE 8080

ENTRYPOINT [ "sh", "-c", "exec java $JAVA_OPTS -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE} -Dserver.port=${SERVER_PORT} -jar /app/app.jar" ]
