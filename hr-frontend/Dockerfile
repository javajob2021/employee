# ---------- build stage ----------
FROM node:20-alpine AS build
WORKDIR /app

# Install deps
COPY package*.json ./
RUN npm ci

# Copy source and build with the "docker" configuration
COPY . .
# Make sure angular.json has a "docker" configuration (fileReplacements -> environment.docker.ts)
RUN npm run build -- --configuration=docker

# ---------- runtime stage ----------
FROM nginx:1.27-alpine

# --- Non-root user (Task 1) ---
ARG APP_USER=appuser
ARG APP_UID=10101
ARG APP_GID=10101
RUN addgroup -S -g ${APP_GID} ${APP_USER} \
 && adduser  -S -u ${APP_UID} -G ${APP_USER} ${APP_USER}

# Runtime/writable paths for NGINX when root FS is read-only (Task 2)
# NGINX writes to /var/cache/nginx and /var/run (pid); logs may go to /var/log/nginx
RUN mkdir -p /var/cache/nginx /var/run /var/log/nginx /tmp \
 && chown -R ${APP_UID}:${APP_GID} /var/cache/nginx /var/run /var/log/nginx /tmp

# Copy built app with ownership set (no extra chown layer)
COPY --from=build --chown=${APP_UID}:${APP_GID} /app/dist/hr-frontend/browser /usr/share/nginx/html
# Your nginx.conf should listen on 8080 and NOT declare "user ...;"
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Switch to non-root
USER ${APP_UID}:${APP_GID}

# Use an unprivileged port to avoid needing root
EXPOSE 8080
CMD ["nginx", "-g", "daemon off;"]

# (Optional) declare writable volumes for Docker-only runs (K8s will mount emptyDir instead)
VOLUME ["/var/cache/nginx", "/var/run", "/var/log/nginx"]
