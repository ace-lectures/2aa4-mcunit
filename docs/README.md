# Building the McUnit Framework

  - Author: Sébastien Mosser
  - Version: 2023.03

## Rationale

this repository contains a step-by-step implemention of an xUnit-like framework to implement and execute unit tests in Java. It is designed incrementally, and is planned to run in ~300 minutes (four lecture slots of 50 minutes each).

  - Lecture #1: **First things firsts**
    - Step #0: Technical Environment
    - Step #1: Creating assertions
  - Lecture #2: **Adding some flesh to the walking skeleton**
    - Step #2: Test Cases
    - Step #3: Test Result Collection
  - Lecture #3: **Integration with Java**
    - Step #4: Organizing tests into test suites
    - Step #5: Tests as good old methods
  - Lecture #4: **Dark Arts: Code Introspection**
    - Step #6: Test Scanner
    - Step #7: Using Annotations instead of Inheritance

## How to navigate the repository?

Each step is releases with a tag. You can `git checkout step_x` to a given tag, and then see how the code is looking at the end of `step_x`.