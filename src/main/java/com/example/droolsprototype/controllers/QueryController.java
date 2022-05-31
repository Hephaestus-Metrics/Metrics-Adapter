package com.example.droolsprototype.controllers;

import com.example.droolsprototype.conf.Configuration;
import com.example.droolsprototype.demo.DemoTask;
import com.example.droolsprototype.model.QueryInfo;
import com.example.droolsprototype.query.QueryBuilder;
import com.example.droolsprototype.services.PrometheusQueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.List;

/**
 * Provides an endpoint for posting queries to be executed as part of the demo
 */
@RestController
@RequestMapping("metrics-adapter")
@CrossOrigin(origins = Configuration.GUI_ORIGINS)
public class QueryController {

    private final DemoTask demoTask;


    public QueryController(DemoTask demoTask) {
        this.demoTask = demoTask;
    }

    @PostMapping("/queries")
    public ResponseEntity<Object> postQueriesEndpoint(@RequestBody QueryInfo queryInfo) {
//        demoTask.setToQuery(queryInfo);
        return ResponseEntity.ok().build(); //returns 200 OK
    }

    @RequestMapping(value="/metrics", method = RequestMethod.POST)
    public ResponseEntity postMetrics(@RequestBody String[][] body) {
        String[][] metrics = body;
        QueryBuilder queryBuilder = new QueryBuilder(metrics);
        queryBuilder.buildQueries();
//        demoTask.setToQuery(queryBuilder.getQueries());
        return ResponseEntity.ok().build(); // for now it is OK, but todo in the future, we need to return success message
    }

}
