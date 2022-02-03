package restaurantapplication.core;

import org.checkerframework.checker.units.qual.N;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "dishes")
@NamedQueries({
        @NamedQuery(name = "core.Dish.findAll",
                    query = "select d from Dish d"
        ),
        @NamedQuery(name = "core.Dish.findByName",
                    query = "select d from Dish d "
                    + "where d.dish_name like :name"
        ),
        @NamedQuery(name = "core.Dish.findByType",
                    query = "select d from Dish d "
                    + "where d.dish_type like :name"
        ),
        @NamedQuery(name = "core.Dish.remove",
                query = "delete from Dish d "
                        + "where d.dish_id = :dish_id"
        )

})

public class Dish {

    @Column(name = "dish_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long dish_id;

    @Column(name = "dish_name")
    private String dish_name;

    @Column(name = "dish_type")
    private String dish_type;

    @Column(name = "price")
    private Double price;

    public Dish() {
    }

    public Dish(long dish_id, String dish_name, String dish_type, Double price) {
        this.dish_id = dish_id;
        this.dish_name = dish_name;
        this.dish_type = dish_type;
        this.price = price;
    }


    public long getDish_id() {
        return dish_id;
    }

    public void setDish_id(long dish_id) {
        this.dish_id = dish_id;
    }

    public String getDish_name() {
        return dish_name;
    }

    public void setDish_name(String dish_name) {
        this.dish_name = dish_name;
    }

    public String getDish_type() {
        return dish_type;
    }

    public void setDish_type(String dish_type) {
        this.dish_type = dish_type;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }


    @Override
    public String toString() {
        return "Dish{" +
                "dish_id=" + dish_id +
                ", dish_name='" + dish_name + '\'' +
                ", dish_type='" + dish_type + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dish dish = (Dish) o;
        return dish_id == dish.dish_id && Objects.equals(dish_name, dish.dish_name) && Objects.equals(dish_type, dish.dish_type) && Objects.equals(price, dish.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dish_id, dish_name, dish_type, price);
    }
}
