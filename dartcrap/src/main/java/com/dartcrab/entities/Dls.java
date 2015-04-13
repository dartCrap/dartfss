package com.dartcrab.entities;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.persistence.*;

/**
 * DLS 정보를 담기위한 별도 Entity
 * 
 * 주의: 이 객체는 GenericDartReport와는 무관하다. GenericDartReport는 공시 보고서 자체를 모델링하지만
 * 본 Class는 보고서 내에 포함되어 있는 개별 종목정보를 모델링한다.
 * 
 * 앞으로 정보의 상세화와 로직 개발이 많이 필요하다.
 * 
 * @author Gi Kim
 * @since Mar-25-2015	
 * @version 0.1
 */

@Entity
@Table
public class Dls {
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private	String			instTitle;				// 종목명
	private String[]		underlying; 			// 기초자산
	
	private String			curcny;					// 통화		
	private long			totalIssueAmt;			// 모집총액
	private int				unitParPrice;			// 단위액면가격
	private	int				unitIssuePrice;			// 단위발행가격
	private	long			totalIssueUnits;		// 발행수량

	private Date			subscriptionStrtDt;		// 청약시작일
	private Date			subscriptionEndDt;		// 청약종료일
	private Date			subscriptionSttlDt;		// 청약납입일
	private Date			subscriptionDlvrDt;		// 입고일 (배정일 및 환불일)
	private Date			issueDt;				// 발행일
	
	
	private String			depository;				// 예탁기관
	private	String			listedExchange;			// 거래소
	
	private String			earlyRedemptionSttlMethod; // 조기상환 결제방법
	
	private Date			maturityDt;				// 만기일
	private Date[]			maturityEvalDt;			// 만기평가일 --> 3일 평균으로 하는 경우가 있으므로 List 처리
	private String			maturitySettlMethod;	// 만기결제방법
	private Date			maturitySttlDt;			// 만기상환금액지급일
	private String			hedgeTrader;			// 헷지운용사
	
	private	String			dlsClass;				// DLS 상품종류 ==> TO-BE
	
	@Entity
	@Table
	public class RedemptionSchedule {  //--> To-do
		@Id
		@GeneratedValue(strategy=GenerationType.TABLE)
		int		redemptionId;							//temp
		String	redemptionTriggerClass;		//temp
		String	provisionText;				//temp
		float	yield;						//temp
		
		@Override
		public String toString() {
			return "RedemptionSchedule [redemptionTriggerClass="
					+ redemptionTriggerClass + ", provisionText="
					+ provisionText + ", yield=" + yield + "]";
		}

		public RedemptionSchedule(int id, String redemptionTriggerClass,
				String provisionText, float yield) {
			super();
			this.id = id;
			this.redemptionTriggerClass = redemptionTriggerClass;
			this.provisionText = provisionText;
			this.yield = yield;
		}
		
	};
	
	@OneToMany(mappedBy="redemptionId")
	@OrderBy("redemptionId")
	private	List<RedemptionSchedule>  redemptionSchedule;	// temp 상환스케
	
	private	Date[]			underlyingEvalDt;		// 기준가격결정일
	private	float[]			exercisePriceRate;		// 행사가격
	private	float[]			exercisePriceRateWithCare;	// 케어(?) 발생 시 행사가격 --> to be revised
	private float			maxProfitYield;			// 최대이익
	private float			matLossYield;			// 최대손실
	
