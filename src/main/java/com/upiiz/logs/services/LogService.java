package com.upiiz.logs.services;


import com.upiiz.logs.dto.LogDTO;
import com.upiiz.logs.entities.LogEntity;
import com.upiiz.logs.repositories.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LogService {
    @Autowired
    private LogRepository logRepository;

    public LogDTO createLog(LogDTO logDTO) {
        LogEntity logEntity = convertToEntity(logDTO);
        LogEntity savedLog = logRepository.save(logEntity);
        return convertToDTO(savedLog);
    }

    public List<LogDTO> getAllLogs() {
        List<LogEntity> logs = (List<LogEntity>) logRepository.findAll();
        List<LogDTO> logDTOs = new ArrayList<>();

        for (LogEntity log : logs) {
            logDTOs.add(convertToDTO(log));
        }

        return logDTOs;
    }

    public LogDTO getLogById(Long id) {
        Optional<LogEntity> log = logRepository.findById(id);
        return log.map(this::convertToDTO).orElse(null);
    }

    public LogDTO updateLog(Long id, LogDTO logDTO) {
        Optional<LogEntity> existingLog = logRepository.findById(id);

        if (existingLog.isPresent()) {
            LogEntity logEntity = existingLog.get();
            logEntity.setUser_id(logDTO.getUser_id());
            logEntity.setAction(logDTO.getAction());

            LogEntity updatedLog = logRepository.save(logEntity);
            return convertToDTO(updatedLog);
        }

        return null;
    }

    public void deleteLog(Long id) {
        logRepository.deleteById(id);
    }

    private LogEntity convertToEntity(LogDTO logDTO) {
        return LogEntity.builder()
                .log_id(logDTO.getLog_id())
                .log_date(logDTO.getLog_date())
                .user_id(logDTO.getUser_id())
                .action(logDTO.getAction())
                .build();
    }

    private LogDTO convertToDTO(LogEntity logEntity) {
        LogDTO logDTO = new LogDTO();
        logDTO.setLog_id(logEntity.getLog_id());
        logDTO.setLog_date(logEntity.getLog_date());
        logDTO.setUser_id(logEntity.getUser_id());
        logDTO.setAction(logEntity.getAction());
        return logDTO;
    }
}