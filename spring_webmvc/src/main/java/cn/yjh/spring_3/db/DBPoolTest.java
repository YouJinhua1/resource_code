package cn.yjh.spring_3.db;

import cn.yjh.spring_3.utils.ExcelUtil;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 测试手写的JDBC连接池
 * @author: You Jinhua
 * @create: 2021-01-18 16:09
 */
public class DBPoolTest {

    public static void main(String[] args) throws Exception {
        DBConn dbConn = null;

        List<String> tableNames = getTableNames();
        ExcelUtil.ExcelSheet[] excelSheets = new ExcelUtil.ExcelSheet[tableNames.size()];
        List<Map<String, Integer>> column_mapping = getColumn_mapping();

        Map<Integer,String> map0 = new HashMap<>();
        map0.put(0,"不可编辑");
        map0.put(1,"不可编辑");
        map0.put(2,"不可编辑");
        map0.put(3,"不可编辑");
        map0.put(4,"不可编辑");
        map0.put(5,"必填");
        map0.put(6,"必填");
        map0.put(12,"必填");
        map0.put(15,"必填");
        map0.put(16,"必填");
        map0.put(18,"必填");

        int i = 0;
        for (String tableName:tableNames) {
            ExcelUtil.ExcelSheet excelSheet = new ExcelUtil.ExcelSheet(ExcelBean.class);
            excelSheet.setTitle_column_mapping(column_mapping.get(0));
            excelSheet.setTitle_field_mapping(getField_mapping());
            excelSheet.setSheetName("IMS."+tableName);
            String s = ""/*"
                SELECT
                'IMS' systemname,
                    'IMS.'||t1.Table_Name||'.' location,
                	t1.Table_Name tablename,
                	t1.Column_Name columnname,
                	t1.DATA_TYPE || '(' || t1.DATA_LENGTH || ')' datatype,
                	t1.nullable as isnull,
                	(
                	 case when (select count(1) from user_cons_columns a, user_constraints b where a.constraint_name = b.constraint_name
                   and b.constraint_type = 'P' and a.table_name = t1.Table_Name and a.column_name=t1.Column_Name) >0 then 'Y' else 'N' end
                	) ispk,
                	
                	t1.Data_Default defaultvalue, 
                	t2.Comments comments
                FROM
                	cols t1
                	LEFT JOIN user_col_comments t2 ON t1.Table_name = t2.Table_name
                	AND t1.Column_Name = t2.Column_Name
                WHERE
                """*/;
            s+="t1.Table_Name = '"+tableName+"'";
            dbConn = DBConnPoolFactory.DB_CONN_POOL.getConnection();
            List<ExcelBean> beanList = dbConn.query(s, ExcelBean.class);
            excelSheet.setBeanList(beanList);
            excelSheets[i++] = excelSheet;
        }
        long x = System.currentTimeMillis();
        ExcelUtil.createExcel("C:\\Users\\youjinhua\\Desktop\\test\\影像系统表结构-（全部）.xlsx",map0,column_mapping.get(1),excelSheets);
        System.out.println((System.currentTimeMillis() - x)/1000);
    }

    public static List<String> getTableNames(){
        List<String> tableName = new ArrayList<>();

        //ableName.add("CC_ACCESS_LOGS");
        tableName.add("DOWN_IMAGES_LOGS");
        tableName.add("ES_BATCHNO");
        tableName.add("ES_CODE_EXTEND");
        tableName.add("ES_COM_SERVER");
        tableName.add("ES_CONTTOCLM");
        tableName.add("ES_CONTTOCUSTOMER");
        tableName.add("ES_COST_LOG");
        tableName.add("ES_CUSTOMER");
        tableName.add("ES_DOC_CLASS_DEF");
        tableName.add("ES_DOC_CODE_DEF");
        tableName.add("ES_DOC_DEF");
        tableName.add("ES_DOC_LOG");
        tableName.add("ES_DOC_LOG_P");
        tableName.add("ES_DOC_LOG_PB");
        tableName.add("ES_DOC_MAIN");
        tableName.add("ES_DOC_MAIN_P");
        tableName.add("ES_DOC_MAIN_PB");
        tableName.add("ES_DOC_NAME");
        tableName.add("ES_DOC_PAGES");
        tableName.add("ES_DOC_PAGES_LOG");
        tableName.add("ES_DOC_PAGES_P");
        tableName.add("ES_DOC_PAGES_PB");
        tableName.add("ES_DOC_PROPERTY");
        tableName.add("ES_DOC_PROPERTY_P");
        tableName.add("ES_DOC_PROPERTY_PB");
        tableName.add("ES_DOC_QUERY_RESULT_DEF");
        tableName.add("ES_DOC_RELATION");
        tableName.add("ES_DOC_RELATION_PB");
        tableName.add("ES_DOC_SCANNO");
        tableName.add("ES_EDOR_ITEM");
        tableName.add("ES_ISSUEDOC");
        tableName.add("ES_ISSUEDOC_P");
        tableName.add("ES_ISSUEDOC_PB");
        tableName.add("ES_MODIFYMANAGECOM_LOGS");
        tableName.add("ES_MOVEFILE_LOG");
        tableName.add("ES_NAME_DEF");
        tableName.add("ES_NOTICETOBUSS");
        tableName.add("ES_PIN_MAIN");
        tableName.add("ES_PIN_TODOC");
        tableName.add("ES_PINUPLOAD_LOGS");
        tableName.add("ES_PROCESS_DEF");
        tableName.add("ES_PROPERTY_DEF");
        tableName.add("ES_SERVER_INFO");
        tableName.add("ES_TWAIN_DEF");
        //tableName.add("ES_UPLOADLOG");
       // tableName.add("GW_COORDINATE");
        tableName.add("GW_DOWMLOAD_LOGS");
        tableName.add("LDCODE");
        tableName.add("LDSYSVAR");
        tableName.add("SYS_COMPANY");
        return tableName;
    }

