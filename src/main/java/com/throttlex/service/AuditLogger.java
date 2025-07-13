package com.throttlex.service;

import com.throttlex.model.AuditRecord;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class AuditLogger {
    private final Queue<AuditRecord> records = new LinkedList<>();

    public void log(AuditRecord record){
        records.add(record);
        if(records.size() > 1000){
            records.poll();
        }
    }
    public List<AuditRecord> getRecentLogs(){
        return new LinkedList<>(records);
    }
}
