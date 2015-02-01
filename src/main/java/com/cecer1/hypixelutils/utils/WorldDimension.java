package com.cecer1.hypixelutils.utils;

public enum WorldDimension {
    OVERWORLD(0),
    NETHER(-1),
    END(1);

    private int _dimensionId;
    private WorldDimension(int dimensionId) {
        _dimensionId = dimensionId;
    }

    public static WorldDimension fromDimensionId(int dimensionId) {
        switch(dimensionId) {
            case 0:
                return WorldDimension.OVERWORLD;
            case -1:
                return WorldDimension.NETHER;
            case 1:
                return WorldDimension.END;
            default:
                throw new IllegalArgumentException("Invalid dimension ID (" + dimensionId + ")!");
        }
    }
}
