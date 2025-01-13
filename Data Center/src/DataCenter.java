public class DataCenter {
    public static int getCommunicatingServersCount(int[][] map) {
        int serversCommunicating = 0;
        for (int i = 0; i < map.length; i++) {
            int serversInRow = 0;
            int serverColumn = 0;

            for (int j = 0; j < map[i].length; j++)
                if (map[i][j] == 1) {
                    serversInRow++;
                    serverColumn = j;
                }

            if (serversInRow >= 2)
                serversCommunicating += serversInRow;
            else if (serversInRow == 1) {
                for (int k = 0; k < map.length; k++)
                    if (k != serverColumn && map[k][serverColumn] == 1) {
                        serversCommunicating++;
                        break;
                    }
            }
        }

        return serversCommunicating;
    }

    public static void main(String[] args) {
        System.out.println(getCommunicatingServersCount(new int[][]{{1, 0}, {0, 1}}));
        System.out.println(getCommunicatingServersCount(new int[][]{{1, 0}, {1, 1}}));
        System.out.println(getCommunicatingServersCount(new int[][]{{1, 1, 0, 0}, {0, 0, 1, 0}, {0, 0, 1, 0}, {0, 0, 0, 1}}));
    }
}