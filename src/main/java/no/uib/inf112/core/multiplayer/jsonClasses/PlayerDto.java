package no.uib.inf112.core.multiplayer.jsonClasses;

import com.badlogic.gdx.graphics.Color;

import java.util.List;

public class PlayerDto {

    public String name;
    public Color color;
    public int id;
    public List<CardDto> cards;
    public boolean isPoweredDown = false;
    public List<CardDto> drawnCards;

    public PlayerDto() {
    }

}
