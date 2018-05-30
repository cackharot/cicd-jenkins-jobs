package org.cackharot.jenkins.dsl.custom

import javaposse.jobdsl.dsl.DslFactory
import javaposse.jobdsl.dsl.Job

interface JobImpl {
  String getName()

  List<Job> build(DslFactory factory)
}