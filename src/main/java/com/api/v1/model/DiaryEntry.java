package com.api.v1.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "DiaryEntrys")
@EntityListeners(AuditingEntityListener.class)
public class DiaryEntry {
    enum publicity{Public,Friends,Private}
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;


    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "privacy", nullable = false)
    private publicity privacy;


    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @CreatedBy
    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

    @LastModifiedBy
    @Column(name = "updated_by", nullable = false)
    private String updatedBy;

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getContent(){ return content; }
    public void   setContent(String content){this.content = content;}

    public publicity getPrivacy(){ return privacy; }
    public void   setPrivacy(publicity privacy){this.privacy=privacy;}

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt){ this.createdAt=createdAt;}

    public String getCreatedBy(){ return createdBy; }
    public void   setCreatedBy(String createdBy){ this.createdBy=createdBy;}

    public Date getUpdatedAt(){ return updatedAt; }
    public void setUpdatedAt(Date updatedAt){this.updatedAt=updatedAt;}

    public String getUpdatedBy(){ return updatedBy;}
    public void   setUpdatedBy(String updatedBy){this.updatedBy=updatedBy;}

    @Override
    public String toString() {
        return "DiaryEntry{" +
                "id=" + id +
                ", data='" + content + '\'' +
                ", privacy='" + privacy + '\'' +
                ", createdAt=" + createdAt +
                ", createdBy='" + createdBy + '\'' +
                ", updatedAt=" + updatedAt +
                ", updatedby='" + updatedBy + '\'' +
                '}';
    }
}
