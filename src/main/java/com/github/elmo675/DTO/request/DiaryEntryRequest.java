package com.github.elmo675.DTO.request;

import com.github.elmo675.model.Accessibility;
import lombok.Data;

@Data
public class DiaryEntryRequest {
    private String content;
    private Accessibility access;
    private String createdBy;
}
