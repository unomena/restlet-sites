
Every APISpark cell is present in two different environments.

The *APISpark web console* is the development environment in which you design and maintain your cells.

When you deploy a cell, APISpark generates a program that will be deployed in the cloud and executed in the *APISpark runtime environment*.

Thus, your Data Stores and web APIs are only available to store data or receive HTTP requests after having been deployed.

Cells can be deployed from the draft, published and deprecated states, and can be re-deployed at any time.

![cell deployment](images/cell-deployment.jpg "cell deployment")

The deployment process is composed of several steps, which are displayed in the deployment information window.

![cell deployment process](images/deploymentmessages.png "cell deployment process")

Each step of the deployment process is also documented by a message in the **Messages** tab. The events are listed in the **Traces** section.

![traces](images/06.jpg "traces")
