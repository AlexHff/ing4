package hibernate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * The product entity class. Exercise 1.2.
 * <p>
 * Observe that the class meets all the requirements of Java Persistence API:
 * <ul>
 * <li>Product is a top-level, non-final class, with a no-argument constructor. The no-argument constructor need not be
 * public, it can be protected. We elect to make it protected for better encapsulation and because it does not make
 * sense for the application to create a product with unspecified fields.</li>
 * <li>Persisted fields are either private, package-private or protected. We elect to make them private for better
 * encapsulation.</li>
 * <li>Fields need not have getters and setters as far as JPA is concerned. We elect to define the only getters and
 * setters that make sense for the application.</li>
 * </ul>
 *
 * @author Jean-Michel Busca
 */

// Exercise 2.2, second method: named queries
@NamedQueries({
    @NamedQuery(
        name = "raiseProductPriceBy",
        query = "update Product set price = price + :amount"
    )
})

@Entity//(name = "product")
// Declares the class as an entity.
// The name of the entity defaults to the unqualified name of the class.
//@Table(name = "Product")
// @Table id optional. If not used, the entity is mapped to the table with the same (entity) name
public class Product {

  //
  // INSTANCE FIELDS
  //
  @Id
  @GeneratedValue
  // For best portability across databases (Oracle, MySQL, etc.), let Hibernate select the right generation strategy
  private int id;
  //@Column(name = "pname")
  // @Column is optional. If not used: the Java attribute (a) is mapped (unless marked @Transient),
  // (b) to the table attribute with the same name
  private String name;
  //@Column(name = "price")
  private double price;

  //
  // CONSTRUCTORS
  //

  /**
   * Creates a new product with unspecified id, name and price.
   * <p>
   * Note: This no-argument constructor is required by Hibernate. It is never called by the application. The constructor
   * may be declared private, but declaring it package-private or public is more CPU-efficient.
   */
  protected Product() {
  }

  /**
   * Creates a new product with the specified name and price.
   * <p>
   * Note: This constructor is called by the application. The ID of the new product will be set when inserting the
   * product into the database.
   *
   * @param name  the name of the new product
   * @param price the price of the new product
   */
  public Product(String name, double price) {
    this.name = name;
    this.price = price;
  }

  @Override
  public String toString() {
    return "Product{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", price=" + price +
        '}';
  }

  //
  // ACCESSORS AND MUTATORS
  //
  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

}
