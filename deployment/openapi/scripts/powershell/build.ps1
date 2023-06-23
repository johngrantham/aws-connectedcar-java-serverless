
. "./config.ps1"

Write-Host " "
Write-Host "*************************************************************"
Write-Host "*                 Running the maven build                   *"
Write-Host "*************************************************************"
Write-Host " "

mvn clean install -q -f "${workspacePath}/main/pom.xml"

Write-Host " "
Write-Host "*************************************************************"
Write-Host "*              Uploading the Lambda jar file                *"
Write-Host "*************************************************************"
Write-Host " "

Write-S3Object `
    -BucketName ${bucket} `
    -File "${workspacePath}/main/lambda/target/lambda-LAMBDA-SNAPSHOT.jar" `
    -Key "${service}/${environment}/lambda-${version}.jar"

Write-Host " "
