#!/usr/bin/env bash
# first arg -> env file source

source $1
./gradlew :buildFatJar
docker compose rm
docker compose up -d
docker ps
exec bash