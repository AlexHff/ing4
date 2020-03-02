package hibernate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

/**
 * The order entity class.
 *
 * @author Jean-Michel Busca
 */
@Entity
@Table(name = "Orders")
public class Order {
    @Id
    @GeneratedValue
    private int id;
    private Date date;
    @OneToMany
    private List<Product> products;

    public Order() {
    }

    public Order(Date date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Order [date=" + date + ", id=" + id + ", products=" + products + "]";
    }
}
