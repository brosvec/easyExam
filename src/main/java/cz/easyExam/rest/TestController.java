package cz.easyExam.rest;

import cz.easyExam.model.Test;
import cz.easyExam.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/rest/test")
public class TestController {

    private static final Logger LOG = LoggerFactory.getLogger(TestController.class);

    private final TestService testService;

    @Autowired
    public TestController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Test> getProducts() {
        return testService.findAll();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity saveTest(@RequestBody Test test) {
        testService.saveTest(test);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
