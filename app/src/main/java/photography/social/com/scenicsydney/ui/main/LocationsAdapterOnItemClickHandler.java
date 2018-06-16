package photography.social.com.scenicsydney.ui.main;

import photography.social.com.scenicsydney.data.database.LocationEntry;

/**
 * Handles RecyclerView item click.
 */
interface LocationsAdapterOnItemClickHandler {
    void onItemClick(LocationEntry locationEntry);
}
