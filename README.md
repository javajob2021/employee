HR Platform on Azure Kubernetes Service (AKS): A Full-Stack Cloud-Native Demo

1. Overview

summary: “This project demonstrates a full-stack HR system built with Spring Boot 3, Angular 17, and MySQL 8, deployed on Azure Kubernetes Service using GitHub Actions, Argo CD, and Terraform for infrastructure automation.”

2. Objectives

Modernize a monolithic HR web app into a cloud-native microservice.

Build production-ready CI/CD pipelines.

Enable observability (metrics, logs, alerts) and secure configurations.

3. Project Structure
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

