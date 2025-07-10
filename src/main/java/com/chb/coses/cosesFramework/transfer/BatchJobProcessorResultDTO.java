package com.chb.coses.cosesFramework.transfer;

import com.chb.coses.framework.transfer.DTO;

/**
 * Batch Job Processor Result Data Transfer Object
 */
public class BatchJobProcessorResultDTO extends DTO {

    private String jobId;
    private String jobName;
    private String status;
    private String resultCode;
    private String resultMessage;
    private int processedCount;
    private int successCount;
    private int failureCount;
    private long startTime;
    private long endTime;
    private long duration;

    public BatchJobProcessorResultDTO() {
        super();
    }

    public BatchJobProcessorResultDTO(String jobId, String jobName) {
        this.jobId = jobId;
        this.jobName = jobName;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public int getProcessedCount() {
        return processedCount;
    }

    public void setProcessedCount(int processedCount) {
        this.processedCount = processedCount;
    }

    public int getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(int successCount) {
        this.successCount = successCount;
    }

    public int getFailureCount() {
        return failureCount;
    }

    public void setFailureCount(int failureCount) {
        this.failureCount = failureCount;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    /**
     * Calculate duration from start and end time
     */
    public void calculateDuration() {
        if (startTime > 0 && endTime > 0) {
            this.duration = endTime - startTime;
        }
    }

    /**
     * Check if job completed successfully
     */
    public boolean isSuccess() {
        return "SUCCESS".equals(status) || "COMPLETED".equals(status);
    }

    /**
     * Check if job failed
     */
    public boolean isFailed() {
        return "FAILED".equals(status) || "ERROR".equals(status);
    }

    @Override
    public String toString() {
        return "BatchJobProcessorResultDTO{" +
                "jobId='" + jobId + '\'' +
                ", jobName='" + jobName + '\'' +
                ", status='" + status + '\'' +
                ", resultCode='" + resultCode + '\'' +
                ", resultMessage='" + resultMessage + '\'' +
                ", processedCount=" + processedCount +
                ", successCount=" + successCount +
                ", failureCount=" + failureCount +
                ", duration=" + duration +
                '}';
    }
}