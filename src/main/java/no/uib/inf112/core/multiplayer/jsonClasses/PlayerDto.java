package no.uib.inf112.core.multiplayer.jsonClasses;

import com.badlogic.gdx.graphics.Color;

import java.util.List;

public class PlayerDto {

    public String name;
    public Color color;
    public int id;
    public List<CardDto> cards;

    public PlayerDto(String name, Color color, int id, List<CardDto> cards) {
        this.name = name;
        this.color = color;
        this.id = id;

        this.cards = cards;
    }

    public PlayerDto() {
    }

}
