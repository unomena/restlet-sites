# The data browser

The data browser is a useful tool that lets you preview and edit data stored in an Entity Store.

In order to use the data browser, the Entity Store must be deployed first (see Entity Etore deployement **TODO: link**).

If updates are made to the Entity Store's data model, the Entity Store will need to be redeployed in order for the data browser to update itself.

## Editing data

The data browser lets you view existing data, add new entries, update existing entries, and delete entries.


![Add](images/06.jpg "Add")

Depending on the primary key policy you chose for a given entity, the value primary key field will or won't be user-defined (see Primary key policy **TODO: link**).

### Editing multiple cardinality properties

The values of multiple cardinality properties are comma seperated, without white spaces.

Take for example a Film entity with a high cardinality property called alternativeTitles.

Adding alternative titles via the data browser would be done as follows:

  ![addlist](images/databrowser.png "Add list")
