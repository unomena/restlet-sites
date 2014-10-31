APISpark automates the version management of your cells. As you create a new cell, a V1 is automatically created.

There are 2 cell states possible: in development (not deployed yet) or already deployed. Each cell can have multiple (draft and deployed) versions in parallel but the last version deployed is the accessible version.
Each version follows its own lifecycle: draft, published, deprecated, archived, deleted, see [cell lifecycle](/guide/02_explore/02_cell-lifecycle.md).

A versioned entity store can only be edited incrementally (adding new entities and new properties to existing entities), no existing properties can be modified. Versioned web APIs and file stores can be modified without restriction.

Versioning allows you to make a static version of your cell available to your consumers while you keep working on a version that is in development with no impact on your cell consumers.
