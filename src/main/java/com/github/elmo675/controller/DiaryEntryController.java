package com.github.elmo675.controller;


import com.github.elmo675.exception.ResourceNotFoundException;
import com.github.elmo675.model.Accessibility;
import com.github.elmo675.model.DiaryEntry;
import com.github.elmo675.repository.DiaryEntryRepository;
import com.github.elmo675.DTO.response.*;
import com.github.elmo675.DTO.request.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.text.ParseException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class DiaryEntryController {

    @Autowired
    private DiaryEntryRepository DiaryEntryRepository;

    @Autowired
    ModelMapper modelMapper = new ModelMapper();

    private DiaryEntryResponse convertDiaryEntryToResponse(DiaryEntry diaryEntry) {
        DiaryEntryResponse DTO = modelMapper.map(diaryEntry, DiaryEntryResponse.class);
        DTO.setId(diaryEntry.getId());
        DTO.setAcces(diaryEntry.getAccess());
        DTO.setContent(diaryEntry.getContent());
        DTO.setCreatedBy(diaryEntry.getCreatedBy());
        DTO.setCreatedAt(diaryEntry.getCreatedAt());
        DTO.setUpdatedAt(diaryEntry.getUpdatedAt());
        DTO.setUpdatedBy(diaryEntry.getUpdatedBy());
        return DTO;
    }

    private DiaryEntryRequest convertDiaryEntryToRequest(DiaryEntry diaryEntry) {
        DiaryEntryRequest DTO = modelMapper.map(diaryEntry, DiaryEntryRequest.class);
        DTO.setAccess(diaryEntry.getAccess());
        DTO.setContent(diaryEntry.getContent());
        DTO.setCreatedBy(diaryEntry.getCreatedBy());
        return DTO;
    }

    private DiaryEntry convertResponseToDiaryEntry(DiaryEntryResponse diaryEntryResponse) throws ParseException {
        DiaryEntry diaryEntry = modelMapper.map(diaryEntryResponse, DiaryEntry.class);
        diaryEntry.setId(diaryEntryResponse.getId());
        diaryEntry.setContent(diaryEntryResponse.getContent());
        diaryEntry.setAccess(diaryEntryResponse.getAcces());
        diaryEntry.setUpdatedBy(diaryEntryResponse.getUpdatedBy());
        diaryEntry.setUpdatedAt(diaryEntryResponse.getUpdatedAt());
        diaryEntry.setCreatedAt(diaryEntryResponse.getCreatedAt());
        diaryEntry.setCreatedBy(diaryEntryResponse.getCreatedBy());
        return diaryEntry;
    }
    private DiaryEntry convertRequestToDiaryEntry(DiaryEntryRequest diaryEntryRequest) throws ParseException {
        DiaryEntry diaryEntry = modelMapper.map(diaryEntryRequest, DiaryEntry.class);
        //DiaryEntry diaryEntry = new DiaryEntry();
        diaryEntry.setContent(diaryEntryRequest.getContent());
        diaryEntry.setAccess(diaryEntryRequest.getAccess());
        if(diaryEntry.getCreatedBy() != null){
            diaryEntry.setUpdatedBy(diaryEntryRequest.getCreatedBy());
            diaryEntry.setUpdatedAt(new Date());
        }
        else{
            Date date = new Date();
            diaryEntry.setCreatedBy(diaryEntryRequest.getCreatedBy());
            diaryEntry.setUpdatedBy(diaryEntryRequest.getCreatedBy());
            diaryEntry.setCreatedAt(date);
            diaryEntry.setUpdatedAt(date);
        }
        return diaryEntry;
    }





    @GetMapping("/entry")
    public List<DiaryEntryResponse> getAllDiaryEntries() {
        List <DiaryEntry> entryList = DiaryEntryRepository.findAll();
        return entryList.stream().map(this::convertDiaryEntryToResponse).collect(Collectors.toList());
    }


    @GetMapping("/entry/{id}")
    public ResponseEntity<DiaryEntryResponse> getDiaryEntryById(@PathVariable(value = "id") Long diaryEntryId)
            throws ResourceNotFoundException {
        DiaryEntry diaryEntry =
                DiaryEntryRepository
                        .findById(diaryEntryId)
                        .orElseThrow(() -> new ResourceNotFoundException("DiaryEntry not found on :: " + diaryEntryId));
        return ResponseEntity.ok().body(convertDiaryEntryToResponse(diaryEntry));
    }

    @PostMapping("/entry")
    public DiaryEntryResponse createDiaryEntry(@Valid @RequestBody DiaryEntryRequest diaryEntryRequest) {
        try {
            DiaryEntry result = convertRequestToDiaryEntry(diaryEntryRequest);
            return convertDiaryEntryToResponse(DiaryEntryRepository.save(result));
        }
        catch (ParseException e){
            return null;
        }
    }
    @PutMapping("/entry/{id}")
    public ResponseEntity<DiaryEntryResponse> updateDiaryEntry(
            @PathVariable(value = "id") Long diaryEntryId, @Valid @RequestBody DiaryEntryRequest diaryEntryRequest)
            throws ResourceNotFoundException {

        DiaryEntry diaryEntry =
                DiaryEntryRepository
                        .findById(diaryEntryId)
                        .orElseThrow(() -> new ResourceNotFoundException("DiaryEntry not found on :: " + diaryEntryId));

        diaryEntry.setContent(diaryEntryRequest.getContent());
        diaryEntry.setAccess(diaryEntryRequest.getAccess());
        diaryEntry.setUpdatedBy(diaryEntryRequest.getCreatedBy());
        diaryEntry.setUpdatedAt(new Date());
        final DiaryEntry updatedDiaryEntry = DiaryEntryRepository.save(diaryEntry);
        return ResponseEntity.ok(convertDiaryEntryToResponse(updatedDiaryEntry));
    }
    @DeleteMapping("/entry/{id}")
    public Map<String, Boolean> deleteDiaryEntry(@PathVariable(value = "id") Long DiaryEntryId) throws Exception {
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
