package com.github.elmo675.DTO.Response;

import com.github.elmo675.model.Accessibility;
import lombok.Builder;
import lombok.Data;
import lombok.With;

import java.util.Date;
@Data
@With
@Builder
public class SessionResponse {
    private long id;
    private String content;
    private Accessibility access;
    private Date createdAt;
    private String createdBy;
    private Date updatedAt;
    private String updatedBy;
}
