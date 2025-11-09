package org.example.smart_delivery.service.colis;

import lombok.Getter;
import lombok.Setter;
import org.example.smart_delivery.entity.enums.ColisStatus;
import org.example.smart_delivery.entity.enums.Priority;
@Getter
@Setter
public class Colisfilter {
    private String zoneId;
    private ColisStatus Status;
    private Priority priority;

}
