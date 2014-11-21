
# I get an Error 500 when I try to invoke my API

Before invoking your API, make sure you call the last version of your API and Data Store. You need to (re)deploy your API and the Data Stores (or/and File Stores) associated.

## (Re)deploy your API
Open your API's Overview and click on the **Deploy** button in the top right corner of your screen.

## (Re)deploy your Data Stores
To retrieve the different cells your API uses, open your API's Overview and click on the **Settings** tab.  
Click on the **Imports** section to see the different stores linked to your API.  

![Imports section](images/imports-section.jpg "Imports section")

To open a Store, click on the store you want to open from the **Imports** section.  
Click on the name of the store in the central panel.

![Open store](images/open-store.jpg "Open store")

Click on the **Deploy** button in the top right corner of your screen.

# I wonder if my cell has been deployed

Once your cell has been deployed, a confirmation message displays on top of your screen to inform you that the deployment is successful.

![Confirmation message](images/cell-deployed.jpg "confirmation message")

If you have any doubt, go to the **Messages** tab which reports the last actions performed on your cell.

![Messages tab](images/messages-section.jpg "Messages tab")

# I want a resource to be accessible without authentication

The API credentials (login/password) are used to identify the users of your API.
If you want a Resource to be accessible without authentication, you can modify the security access directly on a method and set it to **Anyone**, meaning a user authenticated or not.

Open your API's Overview, in the **Resources** section, select a resource method e.g. GET method.  
In the central panel, click on the **Security** tab.
Select the **Anyone** checkbox.  
Click on the **Save** button.

![Access to anyone](images/method-anyone.jpg "Access to anyone")
