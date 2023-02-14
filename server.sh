#!/usr/bin/bash
export API_KEY=$1
docker-compose down
docker-compose pull && envsubst < docker-compose.yaml | docker-compose -f - up --detach