    public static List<Map<String,Integer>> getColumn_mapping(){
        List<Map<String,Integer>> list = new ArrayList();
        Map<String,Integer> map = new HashMap<>();
        map.put("系统名称",0);
        map.put("表英文名",1);
        map.put("字段英文名",2);
        map.put("字段类型",3);
        map.put("主键",4);
        map.put("中文名",5);
        map.put("业务含义",6);
        map.put("数据类型",7);
        map.put("精度",8);
        map.put("最大值/长度",9);
        map.put("最小值/长度",10);
        map.put("代码类别编号",11);
        map.put("允许为空",12);
        map.put("为空的情形",13);
        map.put("默认值",14);
        map.put("是否主本",15);
        map.put("是否副本",16);
        map.put("主本位置",17);
        map.put("敏感字段",18);
        map.put("特定数据来说明",19);
        list.add(map);
       /* Map<Integer,String> map0 = new HashMap<>();
        map0.put(0,"不可编辑");
        map0.put(1,"不可编辑");
        map0.put(2,"不可编辑");
        map0.put(3,"不可编辑");
        map0.put(4,"不可编辑");
        map0.put(5,"必填");
        map0.put(6,"必填");
        map0.put(12,"必填");
        map0.put(15,"必填");
        map0.put(16,"必填");
        map0.put(18,"必填");

        list.add(map0);*/
        Map<String,Integer> map1 = new HashMap<>();
        map1.put("物理模型中的系统名称",0);
        map1.put("物理模型中的表名称",1);
        map1.put("物理模型中的字段名称",2);
        map1.put("物理模型中的字段类型",3);
        map1.put("是否主键",4);
        map1.put("易于被数据使用人理解和识别，原物理模型中如有，检查物理模型中的定义是否规范",5);
        map1.put("字段的业务含义和能够。存在一个字段有多业务含义的情况，都需要说明",6);
        map1.put("如果现状系统字段类型定义错误必填，选择更正后的类型",7);
        map1.put("与数据类型配套使用。对于数值型字段，定义小数点后位数",8);
        map1.put("从业务层面定义字段的最大允许值，对于字符类型，填写最大长度。对于数值类型字段，填写最大值",9);
        map1.put("从业务成眠定义字段的最小允许值，对于字符类型，填写最大长度。对于数值类型字段，填写最小值",10);
        map1.put("字段是代码类、标志类必填",11);
        map1.put("是否容许为空",12);
        map1.put("如果允许为空，对关键业务字段描述什么场景下有值什么场景下为空",13);
        map1.put("该字段的默认值",14);
        map1.put("是否主本数据",15);
        map1.put("是否副本数据",16);
        map1.put("如果是副本，相应朱本的系统表明.字段名。即system.table.field",17);
        map1.put("识别铭感数据信息",18);
        map1.put("对梳理和探查中发现的特定数据现象进行描述。",19);
        list.add(map1);
        return list;
    }

    public static Map<String,String> getField_mapping(){

        Map<String,String> map = new HashMap<>();
        map.put("systemname","系统名称");
        map.put("tableName","表英文名");
        map.put("columnName","字段英文名");
        map.put("dataType","字段类型");
        map.put("ispk","主键");
        map.put("comments","中文名");
       /* map.put("业务含义",6);
        map.put("数据类型",7);
        map.put("精度",8);
        map.put("最大值/长度",9);
        map.put("最小值/长度",10);
        map.put("代码类别编号",11);*/
        map.put("isnull","允许为空");
        /*map.put("为空的情形",13);*/
        map.put("defaultValue","默认值");
        map.put("isMain","是否主本");
        map.put("isP","是否副本");
        map.put("location","主本位置");
       /* map.put("敏感字段",18);
        map.put("特定数据来说明",19);*/
        return map;
    }

    }
