# Introduction

This tutorial will show you how to create a custom web API that gives access to existing data stored in a Parse backend.
[Parse.com](http://parse.com/) is a popular Backend as a Service (BaaS) provider that powers connected mobile applications.

# Requirements

To follow this tutorial, you will need:

*   a web browser,
*   20 minutes of your time,
*   your Parse login details.

# 1. Prepare the Parse Backend

Sign in to your **Parse** account and go to the **Dashboard**.

Select the **App** you wish to work on. Ours is called APISpark.

Click on the **Data Browser** tab.

Click on the **New Class** button and give it a name. For the purpose of this tutorial, we named our class **Contact**.

You will see four automatically generated columns: objectId, createAt, updatedAt and ACL.

Add the following extra columns:

*   **lastName** (String) : last name
*   **firstName** (String) : first name
*   **age** (Number) : age

Create a new row by clicking **+Row**. You can directly edit the row’s data by entering values in the fields. For the sake of this
	tutorial, we added a person called John Smith, age 34.

![](/static/images/site/tutorials/tutorial-parse-00-dataBrowser.png)

Click on the **Settings** tab then click on the **Application keys** tab.

Copy both your **Application ID** and **REST API** keys.

![](/static/images/site/tutorials/tutorial-parse-01-appKeys.png)

# 2. Deploy the Entity Store Wrapper

Sign in to your **APISpark** account.

Click on **+ Data Store**.

Click on the **Entity Store** button.

Fill in a name. For this tutorial we chose **Contact Entity Store**.

Input a description if you wish.

For **Source**, select **Parse**.

![](/static/images/site/tutorials/tutorial-parse-02-createStore.png)

Click **Create**. You will be taken to the **Entity Store** overview tab.

Click on the **Settings** tab.

In the **Security** drop down, click on **Parse Account**.

Paste your **Application ID** and **REST API** keys.

Click on the **Test connection** button.

![](/static/images/site/tutorials/tutorial-parse-03-parseAccount.png)

Parse doesn't expose metadata about its data store. Therefore the structure or your Parse store must be re-created manually in APISpark, by creating entities and properties with matching names.

To do so, click on the **Overview** tab.

Click on **Add an entity**.

Name your **Entity**. In this tutorial example, we will name it **Contact** and add three properties to it: firstName, lastName and age.

![](/static/images/site/tutorials/tutorial-parse-04-entityOverview.png)

Note that your **Entity** elements and the **Schema** elements from Parse.com don’t have to match up perfectly. For this purpose, you can
	use the **Mapping** tab of the entity. This allows you to specify the name of the target element (entity or property) in Parse.

![](/static/images/site/tutorials/tutorial-parse-05-entityMapping.png)

Click on the **Deploy** button.

![](/static/images/site/tutorials/tutorial-parse-06-storeDeploy.png)

When your **Entity Store** has been deployed, you can export it as a new web API.

# 3. Deploy the Web API

Remaining on the **Entity Store**’s page, click on the **Actions** button (down-facing arrow) on the right of the **Deploy** button.

Select **Export custom API** and name it **Contact Entity Store API**.

The domain will be created automatically but may not be available anymore so make sure to adjust it.

![](/static/images/site/tutorials/tutorial-parse-07-createApi.png)

Click **Create**. You will be taken to the API’s **Overview**.

The screenshot below shows some of the drop down menus opened to give you an idea of how it’s organized.

![](/static/images/site/tutorials/tutorial-parse-08-apiOverview.png)

Deploy the API by clicking the **Deploy** button.

![](/static/images/site/tutorials/tutorial-parse-09-apiDeploySuccess.png)

The API should now be accessible online. To reach it, use the subdomain of apispark.net that you chose when you created the API.

![](/static/images/site/tutorials/tutorial-parse-10-endpoints.png)

As you can see, the credentials required to invoke the API can be found in the **Overview** tab, by clicking on the relevant **Endpoint**.
You will need to copy the **Endpoint URI**, **Login** and **Password** information for the next step.

# 4. Invoke the Web API

Using a web API does not impose any particular programming language. It can even be done from a web browser. However, to test your API we recommend the use of tools such as the Chrome extensions POSTMAN and DHC that provide a graphical user interface to perform HTTP calls.
The following figure shows the call to the Contacts API with POSTMAN.

When using POSTMAN, click on the **Basic Auth** tab, fill in the **Username** and **Password** fields with the information copied from your APISpark **Endpoint**. Fill in the **Endpoint URI** and add **/contacts/** at the end of it.

To retrieve the list of contacts in JSON, click the **Headers** button on the far right and input the **Accept** command in the Header field and write **application/json** in the **Value** field opposite.

Click the **Send** button.

The following figure shows the API call in POSTMAN.

![](/static/images/site/tutorials/tutorial-parse-11-postman.png)

Note that any POST requests made to the API will result in new data being created in your Parse App. Likewise, any data manually inserted via the Parse Data Browser is visible via the custom web API.

>**Note:** APISpark can also generate custom Client SDKs for different environments such as Java, Android, GWT and JavaScript (AJAX or Node.js). More environments will be supported in the future.

Congratulations on completing this tutorial! If you have questions or suggestions, feel free to contact the [Help Desk](http://support.apispark.com/).
