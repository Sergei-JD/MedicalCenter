package com.itrex.java.lab.repository.impl;

import com.itrex.java.lab.entity.Visit;
import com.itrex.java.lab.repository.VisitRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

public class JDBCVisitRepositoryImpl implements VisitRepository {

    private static final String VISIT_ID_COLUMN = "visit_id";
    private static final String DOCTOR_ID_COLUMN = "doctor_id";
    private static final String PATIENT_ID_COLUMN = "patient_id";
    private static final String TIMESLOT_ID_COLUMN = "timeslot_id";

    private static final String SELECT_ALL_VISIT_QUERY = "SELECT * FROM visit";
    private static final String SELECT_VISIT_BY_ID_QUERY = "SELECT * FROM visit WHERE visit_id = ?";
    private static final String INSERT_VISIT_QUERY = "INSERT INTO visit (name) VALUES (?)";
    private static final String DELETE_VISIT_BY_ID_QUERY = "DELETE FROM visit WHERE visit_id = ?";

    private final DataSource dataSource;

    public JDBCVisitRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Visit> getAllVisit() throws RepositoryException {
        List<Visit> visits = new ArrayList<>();
        try(Connection con = dataSource.getConnection();
            Statement stm = con.createStatement();
            ResultSet resultSet = stm.executeQuery(SELECT_ALL_VISIT_QUERY)) {
            while (resultSet.next()) {
                Visit visit = buildVisit(resultSet);

                visits.add(visit);
            }
        } catch (SQLException ex) {
            throw new RepositoryException("Visit table is empty");
        }

        return visits;
    }

    @Override
    public Visit getVisitById(int visitId) throws RepositoryException {
        Visit visit = null;
        try(Connection con = dataSource.getConnection();
            PreparedStatement pstm = con.prepareStatement(SELECT_VISIT_BY_ID_QUERY)) {
            pstm.setInt(1, visitId);
            try (ResultSet resultSet = pstm.executeQuery()) {
                while (resultSet.next()) {
                    visit = buildVisit(resultSet);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RepositoryException("Visit does not exist by id = " + visitId);
        }

        return visit;
    }

    @Override
    public void addVisit(Visit visit) throws RepositoryException {
        try (Connection con = dataSource.getConnection();
             PreparedStatement pstm = con.prepareStatement(INSERT_VISIT_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            pstm.setInt(1, visit.getVisitId());

            int effectiveRows = pstm.executeUpdate();

            if (effectiveRows == 1) {
                try (ResultSet generatedKeys = pstm.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        visit.setVisitId(generatedKeys.getInt(VISIT_ID_COLUMN));
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RepositoryException(ex.getMessage());
        }
    }

    @Override
    public void deleteVisit(int visitId) throws RepositoryException {
        int effectiveRows = 0;
        try(Connection con = dataSource.getConnection();
            PreparedStatement pstm = con.prepareStatement(DELETE_VISIT_BY_ID_QUERY)) {
            pstm.setInt(1, visitId);
            effectiveRows = pstm.executeUpdate();
        }  catch (SQLException ex) {
            throw new RepositoryException("Can't delete visit with id " + visitId);
        }
    }

    private Visit buildVisit(ResultSet resultSet) throws SQLException {
        Visit visit = new Visit();
        visit.setVisitId(resultSet.getInt(VISIT_ID_COLUMN));
        visit.setDoctorId(resultSet.getInt(DOCTOR_ID_COLUMN));
        visit.setPatientId(resultSet.getInt(PATIENT_ID_COLUMN));
        visit.setPatientId(resultSet.getInt(TIMESLOT_ID_COLUMN));

        return visit;
    }

}
