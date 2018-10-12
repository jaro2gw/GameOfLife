package myApp;
/*
 * Created by Jaro on 2017-08-29.
 */

public class Life {
    private boolean[][] gen;

    Life(int y, int x) {
        gen = new boolean[y][x];
    }

    boolean[][] getGeneration() { return gen; }

//    private void setGeneration(boolean[][] other) { gen = other; }

    void nextGeneration() {
        boolean[][] next = new boolean[gen.length][gen[0].length];
        for (int y = 0; y < gen.length; y++)
            for (int x = 0; x < gen[0].length; x++)
                next[y][x] = willLive(y, x);
        gen = next;
    }

    public void expand() {
        boolean[][] exp = new boolean[gen.length + 2][gen[0].length + 2];
        for (int y = 1; y < exp.length - 1; y++) System.arraycopy(gen[y - 1], 0, exp[y], 1, exp[0].length - 2);
        gen = exp;
    }

    public void reset() { gen = new boolean[gen.length][gen[0].length]; }

    void set(int y, int x, boolean alive) {
        gen[y][x] = alive;
    }

    private boolean willLive(int y, int x) {
        if (gen[y][x]) return willSurvive(count(y, x));
        else return count(y, x) == 3;
    }

    private boolean willSurvive(int count) { return count == 2 || count == 3; }

    private int count(int y, int x) {
        int count = 0, yMax = Math.min(gen.length, y + 2), xMax = Math.min(gen[0].length, x + 2);
        for (int yMin = Math.max(0, y - 1); yMin < yMax; yMin++)
            for (int xMin = Math.max(0, x - 1); xMin < xMax; xMin++)
                if ((yMin != y || xMin != x) && gen[yMin][xMin]) count++;
        return count;
    }
}
