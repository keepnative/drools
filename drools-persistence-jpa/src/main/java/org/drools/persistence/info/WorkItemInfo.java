package org.drools.persistence.info;

import com.bmit.platform.soupe.data.core.model.AbstractBaseEntityWithDomainNoAuditing;
import org.drools.core.impl.InternalKnowledgeBase;
import org.drools.core.marshalling.impl.InputMarshaller;
import org.drools.core.marshalling.impl.MarshallerReaderContext;
import org.drools.core.marshalling.impl.MarshallerWriteContext;
import org.drools.core.marshalling.impl.ProtobufInputMarshaller;
import org.drools.core.marshalling.impl.ProtobufOutputMarshaller;
import org.drools.core.process.instance.WorkItem;
import org.drools.persistence.Transformable;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.kie.api.runtime.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;

@Entity
@Table(name = "SOUPE_XP_WORK_ITEM")
public class WorkItemInfo extends AbstractBaseEntityWithDomainNoAuditing implements Transformable {

    private static final Logger logger = LoggerFactory.getLogger(WorkItemInfo.class);

    @Id
    @GeneratedValue(generator = "sequenceStyleGenerator")
    @GenericGenerator(
            name = "sequenceStyleGenerator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "S_SOUPE_XP_WORK_ITEM")
            }
    )
    @Column(name = "ID")
    private Long   workItemId;

    @Version
    @Column(name = "VERSION")
    private int    version;

    @Column(name = "NAME")
    private String name;
    @Column(name = "CREATION_DATE")
    private Date   creationDate;
    @Column(name = "PROCESS_INSTANCE_ID")
    private long   processInstanceId;
    @Column(name = "STATE")
    private long   state;
    
    @Lob
    @Column(name = "WORK_ITEM_BYTE_ARRAY",length=2147483647)
    private byte[] workItemByteArray;
    
    private @Transient
    WorkItem       workItem;

    private @Transient
    Environment                               env;
    
    protected WorkItemInfo() {
    }

    public WorkItemInfo(WorkItem workItem, Environment env) {
        this.workItem = workItem;
        this.name = workItem.getName();
        this.creationDate = new Date();
        this.processInstanceId = workItem.getProcessInstanceId();
        this.env = env;
    }

    public Long getId() {
        return workItemId;
    }
    
    public int getVersion() {
        return this.version;
    }

    public String getName() {
        return name;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public long getProcessInstanceId() {
        return processInstanceId;
    }

    public long getState() {
        return state;
    }
    
    public byte [] getWorkItemByteArray() { 
       return workItemByteArray;
    }
    
    public WorkItem getWorkItem(Environment env, InternalKnowledgeBase kBase) {
        this.env = env;
        if ( workItem == null ) {
            try {
                ByteArrayInputStream bais = new ByteArrayInputStream( workItemByteArray );
                MarshallerReaderContext context = new MarshallerReaderContext( bais,
                                                                               kBase,
                                                                               null,
                                                                               null,
                                                                               null,
                                                                               env);
                try {
                    workItem = ProtobufInputMarshaller.readWorkItem(context);
                } catch (Exception e) {
                    // for backward compatibility to be able to restore 5.x data
                    try {
                        context.close();
                        bais = new ByteArrayInputStream( workItemByteArray );
                        context = new MarshallerReaderContext( bais,
                                                               kBase,
                                                               null,
                                                               null,
                                                               null,
                                                               env);

                        workItem = InputMarshaller.readWorkItem(context);
                    } catch (IOException e1) {
                        logger.error("Unable to read work item with InputMarshaller", e1);
                        // throw the original exception produced by failed protobuf op
                        throw new RuntimeException("Unable to read work item ", e);
                    }
                }

                context.close();
            } catch ( IOException e ) {
                e.printStackTrace();
                throw new IllegalArgumentException( "IOException while loading work item: " + e.getMessage() );
            }
        }
        return workItem;
    }

     

//    @PreUpdate
    @Override
    public void transform() {
        this.state = workItem.getState();



        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            MarshallerWriteContext context = new MarshallerWriteContext( baos,
                                                                         null,
                                                                         null,
                                                                         null,
                                                                         null,
                                                                         this.env);
            ProtobufOutputMarshaller.writeWorkItem(context, workItem);

            context.close();
            this.workItemByteArray = baos.toByteArray();
        } catch ( IOException e ) {
            throw new IllegalArgumentException( "IOException while storing workItem " + workItem.getId() + ": " + e.getMessage() );
        }
    }
    
    public void setId(Long id){
        this.workItemId = id;
    }


}
