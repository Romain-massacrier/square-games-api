package com.squaregames.api.game;

public class GameResponse {

    private String gameId;
    private String gameType;
    private int playerCount;
    private int boardSize;
    private String status;
    private String currentPlayerId;
    private String board;

    public GameResponse(
            String gameId,
            String gameType,
            int playerCount,
            int boardSize,
            String status,
            String currentPlayerId,
            String board
    ) {
        this.gameId = gameId;
        this.gameType = gameType;
        this.playerCount = playerCount;
        this.boardSize = boardSize;
        this.status = status;
        this.currentPlayerId = currentPlayerId;
        this.board = board;
    }

    public String getGameId() {
        return gameId;
    }

    public String getGameType() {
        return gameType;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public String getStatus() {
        return status;
    }

    public String getCurrentPlayerId() {
        return currentPlayerId;
    }

    public String getBoard() {
        return board;
    }
}