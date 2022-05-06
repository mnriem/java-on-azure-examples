#!/bin/bash
cd ..


if [[ -z $RESOURCE_GROUP ]]; then
export RESOURCE_GROUP=java-on-azure-$RANDOM
export REGION=westus2
fi

az group create --name $RESOURCE_GROUP --location $REGION
export SERVICE_BUS=service-bus-$RANDOM
az servicebus namespace create \
--resource-group $RESOURCE_GROUP \
--name $SERVICE_BUS \
--sku Premium \
--location $REGION
if [[ -z $SERVICE_BUS_QUEUE ]]; then
export SERVICE_BUS_QUEUE=service-bus-queue-$RANDOM
fi
az servicebus queue create \
--resource-group $RESOURCE_GROUP \
--namespace-name $SERVICE_BUS \
--name $SERVICE_BUS_QUEUE
export SERVICE_BUS_QUEUE_CONNECTION_STRING=$(az servicebus namespace authorization-rule keys list \
--resource-group $RESOURCE_GROUP --namespace-name $SERVICE_BUS --name RootManageSharedAccessKey \
--query primaryConnectionString --output tsv)
cd integration/servicebus/send-message
mvn clean package
java -jar target/send-message.jar
cd ../../..
export SERVICE_BUS_QUEUE_CONNECTION_STRING=$(az servicebus namespace authorization-rule keys list \
--resource-group $RESOURCE_GROUP --namespace-name $SERVICE_BUS --name RootManageSharedAccessKey \
--query primaryConnectionString --output tsv)
cd integration/servicebus/receive-message
mvn clean package
java -jar target/receive-message.jar
cd ../../..
export RESULT=$(az servicebus queue show --resource-group $RESOURCE_GROUP --namespace $SERVICE_BUS --name $SERVICE_BUS_QUEUE --query countDetails.activeMessageCount --output tsv)
az group delete --name $RESOURCE_GROUP --yes || true
if [[ "$RESULT" != "0" ]]; then
exit 1
fi