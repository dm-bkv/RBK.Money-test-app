package com.rbkmoney.test_dev_java;

import com.rbkmoney.test_dev_java.get_data.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Application {


    //private static final Logger LOG = Logger.getLogger(Application.class);

    public static void main(String[] args) {
        ApplicationContext context =
                new FileSystemXmlApplicationContext("src/main/resources/META-INF/context.xml");


        //System.setProperty("log4j.properties", "TestDevJava/src/main/resources/META-INF/log4j.properties");
        Logger logger = LoggerFactory.getLogger(Application.class);
        logger.info("Something...");

/*
        try(Connection conn = DataSource.configureDataSource().getConnection();
            Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT id, amount FROM rbk.transactions ");
            while(rs.next()) {
                System.out.println("id = " + rs.getString("id") + ", amount = " + rs.getString("amount"));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
*/
    }

}
