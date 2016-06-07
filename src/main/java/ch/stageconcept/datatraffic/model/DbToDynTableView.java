package ch.stageconcept.datatraffic.model;

import static ch.stageconcept.datatraffic.util.type.MasterSingleton.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;

import ch.stageconcept.datatraffic.util.DynClassFactory;
import ch.stageconcept.datatraffic.util.JavaSourceFromString;
import ch.stageconcept.datatraffic.util.Pair;
import ch.stageconcept.datatraffic.util.type.MasterSingleton;

/**
 *
 * @author OLDU
 */
@SuppressWarnings("rawtypes")
public final class DbToDynTableView extends TableView {

	// DbToDynTableView
	private final String dbDriver;
	private final String dbUrl;
	private final String dbUser;
	private final String dbPass;
	private final String dbSql;
	private Connection dbConnection;
	private ResultSet dbResultSet;
	private final List<String> dbColumnsNames;
	private final List<String> dbColumnsTypes;
	private final List<Pair<String, String>> dbColumnsNamesTypesPairList;
	private DynClassFactory dynClass;
	private final JavaCompiler compiler;
	private final String dynClassName;
	private final String dynClassPath;
	private final String dynClassFileExtension;
	private File dynClassfRoot;
	private File dynClassFile;
	private Class dynClassCompiled;
	private Constructor dynClassParamListConstructor;
	List<Object> dataDynClassObjectCollection;
	private final ObservableList<Object> dataTableViewObjectCollection;

	// Master Constructor
	public DbToDynTableView(String dbDriver, String dbUrl, String dbUser, String dbPass, String dbSql,
			String dynClassName, String dynClassPath, String dynClassFileExtension, boolean compileInFile)
			throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException,
			NoSuchMethodException, IllegalArgumentException, InvocationTargetException, IOException {

		super();

		this.dbDriver = dbDriver;
		this.dbUrl = dbUrl;
		this.dbUser = dbUser;
		this.dbPass = dbPass;
		this.dbSql = dbSql;
		this.dynClassName = dynClassName;
		this.dynClassPath = dynClassPath;
		this.dynClassFileExtension = dynClassFileExtension;

		dbColumnsNames = new ArrayList<>();
		dbColumnsTypes = new ArrayList<>();
		dbColumnsNamesTypesPairList = new ArrayList<Pair<String, String>>();
		dataTableViewObjectCollection = FXCollections.observableArrayList();

		compiler = ToolProvider.getSystemJavaCompiler();

		// process
		this.dbEstablishConnection();
		this.dbExecuteQuery();
		this.dbFetchColumnMetaData();
		this.dynClassCreateFromDbQuery();

		// jobCompile = () -> ...();
		if (compileInFile) {
			dynClassCompileInFileAndLoad();
		} else {
			// compile in memory
			dynClassCompileInMemoryAndLoad();
		}

		this.dynClassSetParamListConstructor();
		this.dbDataToDynClassObjectCollection();
		this.dbDataToTableViewObjectCollection();
		this.tableViewCreateColumn();
		this.tableViewSetItems();
	}

