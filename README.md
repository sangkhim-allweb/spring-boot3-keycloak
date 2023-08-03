# Spring Boot 3 Keycloak, Redis Cache & Rate Limiting

## swagger
    http://localhost:8080/api/

## h2 database console
    http://localhost:8080/h2-console

## KEYCLOAK
    https://www.keycloak.org/getting-started/getting-started-docker

    docker run -p 18080:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:22.0.1 start-dev

    https://medium.com/geekculture/using-keycloak-with-spring-boot-3-0-376fa9f60e0b

## Redis Cache & Rate Limiting
    https://redis.io/docs/getting-started/install-stack/docker/

    docker run --name redis -p 16379:6379 -d redis:7.0.12 --requirepass p@123456

    https://redis.com/redis-enterprise/redis-insight/

## Jenkins
    docker run -p 18090:8080 -p 50000:50000 jenkins/jenkins:lts
    
    /var/jenkins_home/secrets/initialAdminPassword
    0690a031f41b4b9aa36c08aca1fb6b98

    https://plugins.jenkins.io/blueocean/

## Other
    Google Java Format
    SonaLint
    Disable import * (Settings -> Editor -> Code Syle -> Java -> Imports)