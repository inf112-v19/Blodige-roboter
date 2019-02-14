package no.uib.inf112.core.map;

import no.uib.inf112.core.util.Vector2Int;

public interface MapAction extends Comparable{

    void doAction();

    Vector2Int getResultOfMovement();
}
