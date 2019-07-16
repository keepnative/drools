/*
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/

package org.drools.persistence.info;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.drools.persistence.api.PersistentSession;
import org.drools.persistence.api.SessionMarshallingHelper;
import org.drools.persistence.api.Transformable;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "SOUPE_XP_SESSION")
public class SessionInfo implements PersistentSession {

    @Id
    @GeneratedValue(generator = "S_SOUPE_XP_SESSION")
    @GenericGenerator(
            name = "S_SOUPE_XP_SESSION",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "S_SOUPE_XP_SESSION")
            }
    )
    @Column(name = "ID")
    private Long                        id;

    @Version
    @Column(name = "VERSION")
    private int                version;

    @Column(name = "START_DATE")
    private Date               startDate;
    @Column(name = "LAST_MODIFICATION_DATE")
    private Date               lastModificationDate;

    @Lob
    @Column(name = "RULES_BYTE_ARRAY", length=2147483647)
    private byte[]             rulesByteArray;

    @Transient
    SessionMarshallingHelper helper;

    public SessionInfo() {
        this.startDate = new Date();
    }

    public Long getId() {
        return this.id;
    }

    public int getVersion() {
        return this.version;
    }

    public void setJPASessionMashallingHelper(SessionMarshallingHelper helper) {
        this.helper = helper;
    }

    public SessionMarshallingHelper getJPASessionMashallingHelper() {
        return helper;
    }

    public void setData( byte[] data) {
        this.rulesByteArray = data;
    }

    public byte[] getData() {
        return this.rulesByteArray;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public Date getLastModificationDate() {
        return this.lastModificationDate;
    }

    public void setLastModificationDate(Date date) {
        this.lastModificationDate = date;
    }

    @Override
    public void transform() {
        this.rulesByteArray  = this.helper.getSnapshot();
    }

    public void setId(Long ksessionId) {
        this.id = ksessionId;
    }

}
