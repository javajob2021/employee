HR App

A full-stack Human Resource management system built with Spring Boot 3, Angular 17, and MySQL, containerized with Docker and deployed to Kubernetes.
This project demonstrates end-to-end DevOps practices — from local development to CI/CD and monitoring — using GitHub Actions, Prometheus, and Grafana.

Project Structure
hr-backend
hr-frontend
infra/
  platform/
    ingress-nginx/
    cert-manager/
    monitoring/          # <- grafana, prometheus, loki, promtail live here
      grafana/
      prometheus/
      loki-promtail/
  scripts/
    bootstrap-cluster.sh
    deploy-platform.sh   # helm install/upgrade for monitoring stack
  terraform/             # (optional) AKS/EKS/IAM/etc

k8s/
  base/
    hr-backend/
    hr-frontend/
    namespace.yaml
  overlays/
    dev/
    staging/
    prod/
