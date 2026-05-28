package com.squaregames.api.game;

public class GameCreationParams {

    private String gameType;
    private int playerCount;
    private int boardSize;

    public String getGameType() {
        return gameType;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public int getBoardSize() {
        return boardSize;
    }
}