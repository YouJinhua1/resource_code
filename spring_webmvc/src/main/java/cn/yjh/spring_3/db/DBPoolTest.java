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
 * @description: ������д��JDBC���ӳ�
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
        map0.put(0,"���ɱ༭");
        map0.put(1,"���ɱ༭");
        map0.put(2,"���ɱ༭");
        map0.put(3,"���ɱ༭");
        map0.put(4,"���ɱ༭");
        map0.put(5,"����");
        map0.put(6,"����");
        map0.put(12,"����");
        map0.put(15,"����");
        map0.put(16,"����");
        map0.put(18,"����");

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
        ExcelUtil.createExcel("C:\\Users\\youjinhua\\Desktop\\test\\Ӱ��ϵͳ��ṹ-��ȫ����.xlsx",map0,column_mapping.get(1),excelSheets);
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
        map.put("ϵͳ����",0);
        map.put("��Ӣ����",1);
        map.put("�ֶ�Ӣ����",2);
        map.put("�ֶ�����",3);
        map.put("����",4);
        map.put("������",5);
        map.put("ҵ����",6);
        map.put("��������",7);
        map.put("����",8);
        map.put("���ֵ/����",9);
        map.put("��Сֵ/����",10);
        map.put("���������",11);
        map.put("����Ϊ��",12);
        map.put("Ϊ�յ�����",13);
        map.put("Ĭ��ֵ",14);
        map.put("�Ƿ�����",15);
        map.put("�Ƿ񸱱�",16);
        map.put("����λ��",17);
        map.put("�����ֶ�",18);
        map.put("�ض�������˵��",19);
        list.add(map);
       /* Map<Integer,String> map0 = new HashMap<>();
        map0.put(0,"���ɱ༭");
        map0.put(1,"���ɱ༭");
        map0.put(2,"���ɱ༭");
        map0.put(3,"���ɱ༭");
        map0.put(4,"���ɱ༭");
        map0.put(5,"����");
        map0.put(6,"����");
        map0.put(12,"����");
        map0.put(15,"����");
        map0.put(16,"����");
        map0.put(18,"����");

        list.add(map0);*/
        Map<String,Integer> map1 = new HashMap<>();
        map1.put("����ģ���е�ϵͳ����",0);
        map1.put("����ģ���еı�����",1);
        map1.put("����ģ���е��ֶ�����",2);
        map1.put("����ģ���е��ֶ�����",3);
        map1.put("�Ƿ�����",4);
        map1.put("���ڱ�����ʹ��������ʶ��ԭ����ģ�������У��������ģ���еĶ����Ƿ�淶",5);
        map1.put("�ֶε�ҵ������ܹ�������һ���ֶ��ж�ҵ��������������Ҫ˵��",6);
        map1.put("�����״ϵͳ�ֶ����Ͷ��������ѡ������������",7);
        map1.put("��������������ʹ�á�������ֵ���ֶΣ�����С�����λ��",8);
        map1.put("��ҵ����涨���ֶε��������ֵ�������ַ����ͣ���д��󳤶ȡ�������ֵ�����ֶΣ���д���ֵ",9);
        map1.put("��ҵ����߶����ֶε���С����ֵ�������ַ����ͣ���д��󳤶ȡ�������ֵ�����ֶΣ���д��Сֵ",10);
        map1.put("�ֶ��Ǵ����ࡢ��־�����",11);
        map1.put("�Ƿ�����Ϊ��",12);
        map1.put("�������Ϊ�գ��Թؼ�ҵ���ֶ�����ʲô��������ֵʲô������Ϊ��",13);
        map1.put("���ֶε�Ĭ��ֵ",14);
        map1.put("�Ƿ���������",15);
        map1.put("�Ƿ񸱱�����",16);
        map1.put("����Ǹ�������Ӧ�챾��ϵͳ����.�ֶ�������system.table.field",17);
        map1.put("ʶ������������Ϣ",18);
        map1.put("�������̽���з��ֵ��ض������������������",19);
        list.add(map1);
        return list;
    }

    public static Map<String,String> getField_mapping(){

        Map<String,String> map = new HashMap<>();
        map.put("systemname","ϵͳ����");
        map.put("tableName","��Ӣ����");
        map.put("columnName","�ֶ�Ӣ����");
        map.put("dataType","�ֶ�����");
        map.put("ispk","����");
        map.put("comments","������");
       /* map.put("ҵ����",6);
        map.put("��������",7);
        map.put("����",8);
        map.put("���ֵ/����",9);
        map.put("��Сֵ/����",10);
        map.put("���������",11);*/
        map.put("isnull","����Ϊ��");
        /*map.put("Ϊ�յ�����",13);*/
        map.put("defaultValue","Ĭ��ֵ");
        map.put("isMain","�Ƿ�����");
        map.put("isP","�Ƿ񸱱�");
        map.put("location","����λ��");
       /* map.put("�����ֶ�",18);
        map.put("�ض�������˵��",19);*/
        return map;
    }

    }
