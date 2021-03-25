package cn.yjh.spring_3.core;

public enum CommonType {

    NO("0","否"),
    YES("1","是"),
    QUERY_DOC_YES("8001200","操作成功"),
    QUERY_DOC_NO("8001201","操作失败"),
    NODE_NOT_NULL("8000101","报文体part节点不能为空"),
    BUSSNO_NOT_NULL("8000102","orderNo节点不能为空"),
    DOC_NOT_NULL("8000103","docs节点不能为空"),
    ORDER_NOT_NULL("8000104","bussNo节点不能为空"),
    BUSSNOTYPE_NOT_NULL("8000105","bussNo节点不能为空"),
    NOT_FOUND_DATA("8000106","未查询到数据");

    private String code;
    private String msg;

    CommonType(String code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public static CommonType getTypeByCode(String code){
        CommonType target = null;
        CommonType[] values = values();
        for(CommonType value : values){
            if (code.equals(value.code)) {
               target = value;
               break;
            }
        }
        return target;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
