package ch.stageconcept.datatraffic.dbToDynTableView.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author OLDU
 */
public final class ClassComposer {

    private final String BEGIN_TAG = "// Begin";
    private final String END_TAG = "// End";

    private final String SP1 = " ";
    private final String SP2 = "  ";
    private final String CR = "\n";
    private final String SC = ";";
    private final String CT = "//";
    private final String CBO = "{";
    private final String CBC = "}";
    private final String OP = "(";
    private final String CP = ")";
    private final String CO = ",";
    private final String OD = "<";
    private final String CD = ">";

    private final String PARAMNAME = "value";

    private final String PUBLIC = "public";
    private final String PRIVATE = "private";
    private final String RETURN = "return";
    private final String VOID = "void";
    private final String IMPORT = "import";

    private final String HEADER_TAG = "Header";
    private final String HEADER_BEGIN = BEGIN_TAG + SP1 + HEADER_TAG;
    private final String HEADER_END = END_TAG + SP1 + HEADER_TAG;

    private final String IMPORT_TAG = "Import";
    private final String IMPORT_BEGIN = BEGIN_TAG + SP1 + IMPORT_TAG;
    private final String IMPORT_END = END_TAG + SP1 + IMPORT_TAG;
    private final String PROPERTY = "Property";
    private final String SIMPLE = "Simple";
    private final String IMPORT_PROPERTY_DECLARATION = IMPORT + SP1 + "javafx.beans.property.%s" + PROPERTY + SC + CR;
    private final String IMPORT_LIST_DECLARATION = IMPORT + SP1 + "java.util.List" + SC + CR + CR;

    private final String CLASS_TAG = "Class";
    private final String CLASS_BEGIN = BEGIN_TAG + SP1 + CLASS_TAG;
    private final String CLASS_END = END_TAG + SP1 + CLASS_TAG;
    private final String CLASS_DECLARATION_BEGIN = PUBLIC + SP1 + "class %s" + SP1 + CBO;
    private final String CLASS_DECLARATION_END = CBC;

    private final String MEMBER_TAG = "Member";
    private final String MEMBER_BEGIN = BEGIN_TAG + SP1 + MEMBER_TAG;
    private final String MEMBER_END = END_TAG + SP1 + MEMBER_TAG;
    private final String MEMBER_DECLARATION = SP1 + PRIVATE + SP1 + "final %s" + PROPERTY + SP1 + "%s" + SC + CR;
    private final String MEMBER_DECLARATION_GENERIC = SP1 + PRIVATE + SP1 + "final %s" + PROPERTY + OD + "%s" + CD + SP1 + "%s" + SC + CR;

    private final String CONSTRUCTOR_TAG = "Constructor";
    private final String CONSTRUCTOR_TAG_SHORT = "Cstr";
    private final String CONSTRUCTOR_BEGIN = BEGIN_TAG + SP1 + CONSTRUCTOR_TAG;
    private final String CONSTRUCTOR_BEGIN_SHORT = BEGIN_TAG + SP1 + CONSTRUCTOR_TAG_SHORT;
    private final String CONSTRUCTOR_END = END_TAG + SP1 + CONSTRUCTOR_TAG;
    private final String CONSTRUCTOR_END_SHORT = END_TAG + SP1 + CONSTRUCTOR_TAG_SHORT;
    private final String CONSTRUCTOR_DECLARATION_BEGIN = SP1 + PUBLIC + SP1 + "%s" + OP + CP + SP1 + CBO;
    private final String CONSTRUCTOR_DECLARATION_END = SP1 + CBC + CR;
    private final String CONSTRUCTOR_MEMBER_DECLARATION = SP1 + "this.%s = new" + SP1 + SIMPLE + "%s" + PROPERTY + OP + "%s" + CP + SC + CR;
    private final String CONSTRUCTOR_MEMBER_DECLARATION_GENERIC = SP1 + "this.%s = new" + SP1 + SIMPLE + "%s" + PROPERTY + OD + CD + OP + "%s" + CP + SC + CR;
    private final String CONSTRUCTOR2_PARAM = "List<Object>" + SP1 + PARAMNAME;
    private final String CONSTRUCTOR2_MEMBER_DECLARATION = SP1 + "this.%s = new" + SP1 + SIMPLE + "%s" + PROPERTY + OP + OP + "%s" + CP + SP1 + PARAMNAME + ".get" + OP + "%s" + CP + CP + SC + CR;
    private final String CONSTRUCTOR2_MEMBER_DECLARATION_GENERIC = SP1 + "this.%s = new" + SP1 + SIMPLE + "%s" + PROPERTY + OD + CD + OP + OP + "%s" + CP + SP1 + PARAMNAME + ".get" + OP + "%s" + CP + CP + SC + CR;

