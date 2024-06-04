package com.alex.asset.logs;


import com.alex.asset.logs.domain.Log;
import com.alex.asset.logs.domain.LogDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogMapper {


    public static LogDto toDto(Log entity) {
        LogDto dto = new LogDto();
        dto.setId(entity.getId());
        dto.setCreated(entity.getCreated());
        dto.setUserEmail(entity.getUser().getEmail());
        dto.setAction(entity.getAction());
        dto.setSection(entity.getSection());
        dto.setText(entity.getText());
        return dto;
    }


    public static List<LogDto> toDTOs(List<Log> logs){
        return logs.stream().map(LogMapper::toDto).toList();
    }
}
