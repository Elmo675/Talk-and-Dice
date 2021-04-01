package com.github.elmo675.DTO.Request;

import com.github.elmo675.model.Accessibility;
import lombok.Builder;
import lombok.Data;
import lombok.With;

@Data
@With
@Builder
public class SessionRequest {
    private String content;
    private Accessibility access;
    private String author;
}
