/**
 * 
 */
package com.cisco.cmad.event.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.cisco.cmad.event.mongo.dao.IPAddresses;

/**
 * @author tcheedel
 *
 */
public interface IPAddressRepository extends MongoRepository<IPAddresses, String> {

}
