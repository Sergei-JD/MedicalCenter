package com.itrex.java.lab.repository.impl;

import com.itrex.java.lab.entity.Timeslot;
import com.itrex.java.lab.repository.TimeslotRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

public class JDBCTimeslotRepositoryImpl implements TimeslotRepository {

    private static final String TIMESLOT_ID_COLUMN = "timeslot_id";
    private static final String START_TIME_COLUMN = "start_time";
    private static final String DATE_COLUMN = "date";
    private static final String OFFICE_COLUMN = "office";

    private static final String SELECT_ALL_TIMESLOT_QUERY = "SELECT * FROM timeslot";
    private static final String SELECT_TIMESLOT_BY_ID_QUERY = "SELECT * FROM timeslot WHERE timeslot_id = ?";
    private static final String INSERT_TIMESLOT_QUERY = "INSERT INTO timeslot (start_time, date, office) VALUES (?, ?, ?)";
    private static final String DELETE_TIMESLOT_BY_ID_QUERY = "DELETE FROM timeslot WHERE timeslot_id = ?";

    private final DataSource dataSource;

    public JDBCTimeslotRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Timeslot> getAllTimeslot() throws RepositoryException {
        List<Timeslot> timeslots = new ArrayList<>();
        try(Connection con = dataSource.getConnection();
            Statement stm = con.createStatement();
            ResultSet resultSet = stm.executeQuery(SELECT_ALL_TIMESLOT_QUERY)) {
            while (resultSet.next()) {
                Timeslot timeslot = buildTimeslot(resultSet);

                timeslots.add(timeslot);
            }
        } catch (SQLException ex) {
            throw new RepositoryException("Timeslot table is empty");
        }

        return timeslots;
    }

    @Override
    public Timeslot getTimeslotByID(int timeslotId) throws RepositoryException {
        Timeslot timeslot = null;
        try(Connection con = dataSource.getConnection();
            PreparedStatement pstm = con.prepareStatement(SELECT_TIMESLOT_BY_ID_QUERY)) {
            pstm.setInt(1, timeslotId);
            try (ResultSet resultSet = pstm.executeQuery()) {
                while (resultSet.next()) {
                    timeslot = buildTimeslot(resultSet);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RepositoryException("Timeslot does not exist by id = " + timeslotId);
        }

        return timeslot;
    }

    @Override
    public void addTimeslot(Timeslot timeslot) throws RepositoryException {
        try (Connection con = dataSource.getConnection();
             PreparedStatement pstm = con.prepareStatement(INSERT_TIMESLOT_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            pstm.setInt(1, timeslot.getTimeslotId());

            int effectiveRows = pstm.executeUpdate();

            if (effectiveRows == 1) {
                try (ResultSet generatedKeys = pstm.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        timeslot.setTimeslotId(generatedKeys.getInt(TIMESLOT_ID_COLUMN));
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RepositoryException(ex.getMessage());
        }
    }

    @Override
    public void deleteTimeslot(int timeslotId) throws RepositoryException {
        int effectiveRows = 0;
        try(Connection con = dataSource.getConnection();
            PreparedStatement pstm = con.prepareStatement(DELETE_TIMESLOT_BY_ID_QUERY)) {
            pstm.setInt(1, timeslotId);
            effectiveRows = pstm.executeUpdate();
        }  catch (SQLException ex) {
            throw new RepositoryException("Can't delete timeslot with id " + timeslotId);
        }
    }

    private Timeslot buildTimeslot(ResultSet resultSet) throws SQLException {
        Timeslot timeslot = new Timeslot();
        timeslot.setTimeslotId(resultSet.getInt(TIMESLOT_ID_COLUMN));
        timeslot.setStartTime(resultSet.getTime(START_TIME_COLUMN));
        timeslot.setDate(resultSet.getDate(DATE_COLUMN));
        timeslot.setOffice(resultSet.getInt(OFFICE_COLUMN));

        return timeslot;
    }

}
