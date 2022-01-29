# pigeon
Pigeon is our powerful, feature rich, frontend test framework for automating FedEx browser application home page.

[![GitHub build Status](https://img.shields.io/github/workflow/status/pramodkumaryadav/pigeon/Java%20CI%20with%20Maven)](https://github.com/PramodKumarYadav/pigeon/actions)
![GitHub contributors](https://img.shields.io/github/contributors/pramodkumaryadav/pigeon)
![GitHub last commit](https://img.shields.io/github/last-commit/pramodkumaryadav/pigeon)

## Toolset
Key tools used in pigeon are:
- **ElasticSearch and Kibana** (for real time test monitoring and reporting)
- **Github actions** (for continuous integration)
- **Docker** (for dockerizing the project and running it in a docker selenium grid)
- **Selenium**  (library for browser automation)
- **Java** (as the core programming language)
- **Maven** (for dependency management)
- **Junit 5** (for assertions)
- **Typesafe** (for application config)
- **Surefire** (for secondary xml reports in CI)
- **Surefire Site plugin** (for secondary html reports in CI)
- **Github** (for version control)
- **Faker library** (for generating random test data for different locales - germany, france, netherlands, english)

### Exceptions
Two exceptions that I took liberty to deviate from the given assignment (and with respective reasons) are:

- **Avoid using Cucumber**:
  - *Reason (efficiency)*: The maximum level of parallel execution that can be achieved in cucumber is at a feature file 
    level. This limits the total execution time to tool choice rather than an infrastructure limitation. By avoiding 
    cucumber, we can get faster build times in CI by upgrading to a powerful infrastructure - something that you cannot 
    do if we use cucumber.
  - *Reason (effectiveness)*: Cucumber providing a sugar syntax which comes at the cost of restricted flexibility w.r.t. 
    how you can make the best use of object-oriented programming and underlying libraries that it uses (such as junit5). 
    We will see how we can achieve a high level of BDD (Behaviour driven development - tests), by using the best 
    object-oriented design practices, without using cucumber. 
- **Keeping this repository private**:
  - To be able to showcase the power of badges, (which otherwise can not be demonstrated for private repos). 
    As a measure to avoid others from using this framework as reference, I will make this assignment private right 
    after the interview is done.

## Key framework features
- [ ] **All tests to be atomic and independent**.
    - Reason: When we run our tests in parallel, we do not want the result of one test affect another one.
      This allows us to finish a lot of tests in a very short time - depending on the test infrastructure we have got.
- [ ] **Run tests in parallel**.
    - Reason: Faster feedback time.
- [ ] **Run tests as a part of new pull requests in CI pipelines**.
    - Reason: We would like to catch regression issues 'before' they are merged in the stable develop branch and not 
      'after'. Running our test framework as a part of CI pipeline, when new pull requests are created, allows tests to 
      run "on time" and help us prevent breaking changes from merging into develop. Goal is, if we can finish our 
      tests faster than a developer can review, we have designed a efficient framework. 
- [ ] **Readable tests**.
    - Reason: It is said that it takes 20% of efforts to create tests and 80% in reviewing/maintaining them. It is thus only 
      reasonable to have highly readable tests with minimum or zero duplication. We achieve this by having a clear 
      separation of concerns between test intentions and test implementation code.
- [ ] **Scalable**. 
  - Reason: With time the application will grow in size and complexity. The framework design should allow for easy
    scalability without affecting existing entitites. We achieve this by using more `composition` than `inheritance` in 
    our design. Also by keeping a clear seperation between code, config, data and test intentions.
- [ ] **Multiple parallel execution modes**.
    - Reason: To scale up or down execution based on the infrastructure availability.
- [ ] **Logging**.
  - Reason: To allow logging at multiple levels (debug, warn, err) for multiple needs (debug, execution)

## Getting started

## To run tests

### From maven 
- To include any specific tests, (say to run only smoke tests in CI), run as:
    - `mvn clean -Dgroups=smokeTest test`
- To exclude any flaky or slow tests, you can run as: 
    - `mvn clean -DexcludedGroups="slow, flaky" test`
