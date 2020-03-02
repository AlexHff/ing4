package hibernate;

import org.hibernate.*;
import org.hibernate.query.Query;
import org.hibernate.cfg.Configuration;

import java.util.*;

/**
 * Test program of the lab.
 *
 * @author Jean-Michel Busca
 */
public class Main {
    //
    // CLASS FIELDS
    //
    /**
     * The session factory needed to access the database (see the CRUD methods
     * below).
     */
    private static final SessionFactory factory;

    /**
     * The static section below creates a Hibernate Configuration object, based on
     * the contents of the hibernate.cfg.xml configuration file. (The file includes
     * information to connect to the database entities must be persisted to.) The
     * configuration object is then used to create a session factory.
     * <p>
     * Note: A static block is executed only once, when the embedding class is
     * loaded into the JVM. It can be thought of as an anonymous static method that
     * the class loader automatically calls upon loading the class.
     */
    static {
        try {
            // creates a Hibernate configuration object and initializes it based on the
            // hibernate.cfg.xml configuration file
            Configuration configuration = new Configuration();
            configuration.configure();
            // instructs Hibernate to manage the Product entity class through annotations
            // rather than XML files
            configuration.addAnnotatedClass(Product.class);
            configuration.addAnnotatedClass(Order.class);
            // creates the session factory
            factory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    //
    // MAIN METHOD
    //

    /**
     * Main method of the test program.
     *
     * @param args the command line arguments (none required)
     *
     */
    public static void main(final String[] args) {
        // retrieve all the products stored in the database
        System.out.println("listProducts()=" + listProducts());

        // remove all the products from the database for a fresh start
        System.out.println("deleteProducts()=" + deleteProducts());

        // populate the database with three products (observe that product IDs are
        // automatically generated and set)
        Product sugar = new Product("sugar", 1.0);
        System.out.println("createProduct(" + sugar + ")=" + createProduct(sugar));
        System.out.println("   now, sugar=" + sugar);

        Product milk = new Product("milk", 0.5);
        System.out.println("createProduct(" + milk + ")=" + createProduct(milk));
        System.out.println("   now, milk=" + milk);

        Product chocolate = new Product("chocolate", 5.0);
        System.out.println("createProduct(" + chocolate + ")=" + createProduct(chocolate));
        System.out.println("   now, chocolate=" + chocolate);

        // retrieve all the products stored in the database
        System.out.println("listProducts()=" + listProducts());

        // raise the price of the chocolate and update the database accordingly
        chocolate.setPrice(7.0);
        System.out.println("updateProduct(" + chocolate + ")=" + updateProduct(chocolate));
        System.out.println("readProduct(" + chocolate.getId() + ")=" + readProduct(chocolate.getId()));

        // delete the milk from the database
        System.out.println("deleteProduct(" + milk + ")=" + deleteProduct(milk));
        System.out.println("readProduct(" + milk.getId() + ")=" + readProduct(milk.getId()));

        // retrieve all the products that cost less than 6.0
        double maxPrice = 6.0;
        System.out.println("listProductsLessThan(" + maxPrice + ")=" + listProductsLessThan(maxPrice));

        // raise the price of all the prodcuts by 1.0
        double priceRaise = 1.0;
        System.out.println("raiseProductPriceBy(" + priceRaise + ")=" + raiseProductPriceBy(priceRaise));

        // retrieve all the products stored in the database
        System.out.println("listProducts()=" + listProducts());

        // done
        System.out.println("done");
    }

    //
    // CRUD PRODUCT OPERATIONS
    //

    /**
     * Creates in (i.e. inserts into) the database the specified product. The method
     * automatically computes and set the ID of the product object passed as an
     * argument. Exercise 1.3.
     *
     * @param product the product to create in the database
     *
     * @return the ID of the newly-inserted product, or {@code -1} if the product
     *         could not be created
     */
    private static int createProduct(Product product) {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            Integer id = (Integer) session.save(product);
            transaction.commit();
            return id;
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Reads from the database the product with the specified ID. Exercise 1.3.
     *
     * @param id the ID of the product to read from the database
     *
     * @return the product instance, or {@code null} if no product with the
     *         specified ID was found in the database
     */
    private static Product readProduct(int id) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            Product product = session.get(Product.class, id);
            transaction.commit();
            return product;
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }

    /**
     * Update the specified product in the database. Exercise 1.3.
     *
     * @param product the product to update in the database
     *
     * @return {@code true} if the update was successful and {@code false} otherwise
     */
    private static boolean updateProduct(Product product) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.update(product);
            transaction.commit();
            return true;
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    /**
     * Deletes from the database the product with the specified ID. Exercise 1.3.
     *
     * @param product the product to remove from the database
     *
     * @return {@code true} if the delete was successful and {@code false} otherwise
     */
    private static boolean deleteProduct(Product product) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.delete(product);
            transaction.commit();
            return true;
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    //
    // ADDITIONAL PRODUCT OPERATIONS
    //

    /**
     * Retrieves all the products from the database. Exercise 2.1.
     *
     * @return all the products in the database or {@code Collections.emptyList()}
     *         if there is no product
     */
    private static List<Product> listProducts() {
        Transaction transaction = null;
        Session session = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            Query query = session.createQuery("from Product");
            List<Product> list = query.list();
            transaction.commit();
            return list;
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return Collections.emptyList();
        } finally {
            session.close();
        }
    }

    /**
     * Deletes all the products from the database. Exercise 2.1.
     *
     * @return the number of deleted products
     */
    private static int deleteProducts() {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            Query query = session.createQuery("delete from Product");
            int count = query.executeUpdate();
            transaction.commit();
            return count;
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Retrieves from the database the products than cost less than the specified
     * price. Exercise 2.2.
     * <p>
     * The method dynamically creates a new HQL query every time it is called, using
     * the price argument. The {@code
     * raiseProductPriceBy} method uses an alternate method, based on named queries.
     *
     * @return all the products in the database that cost less than the specified
     *         price or {@code Collections.emptyList()} if no such product is found
     */
    private static List<Product> listProductsLessThan(double maxPrice) {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            Query query = session.createQuery("from Product where price < " + maxPrice);
            List<Product> list = query.list();
            transaction.commit();
            return list;
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    /**
     * Raises the price of all the products in the database by the specified amount.
     * Exercise 2.2.
     * <p>
     * The method use a parameterized named query. The query parameter is replaced
     * by the method argument before executing. Note: named queries are
     * syntactically-checked and pre-compiled at startup time.
     *
     * @return the count of updated products or {@code -1} if the updated failed
     */
    private static int raiseProductPriceBy(double amount) {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            Query query = session.getNamedQuery("raiseProductPriceBy");
            query.setParameter("amount", amount);
            int count = query.executeUpdate();
            transaction.commit();
            return count;
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return -1;
        }
    }
}
