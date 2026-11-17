package com.example.myjaddon.common.stand;

/**
 * Chebyshev box test for the gap between JCraft's timestop box and this stand's box.
 */
public final class TheHandTheWorldRange {
    private TheHandTheWorldRange() {
    }

    /**
     * Returns whether a point sits outside the inner box and inside the outer box.
     *
     * @param dx    offset on X
     * @param dy    offset on Y
     * @param dz    offset on Z
     * @param inner half-extent already covered by JCraft
     * @param outer half-extent this stand should cover
     * @return true when the point needs the extra freeze or shader
     */
    public static boolean inOuterBox(double dx, double dy, double dz, double inner, double outer) {
        if (Double.isNaN(dx) || Double.isNaN(dy) || Double.isNaN(dz) || Double.isNaN(inner) || Double.isNaN(outer)) {
            throw new IllegalArgumentException("range arguments must be finite numbers");
        }
        if (outer < inner) {
            throw new IllegalArgumentException("outer radius " + outer + " is smaller than inner radius " + inner);
        }
        double chebyshev = Math.max(Math.abs(dx), Math.max(Math.abs(dy), Math.abs(dz)));
        return chebyshev > inner && chebyshev <= outer;
    }
}
package com.example.myjaddon.common.stand;

/**
 * Chebyshev box test for the gap between JCraft's timestop box and this stand's box.
 */
public final class TheHandTheWorldRange {
    private TheHandTheWorldRange() {
    }

    /**
     * Returns whether a point sits outside the inner box and inside the outer box.
     *
     * @param dx    offset on X
     * @param dy    offset on Y
     * @param dz    offset on Z
     * @param inner half-extent already covered by JCraft
     * @param outer half-extent this stand should cover
     * @return true when the point needs the extra freeze or shader
     */
    public static boolean inOuterBox(double dx, double dy, double dz, double inner, double outer) {
        if (Double.isNaN(dx) || Double.isNaN(dy) || Double.isNaN(dz) || Double.isNaN(inner) || Double.isNaN(outer)) {
            throw new IllegalArgumentException("range arguments must be finite numbers");
        }
        if (outer < inner) {
            throw new IllegalArgumentException("outer radius " + outer + " is smaller than inner radius " + inner);
        }
        double chebyshev = Math.max(Math.abs(dx), Math.max(Math.abs(dy), Math.abs(dz)));
        return chebyshev > inner && chebyshev <= outer;
    }
}
