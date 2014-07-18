package createTables;
public enum DBVarTypes {
	VARCHAR("VARCHAR(255)"), BIGINT("BIGINT"), DOUBLE("DOUBLE"), LONGVARCHAR(
			"VARCHAR(1024)"), DATE("DATE");

	private DBVarTypes(String name) {
		this.name = name;
	}

	private final String name;

	public String getName() {
		return name;
	}

}
