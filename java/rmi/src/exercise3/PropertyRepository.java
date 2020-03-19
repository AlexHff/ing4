package exercise3;

/**
 * Interface of a property repository.
 *
 */
public interface PropertyRepository {

  public String getProperty(String key);

  public void setProperty(String key, String value);

  public void removeProperty(String key);

}
