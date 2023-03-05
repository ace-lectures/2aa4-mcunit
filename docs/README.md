# Building the McUnit Framework

  - Author: Sébastien Mosser
  - Version: 2023.03

## Rationale

this repository contains a step-by-step implemention of an xUnit-like framework to implement and execute unit tests in Java. It is designed incrementally, and is planned to run in ~200 minutes (four lecture slots of 50 minutes each).

  - Lecture #1: **First things firsts**
    - Step #0: [Technical Environment](./0_tech.md)
    - Step #1: [Creating assertions](./1_assertions.md)
  - Lecture #2: **Adding some flesh to the walking skeleton**
    - Step #2: [Test Cases](./2_test_case.md)
    - Step #3: [Test Result Collection](./3_test_results.md)
  - Lecture #3: **Integration with Java**
    - Step #4: [Organizing tests into test suites](./4_test_suites.md)
    - Step #5: [Tests as good old methods](./5_methods.md)
  - Lecture #4: **Dark Arts: Code Introspection**
    - Step #6: [Test Scanner](./6_test_scanner.md)
    - Step #7: [Using Annotations instead of Inheritance](./7_annotations.md)

## How to navigate the repository?

Each step is releases with a tag. You can `git checkout step_x` to a given tag, and then see how the code is looking at the end of `step_x`.