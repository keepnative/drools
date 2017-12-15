/*
 * Copyright 2010 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.drools.persistence.jpa.marshaller;

import io.keepnative.soupe.model.AbstractBaseEntityWithDomainNoAuditing;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "SOUPE_XP_MAPPED_VARIABLE")
public class MappedVariable extends AbstractBaseEntityWithDomainNoAuditing implements Serializable {

    @Id
    @GeneratedValue(generator = "sequenceStyleGenerator")
    @GenericGenerator(
            name = "sequenceStyleGenerator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "S_SOUPE_XP_MAPPED_VARIABLE")
            }
    )
    @Column(name = "ID")
    private Long mappedVarId;

    @Version
    @Column(name = "VERSION")
    private int version;

    @Column(name = "VARIABLE_ID")
    private Long variableId;

    @Column(name = "VARIABLE_TYPE")
    private String variableType;

    @Column(name = "PROCESS_INSTANCE_ID")
    private Long processInstanceId;
    @Column(name = "TASK_ID")
    private Long taskId;
    @Column(name = "WORK_ITEM_ID")
    private Long workItemId;

    public MappedVariable() {

    }

    public MappedVariable(Long variableId, String variableType, Long processInstanceId) {
        this.variableId = variableId;
        this.variableType = variableType;
        this.processInstanceId = processInstanceId;
    }

    public MappedVariable(Long variableId, String variableType, Long processInstanceId, Long taskId, Long workItemId) {
        this.variableId = variableId;
        this.variableType = variableType;
        this.processInstanceId = processInstanceId;
        this.taskId = taskId;
        this.workItemId = workItemId;
    }

    public Long getMappedVarId() {
        return mappedVarId;
    }

    public void setMappedVarId(Long mappedVarId) {
        this.mappedVarId = mappedVarId;
    }

    public Long getVariableId() {
        return variableId;
    }

    public void setVariableId(Long variableId) {
        this.variableId = variableId;
    }

    public String getVariableType() {
        return variableType;
    }

    public void setVariableType(String variableType) {
        this.variableType = variableType;
    }

    public Long getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(Long processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getWorkItemId() {
        return workItemId;
    }

    public void setWorkItemId(Long workItemId) {
        this.workItemId = workItemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MappedVariable that = (MappedVariable) o;

        if (processInstanceId != null ? !processInstanceId.equals(that.processInstanceId) : that.processInstanceId != null) {
            return false;
        }
        if (taskId != null ? !taskId.equals(that.taskId) : that.taskId != null) {
            return false;
        }
        if (variableId != null ? !variableId.equals(that.variableId) : that.variableId != null) {
            return false;
        }
        if (variableType != null ? !variableType.equals(that.variableType) : that.variableType != null) {
            return false;
        }
        if (workItemId != null ? !workItemId.equals(that.workItemId) : that.workItemId != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = variableId != null ? variableId.hashCode() : 0;
        result = 31 * result + (variableType != null ? variableType.hashCode() : 0);
        result = 31 * result + (processInstanceId != null ? processInstanceId.hashCode() : 0);
        result = 31 * result + (taskId != null ? taskId.hashCode() : 0);
        result = 31 * result + (workItemId != null ? workItemId.hashCode() : 0);
        return result;
    }
}
