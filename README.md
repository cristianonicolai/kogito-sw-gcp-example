# Kogito Serverless Workflow GCP example

This example demonstrates how Kogito Serverless Workflow can be used to manage resources in GCP cloud. More specifically, this project contains a series of workflows that manage Service Account and Compute Instance. 

## Requirements

- Java 11
- Maven
- [Install the Google Cloud CLI](https://cloud.google.com/sdk/docs/install-sdk)
- [Create a GCP project](https://cloud.google.com/resource-manager/docs/creating-managing-projects)

## Running the example

Generate a new token to interact with GCP REST API
```shell
gcloud auth application-default print-access-token
```

> Before executing the example, make sure you enable Compute Instance API in your GCP project.

Add token values to `application.properties`, as well as the GCP project ID, Zone and Region where you would like the resources to be created.

Execute the example tests using
```shell
mvn clean install
```

Follow the log and check your GCP project, where a new service account named `kogito-sw-gcp` and compute instance named `kogito-sw-gcp` will be created and deleted.

## Resources

- [Kogito SW documentation](https://kiegroup.github.io/kogito-docs/serverlessworkflow/latest/)
- [Serverless Workflow specification](https://serverlessworkflow.io/)
- [GCP API discovery](https://developers.google.com/discovery)
- [GCP API explorer](https://developers.google.com/apis-explorer)
- [GCP REST Authentication](https://cloud.google.com/docs/authentication/rest)
- [GCP Compute Instance API](https://cloud.google.com/compute/docs/apis)
- [GCP Service Account API](https://cloud.google.com/iam/docs/reference/rest/v1/projects.serviceAccounts) 