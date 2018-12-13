package com.communityratesgames.transactions;

import com.communityratesgames.domain.User;
import com.communityratesgames.jms.JMSSender;
import com.communityratesgames.user.*;
import com.communityratesgames.util.JsonError;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Stateless
@Default
public class UserService implements UserDataAccess {

    private final static Logger logger = Logger.getLogger(com.communityratesgames.transactions.UserService.class);

    @PersistenceContext(unitName = "communityratesgames")
    private EntityManager em;

    @Override
    public List<User> showAllUsers() {
        Query q = em.createNativeQuery("SELECT * FROM user_entity", User.class);
        List<User> users = q.getResultList();
        return users;
    }

    @Override
    public User register(User user) throws JsonError {
        if (em.createNativeQuery("SELECT * FROM user_entity where email = ?")
                .setParameter(1, user.getEmail())
                .getResultList().size() != 0) {
            throw new JsonError(3, "email already registered");
        }
        if (em.createNativeQuery("SELECT * FROM user_entity where userName = ?")
                .setParameter(1, user.getUserName())
                .getResultList().size() != 0) {
            throw new JsonError(4, "username already registered");
        }
        em.persist(user);
        return user;
    }

    @Override
    public User login(User user) {
        try {
            User u = (User)em.createQuery("SELECT u FROM User u WHERE u.email = :email")
                .setParameter("email", user.getEmail())
                .getSingleResult();
            String password = User.hashPassword(user.getPassword(), u.getPasswordHash());
            if (u.getPassword().equals(password)) {
                //Long token = AuthToken.generateNewToken(u.getId());
                return u;
            } else {
                return null;
            }
        } catch (NoResultException e) {
            return null;
        }
    }

    public boolean logout(Long token) {
        return AuthToken.close(token);
    }
}
