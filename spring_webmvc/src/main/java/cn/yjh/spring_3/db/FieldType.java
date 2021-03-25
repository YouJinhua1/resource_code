package cn.yjh.spring_3.db;

/**
 * @description: ◊÷∂Œ¿‡–Õ
 * @author: You Jinhua
 * @create: 2021-01-19 21:38
 */
public enum FieldType {

    SHORT, INTEGER, LONG, FLOAT, DOUBLE, BOOLEAN, BYTE, STRING, DATE;

    public static FieldType getFiledType(String className) {
        FieldType fieldType = null;
        switch (className) {
            case "java.lang.Short":
                fieldType = SHORT;
                break;
            case "short":
                fieldType = SHORT;
                break;
            case "java.lang.Integer":
                fieldType = INTEGER;
                break;
            case "int":
                fieldType = INTEGER;
                break;
            case "java.lang.Long":
                fieldType = LONG;
                break;
            case "long":
                fieldType = LONG;
                break;
            case "java.lang.Float":
                fieldType = FLOAT;
                break;
            case "float":
                fieldType = FLOAT;
                break;
            case "java.lang.Double":
                fieldType = DOUBLE;
                break;
            case "double":
                fieldType = DOUBLE;
                break;
            case "java.lang.Boolean":
                fieldType = BOOLEAN;
                break;
            case "boolean":
                fieldType = BOOLEAN;
                break;
            case "java.lang.Byte":
                fieldType = BYTE;
                break;
            case "byte":
                fieldType = BYTE;
                break;
            case "java.lang.String":
                fieldType = STRING;
                break;
            case "java.lang.Date":
                fieldType = DATE;
                break;
        }
        return fieldType;
    }
}
