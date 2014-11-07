# Create and Host your First web API

## Introduction

This tutorial will show you how to deploy your first API in 15 minutes or less. Head to our [User Guide](/docs/guide/introduction) page first if you would like to learn about the different concepts within the platform. Now, let’s get right to it.

## Requirements

To follow this tutorial, you will need the following:

*   a web browser,
*   15 minutes of your time

## 1. Deploy the Data Store

[Sign in](https://apispark.com/signin) to your APISpark account or go back to your **Dashboard**.

Click on **+ Data Store**, choose **Entity Store** and name it **Contacts DB**.

![](/static/images/site/tutorials/tutorial1-createStore.png)

Click on the **Create** button.

You will be taken to the **EntityStore** overview page.

Click on **Add an entity** and name it **Contact**.

Click on **Add a property** to create three properties named **lastName**, **firstName** and
    **age**. Note that several properties were created automatically as well.

You can choose to define the data type, cardinality and default value.

![](/static/images/site/tutorials/tutorial1-entityOverview.png)

Click on the **Deploy** button to deploy your Entity Store.

![](/static/images/site/tutorials/tutorial1-storeDeploySuccess.png)

You can now add contacts. For the purpose of this tutorial, let’s add one manually.

Click on your **Contact** entity.

Click on the **Browser** tab in the right hand panel. (The **Browser** will only be visible if you have deployed your **Entity Store**. If it doesn’t appear, try refreshing the page).

Click on the **Add** button.

Fill in the fields.

![](/static/images/site/tutorials/tutorial1-addDataEntity.png)

Click on the **Add** button. Your first contact should be displayed like so:

![](/static/images/site/tutorials/tutorial1-browseData.png)

## 2. Deploy the web API

Remaining on the **Entity Store**’s page, click on the **Actions** button (down-facing arrow) to the right of the Deploy button.

Select **Export custom API** and name it **Contacts API**.

The domain will be created automatically but may not be available anymore so make sure to adjust it.

![](/static/images/site/tutorials/tutorial1-createAPI.png)

Click **Create**. You will be taken to the API’s **Overview**.

The screenshot below shows some of the drop down menus opened to give you an idea of how it is organized.

![](/static/images/site/tutorials/tutorial1-apiOverview.png)

Deploy the API by clicking the **Deploy** button.

![](/static/images/site/tutorials/tutorial1-07-apiDeploySuccess.png)

The API should now be accessible online. To reach it, use the subdomain of apispark.net that you chose when you created the API.

![](/static/images/site/tutorials/tutorial1-08-endpointOverview.png)

As you can see, the credentials required to invoke the API can be found in the **Overview** tab, by clicking on the relevant **Endpoint**. You will need to copy the **Endpoint URI**, **Login** and **Password** information for the next step.

## 3. Invoke the web API

Using a web API does not impose any particular programming language. It can even be done from a web browser. However, to test your API we recommend the use of tools such as the Chrome extensions [POSTMAN](https://chrome.google.com/webstore/detail/postman-rest-client/fdmmgilgnpjigdojojpjoooidkmcomcm?utm_source=chrome-ntp-icon) and [DHC](http://sprintapi.com/dhcs.html) that provide a graphical user interface to perform HTTP calls. The following figure shows the call to the Contacts API with POSTMAN.

When using POSTMAN, click on the **Basic Auth** tab, fill in the **Username** and **Password** fields with the information copied from your APISpark **Endpoint**. Fill in the **Endpoint URI** and add **/contacts/** at the end of it.

To retrieve the list of contacts in JSON, click the **Headers** button on the far right and input the **Accept** command in the **Header** field and write **application/json** in the **Value** field opposite.

Click the **Send** button.

The following figure shows the API call in POSTMAN.

![](/static/images/site/tutorials/tutorial1-09-postman.png)

>**Note:** APISpark can also generate custom Client SDKs for different environments such as Java, Android, GWT and JavaScript
    (AJAX or Node.js). More environments will be supported in the future.

Congratulations on completing this tutorial! If you have questions or suggestions, feel free to contact the [Help Desk](http://support.apispark.com/).
