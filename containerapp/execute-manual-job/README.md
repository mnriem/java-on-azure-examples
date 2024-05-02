# Execute a manual job

## Prerequisites

<!-- 

  if [[ -z $REGION ]]; then
    export REGION=westus
  fi

  -->
<!-- workflow.cron(0 10 * * 3) -->
<!-- workflow.include(../../acr/helloworldjob/README.md) -->
<!-- workflow.include(../create-manual-job/README.md) -->

This example assumes you have previously completed the following examples:

1. [Create an Azure Resource Group](../../group/create/README.md)
1. [Create an Azure Container Registry](../../acr/create/README.md)
1. [Build and push a Hello World Job application to Azure Container Registry](../../acr/helloworldjob/README.md)
1. [Create an Azure Container Apps environment](../create-environment/README.md)
1. [Create a manual job](../create-manual-job/README.md)

## Execute the manual job

To execute the job manually use the command line below.

```shell
  az containerapp job start \
    --name $ACA_JOB_NAME \
    --resource-group $RESOURCE_GROUP
```

<!-- workflow.directOnly()

  sleep 60
  az group delete --name $RESOURCE_GROUP --yes || true

  -->

## Cleanup

Do NOT forget to remove the resources once you are done running the example.

1m
