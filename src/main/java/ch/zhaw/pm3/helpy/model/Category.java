package ch.zhaw.pm3.helpy.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Category {

    @Id
    @NotBlank(message = "You have to provide a name for the category")
    private String name;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Category> listOfRelated;
    @Column(columnDefinition = "LONGTEXT")
    @NotBlank(message = "You have to provide a description for the category")
    private String description;

    public Category() {
        //needed for JPA
    }

    public Category(String name) {
        Objects.requireNonNull(name);
        this.name = name;
        listOfRelated = new ArrayList<>();
        description = "No Description has been added";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Category> getListOfRelated() {
        return listOfRelated;
    }

    public void setListOfRelated(List<Category> listOfRelated) {
        this.listOfRelated = listOfRelated;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return name.equals(category.name) &&
                Objects.equals(listOfRelated, category.listOfRelated) &&
                Objects.equals(description, category.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, listOfRelated, description);
    }

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

}
