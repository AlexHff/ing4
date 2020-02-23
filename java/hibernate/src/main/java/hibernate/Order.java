package hibernate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

/**
 * The order entity class. Exercise 3.
 * <p>
 * <i>Under construction.</i>
 *
 * @author Jean-Michel Busca
 */
@Entity
@Table(name = "Orders")
// "order" is a SQL keyword and therefore cannot be used as a table name,
// hence the use of the @Table annotation to change the name of the table
public class Order {

  //
  // INSTANCE FIELDS
  //
  @Id
  @GeneratedValue
  private int id;
  private Date date;
  @OneToMany
  private List<Product> products;

  //
  // CONSTRUCTORS
  //

  protected Order() {
  }

  public Order(Date date) {
    this.date = date;
  }

  //
  // ACCESSORS AND MUTATORS
  //

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
}
