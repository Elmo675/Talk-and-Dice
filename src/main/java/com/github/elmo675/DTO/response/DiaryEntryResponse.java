package com.github.elmo675.DTO.response;

import com.github.elmo675.model.Accessibility;
import lombok.Data;

import java.util.Date;

@Data
public class DiaryEntryResponse {
    private long id;
    private String content;
    private Accessibility acces;
    private Date createdAt;
    private String createdBy;
    private Date updatedAt;
    private String updatedBy;
}
