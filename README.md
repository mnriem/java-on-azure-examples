# Java on Azure Examples

This GitHub repository contains a set of Azure examples specifically for Java developers to quickly get started with Azure.
Please use the issue tracker to leave feedback, file issues or to propose other examples.

## Getting started

To work with these examples it is assumed you have the Azure CLI installed, and you have logged in and set your default subscription.
If you haven't done so follow the steps below.

_Note: Logging in and setting your default subscription needs to be done once per terminal session._

### Install Azure CLI

To setup the Azure CLI, please visit [Install the Azure CLI](https://docs.microsoft.com/en-us/cli/azure/install-azure-cli).
And once you are done come back to this README.

### Login into Azure

<!-- workflow.skip() -->
````shell
  az login
````

### Set your default subscription

Get a list of your subscriptions (notice the `refresh` parameter that retrieves up-to-date subscriptions from the server) :

<!-- workflow.skip() -->
````shell
  az account list --output table --refresh
````

Set your default subscription for this session using the subscription id from the previous output:

<!-- workflow.skip() -->
````shell
  az account set --subscription "subscription-id"
````

<!-- workflow.run() 

  exit 0

  -->

## Our alphabetical list of examples

1. [Azure App Configuration examples](appconfig/)
1. [Azure App Service examples](appservice/)      
1. [Azure Cache for Redis examples](redis/)            
1. [Azure Cognitive Services examples](cognitiveservices/)
1. [Azure Container Apps examples](containerapp/)
1. [Azure Container Instances examples](container/)
1. [Azure Container Registry examples](acr/)
1. [Azure Cosmos DB examples](cosmosdb/)
1. [Azure Front Door examples](afd/)
1. [Azure Data Explorer examples](kusto/)              
1. [Azure Database for MySQL examples](mysql/)         
1. [Azure Database for PostgreSQL examples](postgres/) 
1. [Azure Event Hubs examples](eventhubs/)             
1. [Azure Functions examples](functionapp/)            
1. [Azure Key Vault examples](keyvault/)               
1. [Azure Kubernetes Service examples](aks/)           
1. [Azure Monitor examples](monitor/)                  
1. [Azure Networking examples](network/)               
1. [Azure Red Hat OpenShift examples](aro/)
1. [Azure Resource Group examples](group/)             
1. [Azure Service Bus examples](servicebus/)           
1. [Azure Spring Apps examples](spring/)               
1. [Azure SQL Database examples](sql/)                 
1. [Azure Storage examples](storage/)                  

## Contributing

How do I contribute? See [Contributing](CONTRIBUTING.md)
