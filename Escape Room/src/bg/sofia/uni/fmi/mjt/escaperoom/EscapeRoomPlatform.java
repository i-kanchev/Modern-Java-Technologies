package bg.sofia.uni.fmi.mjt.escaperoom;

import bg.sofia.uni.fmi.mjt.escaperoom.exception.PlatformCapacityExceededException;
import bg.sofia.uni.fmi.mjt.escaperoom.exception.RoomAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.escaperoom.exception.RoomNotFoundException;
import bg.sofia.uni.fmi.mjt.escaperoom.exception.TeamNotFoundException;
import bg.sofia.uni.fmi.mjt.escaperoom.room.EscapeRoom;
import bg.sofia.uni.fmi.mjt.escaperoom.room.Review;
import bg.sofia.uni.fmi.mjt.escaperoom.team.Team;

public class EscapeRoomPlatform implements EscapeRoomAdminAPI, EscapeRoomPortalAPI {
    private Team[] teams;
    private EscapeRoom[] escapeRooms;
    private final int maxCapacity;
    private int escapeRoomsCount;
    final static double fastEscapeCoefficient = 0.75;
    final static double superFastEscapeCoefficient = 0.5;
    final static int fastEscapePoints = 1;
    final static int superFastEscapePoints = 2;

    public EscapeRoomPlatform(Team[] teams, int maxCapacity) {
        this.teams = new Team[teams.length];
        for (int i = 0; i < teams.length; i++) {
            this.teams[i] = teams[i];
        }
        this.maxCapacity = maxCapacity;
        this.escapeRooms = new EscapeRoom[maxCapacity];
        escapeRoomsCount = 0;
    }

    @Override
    public void addEscapeRoom(EscapeRoom room) throws RoomAlreadyExistsException {
        if (escapeRoomsCount == maxCapacity) {
            throw new PlatformCapacityExceededException("Rooms capacity reached");
        }

        if (room == null) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < escapeRoomsCount; i++) {
            if (room.getName().equals(escapeRooms[i].getName())) {
                throw new RoomAlreadyExistsException("Room already exists");
            }
        }

        escapeRooms[escapeRoomsCount++] = room;
    }

    @Override
    public void removeEscapeRoom(String roomName) throws RoomNotFoundException {
        if (roomName == null || roomName.isEmpty() || roomName.trim().isEmpty()) {
            throw new IllegalArgumentException();
        }

        boolean isFound = false;
        int atIndex = 0;

        for (int i = 0; i < escapeRoomsCount; i++) {
            if (roomName.equals(escapeRooms[i].getName())) {
                isFound = true;
                atIndex = i;
                break;
            }
        }

        if (isFound) {
            for (int i = atIndex; i < escapeRoomsCount - 1; i++) {
                escapeRooms[i] = escapeRooms[i + 1];
            }
            escapeRooms[escapeRoomsCount--] = null;
        } else {
            throw new RoomNotFoundException("Room not found");
        }
    }

    @Override
    public EscapeRoom[] getAllEscapeRooms() {
        EscapeRoom[] validEscapeRooms = new EscapeRoom[escapeRoomsCount];
        for (int i = 0; i < escapeRoomsCount; i++) {
            validEscapeRooms[i] = escapeRooms[i];
        }
        return validEscapeRooms;
    }

    @Override
    public void registerAchievement(String roomName, String teamName, int escapeTime) throws RoomNotFoundException, TeamNotFoundException {
        if (roomName == null || roomName.isEmpty() || roomName.trim().isEmpty()) {
            throw new IllegalArgumentException();
        }
        if (teamName == null || teamName.isEmpty() || teamName.trim().isEmpty()) {
            throw new IllegalArgumentException();
        }

        boolean roomFound = false;
        int roomIndex = 0;
        boolean teamFound = false;
        int teamIndex = 0;

        for (int i = 0; i < escapeRoomsCount; i++) {
            if (roomName.equals(escapeRooms[i].getName())) {
                roomFound = true;
                roomIndex = i;
                break;
            }
        }
        for (int i = 0; i < teams.length; i++) {
            if (teams[i].getName().equals(teamName)) {
                teamFound = true;
                teamIndex = i;
                break;
            }
        }

        if (!roomFound) {
            throw new RoomNotFoundException("Room not found");
        }
        if (!teamFound) {
            throw new TeamNotFoundException("Team not found");
        }

        if (escapeTime < 0 || escapeTime > escapeRooms[roomIndex].getMaxTimeToEscape()) {
            throw new IllegalArgumentException("Room failed to complete on time");
        }
        int points = escapeRooms[roomIndex].getDifficulty().getRank();

        if (escapeTime <= escapeRooms[roomIndex].getMaxTimeToEscape() * superFastEscapeCoefficient) {
            points += superFastEscapePoints;
        } else if (escapeTime <= escapeRooms[roomIndex].getMaxTimeToEscape() * fastEscapeCoefficient) {
            points += fastEscapePoints;
        }

        teams[teamIndex].updateRating(points);
    }

    @Override
    public EscapeRoom getEscapeRoomByName(String roomName) throws RoomNotFoundException {
        if (roomName == null || roomName.isEmpty() || roomName.trim().isEmpty()) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < escapeRoomsCount; i++) {
            if (roomName.equals(escapeRooms[i].getName())) {
                return escapeRooms[i];
            }
        }

        throw new RoomNotFoundException("Room not found");
    }

    @Override
    public void reviewEscapeRoom(String roomName, Review review) throws RoomNotFoundException {
        if (roomName == null || roomName.isEmpty() || roomName.trim().isEmpty()) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < escapeRoomsCount; i++) {
            if (roomName.equals(escapeRooms[i].getName())) {
                escapeRooms[i].addReview(review);
                return;
            }
        }

        throw new RoomNotFoundException("Room not found");
    }

    @Override
    public Review[] getReviews(String roomName) throws RoomNotFoundException {
        if (roomName == null || roomName.isEmpty() || roomName.trim().isEmpty()) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < escapeRoomsCount; i++) {
            if (roomName.equals(escapeRooms[i].getName())) {
                return escapeRooms[i].getReviews();
            }
        }

        throw new RoomNotFoundException("Room not found");
    }

    @Override
    public Team getTopTeamByRating() {
        if (teams == null) {
            return null;
        }

        int index = 0;

        for (int i = 0; i < teams.length; i++) {
            if (teams[i].getRating() > teams[index].getRating()) {
                index = i;
            }
        }

        return teams[index];
    }
}
