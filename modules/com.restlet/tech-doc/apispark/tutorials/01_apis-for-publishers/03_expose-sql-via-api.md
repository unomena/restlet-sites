# Turn your SQL database into a custom web API

## Introduction

This tutorial will show you how to create a custom web API that gives access to existing data stored in an SQL data source.

## Requirements

To follow this tutorial, you will need:

*   a web browser,
*   20 minutes of your time,
*   your SQL database login details.

## 1. Prepare the SQL Data Source

In this tutorial example, we create an SQL table named **T_CONTACT** with the following fields :

<li>**ID** (varchar): primary key</li>
<li>**LAST_NAME** (varchar): last name</li>
<li>**FIRST_NAME** (varchar): first name</li>
<li>**AGE** (int): age</li>

Connect the database engine using the MySQL console:

<pre>$ mysql -u root -p</pre>

Create a database and switch to it:

<pre>mysql> create database apispark;
Query OK, 1 row affected (0.01 sec)
mysql> use apispark;
Database changed</pre>

Create an InnoDB table named T_CONTACT with fields previously listed:

<pre>CREATE TABLE T_CONTACT (
	  ID VARCHAR(255),
	  LAST_NAME VARCHAR (255),
	  FIRST_NAME VARCHAR (255),
	  AGE INT,
	  PRIMARY KEY(ID)
	) ENGINE = InnoDB;
</pre>

Create a new user and grant him all rights on the database. Replace username and password by the values you like:

<pre>CREATE USER 'username'@'%' IDENTIFIED BY "password";
GRANT ALL PRIVILEGES on apispark.T_CONTACT TO 'username'@'%';
FLUSH PRIVILEGES;
</pre>

>**Note:** you can grant privileges on all tables by replacing T_CONTACT by \* and
on all databases by replacing apispark by \*.

## 2. Deploy the Entity Store Wrapper

Sign in to your APISpark account.

Click on **+ Data Store**.

Make sure the **Entity Store** button is selected.

Fill in a name. For this tutorial we chose: **Contact Entity Store**.

Input a description if you wish.

For **Source**, select **SQL**.

![](/static/images/site/tutorials/tutorial-jdbc-00-createStore.png)

Click **Create**. You will be taken to the **Entity Store** overview tab.

Click on the **Settings** tab.

In the **Security** drop down, click on **SQL Source**.

Select the **Driver** (database type) and input the **Host name**, **Port number**, **Username** and **Password**.

Click on the **Test connection** button.

![](/static/images/site/tutorials/tutorial-jdbc-01-source.png)

>**Note:** you need to make sure that the APISpark IP addresses indicated in the Firewall field are authorized to access your database from the internet.

Still in the **Settings tab**, click on **Import a catalog**.

The catalog list contains all the databases visible by the specified user. Select the **Catalog** you wish to import and click on the **Add** button.

![](/static/images/site/tutorials/tutorial-jdbc-02-imports.png)

Under the **Imports** dropdown, click on the **Catalog** you selected. It will open its **Overview** tab.

Next, click on the **Entities** tab.

Click on the **Add entities** button.

This automatically creates entities based on the imported tables. APISpark automatically renames the entities and their properties during this operation.

![](/static/images/site/tutorials/tutorial-jdbc-04-importsEntities.png)

Our entity store now contains an **Entity** called **Contact**.

![](/static/images/site/tutorials/tutorial-jdbc-05-entityOverview.png)

The **Contact** entity’s properties correspond to the columns present in the matching database table.
	![](/static/images/site/tutorials/tutorial-jdbc-06-entityProperties.png)

The elements in your **Entity** don't have to match up perfectly with those in your database Schema. For this purpose, you can use the **Mapping** tab of the entity. This allows you to specify the mapping between each element (entity name and property names).

![](/static/images/site/tutorials/tutorial-jdbc-07-entityMapping.png)
	![](/static/images/site/tutorials/tutorial-jdbc-08-entityMappingDetail.png)

Click on the **Deploy** button.

![](/static/images/site/tutorials/tutorial-jdbc-09-storeDeploy.png)

When your **Entity Store** has been deployed, you can export it as a new web API.

## 3. Deploy the Web API

Remaining on the **Entity Store**’s page, click on the **Actions** button (down-facing arrow) on the right of the **Deploy** button.

Select **Export custom API** and name it **Contact Entity Store API**.

The domain will be created automatically but may not be available anymore so make sure to adjust it.

![](/static/images/site/tutorials/tutorial-jdbc-10-createAPI.png)

Click **Create**. You will be taken to the API’s **Overview**.

The screenshot below shows some of the drop down menus opened to give you an idea of how it’s organized.

![](/static/images/site/tutorials/tutorial-jdbc-11-apiOverview.png)

Deploy the API by clicking the **Deploy** button.
	![](/static/images/site/tutorials/tutorial-jdbc-12-apiDeploySuccess.png)

The API should now be accessible online. To reach it, use the subdomain of apispark.net that you chose when you created the API.

![](/static/images/site/tutorials/tutorial-jdbc-13-endpoints.png)

As you can see, the credentials required to invoke the API can be found in the **Overview** tab, by clicking on the relevant **Endpoint**. You will need to copy the **Endpoint URI**, **Login** and **Password** information for the next step.

## 4. Invoke the Web API

Using a web API does not impose any particular programming language. It can even be done from a web browser. However, to test your API we recommend the use of tools such as the Chrome extensions POSTMAN and DHC that provide a graphical user interface to perform HTTP calls.
The following figure shows the call to the Contacts API with POSTMAN.

When using POSTMAN, click on the **Basic Auth** tab, fill in the **Username** and **Password** fields with the information copied from your APISpark **Endpoint**. Fill in the **Endpoint URI** and add **/contacts/** at the end of it.

To retrieve the list of contacts in JSON, click the **Headers** button on the far right and input the **Accept** command in the Header field and write **application/json** in the **Value** field opposite.

Click the **Send** button.

The following figure shows the API call in POSTMAN.

![](/static/images/site/tutorials/tutorial-jdbc-14-postman.png)

Note that any POST requests made to the API will result in new data being created in your Parse App. Likewise, any data manually inserted via the Parse Data Browser is visible via the custom web API.

>**Note:** any POST requests made to the API will result in new data being created in your Parse App. Likewise, any data manually inserted
	via the Parse Data Browser is visible via the custom web API.

**Did you know?**

APISpark can also generate custom Client SDKs for different environments such as Java, Android, GWT and JavaScript (AJAX or Node.js). More environments will be supported in the future.

Congratulations on completing this tutorial! If you have questions or suggestions, feel free to contact the [Help Desk](http://support.apispark.com/).
