package cn.yjh.spring_3.db;

import cn.yjh.spring_3.db.annotation.Column;

/**
 * @description: Excel µÃÂ
 * @author: You Jinhua
 * @create: 2021-01-19 21:30
 */
public class ExcelBean {
    @Column(value = "tablename",type = FieldType.STRING)
    private String tableName;

    private String columnName;

    private String dataType;

    private String comments;

    private String defaultValue;

    private String ispk;

    private String isnull;

    private String systemname;

    private String isMain;

    private String isP;

    private String location;

    public ExcelBean() {
        this.isMain = "Y";
        this.isP = "N";
    }

    public String getIsMain() {
        return isMain;
    }

    public void setIsMain(String isMain) {
        this.isMain = isMain;
    }

    public String getIsP() {
        return isP;
    }

    public void setIsP(String isP) {
        this.isP = isP;
    }

    public String getLocation() {
        return this.systemname+"."+this.tableName+".";
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getIspk() {
        return ispk;
    }

    public void setIspk(String ispk) {
        this.ispk = ispk;
    }

    public String getIsnull() {
        return isnull;
    }

    public void setIsnull(String isnull) {
        this.isnull = isnull;
    }

    public String getSystemname() {
        return systemname;
    }

    public void setSystemname(String systemname) {
        this.systemname = systemname;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
}
