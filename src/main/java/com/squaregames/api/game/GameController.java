package com.squaregames.api.game;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/games")
    public GameResponse createGame(@RequestBody GameCreationParams params) {
        return gameService.createGame(params);
    }

   @GetMapping("/games/{gameId}")
public GameResponse getGame(@PathVariable String gameId) {
    return gameService.getGame(gameId);
}

    @GetMapping("/games/{gameId}/tokens/{tokenId}/moves")
    public String getPossibleMoves(
            @PathVariable String gameId,
            @PathVariable String tokenId
    ) {
        return gameService.getPossibleMoves(gameId, tokenId);
    }

    @PostMapping("/games/{gameId}/moves")
    public String playMove(
            @PathVariable String gameId,
            @RequestBody MoveParams params
    ) {
        return gameService.playMove(gameId, params);
    }
}