    private final String METHOD_TAG = "Method";
    private final String METHOD_BEGIN = BEGIN_TAG + SP1 + METHOD_TAG;
    private final String METHOD_END = END_TAG + SP1 + METHOD_TAG;

    private final String METHOD_HEADER = SP1 + CT + SP1 + "%s" + SP1 + OP + "%s," + SP1 + "%s" + CP + SP1 + "Methods ####################" + CR + CR;
    private final String METHOD_SIMPLE_CLOSE = SP1 + CBC + CR;

    private final String METHOD_GETTER_DECLARATION
            = SP1 + PUBLIC + SP1 + "%s get%s" + OP + CP + SP1 + CBO + CR
            + SP2 + RETURN + SP1 + "%s.get" + OP + CP + SC + CR
            + METHOD_SIMPLE_CLOSE;

    private final String METHOD_SETTER_DECLARATION
            = SP1 + PUBLIC + SP1 + VOID + SP1 + "set%s" + OP + "%s" + SP1 + PARAMNAME + CP + SP1 + CBO + CR
            + SP2 + "%s.set" + OP + PARAMNAME + CP + SC + CR
            + METHOD_SIMPLE_CLOSE;

    private final String METHOD_PROPERTY_DECLARATION
            = SP1 + PUBLIC + SP1 + "%1$s" + PROPERTY + SP1 + "%2$s" + PROPERTY + OP + CP + SP1 + CBO + CR
            + SP2 + RETURN + SP1 + "%2$s" + SC + CR
            + METHOD_SIMPLE_CLOSE;

    private final String METHOD_PROPERTY_DECLARATION_GENERIC
            = SP1 + PUBLIC + SP1 + "%1$s" + PROPERTY + OD + "%3$s" + CD + SP1 + "%2$s" + PROPERTY + OP + CP + SP1 + CBO + CR
            + SP2 + RETURN + SP1 + "%2$s" + SC + CR
            + METHOD_SIMPLE_CLOSE;

    private final String DATE_TIME_FORMAT = "yyyy-MM-dd HH.mm.ss";
    private final String HEADER_COMMENT = "Dynamically created class @:";

    private final String CLASS_TEMPLATE
            = CR
            + HEADER_BEGIN + CR
            + HEADER_END + CR
            + CR
            + IMPORT_BEGIN + CR
            + IMPORT_END + CR
            + CR
            + CLASS_BEGIN + CR
            + CR
            + SP1 + MEMBER_BEGIN + CR
            + SP1 + MEMBER_END + CR
            + CR
            + SP1 + CONSTRUCTOR_BEGIN + CR
            + SP1 + CONSTRUCTOR_END + CR
            + CR
            + SP1 + METHOD_BEGIN + CR
            + SP1 + METHOD_END + CR
            + CR
            + CLASS_END + CR
            + CR;

    private final StringBuffer stringClass;
    private boolean isConstructorEmpty;
    private int constructorIndex;
    private int paramIndex;
    private final List<String> declaredImport;

    public ClassComposer(String className) {
        this.stringClass = new StringBuffer(CLASS_TEMPLATE);
        this.isConstructorEmpty = true;
        this.constructorIndex = 0;
        this.paramIndex = 0;
        this.declaredImport = new ArrayList<>();

        this.addHeaderComment();
        this.classDeclaration(className);
    }

    public StringBuffer getStringClass() {
        return this.stringClass;
    }

    public String getClassTemplate() {
        return CLASS_TEMPLATE;
    }

    private int getBeginBlockOffset(String value) {
        return this.stringClass.indexOf(value) + value.length() + 1;
    }

    private int getEndBlockOffset(String value) {
        return this.stringClass.indexOf(value) - 1;
    }

    public void addHeaderComment() {
        String timeStamp = new SimpleDateFormat(DATE_TIME_FORMAT).format(new Date());
        this.stringClass.insert(getBeginBlockOffset(HEADER_BEGIN), CT + SP1 + HEADER_COMMENT + SP1 + timeStamp + CR);
    }

    private void declarationHelper(String begin, String declarationBegin, String end, String declarationEnd, String name) {
        this.stringClass.insert(getBeginBlockOffset(begin), String.format(declarationBegin, name) + CR);
        this.stringClass.insert(getBeginBlockOffset(end), declarationEnd);
    }

