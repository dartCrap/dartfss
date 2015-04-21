package com.dartcrab.entities;

import java.sql.Date;
import java.util.List;
import java.util.ArrayList;

import javax.persistence.*;

import com.dartcrab.reports.ReportHeader;

@Entity
@Table
@DiscriminatorValue("SO")
public class StockOptionExecReport extends GenericDartReport{
	
	@Entity
	private static class ExecDetail{

		@SuppressWarnings("unused")
		public ExecDetail() {
			super();
		}

		@Id
		@GeneratedValue(strategy=GenerationType.TABLE)
		Long	execId;
		
		String	rcpNo;

		Date	execDate;			// 행사일
		String	ownerName;			// 행사자명/법인명
		String	relationship;		// 회사와의관계
		float	execPrice;			// 행사가
		float	facePrice;			// 액면가
		int treasuryStockDistributedUnits; 	// 자기주식교부주식수
		int newSharesDistributedUnits;		// 신규발행교부주식수
		int cashSettlePaidUnits;				// 차액보상주식
		
		protected ExecDetail(Date execDate, String ownerName, String relationship,
				float execPrice, float facePrice,
				int treasuryStockDistributedUnits,
				int newSharesDistributedUnits, int cashSettlePaidUnits) {
			super();
			this.execDate = execDate;
			this.ownerName = ownerName;
			this.relationship = relationship;
			this.execPrice = execPrice;
			this.facePrice = facePrice;
			this.treasuryStockDistributedUnits = treasuryStockDistributedUnits;
			this.newSharesDistributedUnits = newSharesDistributedUnits;
			this.cashSettlePaidUnits = cashSettlePaidUnits;
		}

		@Override
		public String toString() {
			return "ExecDetail [execDate=" + execDate + ", ownerName="
					+ ownerName + ", relationship=" + relationship
					+ ", execPrice=" + execPrice + ", facePrice=" + facePrice
					+ ", treasuryStockDistributedUnits="
					+ treasuryStockDistributedUnits
					+ ", newSharesDistributedUnits="
					+ newSharesDistributedUnits + ", cashSettlePaidUnits="
					+ cashSettlePaidUnits + "]";
		}
	
	};
	
	private int	totalIssueUnits; // 총발행주식수
	private int treasuryStockDistributedUnits; 	// 자기주식교부주식수
	private int newSharesDistributedUnits;		// 신규발행교부주식수
	private int cashSettlePaidUnits;				// 차액보상주식
	private float execRatio;					// 행사비율
	
	private int treasuryStockRemainingUnits; 	// 잔여 자기주식교부주식수
	private int newSharesRemainingUnits;		// 잔여 신규발행교부주식수
	private int cashSettleRemainingUnits;		// 잔여 차액보상주식
	
	@OneToMany(mappedBy="rcpNo")
	private List<ExecDetail> execDetailList;	// 일별행사내역
	
	@OneToMany(mappedBy="rcpNo")
	private List<ReportHeader>	relatedReports; // 관련공시 보고서번호
	
	
	public StockOptionExecReport(ReportHeader header) {
		super(header);
		// TODO Auto-generated constructor stub
		this.relatedReports = new ArrayList<ReportHeader>();
		this.execDetailList = new ArrayList<ExecDetail>();
	}
	
	

	public StockOptionExecReport addExecDetail(
			Date	execDate,			// 행사일
			String	ownerName,			// 행사자명/법인명
			String	relationship,		// 회사와의관계
			float	execPrice,			// 행사가
			float	facePrice,			// 액면가
			int treasuryStockDistributedUnits, 	// 자기주식교부주식수
			int newSharesDistributedUnits,		// 신규발행교부주식수
			int cashSettlePaidUnits				// 차액보상주식
		){
		this.execDetailList.add( new ExecDetail (
						execDate,			// 행사일
						ownerName,			// 행사자명/법인명
						relationship,		// 회사와의관계
						execPrice,			// 행사가
						facePrice,			// 액면가
						treasuryStockDistributedUnits, 	// 자기주식교부주식수
						newSharesDistributedUnits,		// 신규발행교부주식수
						cashSettlePaidUnits	
					)
				);
		return this;
	}

	public int getTotalIssueUnits() {
		return totalIssueUnits;
	}

	public void setTotalIssueUnits(int totalIssueUnits) {
		this.totalIssueUnits = totalIssueUnits;
	}

	public int getTreasuryStockDistributedUnits() {
		return treasuryStockDistributedUnits;
	}

	public void setTreasuryStockDistributedUnits(int treasuryStockDistributedUnits) {
		this.treasuryStockDistributedUnits = treasuryStockDistributedUnits;
	}

	public int getNewSharesDistributedUnits() {
		return newSharesDistributedUnits;
	}

	public void setNewSharesDistributedUnits(int newSharesDistributedUnits) {
		this.newSharesDistributedUnits = newSharesDistributedUnits;
	}

	public int getCashSettlePaidUnits() {
		return cashSettlePaidUnits;
	}

	public void setCashSettlePaidUnits(int cashSettlePaidUnits) {
		this.cashSettlePaidUnits = cashSettlePaidUnits;
	}

	public float getExecRatio() {
		return execRatio;
	}

	public void setExecRatio(float execRatio) {
		this.execRatio = execRatio;
	}

	public int getNewSharesRemainingUnits() {
		return newSharesRemainingUnits;
	}

	public void setNewSharesRemainingUnits(int newSharesRemainingUnits) {
		this.newSharesRemainingUnits = newSharesRemainingUnits;
	}

	public int getCashSettleRemainingUnits() {
		return cashSettleRemainingUnits;
	}

	public void setCashSettleRemainingUnits(int cashSettleRemainingUnits) {
		this.cashSettleRemainingUnits = cashSettleRemainingUnits;
	}

	public List<ExecDetail> getExecDetailList() {
		return execDetailList;
	}

	public void setExecDetailList(List<ExecDetail> execDetailList) {
		this.execDetailList = execDetailList;
	}

	public List<ReportHeader> getRelatedReports() {
		return relatedReports;
	}

	public void setRelatedReports(List<ReportHeader> relatedReports) {
		this.relatedReports = relatedReports;
	}


	
	public int getTreasuryStockRemainingUnits() {
		return treasuryStockRemainingUnits;
	}

	
	public StockOptionExecReport() {
		super();
	}



	public void setTreasuryStockRemainingUnits(int treasuryStockRemainingUnits) {
		this.treasuryStockRemainingUnits = treasuryStockRemainingUnits;
	}

	
	@Override
	public String toString() {
		return "StockOptionExecReport [totalIssueUnits=" + totalIssueUnits
				+ ", treasuryStockDistributedUnits="
				+ treasuryStockDistributedUnits
				+ ", newSharesDistributedUnits=" + newSharesDistributedUnits
				+ ", cashSettlePaidUnits=" + cashSettlePaidUnits
				+ ", execRatio=" + execRatio + ", treasuryStockRemainingUnits="
				+ treasuryStockRemainingUnits + ", newSharesRemainingUnits="
				+ newSharesRemainingUnits + ", cashSettleRemainingUnits="
				+ cashSettleRemainingUnits + ", execDetailList="
				+ execDetailList + ", relatedReports=" + relatedReports + "]";
	}
	
	
}
