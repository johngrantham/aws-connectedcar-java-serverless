
$deployment=$args[0]

if ($deployment -eq $null) {
    Write-Host "Usage: must include deployment parameter (sam, openapi)"
    return
}
elseif (("${deployment}" -ne "sam") -and ("${deployment}" -ne "openapi")) {
    Write-Host "Usage: must include deployment parameter (sam, openapi)"
    return
}

$workspacePath="[enter base path]/aws-connectedcar-java-serverless"
$bucket="[enter bucket name]"
$service="ConnectedCar"
$environment="Dev"
$stage="api"

$buildFile="buildspec/${deployment}.buildspec.yml"
$deployFile="deployment/${deployment}/templates/master.yaml"
$testFile="buildspec/test.buildspec.yml"

$repoOwner="johngrantham"
$sourceRepoName="aws-connectedcar-java-serverless"
$commonRepoName="aws-connectedcar-common"

Import-Module AWSPowerShell.NetCore

$ErrorActionPreference = "Stop"

Write-Host " "
Write-Host "*************************************************************"
Write-Host "*      Executing create stack command for the pipeline      *"
Write-Host "*************************************************************"
Write-Host " "

$templateBody = Get-Content -Path "${workspacePath}/pipeline/pipeline.yaml" -raw

New-CFNStack `
    -StackName "${service}Pipeline${environment}" `
    -TemplateBody ${templateBody} `
    -Parameter @( @{ ParameterKey="BucketName"; ParameterValue="${bucket}" }, `
                  @{ ParameterKey="ZipFile"; ParameterValue="${zip}" }, `
                  @{ ParameterKey="ServiceName"; ParameterValue="${service}" }, `
                  @{ ParameterKey="EnvironmentName"; ParameterValue="${environment}" }, `
                  @{ ParameterKey="StageName"; ParameterValue="${stage}" }, `
                  @{ ParameterKey="BuildFile"; ParameterValue="${buildFile}" }, `
                  @{ ParameterKey="TestFile"; ParameterValue="${testFile}" }, `
                  @{ ParameterKey="DeployFile"; ParameterValue="${deployFile}" }, `
                  @{ ParameterKey="RepoOwner"; ParameterValue="${repoOwner}" }, `
                  @{ ParameterKey="SourceRepoName"; ParameterValue="${sourceRepoName}" }, `
                  @{ ParameterKey="CommonRepoName"; ParameterValue="${commonRepoName}" }) `
    -Capability CAPABILITY_IAM,CAPABILITY_NAMED_IAM,CAPABILITY_AUTO_EXPAND

Write-Host " "