	private int				earlyRedemptionSttlDays;// 평가일로부터 조기상환액 지급일까지의 기간		
	
	
	public String getInstTitle() {
		return instTitle;
	}
	public void setInstTitle(String instTitle) {
		this.instTitle = instTitle;
	}
	public String[] getUnderlying() {
		return underlying;
	}
	public void setUnderlying(String[] underlying) {
		this.underlying = underlying;
	}
	public String getCurcny() {
		return curcny;
	}
	public void setCurcny(String curcny) {
		this.curcny = curcny;
	}
	public long getTotalIssueAmt() {
		return totalIssueAmt;
	}
	public void setTotalIssueAmt(long totalIssueAmt) {
		this.totalIssueAmt = totalIssueAmt;
	}
	public int getUnitParPrice() {
		return unitParPrice;
	}
	public void setUnitParPrice(int unitParPrice) {
		this.unitParPrice = unitParPrice;
	}
	public int getUnitIssuePrice() {
		return unitIssuePrice;
	}
	public void setUnitIssuePrice(int unitIssuePrice) {
		this.unitIssuePrice = unitIssuePrice;
	}
	public long getTotalIssueUnits() {
		return totalIssueUnits;
	}
	public void setTotalIssueUnits(int totalIssueUnits) {
		this.totalIssueUnits = totalIssueUnits;
	}
	public Date getSubscriptionStrtDt() {
		return subscriptionStrtDt;
	}
	public void setSubscriptionStrtDt(Date subscriptionStrtDt) {
		this.subscriptionStrtDt = subscriptionStrtDt;
	}
	public Date getSubscriptionEndDt() {
		return subscriptionEndDt;
	}
	public void setSubscriptionEndDt(Date subscriptionEndDt) {
		this.subscriptionEndDt = subscriptionEndDt;
	}
	public Date getSubscriptionSttlDt() {
		return subscriptionSttlDt;
	}
	public void setSubscriptionSttlDt(Date subscriptionSttlDt) {
		this.subscriptionSttlDt = subscriptionSttlDt;
	}
	public Date getSubscriptionDlvrDt() {
		return subscriptionDlvrDt;
	}
	public void setSubscriptionDlvrDt(Date subscriptionDlvrDt) {
		this.subscriptionDlvrDt = subscriptionDlvrDt;
	}
	public Date getIssueDt() {
		return issueDt;
	}
	public void setIssueDt(Date issueDt) {
		this.issueDt = issueDt;
	}
	public String getDepository() {
		return depository;
	}
	public void setDepository(String depository) {
		this.depository = depository;
	}
	public String getListedExchange() {
		return listedExchange;
	}
	public void setListedExchange(String listedExchange) {
		this.listedExchange = listedExchange;
	}
	public Date getMaturityDt() {
		return maturityDt;
	}
	public void setMaturityDt(Date maturityDt) {
		this.maturityDt = maturityDt;
	}
	public Date[] getMaturityEvalDt() {
		return maturityEvalDt;
	}
	public void setMaturityEvalDt(Date [] maturityEvalDt) {
		this.maturityEvalDt = maturityEvalDt;
	}
	public String getMaturitySettlMethod() {
		return maturitySettlMethod;
	}
	public void setMaturitySettlMethod(String maturitySettlMethod) {
		this.maturitySettlMethod = maturitySettlMethod;
	}
	public String getHedgeTrader() {
		return hedgeTrader;
	}
	public void setHedgeTrader(String hedgeTrader) {
		this.hedgeTrader = hedgeTrader;
	}
	public String getDlsClass() {
		return dlsClass;
	}
	public void setDlsClass(String dlsClass) {
		this.dlsClass = dlsClass;
	}
	public Date[] getUnderlyingEvalDt() {
		return underlyingEvalDt;
	}
	public void setUnderlyingEvalDt(Date[] underlyingEvalDt) {
		this.underlyingEvalDt = underlyingEvalDt;
	}
	public float[] getExercisePriceRate() {
		return exercisePriceRate;
	}
	public void setExercisePriceRate(float[] exercisePriceRate) {
		this.exercisePriceRate = exercisePriceRate;
	}
	public float[] getExercisePriceRateWithCare() {
		return exercisePriceRateWithCare;
	}
	public void setExercisePriceRateWithCare(float[] exercisePriceRateWithCare) {
		this.exercisePriceRateWithCare = exercisePriceRateWithCare;
	}
	public float getMaxProfitYield() {
		return maxProfitYield;
	}
	public void setMaxProfitYield(float maxProfitYield) {
		this.maxProfitYield = maxProfitYield;
	}
	public float getMatLossYield() {
		return matLossYield;
	}
	public void setMatLossYield(float matLossYield) {
		this.matLossYield = matLossYield;
	}
	public int getEarlyRedemptionSttlDays() {
		return earlyRedemptionSttlDays;
	}
	public void setEarlyRedemptionSttlDays(int earlyRedemptionSttlDays) {
		this.earlyRedemptionSttlDays = earlyRedemptionSttlDays;
	}	

	public String getEarlyRedemptionSttlMethod() {
		return earlyRedemptionSttlMethod;
	}
	public void setEarlyRedemptionSttlMethod(String earlyRedemptionSttlMethod) {
		this.earlyRedemptionSttlMethod = earlyRedemptionSttlMethod;
	}
	public Date getMaturitySttlDt() {
		return maturitySttlDt;
	}
	public void setMaturitySttlDt(Date maturitySttlDt) {
		this.maturitySttlDt = maturitySttlDt;
	}
	
	public List<RedemptionSchedule>  getRedemptionSchedule() {
		return redemptionSchedule;
	}
	
	public void addRedemptionSchedule(
			int		id							//temp
			,String	redemptionTriggerClass		//temp
			,String	provisionText				//temp
			,float	yield
			) {
		if (this.redemptionSchedule == null) this.redemptionSchedule = new ArrayList<RedemptionSchedule>();
		
		this.redemptionSchedule.add(
				new RedemptionSchedule(
						id, redemptionTriggerClass,provisionText,yield
					)
				);
	}
	@Override
	public String toString() {
		return "Dls [instTitle=" + instTitle + ", underlying="
				+ Arrays.toString(underlying) + ", curcny=" + curcny
				+ ", totalIssueAmt=" + totalIssueAmt + ", unitParPrice="
				+ unitParPrice + ", unitIssuePrice=" + unitIssuePrice
				+ ", totalIssueUnits=" + totalIssueUnits
				+ ", subscriptionStrtDt=" + subscriptionStrtDt
				+ ", subscriptionEndDt=" + subscriptionEndDt
				+ ", subscriptionSttlDt=" + subscriptionSttlDt
				+ ", subscriptionDlvrDt=" + subscriptionDlvrDt + ", issueDt="
				+ issueDt + ", depository=" + depository + ", listedExchange="
				+ listedExchange + ", earlyRedemptionSttlMethod="
				+ earlyRedemptionSttlMethod + ", maturityDt=" + maturityDt
				+ ", maturityEvalDt=" + Arrays.toString(maturityEvalDt)
				+ ", maturitySettlMethod=" + maturitySettlMethod
				+ ", maturitySttlDt=" + maturitySttlDt + ", hedgeTrader="
				+ hedgeTrader + ", dlsClass=" + dlsClass
				+ ", redemptionSchedule=" + redemptionSchedule
				+ ", underlyingEvalDt=" + Arrays.toString(underlyingEvalDt)
				+ ", exercisePriceRate=" + Arrays.toString(exercisePriceRate)
				+ ", exercisePriceRateWithCare="
				+ Arrays.toString(exercisePriceRateWithCare)
				+ ", maxProfitYield=" + maxProfitYield + ", matLossYield="
				+ matLossYield + ", earlyRedemptionSttlDays="
				+ earlyRedemptionSttlDays + "]";
	}
}
