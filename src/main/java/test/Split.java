package test;

/**
 * @author shichenchong@inspur.com
 * data   2019/8/16 18:57
 */
public class Split {
    public static void main(String[] args) {
        String image = "my.png";
        String[] split = image.split("\\.");
        System.out.println(split.length);
    }
}
