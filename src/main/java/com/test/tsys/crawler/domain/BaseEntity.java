package com.test.tsys.crawler.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
@MappedSuperclass
public class BaseEntity implements Serializable{


	private static final long serialVersionUID = -8345577891477643579L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id = null;
 
    @Version
    @Column(name = "version")
    private int version = 0;
  
    public Long getId()
    {
        return this.id;
    }
 
    @SuppressWarnings("unused")
	private void setId(final Long id)
    {
        this.id = id;
    }
 
    public int getVersion()
    {
        return this.version;
    }
 
    @SuppressWarnings("unused")
	private void setVersion(final int version)
    {
        this.version = version;
    }
 
}
