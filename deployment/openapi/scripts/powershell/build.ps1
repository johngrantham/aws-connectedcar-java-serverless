
. "./config.ps1"

Write-Host " "
Write-Host "*************************************************************"
Write-Host "*                 Running the maven build                   *"
Write-Host "*************************************************************"
Write-Host " "

mvn clean install -q -f "${workspacePath}/main/pom.xml"

Write-Host " "
Write-Host "*************************************************************"
Write-Host "*              Uploading the Lambda jar files               *"
Write-Host "*************************************************************"
Write-Host " "

Write-S3Object `
    -BucketName ${bucket} `
    -File "${workspacePath}/main/functions/target/functions-FUNCTIONS-SNAPSHOT.jar" `
    -Key "${service}/${environment}/functions-${version}.jar"

Write-S3Object `
    -BucketName ${bucket} `
    -File "${workspacePath}/main/authorizers/target/authorizers-AUTHORIZERS-SNAPSHOT.jar" `
    -Key "${service}/${environment}/authorizers-${version}.jar"

Write-Host " "
