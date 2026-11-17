package com.example.myjaddon.common.stand;

/**
 * Tunables for The Hand: The World.
 * Kept separate so the durations can be checked without starting Minecraft.
 */
public final class TheHandTheWorldBalance {
    /** Frozen-time length. 100 ticks is 5 seconds. */
    public static final int TIME_STOP_TICKS = 100;
    /** Requested freeze box, in blocks. JCraft's own box is 96. */
    public static final double TIME_STOP_RADIUS_BLOCKS = 100.0;
    /** JCraft timestop box used by {@code JCraft.beginTimestop}. */
    public static final double JCRAFT_TIMESTOP_BOX_BLOCKS = 96.0;
    /** Time Skip distance, matching The World's timeskip. */
    public static final double TIME_SKIP_BLOCKS = 14.0;
    /** Cooldown copied from The World's timestop. */
    public static final int TIME_STOP_COOLDOWN = 1400;
    /** Windup copied from The World's timestop pose. */
    public static final int TIME_STOP_WINDUP = 45;
    /** Move length copied from The World's timestop pose. */
    public static final int TIME_STOP_MOVE_DURATION = 52;
    /** Cooldown copied from The World's timeskip. */
    public static final int TIME_SKIP_COOLDOWN = 300;

    private TheHandTheWorldBalance() {
    }
}
