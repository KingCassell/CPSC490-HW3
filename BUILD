
#======================================================================
# Bare-bones Bazel BUILD file for HW3
# CPSC 490
# Spring, 2022
#======================================================================

load("@rules_java//java:defs.bzl", "java_test")

java_binary(
  name = "hw3",
  srcs = glob(["src/*.java"]),
  main_class = "HW3",
)

java_library(
  name = "cpsc490-lib",
  srcs = glob(["src/*.java"]),
)

#----------------------------------------------------------------------
# TEST SUITES:
#----------------------------------------------------------------------

java_test(
  name = "dfs-test",
  srcs = ["tests/DFSTest.java"], 
  test_class = "DFSTest",
  deps = ["lib/junit-4.13.2.jar", "lib/hamcrest-core-1.3.jar", ":hw3"],
)




