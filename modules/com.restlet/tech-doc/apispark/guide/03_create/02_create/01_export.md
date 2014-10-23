If you have no data yet to surface, we suggest you start by creating local data on APISpark and then surface them.
#Create Local Data on APISpark
As an API needs resources, we want to create a local Data Store where the API will find the necessary resources. If you already have an existing data source, you can directly create your API and import your existing Data Store (see Create an API and import a Data Store).
APISpark allows you to create two types of Data Stores: Entity Stores (data) and File Stores (documents). For more information, see Local Data Stores.
In this example, we create an Entity Store.

1. Open your APISpark **Dashboard** and click on the **+ Entity Store** button.
![+Entity Store](images/01.jpg "+Entity Store")
2. In the **Create an Entity Store** window, select the appropriate Entity Store type (here **Full Stack**).
![Create an Entity Store](images/02.jpg "Create an Entity Store")
3. Enter a **Name** and a **Description** and click on the **Create** button.
4. Click on the **+ Entity** button and give it a name (here “Employee”).
5. To add properties manually, click on the **Add a Property** link and add as many properties as needed. In this window you can add a data type, cardinality and default value.
6. Click on the **Deploy** button to deploy your Entity Store. Wait until a message informs you that it has been deployed successfully.
7. To add an entity, click on the **Browser** tab and click on the **Add** button.
8. Fill in the different fields and click on **Add**.
> **Note:** If you add entities, you need to re-deploy your Entity Store to display them in the browser.

#Create a Web API from a Data Store
1. From your Entity Store, click on the action arrow and select **Export custom API**.
	![export custom API](images/03.jpg "export custom API")
2. In the **Create a Web API** window:
 * Enter the **Name** for your API.
 * Your domain name is automatically suggested in the **Domain** field according to the name you entered. If the domain address is not available, a red cross displays on the right of the domain address. You need to find another domain address. If the domain name is available, a green tick displays on the right of the domain address.
 * Enter a **Description** of your API.
	![Create a web API](images/04.jpg "Create a web API")
3. Click on the **Create** button. A message informs you that your API has just been created.

	> **Note:** Your API is created and your entity store is imported into your API. Your Entity store’s resources are added automatically.

4. Click on the **Deploy** button to deploy your API.