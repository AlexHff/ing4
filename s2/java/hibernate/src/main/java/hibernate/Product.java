package hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The product entity class.
 *
 * @author Jean-Michel Busca
 */

@NamedQueries({ @NamedQuery(name = "raiseProductPriceBy", query = "update Product set price = price + :amount") })

@Entity
@Table(name = "Products")
public class Product {
    @Id
    @GeneratedValue
    private int id;
    @Column(name = "pname")
    private String name;
    @Column(name = "price")
    private double price;

    public Product() {
    }

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product [id=" + id + ", name=" + name + ", price=" + price + "]";
    }
}
