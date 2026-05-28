package com.squaregames.api.catalog;

public class GameCatalogItemResponse {

    private String gameType;
    private String name;

    public GameCatalogItemResponse(String gameType, String name) {
        this.gameType = gameType;
        this.name = name;
    }

    public String getGameType() {
        return gameType;
    }

    public String getName() {
        return name;
    }
}