package org.example.smart_delivery.service.colis;

import org.example.smart_delivery.entity.enums.ColisStatus;
import org.example.smart_delivery.entity.enums.Priority;

public class Colisfilter {
    private String zoneId;
    private ColisStatus Status;
    private Priority priority;

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public ColisStatus getStatus() {
        return Status;
    }

    public void setStatus(ColisStatus status) {
        Status = status;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }
}
