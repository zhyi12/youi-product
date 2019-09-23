package org.youi.scheduler.common.model;

/**
 *
 */
public interface ICronTrigger {

    /**
     * @return the schedName
     */
    String getSchedName();

    /**
     * @return the triggerName
     */
    String getTriggerName();

    /**
     * @return the triggerGroup
     */
    String getTriggerGroup();

    /**
     * @return the cronExpression
     */
    String getCronExpression();

    /**
     * @return the timeZoneId
     */
    String getTimeZoneId();

}
