import bg.sofia.uni.fmi.mjt.escaperoom.EscapeRoomPlatform;
import bg.sofia.uni.fmi.mjt.escaperoom.exception.RoomAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.escaperoom.exception.RoomNotFoundException;
import bg.sofia.uni.fmi.mjt.escaperoom.room.Difficulty;
import bg.sofia.uni.fmi.mjt.escaperoom.room.EscapeRoom;
import bg.sofia.uni.fmi.mjt.escaperoom.room.Review;
import bg.sofia.uni.fmi.mjt.escaperoom.room.Theme;
import bg.sofia.uni.fmi.mjt.escaperoom.team.Team;
import bg.sofia.uni.fmi.mjt.escaperoom.team.TeamMember;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        TeamMember tmr1 = new TeamMember("Gosho", LocalDateTime.now());
        TeamMember tmr2 = new TeamMember("Ivo", LocalDateTime.now());
        TeamMember tmr3 = new TeamMember("Nik", LocalDateTime.now());
        TeamMember tmr4 = new TeamMember("Po", LocalDateTime.now());

        TeamMember[] tm1 = {tmr1, tmr2};
        TeamMember[] tm2 = {tmr3, tmr4};

        Team[] teams = new Team[2];
        teams[0] = Team.of("Nice", tm1);
        teams[1] = Team.of(":D", tm2);

        EscapeRoomPlatform testPl = new EscapeRoomPlatform(teams, 3);
        EscapeRoom testRoom = new EscapeRoom("room", Theme.HORROR, Difficulty.MEDIUM,
                40, 150, 3);

        try {
            testPl.addEscapeRoom(testRoom);
        } catch (RoomAlreadyExistsException e) {
            throw new RuntimeException(e);
        }

        Review r1 = new Review(1, "a");
        Review r2 = new Review(10, "b");
        Review r3 = new Review(5, "c");
        Review r4 = new Review(7, "d");
        Review r5 = new Review(8, "e");

        try {
            testPl.reviewEscapeRoom("room", r1);
        } catch (RoomNotFoundException e) {
            System.out.println(-1);
            throw new RuntimeException(e);
        }

        System.out.println(1);
    }
}