    private void constructorDeclarationHelper(String name) {
        this.constructorIndex++;

        this.stringClass.insert(getEndBlockOffset(CONSTRUCTOR_END), SP1 + CONSTRUCTOR_BEGIN_SHORT + this.constructorIndex + CR);
        this.stringClass.insert(getEndBlockOffset(CONSTRUCTOR_END), String.format(CONSTRUCTOR_DECLARATION_BEGIN, name) + CR);
        this.stringClass.insert(getEndBlockOffset(CONSTRUCTOR_END), CONSTRUCTOR_DECLARATION_END);
        this.stringClass.insert(getEndBlockOffset(CONSTRUCTOR_END), SP1 + CONSTRUCTOR_END_SHORT + this.constructorIndex + CR + CR);
    }

    public void classDeclaration(String name) {
        // Class
        this.declarationHelper(CLASS_BEGIN, CLASS_DECLARATION_BEGIN, CLASS_END, CLASS_DECLARATION_END, name);

        // Constructor
        // 1st Constructor
        this.constructorDeclarationHelper(name);

        // 2nd Constructor
        this.constructorDeclarationHelper(name);
    }

    private void addMember(String classType, String genericType, String name) {
        if (genericType == null) {
            // Member sample
            //  private final IntegerProperty intValue;\n
            //
            this.stringClass.insert(getEndBlockOffset(MEMBER_END), String.format(MEMBER_DECLARATION, classType, name));
        } else {
            // Member sample
            //  private final ObjectProperty<LocalDateTime> birthday;\n
            //
            this.stringClass.insert(getEndBlockOffset(MEMBER_END), String.format(MEMBER_DECLARATION_GENERIC, classType, genericType, name));
        }
    }

    private int findIndexOfClosingParenthesis(String block) {
        int indexOf = getBeginBlockOffset(block);
        String fromCstrDeclareToEnd = this.stringClass.substring(indexOf);
        return fromCstrDeclareToEnd.indexOf(CP);
    }

    private void addMemberToConstructor(String primitiveType, String classType, String genericType, String name) {

        // 1st Constructor
        // Constructor sample
        //  public SimpleValue(int value) {\n
        //   this.intValue = new SimpleIntegerProperty(value);\n
        //  }\n
        //
        // header
        String paramStr = PARAMNAME + this.paramIndex;
        String tmpStr = primitiveType + SP1 + paramStr;
        if (!this.isConstructorEmpty) {
            if (genericType == null) {
                tmpStr = CO + SP1 + primitiveType + SP1 + PARAMNAME + this.paramIndex;
            } else {
                tmpStr = CO + SP1 + genericType + SP1 + PARAMNAME + this.paramIndex;
            }
        } else {
            this.isConstructorEmpty = false;
        }

        String cstrBeginIndex = CONSTRUCTOR_BEGIN_SHORT + 1;
        this.stringClass.insert(this.getBeginBlockOffset(cstrBeginIndex) + this.findIndexOfClosingParenthesis(cstrBeginIndex), tmpStr);

        // body
        if (genericType == null) {
            this.stringClass.insert(getEndBlockOffset(CONSTRUCTOR_END_SHORT + 1) - 3, SP1 + String.format(CONSTRUCTOR_MEMBER_DECLARATION, name, classType, paramStr));
        } else {
            this.stringClass.insert(getEndBlockOffset(CONSTRUCTOR_END_SHORT + 1) - 3, SP1 + String.format(CONSTRUCTOR_MEMBER_DECLARATION_GENERIC, name, classType, paramStr));
        }

        // 2nd Constructor
        // Constructor sample
        //  public SimpleValue(List<Object> values) {\n
        //   this.intValue001 = new SimpleIntegerProperty((int) values.get(0));\n
        //  }\n
        //
        // header
        if (this.paramIndex == 0) {
            tmpStr = CONSTRUCTOR2_PARAM;
            cstrBeginIndex = CONSTRUCTOR_BEGIN_SHORT + 2;
            this.stringClass.insert(this.getBeginBlockOffset(cstrBeginIndex) + this.findIndexOfClosingParenthesis(cstrBeginIndex), tmpStr);
        }

        // body
        if (genericType == null) {
            this.stringClass.insert(getEndBlockOffset(CONSTRUCTOR_END_SHORT + 2) - 3, SP1 + String.format(CONSTRUCTOR2_MEMBER_DECLARATION, name, classType, primitiveType, this.paramIndex));
        } else {
            this.stringClass.insert(getEndBlockOffset(CONSTRUCTOR_END_SHORT + 2) - 3, SP1 + String.format(CONSTRUCTOR2_MEMBER_DECLARATION_GENERIC, name, classType, genericType, this.paramIndex));
        }

        this.paramIndex++;
    }

