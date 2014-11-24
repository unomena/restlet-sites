
# 1. I get an Error 500 when I try to invoke my API

Before invoking your API, make sure you call the last version of your API and Data Store. You need to (re)deploy your API and the Data Stores (or/and File Stores) associated.

## (Re)deploy your API
Open your API's Overview and click on the **Deploy** button in the top right corner of your screen.

## (Re)deploy your Data Stores
To retrieve the different cells your API uses, open your API's Overview and click on the **Settings** tab.  
Click on the **Imports** section to see the different stores linked to your API.  

To open a Store, click on the store you want to open from the **Imports** section.  
Click on the name of the store in the central panel.

![Open store](images/open-store.jpg "Open store")

Click on the **Deploy** button in the top right corner of your screen.

## Check the Entity Store Traces

If you still get a 500 error message, open your Entity Store's Overview page.
Click the **Messages** tab.
The **Traces** section may display a *runtime error*. Click on it and check its **Content**. If the content does not help you, send it to our Help Desk to allow a quick resolution.

![Entity Store Traces](images/entity-store-traces.png "Entity Store Traces")


# 2. I wonder if my cell is deployed

Once your cell has been deployed, a confirmation message displays on top of your screen to inform you that the deployment is successful.

![Confirmation message](images/cell-deployed.jpg "confirmation message")

If you have any doubt, go to the **Messages** tab which reports the last actions performed on your cell.

![Messages tab](images/messages-section.jpg "Messages tab")

Jump to the [Test your web API](technical-resources/apispark/guide/create/test "Test your web API") section of our online documentation for further information.

# 3. What is the difference between deploying & publishing my API?

## Deploying your API
When you deploy a cell, APISpark generates source code, compiles and deploys it in the cloud so that it will be executed in the APISpark runtime environment. As long as you have not deployed your data stores and web APIs, they are not available to store data or receive HTTP requests.

Jump to the [Cell deployment](technical-resources/apispark/guide/explore/cell-deployment "Cell deployment") section of our online documentation for further information.

## Publishing your API
When you publish your API, you specify that it is in a stable state and is available to be used by end users or imported by other cells: you bring it into production.
A Published cell (APIs, data stores, etc.) cannot undergo structural modifications or be deleted as this could break clients or other cells using it.

Jump to the [Cell lifecycle](technical-resources/apispark/guide/explore/lifecycle "Cell lifecycle") section of our online documentation for further information.


# 4. I want a resource to be accessible without authentication

The API credentials (login/password) are used to identify the users of your API.
If you want a Resource to be accessible without authentication, you can modify the security access directly on a method and set it to **Anyone**, meaning a user authenticated or not.

Open your API's Overview, in the **Resources** section, select a resource method e.g. GET method.  
In the central panel, click on the **Security** tab.
Select the **Anyone** checkbox.  
Click on the **Save** button.

![Access to anyone](images/method-anyone.jpg "Access to anyone")

# 5. I cannot find where to download Client SDKs

Deploy your web API.  
Go to the **Downloads** tab.  
Click on the action button on top right of your screen and select **Generate downloads**.  
In the **Clients** section, click the one you want to download.  
Click the **Download** button.

>**Note:** The **Download** button is only visible once you have generated the downloads.

![Generate client SDKs](images/generate-sdks.jpg "Generate client SDKs")

Jump to the [Generate custom client SDKs](technical-resources/apispark/guide/publish/publish/client-sdk "Generate custom client SDKs") section of our user guide for further information.
