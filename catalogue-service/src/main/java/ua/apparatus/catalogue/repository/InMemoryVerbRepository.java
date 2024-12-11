package ua.apparatus.catalogue.repository;


import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;
import ua.apparatus.catalogue.entity.Verb;
import ua.apparatus.catalogue.repository.exception.VerbAlreadyExistException;
import ua.apparatus.catalogue.utils.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


@Repository
@NoArgsConstructor
public class InMemoryVerbRepository implements VerbRepository {

    @Override
    public List<Verb> findAll() {
        List<Verb> verbs = new ArrayList<>();
        String sql = "SELECT * FROM verbs";
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Verb verb = new Verb();
                verb.setId(resultSet.getLong("id"));
                verb.setInfinitive(resultSet.getString("infinitive"));
                verb.setVerb_v2(resultSet.getString("verb_v2"));
                verb.setVerb_v3(resultSet.getString("verb_v3"));
                verb.setIng(resultSet.getString("ing"));
                verb.setTranslate_ua(resultSet.getString("translate_ua"));
                verbs.add(verb);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return verbs;
    }

    @Override
    public Verb save(Verb verb) {
        Optional<Verb> findedVerb = findVerbByInfinitive(verb.getInfinitive());
        if (findedVerb.isEmpty()) {
            String sql = "INSERT INTO verbs (infinitive, verb_v2, verb_v3, ing, translate_ua) " +
                    "VALUES (?, ?, ?, ?, ?) RETURNING id";
            try (Connection connection = ConnectionManager.open()) {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, verb.getInfinitive());
                statement.setString(2, verb.getVerb_v2());
                statement.setString(3, verb.getVerb_v3());
                statement.setString(4, verb.getIng());
                statement.setString(5, verb.getTranslate_ua());

                try (ResultSet resultSet = statement.executeQuery()) {
                    resultSet.next();
                    verb.setId(resultSet.getLong("id"));
                    return verb;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new VerbAlreadyExistException("Таке дієслово вже існує");
        }
    }

    @Override
    public Optional<Verb> findVerbById(Long id) {
        Verb verb = new Verb();
        String sql = "SELECT * FROM verbs WHERE id=?";
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                verb.setId(resultSet.getLong("id"));
                verb.setInfinitive(resultSet.getString("infinitive"));
                verb.setVerb_v2(resultSet.getString("verb_v2"));
                verb.setVerb_v3(resultSet.getString("verb_v3"));
                verb.setIng(resultSet.getString("ing"));
                verb.setTranslate_ua(resultSet.getString("translate_ua"));
                    return Optional.of(verb);
                } else {
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
                throw new NoSuchElementException(e);
        }
    }

    public Optional<Verb> findVerbByInfinitive(String infinitive) {
        String sql = "SELECT * FROM verbs WHERE infinitive=?";
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, infinitive);
            try (ResultSet resultSet = statement.executeQuery()) {
                if(resultSet.next()) {
                    Verb verb = new Verb();
                    verb.setId(resultSet.getLong("id"));
                    verb.setInfinitive(resultSet.getString("infinitive"));
                    verb.setVerb_v2(resultSet.getString("verb_v2"));
                    verb.setVerb_v3(resultSet.getString("verb_v3"));
                    verb.setIng(resultSet.getString("ing"));
                    verb.setTranslate_ua(resultSet.getString("translate_ua"));
                    return Optional.of(verb);
                } else return Optional.empty();
            }
        } catch (SQLException e) {
            throw new NoSuchElementException("Error retrieving verb by infinitive: " + infinitive, e);
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM verbs WHERE id=?";
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);

            int i = statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateVerb(Verb verb) {
            String sql = "UPDATE verbs " +
                    "SET infinitive = ?, verb_v2 = ?, verb_v3 = ?, ing = ?, translate_ua = ? " +
                    "WHERE id=?";
            try (Connection connection = ConnectionManager.open()) {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, verb.getInfinitive());
                statement.setString(2, verb.getVerb_v2());
                statement.setString(3, verb.getVerb_v3());
                statement.setString(4, verb.getIng());
                statement.setString(5, verb.getTranslate_ua());
                statement.setLong(6, verb.getId());

                int i = statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }