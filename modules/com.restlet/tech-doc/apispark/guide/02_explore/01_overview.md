#Cell concept
APISpark relies on the concept of cell. A cell is a basic software package that can interact with other imported cells.

#Cell types
From APISpark, you can create two types of cells: Data Stores (Entity Stores or Files Stores) and Web APIs.

  ![Cell concept](images/01.jpg "Cell concept")

##Data Stores  
Two types of **Datastores** can be created. Entity Stores for structured data (equivalent to a database) and File Stores for static files (text documents, images, videos or style sheets).

##Web APIs  
A **Full Stack Web API** is an API (available web business once deployed via HTTP and exposing data).

  ![Cell concept](images/02.jpg "Cell concept")

#Cell characteristics

Each cell created is given a unique identifier on APISpark prefixed by its category (apis or stores), and followed by its version number. Here is the identifier structure:  
`/<category>/<cell identifier>/versions/<version number>/`  
e.g. `https://apispark.com/apis/3373/versions/1/`  
This identifier will be asked by APISpark HelpDesk team if you need to call them.

> **Note:** You cannot directly invoke a DataStore, you need to first expose it through a WebAPI which imports this store (see Invoke a Web API). A Web API can invoke several stores.
