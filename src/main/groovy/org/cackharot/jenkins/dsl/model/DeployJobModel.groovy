package org.cackharot.jenkins.dsl.model

class DeployJobModel extends JobBaseModel {
  List<DeployEnvironment> environments = new ArrayList<>()
}