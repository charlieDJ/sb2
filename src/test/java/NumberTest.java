public class NumberTest {

    public static void main(String[] args) {
        int level = 4;
        System.out.println(count(level));
    }

    private static Integer count(int level) {
        if (level == 1) {
            return 1;
        } else {
            return count(level - 1) + count(level - 1);
        }
    }

}
