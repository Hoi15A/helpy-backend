package ch.zhaw.pm3.helpy.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Category {

    private final String name;
    private final List<Category> listOfRelated;
    private String description;

    public Category(String name) {
        Objects.requireNonNull(name);
        this.name = name;
        listOfRelated = new ArrayList<>();
        description = "No Description has been added";
    }

    public String getName() {
        return name;
    }

    public List<Category> getListOfRelated() {
        return listOfRelated;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        Objects.requireNonNull(description);
        this.description = description;
    }

    public boolean addToRelated(Category category) {
        Objects.requireNonNull(category);
        return listOfRelated.add(category);
    }

    public boolean removeFromRelated(Category category) {
        Objects.requireNonNull(category);
        return listOfRelated.remove(category);
    }




}
