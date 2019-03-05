package com.cisco.cmad.event.mongo.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Component;

import com.cisco.cmad.event.exceptions.AdminUserException;
import com.cisco.cmad.event.exceptions.UserCreatedException;
import com.cisco.cmad.event.exceptions.UserException;
import com.cisco.cmad.event.logger.CMADLogger;
import com.cisco.cmad.event.mongo.dao.IPAddresses;
import com.cisco.cmad.event.mongo.dao.Users;
import com.cisco.cmad.event.repositories.IPAddressRepository;
import com.cisco.cmad.event.repositories.UserRepository;
import com.mongodb.async.SingleResultCallback;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Component
public class UserService {

    CountDownLatch latch = new CountDownLatch(1);

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    IPAddressRepository ipAddressRepository;

    @Autowired
    Environment environment;

    /**
     * Create admin user at server startup
     */
    public void createAdminUser() {

        SingleResultCallback<Void> callbackWhenFinished = new SingleResultCallback<Void>() {
            @Override
            public void onResult(final Void result, final Throwable t) {
                System.out.println("Operation Finished!");
                latch.countDown();
            }
        };

        Users user = new Users();
        user.setId("1");
        user.setName("admin");
        user.setPassword(bCryptPasswordEncoder.encode("admin"));

        ExampleMatcher em = ExampleMatcher.matching().withIgnoreCase();
        Example<Users> example = Example.of(user, em);
        List<Users> users = userRepository.findAll(example);
        System.out.println("Existing users: " + users);

        if (users.size() == 0) {
            System.out.println("admin user not found, hence creating admin user");
            userRepository.save(user);
            System.out.println("admin user created");
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param userName
     * @param password
     */
    public void createUser(String userName, String password) {

        if (findUser(userName)) {
            String msg = "User '" + userName + "' already created";
            CMADLogger.logInfo(this.getClass().getName(), msg);
            throw new UserCreatedException(msg);
        } else {
            long totalUsers = userRepository.count();
            Users user = new Users();
            user.setId(String.valueOf(++totalUsers));
            user.setName(userName);
            user.setPassword(bCryptPasswordEncoder.encode(password));
            CMADLogger.logInfo(this.getClass().getName(), "Creating user " + userName + " with id " + user.getId());
            userRepository.save(user);
        }
    }

    public boolean removeUser(String userName) {

        boolean deleteStatus = true;

        if (userName.equals("admin")) {
            String msg = "Unable to remove admin user";
            CMADLogger.logInfo(this.getClass().getName(), msg);
            throw new AdminUserException(msg);
        } else if (!findUser(userName)) {
            String msg = "User '" + userName + "' not found";
            CMADLogger.logInfo(this.getClass().getName(), msg);
            throw new UserException(msg);
        } else {
            List<Users> users = userRepository.findUserByName(userName);
            if (users != null && users.size() > 0) {
                userRepository.delete(users.get(0));
            } else {
                CMADLogger.logInfo(this.getClass().getName(), "User '" + userName + "' not found");
                deleteStatus = false;
            }
        }

        return deleteStatus;

    }

    /**
     * @param userName
     * @return true if user already exists
     */
    public boolean findUser(String userName) {
        List<Users> users = userRepository.findUserByName(userName);
        return (users.size() == 0) ? false : true;
    }

    public boolean findUser(String userName, String password) {
        List<Users> users = userRepository.findUserForLogin(userName, password);
        return (users.size() == 0) ? false : true;
    }

    /**
     * @return all the ipAddresses from IPAddresses collection
     */
    public List<String> fetchIPAddresses() {
        List<String> ipAddresses = new ArrayList<String>();
        List<IPAddresses> ipAddressesObjects = ipAddressRepository.findAll();

        ipAddressesObjects.forEach(ipAddressesObject -> {
            ipAddresses.add(ipAddressesObject.getIpaddress());
        });

        return ipAddresses;
    }

    /**
     * This will update the IPAddresses table which are newly added in the
     * properties file at the time of startup
     */
    public void checkAndUpdateIPAddresses() {

        SingleResultCallback<Void> callbackWhenFinished = new SingleResultCallback<Void>() {
            @Override
            public void onResult(final Void result, final Throwable t) {
                System.out.println("Operation Finished!");
                latch.countDown();
            }
        };

        List<IPAddresses> ipAddressesFromDB = ipAddressRepository.findAll();
        String ipAddressesFromPropertyFile = environment.getProperty("ipAddresses");
        LinkedList<String> ipAddList = new LinkedList<String>(Arrays.asList(ipAddressesFromPropertyFile.split(",")));

        List<String> dbIPAddresses = new ArrayList<String>();

        ipAddressesFromDB.forEach(ipAddressObj -> {
            dbIPAddresses.add(ipAddressObj.getIpaddress());
        });

        CMADLogger.logInfo(this.getClass().getName(), "dbIPAddresses " + dbIPAddresses);
        CMADLogger.logInfo(this.getClass().getName(), "ipAddList " + ipAddList);

        ipAddList.removeAll(dbIPAddresses);

        CMADLogger.logInfo(this.getClass().getName(), "IPAddresses to Save is: " + ipAddList);

        List<IPAddresses> ipAddressesToSave = new ArrayList<IPAddresses>();

        CMADLogger.logInfo(this.getClass().getName(), "IPAddress will update to db are:");

        ipAddList.forEach(ipAddress -> {
            IPAddresses ipAddressObject = new IPAddresses();
            CMADLogger.logInfo(this.getClass().getName(), ipAddress.trim());
            ipAddressObject.setIpaddress(ipAddress.trim());
            ipAddressesToSave.add(ipAddressObject);
        });
        CMADLogger.logInfo(this.getClass().getName(), "IPAddress update to db done.");

        if (ipAddressesToSave.size() > 0)
            ipAddressRepository.saveAll(ipAddressesToSave);

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
