import java.util.stream.Stream;

public class Day8 extends Day<Integer> {

    static class Tree {
        boolean visible = false;
        final int height;

        Tree(int height) {
            this.height = height;
        }

        Tree visible() {
            this.visible = true;
            return this;
        }

    }

    static class Forest {
        final Tree[][] trees;
        final int rows;
        final int cols;

        Forest(Tree[][] trees) {
            this.trees = trees;
            this.rows = trees.length;
            this.cols = trees[0].length;
        }

        static Forest from(Stream<String> input) {
            final String[] lines = input.toArray(String[]::new);

            final int rows = lines.length;
            final int cols = lines[0].length();

            Tree[][] trees = new Tree[rows][cols];

            for (int i = 0; i < rows; i++) {
                final char[] row = lines[i].toCharArray();
                for (int j = 0; j < cols; j++) {
                    trees[i][j] = new Tree(row[j] - '0');
                    if (i == 0 || i == rows - 1 || j == 0 || j == cols - 1) {
                        trees[i][j].visible();
                    }
                }
            }
            return new Forest(trees);
        }

        void markVisibleTrees() {
            //from top
            int[] maxHeights = new int[cols];
            for (int i = 0; i < cols; i++) {
                maxHeights[i] = trees[0][i].height;
            }
            for (int i = 1; i < rows - 1; i++) {
                for (int j = 1; j < cols - 1; j++) {
                    Tree tree = trees[i][j];
                    if (!tree.visible && tree.height > maxHeights[j]) {
                        tree.visible();
                    }
                    maxHeights[j] = Math.max(tree.height, maxHeights[j]);
                }
            }

            //from bottom
            for (int i = 0; i < cols; i++) {
                maxHeights[i] = trees[rows-1][i].height;
            }
            for (int i = rows - 2; i > 0; i--) {
                for (int j = 1; j < cols - 1; j++) {
                    Tree tree = trees[i][j];
                    if (!tree.visible && tree.height > maxHeights[j]) {
                        tree.visible();
                    }
                    maxHeights[j] = Math.max(tree.height, maxHeights[j]);
                }
            }

            //from left
            maxHeights = new int[rows];
            for (int i = 0; i < rows; i++) {
                maxHeights[i] = trees[i][0].height;
            }
            for (int j = 1; j < cols - 1 ; j++) {
                for (int i = 1; i < rows - 1; i++) {
                    Tree tree = trees[i][j];
                    if (!tree.visible && tree.height > maxHeights[i]) {
                        tree.visible();
                    }
                    maxHeights[i] = Math.max(tree.height, maxHeights[i]);
                }
            }

            //from right
            for (int i = 0; i < rows; i++) {
                maxHeights[i] = trees[i][cols - 1].height;
            }
            for (int j = cols-2; j > 0 ; j--) {
                for (int i = 1; i < rows - 1; i++) {
                    Tree tree = trees[i][j];
                    if (!tree.visible && tree.height > maxHeights[i]) {
                        tree.visible();
                    }
                    maxHeights[i] = Math.max(tree.height, maxHeights[i]);
                }
            }
        }

        int countVisibleTrees() {
            int count = 0;
            for (Tree[] tree : trees) {
                for (Tree value : tree) {
                    if (value.visible) count++;
                }
            }
            return count;
        }

    }

    @Override
    protected Integer resolveP1(Stream<String> input) {
        Forest forest = Forest.from(input);
        forest.markVisibleTrees();
        return forest.countVisibleTrees();
    }

    @Override
    protected Integer resolveP2(Stream<String> input) {
        return 0;
    }


    public static void main(String[] args) {
        new Day8().resolve();
    }

}
