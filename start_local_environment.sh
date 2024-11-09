#!/bin/bash

# If set to true, skips import
NO_IMPORT=true
PROFILE=akhq

abort() {
  echo "An error occurred. Exiting..." >&2
  exit 1
}

trap 'abort' 0

set -e


# Set necessary variables
for arg in "$@"; do
  case $arg in
  no-import)
    NO_IMPORT=true
    ;;
  esac
done

cd local-env-dataloom

# Execute docker compose\
docker compose --profile ${PROFILE} up --build ${PROFILE} --always-recreate-deps --detach


waitTime=3
#Prepare mongo replicaset
echo "Execute initialize replicaset wait for $waitTime s"
sleep $waitTime
docker exec mongodb bash -c //docker-entrypoint-initdb.d//rs-init.sh

#Add user to mongo replicaset
echo "Execute add user wait for $waitTime s"
sleep $waitTime
docker exec mongodb bash -c //docker-entrypoint-initdb.d//createDBUser.sh

#Create collections to mongo replicaset
echo "Execute create collections wait for $waitTime s"
sleep $waitTime
docker exec mongodb bash -c //docker-entrypoint-initdb.d//createCollections.sh

#Import data collections to mongo replicaset
if [[ ${NO_IMPORT} == false ]]; then
  echo "Execute import collections wait for $waitTime s"
  sleep $waitTime
  docker exec mongodb bash -c //docker-entrypoint-initdb.d//import.sh
fi

trap : 0
