package pro.biocontainers.mongodb.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.support.PageableExecutionUtils;
import pro.biocontainers.mongodb.model.BioContainerTool;

import java.util.List;

/**
 * This code is licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * ==Overview==
 *
 * @author ypriverol on 10/08/2018.
 */
public class CustomBioContainersRepositoryImpl implements CustomBioContainersRepository {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    MongoOperations mongoOperations;

    @Override
    public List<BioContainerTool> filterAll(String id, String name, String toolname, String description, String author) {

        Criteria filterCriteria = null;
        if(id != null && !id.trim().isEmpty()){
            if(filterCriteria == null)
                filterCriteria = Criteria.where("id").regex(id);
            else
                filterCriteria = filterCriteria.andOperator(new Criteria("id").regex(id));
        }else if(name != null && !name.trim().isEmpty()){
            if(filterCriteria == null)
                filterCriteria = Criteria.where("name").regex(name);
            else
                filterCriteria = filterCriteria.andOperator(new Criteria("name").regex(name));
        }else if(description != null && !description.trim().isEmpty()){
            if(filterCriteria == null)
                filterCriteria = Criteria.where("description").regex(description);
            else
                filterCriteria = filterCriteria.andOperator(new Criteria("description").regex(description));
        }else if(toolname != null && !toolname.trim().isEmpty()){
            if(filterCriteria == null)
                filterCriteria = Criteria.where("name").regex(toolname);
            else
                filterCriteria = filterCriteria.andOperator(new Criteria("name").regex(toolname));
        }

        Query queryMongo = new Query().addCriteria(filterCriteria);
        List<BioContainerTool> tools =  mongoTemplate.find(queryMongo, BioContainerTool.class);
        return tools;
    }
}

