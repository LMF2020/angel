package com.starit.common.dao.support;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


@SuppressWarnings("serial")
public class Pagination<E> implements Serializable, Iterable<E> {

	protected List<E> rows;

	private int offset;
	
	protected int limit;
	
	protected long currPage;

	protected long totalPages;

	protected long totalRecords = 0;

	public Pagination(long totalPages, int offset, int limit, long totalRecords) {
		this(totalPages, offset, limit, totalRecords, new ArrayList<E>(0));
	}

	public Pagination(long totalPages, int offset, int limit, long totalRecords,List<E> result) {
		this((offset / limit) + 1, totalPages, offset, limit, totalRecords, result);
	}
	
	public Pagination(int currPage, long totalPages, int offset, int limit, long totalRecords,List<E> result) {
		if(limit <= 0) throw new IllegalArgumentException("[pageSize] must great than zero");
		this.limit = limit;
		this.offset = offset;
		this.currPage = currPage;
		
		if(totalPages == 0)
			this.totalPages = 1;
		else
			this.totalPages = totalPages;
		
		this.totalRecords = totalRecords;
		setResult(result);
	}

	public void setResult(List<E> elements) {
		if (elements == null)
			throw new IllegalArgumentException("'result' must be not null");
		this.rows = elements;
	}
	
	@JSONField(serialize=false)
	public int getOffset() {
		return offset;
	}

	@JSONField(serialize=false)
	public int getLimit() {
		return limit;
	}
	
	@JSONField(name="rows")
	public List<E> getResult() {
		return rows;
	}

	@JSONField(serialize=false)
	public long getCurrPage() {
		return currPage;
	}

	@JSONField(serialize=false)
	public long getTotalPages() {
		return totalPages;
	}

	@JSONField(name="total")
	public long getTotalRecords() {
		return totalRecords;
	}
	
	@SuppressWarnings("unchecked")
	public Iterator<E> iterator() {
		return (Iterator<E>) (rows == null ? Collections.emptyList().iterator() : rows.iterator());
	}
}
