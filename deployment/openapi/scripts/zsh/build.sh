#!/bin/zsh

source config.sh

echo " "
echo "*************************************************************"
echo "*                 Running the maven build                   *"
echo "*************************************************************"
echo " "

mvn clean install -q -f ${workspacePath}/main/pom.xml

echo " "
echo "*************************************************************"
echo "*      Uploading the Lambda jar files to the S3 folder      *"
echo "*************************************************************"
echo " "

aws s3 cp \
    ${workspacePath}/main/authorizers/target/authorizers-AUTHORIZERS-SNAPSHOT.jar \
    s3://${bucket}/${service}/${environment}/authorizers-${version}.jar

aws s3 cp \
    ${workspacePath}/main/functions/target/functions-FUNCTIONS-SNAPSHOT.jar \
    s3://${bucket}/${service}/${environment}/functions-${version}.jar

