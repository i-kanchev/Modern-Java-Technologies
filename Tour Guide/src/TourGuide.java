public class TourGuide {
    public static int getBestSightseeingPairScore(int[] places) {
        int highestValue = 0;
        for (int i = 0; i < places.length - 1; i++)
            for (int j = i + 1; j < places.length; j++) {
                int value = places[i] + places[j] + i - j;
                if (value > highestValue)
                    highestValue = value;
            }
        return highestValue;
    }

    public static void main(String[] args) {
        System.out.println(getBestSightseeingPairScore(new int[]{8, 1, 5, 2, 6}));
        System.out.println(getBestSightseeingPairScore(new int[]{1, 2}));
    }
}