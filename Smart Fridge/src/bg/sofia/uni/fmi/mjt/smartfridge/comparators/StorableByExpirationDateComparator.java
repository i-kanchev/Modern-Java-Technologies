package bg.sofia.uni.fmi.mjt.smartfridge.comparators;

import bg.sofia.uni.fmi.mjt.smartfridge.storable.Storable;

import java.util.Comparator;

public class StorableByExpirationDateComparator implements Comparator<Storable> {
    @Override
    public int compare(Storable first, Storable second) {
        return first.getExpiration().compareTo(second.getExpiration());
    }
}
