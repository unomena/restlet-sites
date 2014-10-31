# The data browser

The data browser is a useful tool that lets you preview and edit data stored in an Entity Store.

In order to use the data browser, the Entity Store must be deployed first (see [Entity Store deployment](apispark/guide/store/entity-store/deploy "Entity Store deployment")).

If updates are made to the Entity Store's data model, the Entity Store will need to be redeployed in order for the data browser to update itself.

## Editing data

The data browser lets you view existing data, add new entries, update existing entries, and delete entries.

![Add](images/06.jpg "Add")

Depending on the primary key policy you chose for a given entity, the value primary key field will or will not be user-defined (see Primary key policy in [Model data section](apispark/guide/store/entity-store/model-data "Model data section")).

### Editing multiple cardinality properties

The values of multiple cardinality properties are comma seperated, without spaces.

Take for example a Film entity with a high cardinality property called alternativeTitles.

Adding alternative titles via the data browser would be done as follows:

  ![Add list](images/databrowser.png "Add list")
