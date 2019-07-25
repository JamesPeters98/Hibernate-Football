package com.jamesdpeters;

import com.jamesdpeters.entities.NationalityEntity;
import com.jamesdpeters.exceptions.NoDatabaseSelectedException;
import com.jamesdpeters.utils.SessionStore;
import org.h2.tools.Csv;
import org.hibernate.Session;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ImportNationalities {

    public static void main(final String[] args) throws SQLException, NoDatabaseSelectedException {
        new ImportNationalities();
    }

    public ImportNationalities() throws SQLException {
        final Session session = SessionStore.getSession();

        session.beginTransaction();
        session.createQuery("delete from NationalityEntity").executeUpdate();

        ResultSet rs = new Csv().read(getClass().getResource("nationalities.csv").getFile(), null, null);
        while (rs.next()) {
                NationalityEntity nationalityEntity = new NationalityEntity();
                nationalityEntity.setId(rs.getInt(1));
                nationalityEntity.setName(rs.getString(2));
                nationalityEntity.setNationality(rs.getString(3));
                session.save(nationalityEntity);
        }
        session.getTransaction().commit();
        session.close();
        rs.close();


    }
}