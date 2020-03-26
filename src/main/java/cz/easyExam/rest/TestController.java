package cz.easyExam.rest;

import cz.easyExam.exception.ValidationException;
import cz.easyExam.model.Difficulty;
import cz.easyExam.model.SubscribedTest;
import cz.easyExam.model.Test;
import cz.easyExam.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NoResultException;
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
    public List<Test> getTests(@RequestParam(defaultValue = "") String testName, @RequestParam(defaultValue = "") Difficulty difficulty, @RequestParam(defaultValue = "") String ownerEmail, @RequestParam(defaultValue = "") String ownerUsername) {
        return testService.findAllByParameters(testName,difficulty,ownerEmail,ownerUsername);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping("/my")
    public List<Test> getUserTests() {
        return testService.findloggedUserTests();
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Test getTestById(@PathVariable Integer id) {
        return testService.findById(id);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteTestById(@PathVariable Integer id) {
        testService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @PutMapping(value = "/{id}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateTest(@PathVariable Integer id,@RequestBody Test test) {
        testService.updateTest(id,test);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity saveTest(@RequestBody Test test) {
            testService.saveTest(test);
            return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @GetMapping(value = "/subscribe")
    public List<Test> getSubcribedTests() {
        return testService.findLoggedUserSubscribedTests();
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @PostMapping(value = "/subscribe/{testId}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity subscribeTest(@PathVariable Integer testId,@RequestBody SubscribedTest subscribedTest) {
            testService.subscribeTest(testId, subscribedTest);
            return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @PutMapping(value = "/subscribe/{testId}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateSubscribeTest(@PathVariable Integer testId,@RequestBody SubscribedTest subscribedTest) {
           testService.updateSubscribeTest(testId, subscribedTest);
           return new ResponseEntity<>(HttpStatus.OK);
    }

}
