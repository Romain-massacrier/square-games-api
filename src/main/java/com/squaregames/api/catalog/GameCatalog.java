package com.squaregames.api.catalog;

import java.util.Collection;
import java.util.Locale;

public interface GameCatalog {

    Collection<GameCatalogItemResponse> getAvailableGames(Locale locale);
}