# pigeon
Pigeon is our powerful, feature rich, frontend test framework for automating FedEx browser application home page.

`Note: If you want to see badges in action, then I have to make this repo public! 
It will then look like below snapshot.`

![badges](./images/badges.png)

## Toolset
Key tools used in pigeon are:
- [x] **ElasticSearch and Kibana** (for real time test monitoring and reporting)
- [x] **Github actions** (for continuous integration)
- [ ] **Docker** (for dockerizing the project and running it in a docker selenium grid)
- [x] **Selenium**  (library for browser automation)
- [x] **Java** (as the core programming language)
- [x] **Maven** (for dependency management)
- [x] **Junit 5** (for assertions)
- [x] **Typesafe** (for application config)
- [x] **Surefire** (for secondary xml reports in CI)
- [x] **Surefire Site plugin** (for secondary html reports in CI)
- [x] **Github** (for version control)
- [ ] **Faker library** (for generating random test data for different locales - germany, france, netherlands, english)
- [ ] **Remote execution on selenium docker grid** (although the performance of selenium grid is not good but this 
  serves as a poc for remote execution)

### Exceptions
An exception that I took liberty to deviate from the given assignment was not using **Cucumber**. 
The reasons for not usig cucumber are as below: 
  - **Impact on execution times**: The maximum level of parallel execution that can be achieved in cucumber is at a feature file 
    level. This limits the total execution time to tool choice rather than an infrastructure limitation. By avoiding 
    cucumber, we can get faster build times in CI by upgrading to a powerful infrastructure - something that we cannot 
    do with cucumber, even if we have high infrastructure. Cucumbers feature-files are the slowest moving parts. However,
    with Junit5 the slowest moving part is a test case. 
  - **Impact on maintenance and design**: Cucumber providing a sugar syntax which comes at the cost of restricted flexibility w.r.t. 
    how you can make the best use of Javas object-oriented programming and underlying libraries that it uses (such as junit5). 
    We will see how we can achieve a high level of BDD (Behaviour driven development - tests), by using the best 
    object-oriented design practices, without using cucumber.

## Key framework features
- [x] **All tests to be atomic and independent**.
    - Reason: When we run our tests in parallel, we do not want the result of one test affect another one.
      This allows us to finish a lot of tests in a very short time - depending on the test infrastructure we have got.
- [x] **Run tests in parallel**.
    - Reason: Faster feedback time.
- [x] **Run tests as a part of new pull requests in CI pipelines**.
    - Reason: We would like to catch regression issues 'before' they are merged in the stable develop branch and not 
      'after'. Running our test framework as a part of CI pipeline, when new pull requests are created, allows tests to 
      run "on time" and help us prevent breaking changes from merging into develop. Goal is, if we can finish our 
      tests faster than a developer can review, we have designed a efficient framework. 
- [x] **Readable tests**.
    - Reason: It is said that it takes 20% of efforts to create tests and 80% in reviewing/maintaining them. It is thus only 
      reasonable to have highly readable tests with minimum or zero duplication. We achieve this by having a clear 
      separation of concerns between test intentions and test implementation code.
- [x] **Scalable**. 
  - Reason: With time the application will grow in size and complexity. The framework design should allow for easy
    scalability without affecting existing entitites. We achieve this by using more `composition` than `inheritance` in 
    our design. Also by keeping a clear seperation between code, config, data and test intentions.
- [x] **Multiple parallel execution modes**.
    - Reason: To scale up or down execution based on the infrastructure availability.
- [x] **Logging**.
  - Reason: To allow logging at multiple levels (debug, warn, err) for multiple needs (debug, execution)

## Getting started

## To run tests

### From maven 
- To include any specific tests, (say to run only smoke tests in CI), run as:
    - `mvn clean -Dgroups=smokeTest test`
- To exclude any flaky or slow tests, you can run as: 
    - `mvn clean -DexcludedGroups="slow, flaky" test`

## Monitoring (using Elastic and Kibana)
- Go to choices.conf file and set MONITORING_INFRA_READY_ON_ELASTIC_KIBANA = "true"
- Start Docker Desktop (if on Windows or linux)
- Open a terminal (as admin)
- [Increase the vm map count as explained here](https://www.elastic.co/guide/en/elasticsearch/reference/current/docker.html#_windows_with_docker_desktop_wsl_2_backend)
- CD to this project root `cd D:/pigeon`
- Run `docker compose -f .\docker-compose-monitoring.yml up` to start all elastic and kibana containers. 
- Go to elastic search home page `http://localhost:9200/` 
- You should see something like this.
```
{
  "name" : "es01",
  "cluster_name" : "es-docker-cluster",
  "cluster_uuid" : "M5wKWldOTXC_Qf6Zqx_5Qg",
  "version" : {
    "number" : "7.16.3",
    "build_flavor" : "default",
    "build_type" : "docker",
    "build_hash" : "4e6e4eab2297e949ec994e688dad46290d018022",
    "build_date" : "2022-01-06T23:43:02.825887787Z",
    "build_snapshot" : false,
    "lucene_version" : "8.10.1",
    "minimum_wire_compatibility_version" : "6.8.0",
    "minimum_index_compatibility_version" : "6.0.0-beta1"
  },
  "tagline" : "You Know, for Search"
}
```
- Go to kibana home page `http://localhost:5601/app/home#/`
- You should see Kibana dashboard.
- Run some tests from this project (pigeon) to create a `testrun` index and publish our test runs data on Elastic Search. 
- You can now add the elastic index `testrun` in Kibana and should be able to create real time dashboards.
- A video showing how it looks like in actions is as below. 
- Run `docker compose -f .\docker-compose-monitoring.yml down` to kill all elastic and kibana containers. 
- Go to choices.conf file and set MONITORING_INFRA_READY_ON_ELASTIC_KIBANA = "false" again to avoid failing your tests.