	private void dbEstablishConnection()
			throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		Class.forName(this.dbDriver).newInstance();
		dbConnection = DriverManager.getConnection(dbUrl, dbUser, dbPass);
	}

	private void dbExecuteQuery() throws SQLException {
		dbResultSet = dbConnection.createStatement().executeQuery(dbSql);
	}

	private void dbFetchColumnMetaData() throws SQLException {
		for (int i = 0; i < dbResultSet.getMetaData().getColumnCount(); i++) {

			String name = dbResultSet.getMetaData().getColumnName(i + 1);
			String type = dbResultSet.getMetaData().getColumnTypeName(i + 1);

			dbColumnsNames.add(name);
			dbColumnsTypes.add(type);

			Pair<String, String> nameTypePair = new Pair<String, String>(name, type);

			dbColumnsNamesTypesPairList.add(nameTypePair);
		}
	}

	public String dbGetTableName() throws SQLException {
		return dbResultSet.getMetaData().getTableName(1);
	}

	public List<String> dbGetColumnsNames() {
		return dbColumnsNames;
	}

	public List<Pair<String, String>> dbGetColumnsNamesTypesPairList() {
		return dbColumnsNamesTypesPairList;
	}

	private void dynClassCreateFromDbQuery() throws SQLException {
		dynClass = new DynClassFactory(dynClassName);

		for (int i = 0; i < dbResultSet.getMetaData().getColumnCount(); i++) {
			MasterSingleton.INSTANCE.getDataHashMap().get(dbColumnsTypes.get(i)).dynClassMemberMasterCreate(dynClass,
					dbColumnsNames.get(i));

		}
	}

	private void dynClassCompileInFileAndLoad() throws IOException, ClassNotFoundException {
		// Write class to file
		dynClassfRoot = new File(dynClassPath);
		// Save source in .fileExtension (.java) file.
		dynClassFile = new File(dynClassfRoot, dynClassName + dynClassFileExtension);

		try (FileWriter writer = new FileWriter(dynClassFile)) {
			// create the source
			writer.write(dynClass.getStringClass().toString());
		}

		// Compile class
		try (StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null)) {
			fileManager.setLocation(StandardLocation.CLASS_OUTPUT, Arrays.asList(new File(dynClassPath)));

			compiler.getTask(null, fileManager, null, null, null,
					fileManager.getJavaFileObjectsFromFiles(Arrays.asList(dynClassFile))).call();
		}

		// Load class
		URLClassLoader classLoader = URLClassLoader.newInstance(new URL[] { dynClassfRoot.toURI().toURL() });
		dynClassCompiled = Class.forName(dynClassName, true, classLoader);
	}

	private void dynClassCompileInMemoryAndLoad() throws IOException {
		// Compile code fully in memory with javax.tools.JavaCompiler
		// SRC:
		// http://stackoverflow.com/questions/12173294/compile-code-fully-in-memory-with-javax-tools-javacompiler

		DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();

		JavaFileObject file = new JavaSourceFromString(dynClassName, dynClass.getStringClass().toString());

		Iterable<? extends JavaFileObject> compilationUnits = Arrays.asList(file);
		JavaCompiler.CompilationTask task = compiler.getTask(null, null, diagnostics, null, null, compilationUnits);

		boolean success = task.call();

		for (Diagnostic diagnostic : diagnostics.getDiagnostics()) {
			System.out.println(diagnostic.getCode());
			System.out.println(diagnostic.getKind());
			System.out.println(diagnostic.getPosition());
			System.out.println(diagnostic.getStartPosition());
			System.out.println(diagnostic.getEndPosition());
			System.out.println(diagnostic.getSource());
			System.out.println(diagnostic.getMessage(null));
		}

		// Load class
		if (success) {
			try {
				URLClassLoader classLoader = URLClassLoader.newInstance(new URL[] { new File("").toURI().toURL() });
				dynClassCompiled = Class.forName(dynClassName, true, classLoader);
			} catch (ClassNotFoundException e) {
				System.err.println("Class not found: " + e);
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void dynClassSetParamListConstructor() throws NoSuchMethodException {
		dynClassParamListConstructor = dynClassCompiled.getConstructor(List.class);
	}

	private void dbDataToDynClassObjectCollection() throws SQLException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		List<Object> dataRow;
		dataDynClassObjectCollection = new ArrayList<>();

		while (dbResultSet.next()) {
			// Iterate Row
			dataRow = new ArrayList<>();
			for (int i = 1; i <= dbResultSet.getMetaData().getColumnCount(); i++) {
				// Iterate Column
				MasterSingleton.INSTANCE.getDataHashMap().get(dbColumnsTypes.get(i - 1)).addRowData(dataRow,
						dbResultSet, i);
			}
			dataDynClassObjectCollection.add(dynClassParamListConstructor.newInstance(dataRow));
		}
	}

	private void dbDataToTableViewObjectCollection() {
		dataTableViewObjectCollection.setAll(dataDynClassObjectCollection);
	}

	@SuppressWarnings("unchecked")
	private void tableViewCreateColumn() {
		for (int i = 0; i < dbColumnsNames.size(); i++) {
			String columnName = dbColumnsNames.get(i);
			TableColumn col = new TableColumn(columnName);
			if (dbColumnsTypes.get(i).equals(DB_TYPE_DATETIME)) {
				col.setMinWidth(160);
			} else {
				col.setMinWidth(80);
			}
			col.setCellValueFactory(new PropertyValueFactory<>(columnName));
			this.getColumns().add(col);
		}
	}

	@SuppressWarnings("unchecked")
	private void tableViewSetItems() {
		this.setItems(dataTableViewObjectCollection);
	}
}
