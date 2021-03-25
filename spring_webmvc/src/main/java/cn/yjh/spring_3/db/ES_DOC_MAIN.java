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
	/** ��֤��� */
	private String DocID;
	/** ��֤���� */
	private String DocCode;
	/** ҵ������ */
	private String BussType;
	/** ��֤ϸ�� */
	private String SubType;
	/** ��֤ҳ�� */
	@Column(value = "numpages",type = FieldType.DOUBLE)
	private double NumPages;
	/** ��֤״̬ */
	private String DocFlag;
	/** ��ע */
	private String DocRemark;
	/** ɨ�����Ա */
	private String ScanOperator;
	/** ������� */
	private String ManageCom;
	/** ¼��״̬ */
	private String InputState;
	/** ����Ա */
	private String Operator;
	/** ¼�뿪ʼ���� */
	@Column(value = "InputStartDate",type = FieldType.DATE)
	private Date InputStartDate;
	/** ¼�뿪ʼʱ�� */
	private String InputStartTime;
	/** ¼��������� */
	@Column(value = "InputEndDate",type = FieldType.DATE)
	private Date InputEndDate;
	/** ¼�����ʱ�� */
	private String InputEndTime;
	/** �������� */
	@Column(value = "MakeDate",type = FieldType.DATE)
	private Date MakeDate;
	/** ����ʱ�� */
	private String MakeTime;
	/** �޸����� */
	@Column(value = "ModifyDate",type = FieldType.DATE)
	private Date ModifyDate;
	/** �޸�ʱ�� */
	private String ModifyTime;
	/** ��֤�汾 */
	private String Version;
	/** ���κ� */
	private String ScanNo;
	/** ��֤ӡˢ���� */
	private String PrintCode;
	/** Ʒ�����κ� */
	private String PinBatchNo;
	/** Ʒ��ɾ������ */
	@Column(value = "PinDelMakeDate",type = FieldType.DATE)
	private Date PinDelMakeDate;
	/** Ʒ���� */
	private String PinOperator;
	/** Ʒ��ɾ��ʱ�� */
	private String PinDelMakeTime;
	/** ɨ������ */
	private String ScanType;
	/** ɾ��ԭ�� */
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
