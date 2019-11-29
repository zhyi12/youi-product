package org.youi.datacenter.ods.cassandra;

import com.datastax.driver.core.DataType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.cql.CqlTemplate;
import org.springframework.data.cassandra.core.cql.generator.CreateTableCqlGenerator;
import org.springframework.data.cassandra.core.cql.keyspace.CreateTableSpecification;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = CassandraTestConfig.class)
public class OdsCassandraTableBuilderTest {

    @Autowired
    private CqlTemplate cqlTemplate;

    @Test
    public void testCreateTable(){


        /**
         * `USER_ID` varchar(36) NOT NULL,
         *   `AGENCY_ID` varchar(36) DEFAULT NULL,
         *   `LOGIN_NAME` varchar(20) DEFAULT NULL,
         *   `USER_CAPTION` varchar(80) DEFAULT NULL,
         *   `PASSWORD` varchar(40) DEFAULT NULL,
         *   `USER_ACTIVE` int(11) DEFAULT NULL,
         *   `INIT_PASSWORD` varchar(20) DEFAULT NULL,
         *   `USER_GROUP` varchar(100) DEFAULT NULL,
         *   `UPDATE_TIME` varchar(20) DEFAULT NULL,
         */
        CreateTableSpecification createTableSpecification = CreateTableSpecification.createTable("youi_user")
                .partitionKeyColumn("USER_ID", DataType.text())
                .column("AGENCY_ID",DataType.text())
                .column("LOGIN_NAME",DataType.text())
                .column("USER_CAPTION",DataType.text())
                .column("PASSWORD",DataType.text())
                .column("INIT_PASSWORD",DataType.text())
                .column("USER_GROUP",DataType.text())
                .column("UPDATE_TIME",DataType.text())
                .ifNotExists(true);


        System.out.println(CreateTableCqlGenerator.toCql(createTableSpecification));
        cqlTemplate.execute(CreateTableCqlGenerator.toCql(createTableSpecification));

    }
}