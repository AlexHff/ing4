package model;

/**
 *
 * @author Jean-Michel Busca
 */
public class DepartmentInfo {

  private final int id;
  private final String name;
  private final String location;

  public DepartmentInfo(int id, String name, String location) {
    this.id = id;
    this.name = name;
    this.location = location;
  }

  @Override
  public String toString() {
    return "DepartmentInfo{" + "id=" + id + ", name=" + name + ", location=" + location + "}\n";
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getLocation() {
    return location;
  }

}
