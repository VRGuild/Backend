package com.mtvs.devlinkbackend.channel.dto.request;

import com.mtvs.devlinkbackend.channel.entity.Position;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Service;

@Getter
@Service
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ObjectInfoRegistDTO {
    private String objectName;
    private String objectClassName;
    private Position position;
    private Position rotator;
}
