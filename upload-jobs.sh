#!/bin/bash

# set -e
# set -x
baseUrl=${1:-"http://localhost:30505/"}
user=${2:-"admin"}
pass=${3:-"pass123"}

echo 'Executing seed job via Jenkins REST API'
./gradlew rest -Dpattern=src/jobs/*.groovy -DbaseUrl="${baseUrl}" -Dusername=$user -Dpassword="$pass"
