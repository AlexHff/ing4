import model.DataAccess;

public final class App {
	public static void main(String[] args) throws Exception {
		DataAccess db = new DataAccess("jdbc:mysql://localhost/company", "root", "");
	}
}
