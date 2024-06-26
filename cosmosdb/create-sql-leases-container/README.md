
# Create a SQL leases container

## Prerequisites

This example assumes you have previously completed the following examples:

1. [Create an Azure Resource Group](../../group/create/README.md)
1. [Create an Azure Cosmos DB](../create/README.md)
1. [Create a database](../create-sql-database/README.md)

<!-- 

  if [[ -z $REGION ]]; then
    export REGION=westus
  fi

  -->
<!-- workflow.cron(0 20 * * 2) -->
<!-- workflow.include(../create-sql-database/README.md) -->

## Create the SQL leases container

To create the SQL leases container use the following command lines:

<!-- workflow.skip() -->
````shell
  export COSMOSDB_SQL_LEASES_CONTAINER=sql-leases-$RANDOM

  az cosmosdb sql container create \
    --resource-group $RESOURCE_GROUP \
    --account-name $COSMOSDB_ACCOUNT_NAME \
    --database-name $COSMOSDB_SQL_DATABASE \
    --name $COSMOSDB_SQL_LEASES_CONTAINER \
    --partition-key-path '/id'
````

<!-- workflow.run()

  if [[ -z $COSMOSDB_SQL_LEASES_CONTAINER ]]; then
    export COSMOSDB_SQL_LEASES_CONTAINER=sql-leases-$RANDOM
    az cosmosdb sql container create \
      --resource-group $RESOURCE_GROUP \
      --account-name $COSMOSDB_ACCOUNT_NAME \
      --database-name $COSMOSDB_SQL_DATABASE \
      --name $COSMOSDB_SQL_LEASES_CONTAINER \
      --partition-key-path '/id'
  fi

  -->

## Cleanup

<!-- workflow.directOnly()
  
  export RESULT=$(az cosmosdb sql container show \
    --resource-group $RESOURCE_GROUP \
    --account-name $COSMOSDB_ACCOUNT_NAME \
    --database-name $COSMOSDB_SQL_DATABASE \
    --name $COSMOSDB_SQL_LEASES_CONTAINER \
    --output tsv --query id)
  az group delete --name $RESOURCE_GROUP --yes || true
  if [[ "$RESULT" == "" ]]; then
    echo "Failed to create CosmosDB SQL leases container $COSMOSDB_SQL_CONTAINER"
    exit 1
  fi

  -->

Do NOT forget to remove the resources once you are done running the example.

## Additional documentation

1. [Azure Cosmos DB documentation](https://docs.microsoft.com/azure/cosmos-db/README.md)
1. [Azure CLI - az cosmosdb sql container](https://docs.microsoft.com/cli/azure/cosmosdb/sql/container)
1. [Change feed in Azure Cosmos DB](https://docs.microsoft.com/azure/cosmos-db/change-feed)

1m
