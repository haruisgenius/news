package com.example.news.constants;

import java.time.LocalDate;
import java.time.LocalDateTime;

public enum Constant {
	
	Today(LocalDate.now()),
	Now(LocalDateTime.now()),
	OpenDefault(false),
	defaultNewsAmount(0);
	
	private LocalDate date;
	
	private LocalDateTime time;
	
	private Boolean openFalse;
	
	private int defaultAmount;
	
	// -------------------------------------

	private Constant(LocalDate date) {
		this.date = date;
	}

	private Constant(LocalDateTime time) {
		this.time = time;
	}

	private Constant(java.lang.Boolean openFalse) {
		this.openFalse = openFalse;
	}

	private Constant(int defaultAmount) {
		this.defaultAmount = defaultAmount;
	}

	// -------------------------------------

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	public Boolean getOpenFalse() {
		return openFalse;
	}

	public void setOpenFalse(Boolean openFalse) {
		this.openFalse = openFalse;
	}

	public int getDefaultAmount() {
		return defaultAmount;
	}

	public void setDefaultAmount(int defaultAmount) {
		this.defaultAmount = defaultAmount;
	}
	
	

}
