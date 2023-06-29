#!/bin/zsh

source config.zsh

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
    ${workspacePath}/main/lambda/target/lambda-LAMBDA-SNAPSHOT.jar \
    s3://${bucket}/${service}/${environment}/lambda-${version}.jar

echo " "
