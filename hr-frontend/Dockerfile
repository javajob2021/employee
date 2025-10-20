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
# Copy built Angular app
COPY --from=build /app/dist/hr-frontend/browser /usr/share/nginx/html
# Nginx: proxy /api to backend service in the Docker network
COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
