/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package cn.yjh.spring_3.db;



import cn.yjh.spring_3.db.annotation.Column;

import java.util.Date;


public class ES_DOC_MAIN
{
	// @Field
	/** 单证编号 */
	private String DocID;
	/** 单证号码 */
	private String DocCode;
	/** 业务类型 */
	private String BussType;
	/** 单证细类 */
	private String SubType;
	/** 单证页数 */
	@Column(value = "numpages",type = FieldType.DOUBLE)
	private double NumPages;
	/** 单证状态 */
	private String DocFlag;
	/** 备注 */
	private String DocRemark;
	/** 扫描操作员 */
	private String ScanOperator;
	/** 管理机构 */
	private String ManageCom;
	/** 录入状态 */
	private String InputState;
	/** 操作员 */
	private String Operator;
	/** 录入开始日期 */
	@Column(value = "InputStartDate",type = FieldType.DATE)
	private Date InputStartDate;
	/** 录入开始时间 */
	private String InputStartTime;
	/** 录入结束日期 */
	@Column(value = "InputEndDate",type = FieldType.DATE)
	private Date InputEndDate;
	/** 录入结束时间 */
	private String InputEndTime;
	/** 生成日期 */
	@Column(value = "MakeDate",type = FieldType.DATE)
	private Date MakeDate;
	/** 生成时间 */
	private String MakeTime;
	/** 修改日期 */
	@Column(value = "ModifyDate",type = FieldType.DATE)
	private Date ModifyDate;
	/** 修改时间 */
	private String ModifyTime;
	/** 单证版本 */
	private String Version;
	/** 批次号 */
	private String ScanNo;
	/** 单证印刷号码 */
	private String PrintCode;
	/** 品管批次号 */
	private String PinBatchNo;
	/** 品管删除日期 */
	@Column(value = "PinDelMakeDate",type = FieldType.DATE)
	private Date PinDelMakeDate;
	/** 品管人 */
	private String PinOperator;
	/** 品管删除时间 */
	private String PinDelMakeTime;
	/** 扫描类型 */
	private String ScanType;
	/** 删除原因 */
	private String DelRemark;

	public String getDocID() {
		return DocID;
	}

	public void setDocID(String docID) {
		DocID = docID;
	}

	public String getDocCode() {
		return DocCode;
	}

	public void setDocCode(String docCode) {
		DocCode = docCode;
	}

	public String getBussType() {
		return BussType;
	}

	public void setBussType(String bussType) {
		BussType = bussType;
	}

	public String getSubType() {
		return SubType;
	}

	public void setSubType(String subType) {
		SubType = subType;
	}

	public double getNumPages() {
		return NumPages;
	}

	public void setNumPages(double numPages) {
		NumPages = numPages;
	}

	public String getDocFlag() {
		return DocFlag;
	}

	public void setDocFlag(String docFlag) {
		DocFlag = docFlag;
	}

	public String getDocRemark() {
		return DocRemark;
	}

	public void setDocRemark(String docRemark) {
		DocRemark = docRemark;
	}

	public String getScanOperator() {
		return ScanOperator;
	}

	public void setScanOperator(String scanOperator) {
		ScanOperator = scanOperator;
	}

	public String getManageCom() {
		return ManageCom;
	}

	public void setManageCom(String manageCom) {
		ManageCom = manageCom;
	}

	public String getInputState() {
		return InputState;
	}

	public void setInputState(String inputState) {
		InputState = inputState;
	}

	public String getOperator() {
		return Operator;
	}

	public void setOperator(String operator) {
		Operator = operator;
	}

	public Date getInputStartDate() {
		return InputStartDate;
	}

	public void setInputStartDate(Date inputStartDate) {
		InputStartDate = inputStartDate;
	}

	public String getInputStartTime() {
		return InputStartTime;
	}

	public void setInputStartTime(String inputStartTime) {
		InputStartTime = inputStartTime;
	}

	public Date getInputEndDate() {
		return InputEndDate;
	}

	public void setInputEndDate(Date inputEndDate) {
		InputEndDate = inputEndDate;
	}

	public String getInputEndTime() {
		return InputEndTime;
	}

	public void setInputEndTime(String inputEndTime) {
		InputEndTime = inputEndTime;
	}

	public Date getMakeDate() {
		return MakeDate;
	}

	public void setMakeDate(Date makeDate) {
		MakeDate = makeDate;
	}

	public String getMakeTime() {
		return MakeTime;
	}

	public void setMakeTime(String makeTime) {
		MakeTime = makeTime;
	}

	public Date getModifyDate() {
		return ModifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		ModifyDate = modifyDate;
	}

	public String getModifyTime() {
		return ModifyTime;
	}

	public void setModifyTime(String modifyTime) {
		ModifyTime = modifyTime;
	}

	public String getVersion() {
		return Version;
	}

	public void setVersion(String version) {
		Version = version;
	}

	public String getScanNo() {
		return ScanNo;
	}

	public void setScanNo(String scanNo) {
		ScanNo = scanNo;
	}

	public String getPrintCode() {
		return PrintCode;
	}

	public void setPrintCode(String printCode) {
		PrintCode = printCode;
	}

	public String getPinBatchNo() {
		return PinBatchNo;
	}

	public void setPinBatchNo(String pinBatchNo) {
		PinBatchNo = pinBatchNo;
	}

	public Date getPinDelMakeDate() {
		return PinDelMakeDate;
	}

	public void setPinDelMakeDate(Date pinDelMakeDate) {
		PinDelMakeDate = pinDelMakeDate;
	}

	public String getPinOperator() {
		return PinOperator;
	}

	public void setPinOperator(String pinOperator) {
		PinOperator = pinOperator;
	}

	public String getPinDelMakeTime() {
		return PinDelMakeTime;
	}

	public void setPinDelMakeTime(String pinDelMakeTime) {
		PinDelMakeTime = pinDelMakeTime;
	}

	public String getScanType() {
		return ScanType;
	}

	public void setScanType(String scanType) {
		ScanType = scanType;
	}

	public String getDelRemark() {
		return DelRemark;
	}

	public void setDelRemark(String delRemark) {
		DelRemark = delRemark;
	}
}
