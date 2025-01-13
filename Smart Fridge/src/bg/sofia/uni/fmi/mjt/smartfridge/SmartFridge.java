package bg.sofia.uni.fmi.mjt.smartfridge;

import bg.sofia.uni.fmi.mjt.smartfridge.comparators.StorableByExpirationDateComparator;
import bg.sofia.uni.fmi.mjt.smartfridge.exception.FridgeCapacityExceededException;
import bg.sofia.uni.fmi.mjt.smartfridge.exception.InsufficientQuantityException;
import bg.sofia.uni.fmi.mjt.smartfridge.ingredient.DefaultIngredient;
import bg.sofia.uni.fmi.mjt.smartfridge.ingredient.Ingredient;
import bg.sofia.uni.fmi.mjt.smartfridge.recipe.Recipe;
import bg.sofia.uni.fmi.mjt.smartfridge.storable.Storable;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class SmartFridge implements  SmartFridgeAPI {
    private List<Storable> items;
    private int totalCapacity;

    public SmartFridge(int totalCapacity) {
        this.items = new LinkedList<>();
        this.totalCapacity = totalCapacity;
    }

    @Override
    public <E extends Storable> void store(E item, int quantity) throws FridgeCapacityExceededException {
        if (item == null || quantity <= 0) {
            throw new IllegalArgumentException();
        }
        if (items.size() + quantity > totalCapacity) {
            throw new FridgeCapacityExceededException("Fridge exceeded");
        }

        for (int i = 0; i < quantity; i++) {
            items.add(item);
        }
    }

    @Override
    public List<? extends Storable> retrieve(String itemName) {
        if (itemName == null || itemName.isEmpty() || itemName.isBlank()) {
            throw new IllegalArgumentException();
        }

        List<Storable> listOfItems = new LinkedList<>();

        Iterator<Storable> iterator = items.iterator();
        while (iterator.hasNext()) {
            Storable currItem = iterator.next();
            if (currItem.getName().equals(itemName)) {
                listOfItems.add(currItem);
                iterator.remove();
            }
        }

        listOfItems.sort(new StorableByExpirationDateComparator());

        return listOfItems;
    }

    @Override
    public List<? extends Storable> retrieve(String itemName, int quantity) throws InsufficientQuantityException {
        if (itemName == null || itemName.isEmpty() || itemName.isBlank()) {
            throw new IllegalArgumentException();
        }

        if (quantity <= 0) {
            throw new IllegalArgumentException();
        }

        List<Storable> listOfItems = new LinkedList<>();

        Iterator<Storable> iterator = items.iterator();
        while (iterator.hasNext()) {
            Storable currItem = iterator.next();
            if (currItem.getName().equals(itemName)) {
                listOfItems.add(currItem);
                iterator.remove();
            }

            if (listOfItems.size() == quantity) {
                break;
            }
        }

        if (listOfItems.size() < quantity) {
            items.addAll(listOfItems);

            throw new InsufficientQuantityException("Insufficient quantity");
        }

        listOfItems.sort(new StorableByExpirationDateComparator());

        return listOfItems;
    }

    @Override
    public int getQuantityOfItem(String itemName) {
        if (itemName == null || itemName.isEmpty() || itemName.isBlank()) {
            throw new IllegalArgumentException();
        }

        int itemsCount = 0;

        for (Storable currItem : items) {
            if (currItem.getName().equals(itemName)) {
                itemsCount++;
            }
        }

        return itemsCount;
    }

    @Override
    public Iterator<Ingredient<? extends Storable>> getMissingIngredientsFromRecipe(Recipe recipe) {
        if (recipe == null) {
            throw new IllegalArgumentException();
        }

        Set<Ingredient<? extends Storable>> ingredientsNeeded = recipe.getIngredients();
        List<Ingredient<? extends Storable>> ingredientsMissing = new LinkedList<>();

        for (Ingredient<? extends Storable> ingredient : ingredientsNeeded) {
            int currQuantity = 0;

            Iterator<Storable> iterator = items.iterator();
            while (iterator.hasNext()) {
                Storable currItem = iterator.next();
                if (currItem.getName().equals(ingredient.item().getName()) && !currItem.isExpired()) {
                    currQuantity++;
                }
            }

            if (currQuantity < ingredient.quantity()) {
                Ingredient<? extends Storable> ingredientQuantityLeft =
                    new DefaultIngredient<>(ingredient.item(), ingredient.quantity() - currQuantity);
                ingredientsMissing.add(ingredientQuantityLeft);
            }
        }

        return ingredientsMissing.iterator();
    }

    @Override
    public List<? extends Storable> removeExpired() {
        List<Storable> listOfExpiredItems  = new LinkedList<>();

        Iterator<Storable> iterator = items.iterator();
        while (iterator.hasNext()) {
            Storable currItem = iterator.next();
            if (currItem.isExpired()) {
                listOfExpiredItems.add(currItem);
                iterator.remove();
            }
        }

        return listOfExpiredItems;
    }
}
