# Introduction to the APISpark Data Stores

APISpark features two types of Data Stores: the Entity Store and the File Store.

The Entity Store is used to store structured data while the File Store provides storage for static files.

Both of these Data Store types have a set different implementations to chose from. The main distinction is that there are *local* implementations that are hosted on the APISpark platform, and *wrapper* implementations that use or wrap third party storage components such as Google Spreadsheets. 

The *local* implementations are the full stack Entity Store and the full stack File Store. The other sort of implementation are the *wrapper* stores, these are the Entity Store implementations, and the Github and Amazon S3 File Store wrapper implementations.

local Data Stores for which data is hosted / backuped and operated by APISpark teams and
wrapped stores for which data are hosted externally (SQL Wrapper, Google spread Sheet Wrapper)...
We distinguish 2 types of local Data Stores:

Entity Stores (structured data)
File Stores (static files)
An Entity Store allows you to store structured data (equivalent to a database). You start by creating Entities and you can express relations between Entities.
