#!/bin/zsh

set -e

workspacePath="[enter base path]/aws-connectedcar-java-lambda"
bucket="[enter bucket name]"
service="ConnectedCar"
environment="Dev"
version="20230105"
stage="api"

number=$(date +"%H%M%S")
domain="connectedcar${number}"

echo " "
echo "*************************************************************"
echo "*            Validating the config.sh variables             *"
echo "*************************************************************"
echo " "

if ! [ -d "${workspacePath}" ] ; then
  echo "Error: workspacePath is not valid"
  exit 1
fi

aws s3api head-bucket --bucket ${bucket}


