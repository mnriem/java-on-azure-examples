
# Receive a JMS message from an Azure Service Bus Queue

## Prerequisites

<!--

  if [[ -z $REGION ]]; then
    export REGION=eastus2
    echo "Using 'eastus2' region"
  fi

  -->
<!-- workflow.cron(0 14 * * 1) -->
<!-- workflow.include(../send-jms-message/README.md) -->

This example assumes you have previously completed the following examples:

1. [Create an Azure Resource Group](../../group/create/README.md)
1. [Create an Azure Service Bus](../create/README.md)
1. [Create an Azure Service Bus Queue](../create-queue/README.md)
1. [Send a JMS message](../send-jms-message/README.md)

## Receive a message from an Azure Service Bus Queue

First, create the environment variables used to connect to our message queue
using the command line below:


```shell
  export SERVICE_BUS_QUEUE_CONNECTION_STRING=$(az servicebus namespace authorization-rule keys list \
    --resource-group $RESOURCE_GROUP --namespace-name $SERVICE_BUS --name RootManageSharedAccessKey \
    --query primaryConnectionString --output tsv)
```

<!-- workflow.run()

  cd servicebus/receive-jms-message

  -->

Then build the client:

```shell
  mvn clean package
```

And then send the message:

```shell
  java -jar target/receive-jms-message.jar
```

<!-- workflow.run()

  cd ../..

  -->

<!-- workflow.directOnly() 

  export RESULT=$(az servicebus queue show --resource-group $RESOURCE_GROUP --namespace $SERVICE_BUS --name $SERVICE_BUS_QUEUE --query countDetails.activeMessageCount --output tsv)
  az group delete --name $RESOURCE_GROUP --yes || true
  if [[ "$RESULT" != "0" ]]; then
    exit 1
  fi

  -->

## Cleanup

Do NOT forget to remove the resources once you are done running the example.

1m
