package ru.sbrf.esipov.hw.atm;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws IOException {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();

        Session session = sessionFactory.openSession();

        MyATM atm;
        ObjectMapper mapper = new ObjectMapper();

        if (args.length == 0) {
            atm = new MyATM();
        } else {
            System.out.println("Reading ATM from " + args[0] + " ...");
            atm = mapper.readValue(new File(args[0]), MyATM.class);
        }

        System.out.println(atm);
        atm.getMoney(100);
        System.out.println(atm);

        session.save(atm);
        session.close();

        Session session2 = sessionFactory.openSession();
        MyATM atmFromDb = session2.get(MyATM.class, 1);

        System.out.println(atmFromDb.toString());

        session2.close();
        sessionFactory.close();
    }
}
