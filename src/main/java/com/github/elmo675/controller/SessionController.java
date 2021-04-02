package com.github.elmo675.controller;


import com.github.elmo675.DTO.Request.SessionRequest;
import com.github.elmo675.DTO.Response.SessionResponse;
import com.github.elmo675.exception.ResourceNotFoundException;
import com.github.elmo675.model.Session;
import com.github.elmo675.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")

public class SessionController {

    @Autowired
    private SessionRepository SessionRepository;

    public SessionResponse convertSessionToResponse(Session session){
        return SessionResponse.builder().build().withId(session.getId()).withAccess(session.getAccess()).
                withContent(session.getContent()).withCreatedAt(session.getCreatedAt()).withCreatedBy(session.getCreatedBy()).
                withUpdatedAt(session.getUpdatedAt()).withUpdatedBy(session.getUpdatedBy());
    }


    @GetMapping("/entry")
    public List<SessionResponse> getAllSessions() {
        List<Session> sessions = SessionRepository.findAll();
        return sessions.stream().map(this::convertSessionToResponse).collect(Collectors.toList());
    }

    @GetMapping("/entry/{id}")
    public ResponseEntity<SessionResponse> getSessionById(@PathVariable(value = "id") Long sessionId)
            throws ResourceNotFoundException {
        Session session =
                SessionRepository
                        .findById(sessionId)
                        .orElseThrow(() -> new ResourceNotFoundException("Session not found on :: " + sessionId));

        return ResponseEntity.ok().body(convertSessionToResponse(session));
    }
    @PostMapping("/entry")
    public SessionResponse createSession(@Valid @RequestBody SessionRequest request) {
        Session result = new Session();
        result.setContent(request.getContent());
        result.setCreatedBy(request.getAuthor());
        result.setUpdatedBy(request.getAuthor());
        result.setAccess(request.getAccess());
        Date now = new Date();
        result.setUpdatedAt(now);
        result.setCreatedAt(now);
        return convertSessionToResponse( SessionRepository.save(result));
    }
    @PutMapping("/entry/{id}")
    public ResponseEntity<SessionResponse> updateSession(
            @PathVariable(value = "id") Long sessionId, @Valid @RequestBody SessionRequest request)
            throws ResourceNotFoundException {

        Session session =
                SessionRepository
                        .findById(sessionId)
                        .orElseThrow(() -> new ResourceNotFoundException("Session not found on :: " + sessionId));
        session.setContent(request.getContent());
        session.setAccess(request.getAccess());
        session.setUpdatedBy(request.getAuthor());
        session.setUpdatedAt(new Date());
        final Session updatedSession = SessionRepository.save(session);
        return ResponseEntity.ok(convertSessionToResponse(updatedSession));
    }
    @DeleteMapping("/entry/{id}")
    public Map<String, Boolean> deleteSession(@PathVariable(value = "id") Long sessionId) throws Exception {
        Session session =
                SessionRepository
                        .findById(sessionId)
                        .orElseThrow(() -> new ResourceNotFoundException("Session not found on :: " + sessionId));

        SessionRepository.delete(session);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
