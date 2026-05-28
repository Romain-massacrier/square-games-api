package com.squaregames.api.game;

public class MoveParams {

    private String playerId;
    private String tokenId;
    private int targetx;
    private int targety;

    public String getPlayerId() {
        return playerId;
    }

    public String getTokenId() {
        return tokenId;
    }
    public int getTargetx() {
        return targetx;
    }
    public int getTargety() {
        return targety;
    }    
}
