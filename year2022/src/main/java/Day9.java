import util.Pair;

import java.util.stream.Stream;

public class Day9 extends Day<Integer> {

    class Rope {
        int tx = 0, ty = 0, hx = 0, hy = 0;

        void move(char direction, int count) {
            switch (direction){
                case 'U': {
                    hy++;
                    
                }
                case 'D':{
                    hy--;
                }
                case 'L':{
                    hx--;
                }
                case 'R':{
                    hx++;
                }
            }
        }


    }

    @Override
    protected Integer resolveP1(Stream<String> input) {
        Pair<Integer, Integer> startingPos = new Pair<>(0, 0);

        return 0;
    }

    @Override
    protected Integer resolveP2(Stream<String> input) {
        return 0;
    }


    public static void main(String[] args) {
        new Day9().resolve();
    }

}
