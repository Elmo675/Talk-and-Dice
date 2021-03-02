package com.api.v1.controller;


import com.api.v1.exception.ResourceNotFoundException;
import com.api.v1.model.DiaryEntry;
import com.api.v1.repository.DiaryEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")

public class DiaryEntryController {

    @Autowired
    private DiaryEntryRepository DiaryEntryRepository;

    @GetMapping("/entry")
    public List<DiaryEntry> getAllDiaryEntrys() {
        return DiaryEntryRepository.findAll();
    }

    @GetMapping("/entry/{id}")
    public ResponseEntity<DiaryEntry> getDiaryEntryById(@PathVariable(value = "id") Long DiaryEntryId)
            throws ResourceNotFoundException {
        DiaryEntry DiaryEntry =
                DiaryEntryRepository
                        .findById(DiaryEntryId)
                        .orElseThrow(() -> new ResourceNotFoundException("DiaryEntry not found on :: " + DiaryEntryId));
        return ResponseEntity.ok().body(DiaryEntry);
    }
    @PostMapping("/entry")
    public DiaryEntry createDiaryEntry(@Valid @RequestBody DiaryEntry DiaryEntry) {
        return DiaryEntryRepository.save(DiaryEntry);
    }
    @PutMapping("/entry/{id}")
    public ResponseEntity<DiaryEntry> updateDiaryEntry(
            @PathVariable(value = "id") Long DiaryEntryId, @Valid @RequestBody DiaryEntry DiaryEntryDetails)
            throws ResourceNotFoundException {

        DiaryEntry DiaryEntry =
                DiaryEntryRepository
                        .findById(DiaryEntryId)
                        .orElseThrow(() -> new ResourceNotFoundException("DiaryEntry not found on :: " + DiaryEntryId));

        DiaryEntry.setContent(DiaryEntryDetails.getContent());
        DiaryEntry.setPrivacy(DiaryEntryDetails.getPrivacy());
        DiaryEntry.setUpdatedAt(new Date());
        final DiaryEntry updatedDiaryEntry = DiaryEntryRepository.save(DiaryEntry);
        return ResponseEntity.ok(updatedDiaryEntry);
    }
    @DeleteMapping("/DiaryEntrys/{id}")
    public Map<String, Boolean> deleteUser(@PathVariable(value = "id") Long DiaryEntryId) throws Exception {
        DiaryEntry DiaryEntry =
                DiaryEntryRepository
                        .findById(DiaryEntryId)
                        .orElseThrow(() -> new ResourceNotFoundException("DiaryEntry not found on :: " + DiaryEntryId));

        DiaryEntryRepository.delete(DiaryEntry);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
