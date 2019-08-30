#!/usr/bin/env bash

set -eu

dir=$(dirname ${0})

ID=${1}

apiToken=$(${dir}/idam-authenticate.sh "${IDAM_ADMIN_USER:-idamOwner@hmcts.net}" "${IDAM_ADMIN_PASSWORD:-Ref0rmIsFun}")

echo -e "\nCreating role with: ${ID}"

STATUS=$(curl -s -o /dev/null -w '%{http_code}' -H 'Content-Type: application/json' -H "Authorization: AdminApiAuthToken ${apiToken}" \
  ${IDAM_API_BASE_URL:-http://localhost:5000}/roles -d '{
  "id": "'${ID}'",
  "name": "'${ID}'",
  "description": "'${ID}'",
  "assignableRoles": [ ],
  "conflictingRoles": [ ]
}')

if [ $STATUS -eq 201 ]; then
  echo "Role created sucessfully"
elif [ $STATUS -eq 409 ]; then
  echo "Role already exists!"
else
  echo "ERROR: HTTPCODE = $STATUS"
  exit 1
fi
