package com.dartcrab.entities;

import java.sql.Date;

public class Dls {
	private	String			instTitle;				// 종목명
	private String[]		underlying; 			// 기초자산
	
	private String			curcny;					// 통화		
	private int				totalIssueAmt;			// 모집총액
	private int				unitParPrice;			// 단위액면가격
	private	int				unitIssuePrice;			// 단위발행가격
	private	int				totalIssueUnits;		// 발행수량

	private Date			subscriptionStrtDt;		// 청약시작일
	private Date			subscriptionEndDt;		// 청약종료일
	private Date			subscriptionSttlDt;		// 청약납입일
	private Date			subscriptionDlvrDt;		// 입고일 (배정일 및 환불일)
	private Date			issueDt;				// 발행일
	
	private String			depository;				// 예탁기관
	private	String			listedExchange;			// 거래소
	
	private Date			maturityDt;				// 만기일
	private Date			maturityEvalDt;			// 만기평가일
	private Date			maturitySettlMethod;	// 만기결제방법
	
	private String			hedgeTrader;			// 헷지운용사
	
	
	private	String			dlsClass;				// DLS 상품종류 ==> TO-BE
	/* Option strucure related something... */
	private	Date[]			underlyingEvalDt;		// 기준가격결정일
	private	float[]			exercisePriceRate;		// 행사가격
	private	float[]			exercisePriceRateWithCare;	// 케어(?) 발생 시 행사가격 --> to be revised
	private float			maxProfitYield;			// 최대이익
	private float			matLossYield;			// 최대손실
	
	private int				earlyRedemptionSttlDays;// 평가일로부터 조기상환액 지급일까지의 기간		
	private int				maturitySttlDays;	// 평가일로부터 조기상환액 지급일까지의 기간
	
}