    public void addImport(String classType, boolean isLitteral) {

    	if (classType == null) return;

        // Already added import?
        boolean hasImport = false;
        if (!this.declaredImport.isEmpty()) {
            Iterator<String> iterator = this.declaredImport.iterator();
            while (!hasImport && iterator.hasNext()) {
                hasImport = iterator.next().equals(classType);
            }
        } else {
            // First time import
            this.stringClass.insert(getEndBlockOffset(IMPORT_END) + 1, IMPORT_LIST_DECLARATION);
        }

        if (!hasImport) {
            this.declaredImport.add(classType);

            // !! Warning, no indentation for the import section, this is the reason of + 1 after getEndBlockOffset() call
            if (!isLitteral) {
                // javafx beans property import
                //
                // Import sample
                // import javafx.beans.property.IntegerProperty;\n
                // import javafx.beans.property.SimpleIntegerProperty;\n
                //
                this.stringClass.insert(getEndBlockOffset(IMPORT_END) + 1, String.format(IMPORT_PROPERTY_DECLARATION, classType));
                this.stringClass.insert(getEndBlockOffset(IMPORT_END) + 1, String.format(IMPORT_PROPERTY_DECLARATION, SIMPLE + classType) + CR);
            } else {
                // litteral import
                //
                this.stringClass.insert(getEndBlockOffset(IMPORT_END) + 1, IMPORT + SP1 + classType + SC + CR + CR);
            }
        }
    }

    private String firstLetterToUpperCase(String toConvert) {
        return toConvert.substring(0, 1).toUpperCase() + toConvert.substring(1);
    }

    private void addGetMethod(String primitiveType, String genericType, String name) {
        // Getter sample
        //  public int getIntValue() {\n
        //   return intValue.get();\n
        //  }\n
        if (genericType == null) {
            this.stringClass.insert(getEndBlockOffset(METHOD_END), String.format(METHOD_GETTER_DECLARATION, primitiveType, this.firstLetterToUpperCase(name), name) + CR);
        } else {
            this.stringClass.insert(getEndBlockOffset(METHOD_END), String.format(METHOD_GETTER_DECLARATION, genericType, this.firstLetterToUpperCase(name), name) + CR);
        }
    }

    private void addSetMethod(String primitiveType, String genericType, String name) {
        // Setter sample
        //  public void setIntValue(int value) {\n
        //   intValue.set(value);\n
        //  }\n
        if (genericType == null) {
            this.stringClass.insert(getEndBlockOffset(METHOD_END), String.format(METHOD_SETTER_DECLARATION, this.firstLetterToUpperCase(name), primitiveType, name) + CR);
        } else {
            this.stringClass.insert(getEndBlockOffset(METHOD_END), String.format(METHOD_SETTER_DECLARATION, this.firstLetterToUpperCase(name), genericType, name) + CR);
        }
    }

    private void addPropertyMethod(String classType, String genericType, String name) {
        // Property sample
        //  public IntegerProperty intValueProperty() {\n
        //   return intValue;\n
        //  }\n
        if (genericType == null) {
            this.stringClass.insert(getEndBlockOffset(METHOD_END), String.format(METHOD_PROPERTY_DECLARATION, classType, name) + CR);
        } else {
            this.stringClass.insert(getEndBlockOffset(METHOD_END), String.format(METHOD_PROPERTY_DECLARATION_GENERIC, classType, name, genericType) + CR);
        }
    }

    private void addMethods(String primitiveType, String classType, String genericType, String name) {
        // Header
        this.stringClass.insert(getEndBlockOffset(METHOD_END), String.format(METHOD_HEADER, name, primitiveType, classType));
        // Getter
        this.addGetMethod(primitiveType, genericType, name);
        // Setter
        this.addSetMethod(primitiveType, genericType, name);
        // Property
        this.addPropertyMethod(classType, genericType, name);
    }

    public void memberMasterCreate(String primitiveType, String classType, String genericType, String name) {
        // Member
        this.addMember(classType, genericType, name);

        // Constructor
        this.addMemberToConstructor(primitiveType, classType, genericType, name);

        // Import
        this.addImport(classType, false);

        // Methods
        this.addMethods(primitiveType, classType, genericType, name);
    }
}
