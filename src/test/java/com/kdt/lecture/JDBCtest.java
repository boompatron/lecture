package com.kdt.lecture;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@Slf4j
@SpringBootTest
public class JDBCtest {
    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:~/test";
    static final String USER = "sa";
    static final String PASS = "";

    static final String DROP_TABLE_SQL = "DROP TABLE customers IF EXISTS";
    static final String CREATE_TABLE_SQL = "CREATE TABLE customers(id SERIAL, first_name varchar(255), last_name varchar(255))";
    static final String INSERT_SQL = "INSERT INTO customers (id, first_name, last_name) VALUES (1, 'bosub', 'kim')";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    void jdbc_sample(){
        try {
            Class.forName(JDBC_DRIVER); // JDBC DRIVER
            Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
            log.info("GET CONNECTION");
            // CONNECTION

            Statement statement = connection.createStatement();
            // STATEMENT
            statement.executeUpdate(DROP_TABLE_SQL);
            statement.executeUpdate(CREATE_TABLE_SQL);
            log.info("TABLE CREATED");

            statement.executeUpdate(INSERT_SQL);
            log.info("CUSTOMER INSERTED");
            // QUERY USING STATEMENT

            ResultSet resultSet =  statement.executeQuery("select * from customers where id = 1");
            // RESULT SET

            while (resultSet.next()){
                String fullName = resultSet.getString("first_name") + " " + resultSet.getString("last_name");
                log.info("CUSTOMER FULL NAME : {}", fullName);
            }
            // USING

            statement.close();
            connection.close();
            // CLOSE
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void JdbcTemplate_sample(){
        jdbcTemplate.update(DROP_TABLE_SQL);
        jdbcTemplate.update(CREATE_TABLE_SQL);
        log.info("CREATE TABLE WITH JDBC TEMPLATE");


        jdbcTemplate.update(INSERT_SQL);
        log.info("INSERT CUSTOMER INTO TABLE WITH JDBC TEMPLATE");

        String fullName = jdbcTemplate.queryForObject(
                "select * from customers where id = 1",
                (resultSet, i) -> resultSet.getString("first_name") + " " + resultSet.getString("last_name")
        );
        log.info("FULL NAME : {}", fullName);
    }
}
