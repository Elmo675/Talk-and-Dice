package com.github.elmo675.controller;


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

@RestController
@RequestMapping("/api/v1")

public class SessionController {

    @Autowired
    private SessionRepository SessionRepository;

    @GetMapping("/entry")
    public List<Session> getAllSessions() {
        return SessionRepository.findAll();
    }

    @GetMapping("/entry/{id}")
    public ResponseEntity<Session> getSessionById(@PathVariable(value = "id") Long SessionId)
            throws ResourceNotFoundException {
        Session Session =
                SessionRepository
                        .findById(SessionId)
                        .orElseThrow(() -> new ResourceNotFoundException("Session not found on :: " + SessionId));
        return ResponseEntity.ok().body(Session);
    }
    @PostMapping("/entry")
    public Session createSession(@Valid @RequestBody Session Session) {
        return SessionRepository.save(Session);
    }
    @PutMapping("/entry/{id}")
    public ResponseEntity<Session> updateSession(
            @PathVariable(value = "id") Long SessionId, @Valid @RequestBody Session sessionDetails)
            throws ResourceNotFoundException {

        Session Session =
                SessionRepository
                        .findById(SessionId)
                        .orElseThrow(() -> new ResourceNotFoundException("Session not found on :: " + SessionId));

        Session.setContent(sessionDetails.getContent());
        Session.setAcces(sessionDetails.getAcces());
        Session.setUpdatedAt(new Date());
        final Session updatedSession = SessionRepository.save(Session);
        return ResponseEntity.ok(updatedSession);
    }
    @DeleteMapping("/entry/{id}")
    public Map<String, Boolean> deleteUser(@PathVariable(value = "id") Long SessionId) throws Exception {
        Session Session =
                SessionRepository
                        .findById(SessionId)
                        .orElseThrow(() -> new ResourceNotFoundException("Session not found on :: " + SessionId));

        SessionRepository.delete(Session);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
